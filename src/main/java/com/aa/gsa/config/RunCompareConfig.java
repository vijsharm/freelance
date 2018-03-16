package com.aa.gsa.config;

import java.io.IOException;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.aa.gsa.listener.RunCompareListener;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.service.RunCompareService;
import com.aa.gsa.service.impl.RunCompareServiceImpl;
import com.cloudant.client.api.CloudantClient;

/**
 * 
 * CPP Compare Configuration 
 * @author 940914
 * 
 */
@Configuration
@Import({ RabbitConfig.class })
public class RunCompareConfig {
	
	@Autowired
	private RabbitConfig rabbitConfig;
	
	@Autowired
	private RunCompareProperties runCompareProps;
	
	@Autowired
	private BatchJobProperties batchJobProperties;
	
	@Autowired
	private CloudantClient cloudantClient;
		
	@Bean
	public RunCompareService runCompareService() {
		return new RunCompareServiceImpl(cloudantClient, runCompareProps, batchJobProperties);
	}
	
	@Bean
	public SimpleMessageListenerContainer runCompareListener(ConnectionFactory connectionFactory) throws AmqpException, IOException {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(rabbitConfig.cppCompareSubmitQueue());
		container.setMessageConverter(rabbitConfig.jackson2MessageConverter());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setDefaultRequeueRejected(false);
		container.setMessageListener(new RunCompareListener(rabbitConfig.template(), runCompareService(), runCompareProps));
		return container;
	}	
}
