package com.aa.gsa.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.ServiceLevel;

public interface ScheduleService {

	/**
	 * 
	 * @param arrivalTime
	 * @param departureTime
	 * @return
	 */
	boolean isEastBound(Schedule schedule);


	/**
	 * @param arrivalTime
	 * @param departureTime
	 * @return
	 * 
	 */
	boolean isWestBound(Schedule schedule);

	/**
	 * @return
	 */
	boolean isDepartingBetweenSixAndElevenPM(Schedule schedule);


	/**
	 * 
	 * @param schedule
	 * @return
	 */
	long calculateGroundTimeInMinutes(Schedule schedule);
	
	/**
	 * 
	 * @param schedule
	 * @return
	 */
	ServiceLevel getServiceLevel(Schedule schedule);


	/**
	 * Populates Service Levels for a Collection
	 * 
	 * @param schedules
	 */
	void populateServiceLevels(Collection<Schedule> schedules);
	
	
	/**
	 * 
	 * @param schedules
	 * @return
	 * 
	 */
	Map<ServiceLevel, Set<Schedule>> groupByServiceLevels(Set<Schedule> schedules);
	
	
	/**
	 * Determines if a schedule is codeshare
	 * 
	 * @param schedule
	 * @return
	 */
	boolean isCodeshare(Schedule schedule);
	
	
	/**
	 * 
	 * Merges Equal service by grouping the frequencies
	 * 
	 **/
	Set<Schedule> mergeDuplicateRecords(Collection<Schedule> schedules);
	
	
	/**
	 * Calculates the numberofFlights for each day.
	 * 
	 * @param schedules
	 * @param cpp
	 * @return
	 */
	Map<DayOfOperation, Integer> calculateNumberOfFlightsPerDay(Set<Schedule> schedules, CPP cpp, EligibilityResultService eligibilityResultService);
}