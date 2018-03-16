package com.aa.gsa.processor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Payload;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.service.CPPService;
import com.aa.gsa.service.ScheduleService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class InitProcessor implements ItemProcessor<CPP, Payload> {
	
	private CPPService cppService;

	private ScheduleService scheduleService;

	@Autowired
	public InitProcessor(CPPService cppService, ScheduleService scheduleService) {
		this.cppService = cppService;
		this.scheduleService = scheduleService;
	}

	@Override
	public Payload process(CPP cpp) throws Exception {
		//determine if crossing three time zones
		if (cpp.travelType().equals(TravelType.DOMESTIC)) {
			cpp.setIsCrossingThreeTimeZones(cppService.isCrossingThreeTimezones(cpp));
		}
		
		//get schedules for a CPP
		Table<Airline, Direction, List<Schedule>> allSchedules = cppService.getSchedulesGroupedByAirlineAndDirection(cpp);	

		//remove duplicates and merge identical ones
		Table<Airline, Direction, Set<Schedule>> schedules = HashBasedTable.create();
		for (Airline airline : allSchedules.rowKeySet()) {
			for (Direction direction : allSchedules.columnKeySet()) {
				List<Schedule> directionalSchedulesForAnAirline = allSchedules.get(airline, direction);
				if (!CollectionUtils.isEmpty(directionalSchedulesForAnAirline)) {
					Set<Schedule> uniqueSchedules = scheduleService.mergeDuplicateRecords(directionalSchedulesForAnAirline);
					if (!CollectionUtils.isEmpty(directionalSchedulesForAnAirline)) {
						schedules.put(airline, direction, uniqueSchedules);
					}
				} 
			}
		}

		//populate Service Levels
		schedules
			.values()
			.stream()
			.forEach(listOfSchedules -> scheduleService.populateServiceLevels(listOfSchedules));
		
		//if CPP is international then populate noCodeshare
		if(TravelType.INTERNATIONAL.equals(cpp.travelType())) {
			final Table<Airline, Direction, Set<Schedule>> schedulesNoCodeshare = HashBasedTable.create(); 
			for (Airline airline : schedules.rowKeySet()) {
				for (Direction direction : schedules.columnKeySet()) {
					if (schedules.get(airline, direction) != null) {
						Set<Schedule> noCodeShare = schedules.get(airline, direction)
								.stream()
								.filter(schedule -> !schedule.getServiceType().equals("*"))
								.collect(Collectors.toSet());
						schedulesNoCodeshare.put(airline, direction, noCodeShare);
					}
				}	
			}
			return new Payload(cpp, schedules, schedulesNoCodeshare);
		}

		return new Payload(cpp, schedules);
	}	
}
