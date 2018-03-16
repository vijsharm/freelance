package com.aa.gsa.service;

import java.util.List;
import java.util.Set;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.google.common.collect.Table;

public interface CPPService {
	
	/**
	 * Gets Schedules between a City Pair	
	 * 
	 * @param cpp
	 * @return
	 * 
	 */
	List<Schedule> getSchedulesForaCityPair(CPP cpp);

	
	/**
	 * Gets Schedules Grouped By Airline & Direction between a City Pair	
	 * 
	 * @param cpp
	 * @return
	 * 
	 */
	Table<Airline, Direction, List<Schedule>> getSchedulesGroupedByAirlineAndDirection(CPP cpp);
	
	
	
	/**
	 * Gets Station Codes for a City Pair
	 * 
	 * @param cpp
	 * @return
	 */
	Set<String> getStationCodesForaCityPair(CPP cpp);
	
	
	/**
	 * Splits nonDirectionalschedules by Airline & Inbound/Outbound for a given City Pair
	 * 
	 * @param nonDirectionalschedules
	 * @param cpp
	 * @return
	 */
	Table<Airline, Direction, List<Schedule>> splitSchedulesForaCityPair(List<Schedule> nonDirectionalschedules, CPP cpp);
		
	
	/**
	 * 
	 * 
	 * @param cpp
	 * @return
	 */
	boolean isCrossingThreeTimezones(CPP cpp);

}
