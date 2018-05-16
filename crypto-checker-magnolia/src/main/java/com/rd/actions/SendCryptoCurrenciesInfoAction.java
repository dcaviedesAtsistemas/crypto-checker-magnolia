package com.rd.actions;

import java.util.Arrays;
import java.util.List;

import info.magnolia.commands.CommandsManager;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.framework.action.AbstractCommandAction;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;

public class SendCryptoCurrenciesInfoAction<D extends SendCryptoCurrenciesInfoActionDefinition> extends AbstractCommandAction<D> {

	public SendCryptoCurrenciesInfoAction(D definition, JcrItemAdapter item, CommandsManager commandsManager,
			UiContext uiContext, SimpleTranslator i18n) {
		this(definition, Arrays.asList(item), commandsManager, uiContext, i18n);
	}
	
	public SendCryptoCurrenciesInfoAction(D definition, List<JcrItemAdapter> items, CommandsManager commandsManager,
			UiContext uiContext, SimpleTranslator i18n) {
		super(definition, items, commandsManager, uiContext, i18n);
	}

}
