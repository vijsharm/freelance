package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	INBOUND("I"),
	OUTBOUND("O");
	
	private String value;

	Direction(String value) {
		this.value = value;
	}

	private static final Map<String, Direction> lookup =  new HashMap<String, Direction>();

	static {
		for (Direction direction : Direction.values()) {
			lookup.put(direction.value(), direction);
		}
	}
	
	public String value() {
		return value;
	}
}
