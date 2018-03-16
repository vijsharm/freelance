package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum ServiceLevel {
	NON_STOP("N"),
	DIRECT("D"),
	CONNECTION("C");
		
	private static final Map<String, ServiceLevel> lookup =  new HashMap<String, ServiceLevel>();

	static {
		for (ServiceLevel serviceLevel : ServiceLevel.values()) {
			lookup.put(serviceLevel.value(), serviceLevel);
		}
	}

	private String value;

	ServiceLevel(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static ServiceLevel findByValue(String value){
		return lookup.get(value);
	}
}
