package com.aa.gsa.service;

import java.util.Map;

import com.aa.gsa.enums.EquipmentType;

/**
 * Equipment Codes 
 */
public interface EquipmentReader {

	Map<String, EquipmentType> getEquipmentCodes();
	
}
