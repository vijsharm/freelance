package com.aa.gsa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cpp-run-compare")
public class RunCompareProperties {

	private String databaseName;
	
	private String statusDatabaseName;
	
	private int readSize;
	
	private int writeSize;
	
	private int corePoolSize;
	
	private int maxPoolSize;
	
	private int queueCapacity;
	
	private Messaging messaging;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getStatusDatabaseName() {
		return statusDatabaseName;
	}

	public void setStatusDatabaseName(String statusDatabaseName) {
		this.statusDatabaseName = statusDatabaseName;
	}

	public int getReadSize() {
		return readSize;
	}

	public void setReadSize(int readSize) {
		this.readSize = readSize;
	}

	public int getWriteSize() {
		return writeSize;
	}

	public void setWriteSize(int writeSize) {
		this.writeSize = writeSize;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public Messaging getMessaging() {
		return messaging;
	}

	public void setMessaging(Messaging messaging) {
		this.messaging = messaging;
	}

	public static class Messaging {
		
		private String exchangeName;
		
		private String submitQueue;
		
		private String statusQueue;

		private String submitRoutingKey;
		
		private String statusRoutingKey;

		public String getExchangeName() {
			return exchangeName;
		}

		public void setExchangeName(String exchangeName) {
			this.exchangeName = exchangeName;
		}

		public String getSubmitQueue() {
			return submitQueue;
		}

		public void setSubmitQueue(String submitQueue) {
			this.submitQueue = submitQueue;
		}

		public String getStatusQueue() {
			return statusQueue;
		}

		public void setStatusQueue(String statusQueue) {
			this.statusQueue = statusQueue;
		}

		public String getSubmitRoutingKey() {
			return submitRoutingKey;
		}

		public void setSubmitRoutingKey(String submitRoutingKey) {
			this.submitRoutingKey = submitRoutingKey;
		}

		public String getStatusRoutingKey() {
			return statusRoutingKey;
		}

		public void setStatusRoutingKey(String statusRoutingKey) {
			this.statusRoutingKey = statusRoutingKey;
		}
	}
}
