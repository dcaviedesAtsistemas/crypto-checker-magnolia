package com.rd.setup;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleModuleResource;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.FilterOrderingTask;
import info.magnolia.module.delta.Task;

/**
 * This class is optional and lets you manage the versions of your module, by
 * registering "deltas" to maintain the module's configuration, or other type of
 * content. If you don't need this, simply remove the reference to this class in
 * the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class CryptoCheckerMagnoliaVersionHandler extends DefaultModuleVersionHandler {

	public CryptoCheckerMagnoliaVersionHandler() {
		register(DeltaBuilder.update("1.0", "")
		.addTask(new BootstrapSingleModuleResource("CryptoCheckerMagnolia", "Setting up commands", "config.modules.crypto-checker-magnolia.commands.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("CryptoCheckerMagnolia", "Setting up filters", "config.server.filters.addCORSHeaders.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("CryptoCheckerMagnolia", "Setting up rest endpoints", "config.modules.crypto-checker-magnolia.rest-endpoints.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))				
		.addTask(new BootstrapSingleModuleResource("CryptoCheckerMagnolia", "Setting up scheduler", "config.modules.scheduler.config.jobs.getCryptoInformationApi.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Mail config.", "SMTP Mail configuration", "config.modules.mail.config.smtpConfiguration.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Password for mail", "Magnolia password for gmail account", "keystore.rd-magnolia-atsistemas.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Crypto info. mail template", "Mail template configuration for cryptocurrencies information", "config.modules.mail.config.templatesConfiguration.cryptocurrenciesInfo.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Anonymous role", "Anonymous role configured to get necessary permissions", "userroles.anonymous.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Cryptocurrencies sample assets", "Assets for sample cryptocurrencies", "dam.cryptocurrencies.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Sample cryptocurrencies", "Cryptocurrencies for samples", "crypto.cryptocurrencies.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		.addTask(new BootstrapSingleModuleResource("Admincentral app group", "App group for custom applications", "config.modules.ui-admincentral.config.appLauncherLayout.groups.rd-magnolia-2018.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		);
	}
	
    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        List<Task> extraInstallTasks = new ArrayList<Task>(super.getExtraInstallTasks(installContext));
        extraInstallTasks.add(new FilterOrderingTask("addCORSHeaders", new String[]{"uriSecurity"}));
        return extraInstallTasks;
    }

}
