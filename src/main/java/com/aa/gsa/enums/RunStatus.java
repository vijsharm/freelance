package com.aa.gsa.enums;

public enum RunStatus {
	SUBMITTED("Submitted"),
	RUNNING("Running"),
	FAILED("Failed"),
	COMPLETED("Completed");
	
	private final String status;

	private RunStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
