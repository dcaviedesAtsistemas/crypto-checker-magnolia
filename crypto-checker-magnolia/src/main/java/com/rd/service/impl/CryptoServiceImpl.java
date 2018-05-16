package com.rd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rd.service.ICryptoService;
import com.rd.wrapper.ApiWrapper;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.repository.RepositoryConstants;

public class CryptoServiceImpl implements ICryptoService {

	@Inject
	private ApiWrapper wrapper;
	
	@Override
	public ResponseBuilder compileErrorResponseBuilder(JSONObject jsonRest, Status errorStatus) {
		ResponseBuilder res = Response.status(errorStatus)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(jsonRest.toString());
		return res;
	}
	
	@Override
	public void updateCryptoCurrencies() throws Exception {

		QueryManager qm = MgnlContext.getJCRSession("crypto").getWorkspace()
				.getQueryManager();
		Session s = MgnlContext.getJCRSession("crypto").getWorkspace().getSession();


		List<String> lista = new ArrayList<>();
		
		lista.add("EUR");
		lista.add("JPY");
		lista.add("USD");
		
		String query = "select * from [mgnl:cryptoNode]";

		Query q = qm.createQuery(query, Query.JCR_SQL2);
		QueryResult qr = q.execute();
		NodeIterator nodes = qr.getNodes();

		while (nodes.hasNext()) {
			Node n = nodes.nextNode();
			updateNode(lista, n);
		}
		
		s.save();
		
	}

	private void updateNode(List<String> lista, Node n) throws ValueFormatException, RepositoryException,
			PathNotFoundException, Exception, VersionException, LockException, ConstraintViolationException {
		
		String symbol = n.getProperty("symbol").getString();
		JSONObject jOPrice = wrapper.doGet(symbol, lista, false);
		JSONObject jOPriceFull = wrapper.doGet(symbol, lista, true);
		JSONObject jDisplay = jOPriceFull.getJSONObject("DISPLAY");
		JSONObject symbolDisplay = jDisplay.getJSONObject(symbol);
		
		String eurConv = jOPrice.get("EUR").toString();
		String usdConv = jOPrice.get("USD").toString();
		String jpyConv = jOPrice.get("JPY").toString();
		
		String highDayUSD = symbolDisplay.getJSONObject("USD").getString("HIGHDAY").substring(getFirstNumberIndex(symbolDisplay.getJSONObject("USD").getString("HIGHDAY")));
		String lowDayUSD = symbolDisplay.getJSONObject("USD").getString("LOWDAY").substring(getFirstNumberIndex(symbolDisplay.getJSONObject("USD").getString("LOWDAY")));
		String highDayEUR = symbolDisplay.getJSONObject("EUR").getString("HIGHDAY").substring(getFirstNumberIndex(symbolDisplay.getJSONObject("EUR").getString("HIGHDAY")));
		String lowDayEUR = symbolDisplay.getJSONObject("EUR").getString("LOWDAY").substring(getFirstNumberIndex(symbolDisplay.getJSONObject("EUR").getString("LOWDAY")));
		String totalVolume24H = symbolDisplay.getJSONObject("EUR").getString("TOTALVOLUME24H").substring(getFirstNumberIndex(symbolDisplay.getJSONObject("EUR").getString("TOTALVOLUME24H")));
		
		this.addNodeInCircularArray(eurConv,n);
		
		n.setProperty("eurValue", eurConv);
		n.setProperty("usdValue", usdConv);
		n.setProperty("jpyValue", jpyConv);
		n.setProperty("highDayUSD", highDayUSD);
		n.setProperty("lowDayUSD", lowDayUSD);
		n.setProperty("highDayEUR", highDayEUR);
		n.setProperty("lowDayEUR", lowDayEUR);
		n.setProperty("totalVolume24H", totalVolume24H);
		
		n.getSession().save();
		
	}
	
	private void addNodeInCircularArray(String value, Node n) throws RepositoryException {
		
		if(n.hasProperty("values")) {
			String listInString = n.getProperty("values").getString();
			String newList = processJson(listInString,value);
			n.setProperty("values", newList);
			n.getSession().save();
		}
		else {
			n.setProperty("values", "[]");
			n.getSession().save();
		}
			
	}

	private String processJson(String listInString, String value) {

		JSONArray jArray = new JSONArray(listInString);
		if(jArray.length() < 120) {
			jArray.put(Double.parseDouble(value));
		}
		else {
			jArray.remove(0);
			jArray.put(Double.parseDouble(value));
		}
		
		return jArray.toString();
	}
	
	private Integer getFirstNumberIndex(String str) {
		
		Integer index = -1;
		
		for(int i=0;i<str.length();i++) {
			if(Character.isDigit(str.charAt(i)))
				return i;
		}
		
		return index;
		
	}

	@Override
	public boolean addEmail(String email) throws RepositoryException {
		
		Session session = MgnlContext.getJCRSession(RepositoryConstants.CONFIG).getWorkspace().getSession();
		String commandPath = "/modules/crypto-checker-magnolia/commands/catalogApi/sendCryptocurrenciesInfoMailCommand";
		Node nodeCommand = SessionUtil.getNode(session, commandPath);
		if(nodeCommand != null && nodeCommand.isNode()) {
			String propertyOld = nodeCommand.getProperty("to").getString();
			nodeCommand.setProperty("to", propertyOld + " ; " + email);
			nodeCommand.getSession().save();
		}
		
		return true;
	}
	
}
