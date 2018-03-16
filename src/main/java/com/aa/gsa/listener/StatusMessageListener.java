package com.aa.gsa.listener;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;

import com.aa.gsa.domain.StatusMessage;
import com.aa.gsa.enums.StatusMessageType;

//TODO: Only for test purposes in local. 
public class StatusMessageListener implements MessageListener {

	private Log log = LogFactory.getLog(StatusMessageListener.class);
	
	private int totalItemsProcessed = 0;
	
	@Override
	public void onMessage(Message message) {
		try {
			StatusMessage statusMessage = new ObjectMapper().readValue(message.getBody(), StatusMessage.class);
			if (statusMessage.getMessageType().equals(StatusMessageType.PROGRESS.value())) {
				totalItemsProcessed += statusMessage.getNoOfMarketsProcessedSinceLastUpdate();
				log.info("totalItemsProcessed = "+totalItemsProcessed);
			} else if (statusMessage.getMessageType().equals(StatusMessageType.ERROR.value())) {
				log.info("ERROR MESSAGE = "+statusMessage.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
