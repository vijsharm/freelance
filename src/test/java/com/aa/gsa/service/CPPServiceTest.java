package com.aa.gsa.service;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.exception.ODKeyNotFoundException;
import com.aa.gsa.service.impl.CPPServiceImpl;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CPPServiceTest {

	@Mock
	private StationCodeReader stationCodeReader;

	@Mock
	private ScheduleQueryService scheduleQueryService;

	@Mock
	private SettingsPerGroupService settingsPerGroupService;

	public static final int MAX_CONNECTIONS = 2;

	public static final String CODESHARE = "*";

	private CPPService serviceToTest;

	@Before
	public void setUp() {
		serviceToTest = new CPPServiceImpl(stationCodeReader, scheduleQueryService, settingsPerGroupService);
	}

	@Test
	public void getSchedulesForaCityPairTest() {
		CPPService serviceToTestSpy = spy(serviceToTest);
		CPP cpp =  new CPP();
		Set<String> stationCodePairs = new HashSet<>(Arrays.asList("PHX-JFK", "JFK-PHX"));
		doReturn(stationCodePairs).when(serviceToTestSpy).getStationCodesForaCityPair(any(CPP.class));
		Schedule schedule = new Schedule();
		List<Schedule> list = Arrays.asList(schedule);
		when(scheduleQueryService.getSchedulesByStationCodePairs(stationCodePairs)).thenReturn(list);
		assertEquals(list, serviceToTestSpy.getSchedulesForaCityPair(cpp));
		//Capturing arguments
		ArgumentCaptor<Set> codesCaptor = ArgumentCaptor.forClass(Set.class);
		ArgumentCaptor<CPP> cppCaptor = ArgumentCaptor.forClass(CPP.class);
		verify(serviceToTestSpy, times(1)).getSchedulesForaCityPair(cppCaptor.capture());
		assertEquals(cppCaptor.getValue(), cpp);
		verify(scheduleQueryService, times(1)).getSchedulesByStationCodePairs(codesCaptor.capture());
		assertEquals(codesCaptor.getValue(), stationCodePairs);
	}

	@Test
	public void getSchedulesGroupedByAirlineAndDirectionTest() {
		CPPService serviceToTestSpy = spy(serviceToTest);
		CPP cpp =  new CPP();
		Schedule schedule = new Schedule();
		List<Schedule> list = Arrays.asList(schedule);
		Table<Airline, Direction, List<Schedule>> scheduleTable = HashBasedTable.create();

		doReturn(list).when(serviceToTestSpy).getSchedulesForaCityPair(any(CPP.class));
		doReturn(scheduleTable).when(serviceToTestSpy).splitSchedulesForaCityPair(any(List.class), any(CPP.class));

		ArgumentCaptor<List> scheduleCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<CPP> cppCaptor = ArgumentCaptor.forClass(CPP.class);
		assertEquals(scheduleTable, serviceToTestSpy.getSchedulesGroupedByAirlineAndDirection(cpp));
		verify(serviceToTestSpy, times(1)).getSchedulesForaCityPair(cppCaptor.capture());
		assertEquals(cppCaptor.getValue(), cpp);
		verify(serviceToTestSpy, times(1)).splitSchedulesForaCityPair(scheduleCaptor.capture(), cppCaptor.capture());
		assertEquals(scheduleCaptor.getValue(), list);
		assertEquals(cppCaptor.getValue(), cpp);

	}

	@Test
	public void getStationCodesForaCityPairTestDomestic() {
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("PHX");
		cpp.setDestinationAirport("JFK");
		Set<String> stationPairs = serviceToTest.getStationCodesForaCityPair(cpp);
		assertTrue(stationPairs.contains("PHX-JFK"));
		assertTrue(stationPairs.contains("JFK-PHX"));
		assertEquals(2, stationPairs.size());
		verify(stationCodeReader, never()).getStationsByCityCode(anyString());
	}

	@Test
	public void getStationCodesForaCityPairTestInternational() {
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("I");
		cpp.setOriginAirport("US");
		cpp.setDestinationAirport("EU");
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("MUC", "FRA"));
		Set<String> stationPairs = serviceToTest.getStationCodesForaCityPair(cpp);
		assertTrue(stationPairs.contains("PHX-MUC"));
		assertTrue(stationPairs.contains("PHX-FRA"));
		assertTrue(stationPairs.contains("JFK-MUC"));
		assertTrue(stationPairs.contains("JFK-FRA"));
		assertTrue(stationPairs.contains("MUC-PHX"));
		assertTrue(stationPairs.contains("FRA-PHX"));
		assertTrue(stationPairs.contains("MUC-JFK"));
		assertTrue(stationPairs.contains("FRA-JFK"));
		assertEquals(8, stationPairs.size());
		verify(stationCodeReader, times(3)).getStationsByCityCode(anyString());
	}

	@Test
	public void isValidScheduleFailTestDomesticOutbound() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(1);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("MUC", "FRA"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("PHX", "JFK"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(1, tableRow.size());
		for(Map.Entry<Direction, List<Schedule>> entry : tableRow.entrySet()) {
			assertEquals(Direction.OUTBOUND, entry.getKey());
		}
		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}

	@Test
	public void isValidScheduleFailTestDomesticInbound() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(1);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(1, tableRow.size());
		for(Map.Entry<Direction, List<Schedule>> entry : tableRow.entrySet()) {
			assertEquals(Direction.INBOUND, entry.getKey());
		}
		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());

	}

	@Test
	public void isCrossingThreeTimezonesTest() {
		CPP cpp = new CPP();
		cpp.setOriginAirport("MUC");
		cpp.setDestinationAirport("FRA");
		when(stationCodeReader.getGMTOffset(anyString())).thenReturn(100).thenReturn(400);
		assertTrue(serviceToTest.isCrossingThreeTimezones(cpp));
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(stationCodeReader, times(2)).getGMTOffset(captor.capture());
		assertTrue(captor.getAllValues().contains("MUC"));
		assertTrue(captor.getAllValues().contains("FRA"));
	}

	@Test
	public void isCrossingThreeTimezonesNegativeTest() {
		CPP cpp = new CPP();
		cpp.setOriginAirport("MUC");
		cpp.setDestinationAirport("FRA");
		when(stationCodeReader.getGMTOffset(anyString())).thenReturn(100).thenReturn(200);
		assertFalse(serviceToTest.isCrossingThreeTimezones(cpp));
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(stationCodeReader, times(2)).getGMTOffset(captor.capture());
		assertTrue(captor.getAllValues().contains("MUC"));
		assertTrue(captor.getAllValues().contains("FRA"));

	}

	@Test
	public void isValidScheduleFailTestDomesticInvalidCodeshare() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(1);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		schedule.setServiceType("*");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(0, tableRow.size());

		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}

	@Test
	public void isValidScheduleFailTestDomesticInvalidConnections() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(3);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(0, tableRow.size());

		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}

	@Test
	public void isValidScheduleFailTestDomesticInvalidSameOperator() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setLegAirline2("DL");
		schedule.setNumberOfConnections(1);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("D");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(0, tableRow.size());

		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}



	@Test(expected = ODKeyNotFoundException.class)
	public void isValidScheduleFailTestInternationalInvalidODKey() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(1);
		schedule.setODkey(1);
		schedule.setOperationalODKey(2);
		CPP cpp = new CPP();
		cpp.setTypeOfTravel("I");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		schedule.setServiceType("*");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(1, tableRow.size());
		for(Map.Entry<Direction, List<Schedule>> entry : tableRow.entrySet()) {
			assertEquals(Direction.INBOUND, entry.getKey());
		}
		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}

	@Test(expected = ODKeyNotFoundException.class)
	public void isValidScheduleFailTestInternationalNonUniqueODKey() {
		Schedule schedule = new Schedule();
		schedule.setArrivalAirportCode("PHX");
		schedule.setDepartureAirportCode("MUC");
		schedule.setArrivalAirportCountryCode("B");
		schedule.setDepartureAirportCountryCode("D");
		schedule.setLegAirline1("AA");
		schedule.setNumberOfConnections(1);
		schedule.setODkey(1);
		schedule.setOperationalODKey(1);

		Schedule schedule1 = new Schedule();
		schedule1.setArrivalAirportCode("PHX");
		schedule1.setDepartureAirportCode("MUC");
		schedule1.setArrivalAirportCountryCode("B");
		schedule1.setDepartureAirportCountryCode("D");
		schedule1.setLegAirline1("AA");
		schedule1.setNumberOfConnections(1);
		schedule1.setODkey(1);
		schedule1.setOperationalODKey(1);

		CPP cpp = new CPP();
		cpp.setTypeOfTravel("I");
		cpp.setOriginAirport("EU");
		cpp.setDestinationAirport("US");

		schedule.setServiceType("*");

		List<Schedule> nonDirectionalschedules = Arrays.asList(schedule, schedule1);

		when(stationCodeReader.getStationsByCityCode("EU")).thenReturn(Arrays.asList("PHX", "JFK"));
		when(stationCodeReader.getStationsByCityCode("US")).thenReturn(Arrays.asList("MUC", "FRA"));

		Table<Airline, Direction, List<Schedule>> table = serviceToTest.splitSchedulesForaCityPair(nonDirectionalschedules, cpp);
		Map<Direction, List<Schedule>> tableRow = table.row(Airline.AA);
		assertEquals(1, tableRow.size());
		for(Map.Entry<Direction, List<Schedule>> entry : tableRow.entrySet()) {
			assertEquals(Direction.INBOUND, entry.getKey());
		}
		verify(stationCodeReader, times(2)).getStationsByCityCode(anyString());
	}

}
