package com.aa.gsa.service.impl;

import static com.aa.gsa.enums.EquipmentType.JET;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.EquipmentType;
import com.aa.gsa.service.EquipmentReader;
import com.aa.gsa.service.EquipmentService;

public class EquipmentServiceImpl implements EquipmentService {
	
	private Map<String, EquipmentType> equipmentMap;

	@Autowired
	public EquipmentServiceImpl(EquipmentReader equipmentReader) {
		this.equipmentMap = equipmentReader.getEquipmentCodes();
	}
	
	@Override
	public boolean isJet(Schedule schedule) {
		Set<Boolean> bools = new HashSet<>(6);
		
		bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode1())));

		if (schedule.getEquipCode2() != null) {
			bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode2())));
		}

		if (schedule.getEquipCode3() != null) {
			bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode3())));
		}

		if (schedule.getEquipCode4() != null) {
			bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode4())));
		}

		if (schedule.getEquipCode5() != null) {
			bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode5())));
		}

		if (schedule.getEquipCode6() != null) {
			bools.add(JET.equals(equipmentMap.get(schedule.getEquipCode6())));
		}
		
		return !bools.contains(false);
	}
}
