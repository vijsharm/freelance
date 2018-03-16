package com.aa.gsa.service;

import com.aa.gsa.enums.StatusMessageType;

public interface MessageProcessor {

	
	void sendMessage(String message, StatusMessageType statusMessageType);

	
	
}
