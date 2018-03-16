package com.aa.gsa.enums;

public enum StatusMessageType {
	INFO("Info"),
	WARNING("Warning"),
	ERROR("Error"),
	PROGRESS("Progress");
	
	String value;
	
	StatusMessageType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
