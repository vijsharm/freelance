package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 940914
 * 
 */
public enum Group {
	Group1(1, "Group1"),
	Group2(2, "Group2"),
	Group3(3, "Group3"),
	Group4(4, "Group4"),
	Group5(5, "Group5"),
	Group6(6, "Group6");
	
	private Integer groupNumber;
	
	private String groupName;
	
	private static final Map<Integer, Group> groupNumberLookup = new HashMap<Integer, Group>();

	static {
		for (Group group : Group.values()) {
			groupNumberLookup.put(group.getGroupNumber(), group);
		}
	}

	Group(int groupNumber, String groupName) {
		this.groupNumber = groupNumber;
		this.groupName = groupName;
	}

	public int getGroupNumber() {
		return groupNumber;
	}

	public String getGroupName() {
		return groupName;
	}

	public static Group findByGroupNumber(Integer groupNumber) {
		return groupNumberLookup.get(groupNumber);
	}
}