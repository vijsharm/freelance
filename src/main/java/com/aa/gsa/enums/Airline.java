package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum Airline {
	AA("American Airlines"),
	DL("Delta Airlines"),
	UA("United Airlines"),
	WN("Southwest Airlines"),
	AS("Alaska Airlines"),
	B6("Jet Blue"),
	HA("Hawaiian Airlines"),
	F9("Frontier Airlines"),
	VX("Virgin America");
	
	private String airLineName;
	
	private static final Map<String, Airline> lookup =  new HashMap<String, Airline>();

	static {
		for (Airline airline : Airline.values()) {
			lookup.put(airline.name(), airline);
		}
	}
	
	Airline(String airLineName) {
		this.airLineName = airLineName;
	}

	public String airLineName() {
		return airLineName;
	}
	
	public static boolean contains(String airlineCode) {
	    return lookup.containsKey(airlineCode);
	}
}
