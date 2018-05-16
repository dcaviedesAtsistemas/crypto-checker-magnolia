package com.rd.messages.magnoliaUI;

import info.magnolia.ui.api.message.MessageType;

public interface MessagesUI {

	/**
	 * Sends the message to current UI user
	 * 
	 * @param messageType
	 * @param subject
	 * @param messageText
	 */
	void sendToCurrentUser(MessageType messageType, String subject, String messageText);
	
}
