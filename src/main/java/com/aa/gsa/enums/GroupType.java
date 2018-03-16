package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum GroupType {
	GROUP_1_DOMESTIC("Group 1 Domestic"),
	GROUP_1_INTERNATIONAL("Group 1 International"),
	GROUP_1_DOMESTIC_EC("Group 1 Dom EC"),
	GROUP_1_INTERNATIONAL_EC("Group 1 Intl EC"),
	GROUP_1_INTERNATIONAL_EC_WITH_BUSINESS_CLASS("G 1 Intl EC wBussiness Class"),
	GROUP_1_5TH_FREEDOM("G 1 5th Freedom"),
	GROUP_1_HAVANA("G 1 Havana"),
	GROUP_2_DOMESTIC("Group 2 Domestic"),
	GROUP_2_INTERNATIONAL("Group 2 International"),
	GROUP_3_DOMESTIC("G3 DOM"),
	GROUP_3_INTERNATIONAL("G3 INTL");
	

	private static final Map<String, GroupType> lookup =  new HashMap<String, GroupType>();

	static {
		for (GroupType groupType : GroupType.values()) {
			lookup.put(groupType.value(), groupType);
		}
	}

	private String value;

	GroupType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static GroupType findByValue(String value) {
		for (GroupType groupType : values()) {
			if (groupType.value().equals(value)) {
				return groupType;
			}
		}
		return null;
	}
}
