package com.rd.messages.magnoliaUI;

import javax.inject.Inject;

import info.magnolia.ui.api.message.Message;
import info.magnolia.ui.api.message.MessageType;
import info.magnolia.ui.framework.message.MessagesManager;

public class MgnlMessagesUI implements MessagesUI {

	private final MessagesManager messagesManager;

	@Inject
	public MgnlMessagesUI(final MessagesManager messagesManager) {

		super();
		this.messagesManager = messagesManager;
	}
	
	/* (non-Javadoc)
	 * @see com.rd.messages.magnoliaUI.MessagesUI#sendToCurrentUser(info.magnolia.ui.api.message.MessageType, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendToCurrentUser(MessageType messageType, String subject, String messageText) {

		final Message message = new Message();
		message.setSubject(subject);
		message.setMessage(messageText);
		message.setType(messageType);
		
		this.messagesManager.sendLocalMessage(message);
	}

}
