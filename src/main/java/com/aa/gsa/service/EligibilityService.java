package com.aa.gsa.service;

import java.util.Set;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.DayOfOperation;

public interface EligibilityService {
    /**
     * Determines if required minimum flights exist or not
     * @param schedules
     * @param cpp
     * @param flightTimes
     * @return
     */
    boolean hasMinNoOfFlights(Set<Schedule> schedules, CPP cpp, Set<DayOfOperation> flightTimes);
    
	/**
	 * Determines if minimum service is met for a given set of eligible schedules. 
	 * 
	 * @param schedules
	 * @param cpp
	 * @param flightTimes
	 * @return
	 */
	boolean hasMetMinimumService(Set<Schedule> schedules, CPP cpp, Set<DayOfOperation> flightTimes);
		
	/**
	 * Deletes the repeated legs for a given set of schedules.
	 * Schedule with that gives most points is kept, and all other schedules for the day will be deleted. 
	 * @param schedules
	 * @param cpp
	 */
	void deleteRepeatedLegs(Set<Schedule> schedules, CPP cpp);
		
	
	/**
	 * Calculates points for a schedule given averageElapsedTime and CPP.
	 * Used in the context of Delete repeated schedules.
	 * @param schedule
	 * @param avgElapsedTime
	 * @param cpp
	 * @return
	 * 
	 */
	int calculatePointsForSchedule(Schedule schedule, int avgElapsedTime, CPP cpp);
}