package com.aa.gsa.service;

import java.util.List;
import java.util.Set;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.ServiceLevel;

public interface ScheduleQueryService {

	/**
	 * Gets directional schedules with given station code pairs, e.g DFW-LAX, LAX-DFW, CLT-NYC etc.
	 * 
	 * @param stationPairs
	 * @return
	 * 
	 */
	List<Schedule> getSchedulesByStationCodePairs(Set<String> stationCodePairs);
	
	
	/**
	 * 
	 * Gets directional schedules with given station code pairs, e.g DFW-LAX, LAX-DFW, CLT-NYC filtered by ServiceLevels
	 * 
	 * @param stationPairs
	 * @param serviceLevels
	 * @return
	 */
	List<Schedule> getSchedulesForStationCodePairsByServiceLevels(Set<String> stationCodePairs, ServiceLevel[] serviceLevels);
	
}
