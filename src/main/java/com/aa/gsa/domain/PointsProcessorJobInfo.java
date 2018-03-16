package com.aa.gsa.domain;

import java.util.Date;
import java.util.Properties;

import org.springframework.batch.core.BatchStatus;

public class PointsProcessorJobInfo {

	private Long jobId;
	
	private BatchStatus batchStatus;

	private Date startTime;
	
	private Properties jobParameters;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public BatchStatus getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(BatchStatus batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Properties getJobParameters() {
		return jobParameters;
	}

	public void setJobParameters(Properties jobParameters) {
		this.jobParameters = jobParameters;
	}	
}
