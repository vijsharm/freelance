package com.aa.gsa.listener;

import static com.aa.gsa.enums.RunCompareStatus.COMPLETED;
import static com.aa.gsa.enums.RunCompareStatus.FAILED;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.aa.gsa.domain.RunCompareMsg;
import com.aa.gsa.enums.RunCompareStatus;
import com.aa.gsa.exception.RunCompareException;
import com.aa.gsa.exception.RunCompareExistsException;
import com.aa.gsa.exception.InvalidRunCompareException;
import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.service.RunCompareService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 940914
 * 
 */
public class RunCompareListener implements MessageListener {
	
	private Log log = LogFactory.getLog(RunCompareListener.class);

	private RabbitTemplate rabbitTemplate; 

	private RunCompareService runCompareService;

	private RunCompareProperties runCompareProps;

	private ThreadPoolTaskExecutor taskExecutor;

	private ObjectMapper objectMapper;

	public RunCompareListener(RabbitTemplate rabbitTemplate, RunCompareService runCompareService , RunCompareProperties runCompareProps) {
		this.rabbitTemplate = rabbitTemplate;
		this.runCompareService = runCompareService;
		this.runCompareProps = runCompareProps;
		this.objectMapper = new ObjectMapper();
		initTaskScheduler();
	}

	@Override
	public void onMessage(Message message) {
		RunCompareMsg runCompareMsg = parseMessage(message);
		taskExecutor.submit(() -> 
		{
			try {
				runCompareService.compare(runCompareMsg);
				sendMessage(runCompareMsg, COMPLETED);
			} catch(Exception ex) {
				log.error("Error during compare with runIds = ["+runCompareMsg.getRunId1()+","+runCompareMsg.getRunId2()+"]", ex);
				runCompareMsg.setErrorMsg(ex.getMessage());
				sendMessage(runCompareMsg, FAILED);
				if (!(ex instanceof InvalidRunCompareException || ex instanceof RunCompareExistsException)) {
					runCompareService.setStatus(runCompareMsg, FAILED);
				}
			}
		});
	}
	
	private void sendMessage(RunCompareMsg runCompareMsg, RunCompareStatus status) {
		runCompareMsg.setStatus(status);
		rabbitTemplate.convertAndSend(runCompareProps.getMessaging().getExchangeName(), runCompareProps.getMessaging().getStatusRoutingKey(), runCompareMsg);
	}

	private void initTaskScheduler() {
		taskExecutor = new ThreadPoolTaskExecutor(); 
		taskExecutor.setCorePoolSize(runCompareProps.getCorePoolSize());
		taskExecutor.setMaxPoolSize(runCompareProps.getMaxPoolSize());
		taskExecutor.setQueueCapacity(runCompareProps.getQueueCapacity());
		taskExecutor.initialize();
	}

	private RunCompareMsg parseMessage(Message message) {
		try {
			return objectMapper.readValue(message.getBody(), RunCompareMsg.class);
		} catch (IOException ex) {
			throw new RunCompareException("Error parsing the CPPCompareRequest body "+new String(message.getBody()), ex);
		}
	}
}
