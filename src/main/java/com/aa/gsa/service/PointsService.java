package com.aa.gsa.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.result.DirectionalResult;
import com.aa.gsa.domain.result.PointsResult;
import com.aa.gsa.enums.DayOfOperation;

public interface PointsService {
	/**
	 * Calcualtes points for a market given outbound and inbound schedules
	 * 
	 * @param outbound
	 * @param inbound
	 * @param cpp
	 * @return
	 */
	PointsResult calcluatePoints(Set<Schedule> outbound, Set<Schedule> inbound, CPP cpp); 
	
	/**
	 * 
	 * Calcuates counts and points for a @Direction OUTBOUND/INBOUND 
	 * 
	 * @param schedules
	 * @param cpp
	 * @return
	 */
	DirectionalResult calculateCounts(Set<Schedule> schedules, CPP cpp);
	
	/**
	 * Calculates the average elasped time given outbound and inbound elapsed times.
	 * 
	 * @param outboundElapsedTimes
	 * @param inboundElapsedTimes
	 * @param cpp
	 * @return
	 */
	int calculateAverageElapsedTime(Map<DayOfOperation, Integer> outboundElapsedTimes, Map<DayOfOperation, Integer> inboundElapsedTimes, CPP cpp);

	
	/**
	 * 
	 * @param elapsedTimes
	 * @param minNumberOfFlights
	 * @return
	 */
	Map<DayOfOperation, Integer> averageElapsedTimeForMinNumberOfFlights(Map<DayOfOperation, List<Integer>> elapsedTimes, int minNumberOfFlights);

	/**
	 * Calcualates elapsed time points given and elapsedTime and shortestElapsedTime.
	 * 	
	 * @param elapsedTime
	 * @param shortestElapsedTime
	 * @param nonStopFlag: true/false. true = This carrier has non-stop flight when at least on carries has only Connection flights 
	 * @param cpp
	 * @return
	 */
	int calculateElapsedTimePoints(int elapsedTime, int shortestElapsedTime, boolean nonStopFlag, CPP cpp);
	

}
