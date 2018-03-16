package com.aa.gsa.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.ODKeyNotFoundException;
import com.aa.gsa.service.CPPService;
import com.aa.gsa.service.ScheduleQueryService;
import com.aa.gsa.service.SettingsPerGroupService;
import com.aa.gsa.service.StationCodeReader;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class CPPServiceImpl implements CPPService {

	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(CPPServiceImpl.class);

	private StationCodeReader stationCodeReader;

	private ScheduleQueryService scheduleQueryService;

	private SettingsPerGroupService settingsPerGroupService;

	public static final int MAX_CONNECTIONS = 2;

	public static final String CODESHARE = "*";

	public CPPServiceImpl(StationCodeReader stationCodeReader, ScheduleQueryService scheduleQueryService, SettingsPerGroupService settingsPerGroupService) {
		this.stationCodeReader = stationCodeReader;
		this.scheduleQueryService = scheduleQueryService;
		this.settingsPerGroupService = settingsPerGroupService;
	}

	@Override
	public List<Schedule> getSchedulesForaCityPair(CPP cpp) {
		Set<String> stationCodePairs = getStationCodesForaCityPair(cpp);
		return scheduleQueryService.getSchedulesByStationCodePairs(stationCodePairs);
	}

	@Override
	public Table<Airline, Direction, List<Schedule>> getSchedulesGroupedByAirlineAndDirection(CPP cpp) {
		List<Schedule> schedules = getSchedulesForaCityPair(cpp);
		return splitSchedulesForaCityPair(schedules, cpp);
	}

	@Override
	public Set<String> getStationCodesForaCityPair(CPP cpp) {
		Set<String> stationCodePairs = new HashSet<>(2);
		switch (cpp.travelType()) {
		case DOMESTIC:
			stationCodePairs.add(cpp.getOriginAirport() + "-" + cpp.getDestinationAirport());
			stationCodePairs.add(cpp.getDestinationAirport() + "-" + cpp.getOriginAirport());
			break;

		case INTERNATIONAL:
			stationCodeReader.getStationsByCityCode(cpp.getOriginAirport()).forEach(stationCode1 -> 
			stationCodeReader.getStationsByCityCode(cpp.getDestinationAirport()).forEach(stationCode2 -> {
				stationCodePairs.add(stationCode1 + "-" + stationCode2); 
				stationCodePairs.add(stationCode2 + "-" + stationCode1);	
			}
					));	
			break;
		}	
		return stationCodePairs;
	}		

	public Table<Airline, Direction, List<Schedule>> splitSchedulesForaCityPair(List<Schedule> nonDirectionalschedules, CPP cpp) {
		final List<String> departureStationCodes = stationCodeReader.getStationsByCityCode(cpp.getOriginAirport());
		final List<String> arrivalStationCodes = stationCodeReader.getStationsByCityCode(cpp.getDestinationAirport());
		Table<Airline, Direction, List<Schedule>> schedules = HashBasedTable.create(); 

		for (Schedule schedule : nonDirectionalschedules) {
			if (schedule.getLegAirline1() != null && Airline.contains(schedule.getLegAirline1()) && isValidSchedule(schedule, cpp, nonDirectionalschedules)) {
				Airline airline = Airline.valueOf(schedule.getLegAirline1());
				if (departureStationCodes.contains(schedule.getDepartureAirportCode()) && arrivalStationCodes.contains(schedule.getArrivalAirportCode())) {
					addScheduleToTable(schedule, schedules, airline, Direction.OUTBOUND, nonDirectionalschedules.size()/2);
				} else if(arrivalStationCodes.contains(schedule.getDepartureAirportCode()) && departureStationCodes.contains(schedule.getArrivalAirportCode())) {
					addScheduleToTable(schedule, schedules, airline, Direction.INBOUND, nonDirectionalschedules.size()/2);
				}

			} 
		}
		return schedules;
	}

	private boolean isValidSchedule(Schedule schedule, CPP cpp, List<Schedule> nonDirectionalschedules) {
		if (!isValidSchedule(schedule) || (hasConnections(schedule) && !hasValidNumberOfConnections(schedule))) {
			return false;
		}

		if (cpp.travelType().equals(TravelType.DOMESTIC)) {
			if (CODESHARE.equals(schedule.getServiceType())) {
				return false;
			} 
			if (!isOperatedBySameAirlines(schedule)) {
				return false;
			}
		} else if (cpp.travelType().equals(TravelType.INTERNATIONAL)) {
			if (!CODESHARE.equals(schedule.getServiceType()) && !isOperatedBySameAirlines(schedule)) {
				return false;
			}

			if (CODESHARE.equals(schedule.getServiceType()) && !isOperatedByApprovedPartners(schedule, nonDirectionalschedules)) {
				return false;
			}
		}
		return true;
	}

	private boolean isOperatedByApprovedPartners(Schedule schedule, List<Schedule> nonDirectionalschedules) {
		int operationalODKey = schedule.getOperationalODKey();

		List<Schedule> parentSchedules =  nonDirectionalschedules
				.stream()
				.filter(schedule1 -> schedule1.getODkey().equals(operationalODKey))
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(parentSchedules) || parentSchedules.size() > 1) {
			throw new ODKeyNotFoundException(operationalODKey);
		}

		Schedule operatingSchedule = parentSchedules.get(0);

		boolean valid = isOperatedByApprovedPartners(operatingSchedule.getLegAirline1())
				&& (operatingSchedule.getLegAirline2() == null || (operatingSchedule.getLegAirline2() != null && isOperatedByApprovedPartners(operatingSchedule.getLegAirline2())))
				&& (operatingSchedule.getLegAirline3() == null || (operatingSchedule.getLegAirline3() != null && isOperatedByApprovedPartners(operatingSchedule.getLegAirline3())));  

		return valid;
	}

	private boolean isOperatedByApprovedPartners(String airline) {
		Set<String> approvedPartners = settingsPerGroupService.approvedPartners();
		return airline.equals(Airline.AA.name()) || approvedPartners.contains(airline);
	}

	private boolean isValidSchedule(Schedule schedule) {
		return schedule.getArrivalAirportCode() != null && schedule.getDepartureAirportCode() != null &&
				schedule.getArrivalAirportCountryCode() != null && schedule.getDepartureAirportCountryCode() != null &&
				schedule.getLegAirline1() != null;	
	}

	private boolean hasConnections(Schedule schedule) {
		return schedule.getNumberOfConnections() != null;
	}

	private boolean hasValidNumberOfConnections(Schedule schedule) {
		return schedule.getNumberOfConnections() <= MAX_CONNECTIONS;
	}

	private boolean isOperatedBySameAirlines(Schedule schedule) {
		String airline1 = schedule.getLegAirline1();
		String airline2 = schedule.getLegAirline2();
		String airline3 = schedule.getLegAirline3();

		Set<Boolean> flags = new HashSet<>();

		if (airline2 != null) {
			flags.add(airline1.equals(airline2));
		}

		if (airline3 != null) {
			flags.add(airline1.equals(airline3));
		}

		return !flags.contains(false);
	}

	private void addScheduleToTable(Schedule schedule, Table<Airline, Direction, List<Schedule>> schedules, Airline airline, Direction direction, int defaultListSize) {
		if(schedules.get(airline, direction) == null) {
			schedules.put(airline, direction, new ArrayList<>(defaultListSize));
		}		
		schedules.get(airline, direction).add(schedule);
	}

	@Override
	public boolean isCrossingThreeTimezones(CPP cpp) {
		int originGMTOffset = stationCodeReader.getGMTOffset(cpp.getOriginAirport());
		int destinGMTOffset = stationCodeReader.getGMTOffset(cpp.getDestinationAirport());

		return Math.abs(destinGMTOffset - originGMTOffset) > 150;
	}
}
