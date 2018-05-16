package com.rd.commands;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.service.ICryptoService;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.objectfactory.Components;

public class GetCryptoApiInformationCmd extends BaseRepositoryCommand {

	private static final Logger log = LoggerFactory.getLogger(GetCryptoApiInformationCmd.class);
	
	@Inject
	private ICryptoService cryptoService;
	
	@Override
	public boolean execute(Context arg0) throws Exception {
		
		boolean isAdmin = Components.getComponent(ServerConfiguration.class).isAdmin();
		
		try{
			
			if(isAdmin) {
				cryptoService.updateCryptoCurrencies();
			}
			
		} catch (Exception e) {
			log.debug("Error updating cryptocurrencies: " + e.getMessage()); 
		}
		
		return true;
	}
	
}
