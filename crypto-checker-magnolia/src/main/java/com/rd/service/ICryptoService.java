package com.rd.service;

import javax.jcr.LoginException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.google.inject.ImplementedBy;
import com.rd.service.impl.CryptoServiceImpl;

@ImplementedBy(CryptoServiceImpl.class)
public interface ICryptoService {

	public ResponseBuilder compileErrorResponseBuilder(JSONObject jsonRest, Status errorStatus);
	
	public void updateCryptoCurrencies() throws ValueFormatException, PathNotFoundException, RepositoryException, Exception;
	
	public boolean addEmail(String email) throws LoginException, RepositoryException;
	
}
