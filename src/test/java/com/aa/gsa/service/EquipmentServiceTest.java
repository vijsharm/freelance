package com.aa.gsa.service;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.EquipmentType;
import com.aa.gsa.service.impl.EquipmentServiceImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;


import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceTest {

	@Mock
	private EquipmentReader equipmentReader;

	private EquipmentService serviceToTest;

	private final String jetCode = "J";

	private final String turboCode = "T";

	@Before
	public void setUp() {
		when(equipmentReader.getEquipmentCodes()).thenReturn(getEquipmentMap());
		serviceToTest = new EquipmentServiceImpl(equipmentReader);
	}

	@Test
	public void allCodeJetTest() {
		Schedule schedule = getSchedule(0);
		assertTrue("Returning false when all codes are of Jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void firstCodeNullTest() {
		Schedule schedule = getSchedule(0);
		//Testing first code must be Jet
		schedule.setEquipCode1(null);
		assertFalse("Returning true when 1st code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void firstCodeNonJetTest() {
		Schedule schedule = getSchedule(1);
		assertFalse("Returning true when 1st code is of Turbo", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void secondCodeNonJetTest() {
		Schedule schedule = getSchedule(2);
		assertFalse("Returning true when 2nd code is of Turbo", serviceToTest.isJet(schedule));
		//Testing null code
		schedule.setEquipCode2(null);
		assertTrue("Returning false when 2nd code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void thirdCodeNonJetTest() {
		Schedule schedule = getSchedule(3);
		assertFalse("Returning true when 3rd code is of Turbo", serviceToTest.isJet(schedule));
		//Testing null code
		schedule.setEquipCode3(null);
		assertTrue("Returning false when 3rd code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void fourthCodeNonJetTest() {
		Schedule schedule = getSchedule(4);
		assertFalse("Returning true when 4th code is of Turbo", serviceToTest.isJet(schedule));
		//Testing null code
		schedule.setEquipCode4(null);
		assertTrue("Returning false when 4th code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void fifthCodeNonJetTest() {
		Schedule schedule = getSchedule(5);
		assertFalse("Returning true when 5th code is of Turbo", serviceToTest.isJet(schedule));
		//Testing null code
		schedule.setEquipCode5(null);
		assertTrue("Returning false when 5th code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	@Test
	public void sixthCodeNonJetTest() {
		Schedule schedule = getSchedule(6);
		assertFalse("Returning true when 6th code is of Turbo", serviceToTest.isJet(schedule));
		//Testing null code
		schedule.setEquipCode6(null);
		assertTrue("Returning false when 6th code is null and rest are of jet", serviceToTest.isJet(schedule));
		verify(equipmentReader, times(1)).getEquipmentCodes();
	}

	private Map<String, EquipmentType> getEquipmentMap(){
		Map<String, EquipmentType> equipmentMap = new HashMap<>();
		equipmentMap.put("J", EquipmentType.JET);
		equipmentMap.put("T", EquipmentType.TURBO);
		return equipmentMap;
	}

	private Schedule getSchedule(int nonJetCode) {
		Schedule schedule = new Schedule();
		schedule.setEquipCode1(jetCode);
		schedule.setEquipCode2(jetCode);
		schedule.setEquipCode3(jetCode);
		schedule.setEquipCode4(jetCode);
		schedule.setEquipCode5(jetCode);
		schedule.setEquipCode5(jetCode);
		switch (nonJetCode) {
			case 1 : schedule.setEquipCode1(turboCode); break;
			case 2 : schedule.setEquipCode2(turboCode); break;
			case 3 : schedule.setEquipCode3(turboCode); break;
			case 4 : schedule.setEquipCode4(turboCode); break;
			case 5 : schedule.setEquipCode5(turboCode); break;
			case 6 : schedule.setEquipCode6(turboCode); break;
			case 0 :
		}

		return schedule;
	}
	

}
