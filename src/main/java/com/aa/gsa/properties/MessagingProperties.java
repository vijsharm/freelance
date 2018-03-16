package com.aa.gsa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging")
public class MessagingProperties {

	private String runExchange;
	
	private String runQueue;

	private String runRoutingKey;
	
	private String statusExchange;
	
	private String runStatusUpdateExchange;
	
	private String statusQueue;

	private String statusRoutingKey;
	
	private String logExchange;

	public String getRunExchange() {
		return runExchange;
	}

	public void setRunExchange(String runExchange) {
		this.runExchange = runExchange;
	}

	public String getRunQueue() {
		return runQueue;
	}

	public void setRunQueue(String runQueue) {
		this.runQueue = runQueue;
	}

	public String getRunRoutingKey() {
		return runRoutingKey;
	}

	public void setRunRoutingKey(String runRoutingKey) {
		this.runRoutingKey = runRoutingKey;
	}

	public String getStatusExchange() {
		return statusExchange;
	}

	public void setStatusExchange(String statusExchange) {
		this.statusExchange = statusExchange;
	}

	public String getStatusQueue() {
		return statusQueue;
	}

	public void setStatusQueue(String statusQueue) {
		this.statusQueue = statusQueue;
	}

	public String getStatusRoutingKey() {
		return statusRoutingKey;
	}

	public void setStatusRoutingKey(String statusRoutingKey) {
		this.statusRoutingKey = statusRoutingKey;
	}

	public String getLogExchange() {
		return logExchange;
	}

	public void setLogExchange(String logExchange) {
		this.logExchange = logExchange;
	}

	public String getRunStatusUpdateExchange() {
		return runStatusUpdateExchange;
	}

	public void setRunStatusUpdateExchange(String runStatusUpdateExchange) {
		this.runStatusUpdateExchange = runStatusUpdateExchange;
	}
	
	
	
}
