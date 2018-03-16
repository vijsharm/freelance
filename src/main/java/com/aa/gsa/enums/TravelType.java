package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum TravelType {
	DOMESTIC("D"),
	INTERNATIONAL("I");

	private static final Map<String, TravelType> lookup =  new HashMap<String, TravelType>();

	static {
		for (TravelType travelType : TravelType.values()) {
			lookup.put(travelType.value(), travelType);
		}
	}
	
	private String value;

	TravelType(String value) {
		this.value = value;	
	}

	public String value() {
		return value;
	}

	public static TravelType findByValue(String value) {
		for (TravelType travelType : values()) {
			if (travelType.value().equals(value)) {
				return travelType;
			}
		}
		return null;
	}
}
