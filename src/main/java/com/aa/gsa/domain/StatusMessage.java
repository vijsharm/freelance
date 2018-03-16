package com.aa.gsa.domain;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.aa.gsa.enums.StatusMessageType;

/**
 * 
 * Run Progress domain
 * @author 940914
 * 
 */
public class StatusMessage {

	private Long runId;
	
	private String runName;
	
	private Integer noOfMarketsProcessedSinceLastUpdate;

	private Integer totalNoOfMarkets;
	
	private String timestamp;
	
	private String message;
	
	private String messageType;
	
	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Integer getNoOfMarketsProcessedSinceLastUpdate() {
		return noOfMarketsProcessedSinceLastUpdate;
	}

	public void setNoOfMarketsProcessedSinceLastUpdate(Integer noOfMarketsProcessedSinceLastUpdate) {
		this.noOfMarketsProcessedSinceLastUpdate = noOfMarketsProcessedSinceLastUpdate;
	}

	public Integer getTotalNoOfMarkets() {
		return totalNoOfMarkets;
	}

	public void setTotalNoOfMarkets(Integer totalNoOfMarkets) {
		this.totalNoOfMarkets = totalNoOfMarkets;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
		
	public static StatusMessage progressMsg(Integer noOfMarketsProcessedSinceLastUpdate, Integer totalNoOfMarkets, Run run) {
		StatusMessage statusMessage = new StatusMessage();
		
		if (run.getRunId() != null && !StringUtils.isEmpty(run.getRunId())) {
			statusMessage.setRunId(Long.valueOf(run.getRunId()));
		}
		statusMessage.setRunName(run.getName());
		setProgressMsg(noOfMarketsProcessedSinceLastUpdate, totalNoOfMarkets, statusMessage);
		return statusMessage;
	}

	public static StatusMessage progressMsg(Integer noOfMarketsProcessedSinceLastUpdate, Integer totalNoOfMarkets, Long runId, String runName) {
		StatusMessage statusMessage = new StatusMessage();
		statusMessage.setRunId(runId);
		statusMessage.setRunName(runName);
		setProgressMsg(noOfMarketsProcessedSinceLastUpdate, totalNoOfMarkets, statusMessage);
		return statusMessage;
	}
	
	public static StatusMessage textMsg(String message, StatusMessageType messageType, Run run) {
		StatusMessage statusMessage = new StatusMessage();
		if (run.getRunId() != null && !StringUtils.isEmpty(run.getRunId())) {
			statusMessage.setRunId(Long.valueOf(run.getRunId()));
		}
		statusMessage.setRunName(run.getName());
		setTextMsg(message, messageType, statusMessage);
		return statusMessage;
	}
	
	public static StatusMessage infoMsg(String message, Run run) {
		return textMsg(message, StatusMessageType.INFO, run);
	}
	
	public static StatusMessage errorMsg(String message, Run run) {
		return textMsg(message, StatusMessageType.ERROR, run);
	}
	
	public static StatusMessage textMsg(String message, StatusMessageType messageType) {
		StatusMessage statusMessage = new StatusMessage();
		setTextMsg(message, messageType, statusMessage);
		return statusMessage;
	}
	
	private static void setProgressMsg(Integer noOfMarketsProcessedSinceLastUpdate, Integer totalNoOfMarkets, StatusMessage statusMessage) {
		statusMessage.setNoOfMarketsProcessedSinceLastUpdate(noOfMarketsProcessedSinceLastUpdate);
		statusMessage.setTotalNoOfMarkets(totalNoOfMarkets);
		statusMessage.setTimestamp(LocalDateTime.now().toString());
		statusMessage.setMessageType(StatusMessageType.PROGRESS.value());
	}
	
	private static void setTextMsg(String message, StatusMessageType messageType, StatusMessage statusMessage) {
		statusMessage.setMessage(message);
		statusMessage.setTimestamp(LocalDateTime.now().toString());
		statusMessage.setMessageType(messageType.value());
	}
}
