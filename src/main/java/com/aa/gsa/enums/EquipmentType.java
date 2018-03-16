package com.aa.gsa.enums;

import java.util.HashMap;
import java.util.Map;

public enum EquipmentType {
	TURBO("T"),
	JET("J");
	
	private static final Map<String, EquipmentType> lookup =  new HashMap<String, EquipmentType>();

	static {
		for (EquipmentType equipmentType : EquipmentType.values()) {
			lookup.put(equipmentType.type(), equipmentType);
		}
	}
	
	private String type;
	
	EquipmentType(String type) {
		this.type = type;
	}
	
	public String type() {
		return type;
	}
	
	public static EquipmentType findByValue(String value){
		return lookup.get(value);
	}
}
