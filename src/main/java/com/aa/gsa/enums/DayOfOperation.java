package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 940914
 */
public enum DayOfOperation {
	MONDAY('1'),
	TUESDAY('2'),
	WEDNESDAY('3'),
	THURSDAY('4'),
	FRIDAY('5'),
	SATURDAY('6'),
	SUNDAY('7');
	
	char day;
	
	DayOfOperation(char day) {
		this.day = day;
	}
	
	private static final Map<Character, DayOfOperation> lookup =  new HashMap<Character, DayOfOperation>();

	static {
		for (DayOfOperation dayOfOperation : DayOfOperation.values()) {
			lookup.put(dayOfOperation.day(), dayOfOperation);
		}
	}

	public char day() {
		return day;
	}
	
	public static DayOfOperation findByValue(char day) {
		return lookup.get(day);
	}
}
