package com.rd.commands;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.rd.messages.magnoliaUI.MessagesUI;

import info.magnolia.commands.CommandsManager;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.module.mail.MailTemplate;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.message.MessageType;

public class SendCryptocurrenciesInfoMailCommand extends BaseRepositoryCommand {
    
    /** The Constant TEMPLATE_PARAMETER_NAME. */
    private static final String TEMPLATE_PARAM_NAME = "mailTemplate";
    
    /** The Constant DATA_PARAMETER_NAME. */
    private static final String DATA_PARAM_NAME = "emailData";
    
    private static final String MAIL_PARAMETER_SEPARATOR = "\r\n";
    
    private static final String ACTION_FAILED_SUBJECT_I18N_KEY = "crypto-checker-magnolia.templates.email.cryptocurrenciesInfo.actionFailedSubject";
    private static final String ACTION_FAILED_BODY_MESSAGE_I18N_KEY = "crypto-checker-magnolia.templates.email.cryptocurrenciesInfo.actionFailedMessage";
    private static final String ACTION_COMPLETED_SUBJECT_I18N_KEY = "crypto-checker-magnolia.templates.email.cryptocurrenciesInfo.actionCompletedSubject";
    private static final String ACTION_COMPLETED_BODY_MESSAGE_I18N_KEY = "crypto-checker-magnolia.templates.email.cryptocurrenciesInfo.actionCompletedMessage";
	
	private final CommandsManager commandsManager;
	
    /** The mail command catalog. */
    private String mailCommandCatalog;
  
    /** The mail command. */
    private String mailCommand;
    
    private final SimpleTranslator i18n;
    
    private final MessagesUI messagesUI;
    
    /** The mail template. */
    private String mailTemplate;
    
    private String subject;
    
    private String from;
    
    private String to;
	
	@Inject
	public SendCryptocurrenciesInfoMailCommand(final CommandsManager commandsManager, final SimpleTranslator i18n, final MessagesUI messagesUI) {
		this.commandsManager = commandsManager;
		this.i18n = i18n;
		this.messagesUI = messagesUI;
	}

	@Override
	public boolean execute(Context context) throws Exception {

		Map<String, Object> mailCommandParameters = new TreeMap<>();
		
		this.addTemplateNodeConfigName(mailCommandParameters);
        
		this.addTemplateData(mailCommandParameters);
		
		this.addEMailParams(mailCommandParameters);
		
		this.commandCall(mailCommandParameters);
		
		this.notifySuccessResult();

		return true;
	}
	
	/**
	 * @param mailCommandParameters
	 * @return mail command parameters with node name added that defines mail template in /modules/mail/config/templatesConfiguration
	 */
	private Map<String, Object> addTemplateNodeConfigName(Map<String, Object> mailCommandParameters) {
		
		mailCommandParameters.put(TEMPLATE_PARAM_NAME, this.mailTemplate);
		
		return mailCommandParameters;
	}
	
	/**
	 * @param mailCommandParameters
	 * @return mail command parameters with Map that represents data to be inserted into template script
	 * @throws RepositoryException 
	 * @throws LoginException 
	 */
	private Map<String, Object> addTemplateData(Map<String, Object> mailCommandParameters) throws LoginException, RepositoryException {
        
		    Map<String, Object> dataParameters = new TreeMap<>();
		    dataParameters = this.populateDataParameters();
		    dataParameters.put("fecha", new Date());
        mailCommandParameters.put(DATA_PARAM_NAME, dataParameters);
        
        return mailCommandParameters;
	}
	
	/**
	 * @return map with data to send
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	private Map<String, Object> populateDataParameters() throws RepositoryException {
		TreeMap<String, Object> dataParameters = new TreeMap<>();
		TreeMap<String, Object> nodesMap = new TreeMap<>();
		
		QueryManager qm = MgnlContext.getJCRSession("crypto").getWorkspace().getQueryManager();

		String query = "select * from [mgnl:cryptoNode]";

		Query q = qm.createQuery(query, Query.JCR_SQL2);
		QueryResult qr = q.execute();
		NodeIterator nodes = qr.getNodes();

		while (nodes.hasNext()) {
			Node n = nodes.nextNode();
			Map<String, Object> nodeParam = new TreeMap<>();
			nodeParam.put("1", n.getProperty("eurValue").getString());
			nodeParam.put("2", n.getProperty("usdValue").getString());
			nodeParam.put("3", n.getProperty("jpyValue").getString());
			nodeParam.put("4", n.getProperty("highDayUSD").getString());
			nodeParam.put("5", n.getProperty("lowDayUSD").getString());
			nodeParam.put("6", n.getProperty("highDayEUR").getString());
			nodeParam.put("7", n.getProperty("lowDayEUR").getString());
			nodeParam.put("8", n.getProperty("totalVolume24H").getString());
			nodeParam.put("9", calculateAverage(n.getProperty("values").getString()));
			nodesMap.put(n.getName(),nodeParam);
		}
		dataParameters.put("nodes", nodesMap);
		
		return dataParameters;
	}

	/**
	 * @param string
	 * @return the average of cryptocurrency value
	 */
	private Object calculateAverage(String string) {
		JSONArray jArray = new JSONArray(string);
		int avg = 0;
		
		for(int i=0;i<jArray.length();i++) {
			double val = jArray.getDouble(i);
			avg += val;
		}
		
		return avg/jArray.length();
	}

	/**
	 * @param mailCommandParameters
	 * @return mail command parameters with mail parameters (to, from, etc) added
	 */
	private Map<String, Object> addEMailParams(Map<String, Object> mailCommandParameters) {
		
        // Mail parameters (to, from, etc) defined in a string with format name=value\r\nname=value
	        StringBuilder sbParameters = new StringBuilder();
	        sbParameters.append(MailTemplate.MAIL_TO + "=" + this.to);
	        sbParameters.append(MAIL_PARAMETER_SEPARATOR);
	        sbParameters.append(MailTemplate.MAIL_FROM + "=" + this.from);
	        sbParameters.append(MAIL_PARAMETER_SEPARATOR);
	        sbParameters.append(MailTemplate.MAIL_SUBJECT + "=" + this.i18n.translate(this.subject));
	    mailCommandParameters.put(MailTemplate.MAIL_PARAMETERS, sbParameters.toString());
		
	    return mailCommandParameters;
	}
	
	/**
	 * @param mailCommandParameters
	 * @throws ActionExecutionException 
	 */
	private void commandCall(Map<String, Object> mailCommandParameters) throws ActionExecutionException {
        
        // Command call
        String commandExecutionFailedSubject = this.i18n.translate(ACTION_FAILED_SUBJECT_I18N_KEY);
        String commandExecutionFailedMessage = this.i18n.translate(ACTION_FAILED_BODY_MESSAGE_I18N_KEY, StringUtils.EMPTY);
		try {
			
			// Method executeCommand returns an inverse logic result: true if failed...
			if (this.commandsManager.executeCommand(this.mailCommandCatalog, this.mailCommand, mailCommandParameters)) {
				this.notifyActionExecutionErrorResult(commandExecutionFailedSubject, commandExecutionFailedMessage);
			}
			
		} catch (Exception e) {
			
			this.notifyActionExecutionErrorResult(commandExecutionFailedSubject, commandExecutionFailedMessage);
		}
	}
	
	/**
	 * Notify success result to current Magnolia user
	 */
	private void notifySuccessResult() {
		
		this.messagesUI.sendToCurrentUser(MessageType.INFO, this.i18n.translate(ACTION_COMPLETED_SUBJECT_I18N_KEY), this.i18n.translate(ACTION_COMPLETED_BODY_MESSAGE_I18N_KEY));
	}
	
	/**
	 * Notify action execution error result to current Magnolia user
	 * 
	 * @param subject
	 * @param message
	 * @throws ActionExecutionException 
	 */
	private void notifyActionExecutionErrorResult(String subject, String message) throws ActionExecutionException {
		
		this.messagesUI.sendToCurrentUser(MessageType.ERROR, subject, message);
		
		throw new ActionExecutionException(message);
	}

	public String getMailCommandCatalog() {
		return mailCommandCatalog;
	}

	public void setMailCommandCatalog(String mailCommandCatalog) {
		this.mailCommandCatalog = mailCommandCatalog;
	}

	public String getMailCommand() {
		return mailCommand;
	}

	public void setMailCommand(String mailCommand) {
		this.mailCommand = mailCommand;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
}
