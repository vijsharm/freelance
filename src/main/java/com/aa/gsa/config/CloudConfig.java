package com.aa.gsa.config;


import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.cloudant.client.api.CloudantClient;


/**
 * 
 * @author 940914
 */
@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

	Log log = LogFactory.getLog(CloudConfig.class);

	@Bean
	public CloudantClient cloudantClient() {
		return connectionFactory().service(CloudantClient.class);
	}

	@Bean
	public DataSource dataSource() {
		return connectionFactory().service(DataSource.class);
	}
	
	@Bean
	@Primary
	public ConnectionFactory rabbitConnectionFactory() {
		return connectionFactory().rabbitConnectionFactory();
	}
}
