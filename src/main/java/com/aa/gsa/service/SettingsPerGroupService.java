package com.aa.gsa.service;

import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.domain.settings.CircuitySettings;
import com.aa.gsa.domain.settings.GroundTimeSettings;
import com.aa.gsa.domain.settings.TimebandKeyDomesticPerGroup;
import com.aa.gsa.domain.settings.TimebandKeyInternationalPerGroup;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.SAEFT;
import com.aa.gsa.enums.TravelType;
import com.google.common.collect.Table;

public interface SettingsPerGroupService {

	/**
	 * GroundTime Settings
	 * @return
	 * 
	 */
	Map<Group, GroundTimeSettings> groundTimeSettings();
	
	
	/**
	 * Circuity Settings
	 * @return
	 */
	Table<Group, TravelType, Set<CircuitySettings>> circuitySettings();
		
	
	/**
	 * Gets a timeband associated with a from & to time
	 * 
	 * @param from
	 * @param to
	 * @param group
	 * @return
	 */
	Timeband findTimebandByFromTo(String from, String to, Group group);

	/**
	 * 
	 * @param group
	 * @return
	 */
	Set<Timeband> timebandsByGroup(Group group);
	
	/**
	 * Given a time(hour & minute) for a group, finds out which time-band it falls into
	 * 
	 * @param hour
	 * @param minute
	 * @param group
	 * @return
	 */
	Timeband findTimebandByRange(String hour, String minute, Group group);
	
	/**
	 * 
	 * Domestic time-band Settings
	 * 
	 */
	Map<TimebandKeyDomesticPerGroup, Integer> timebandSettingsDomestic();
		
	/**
	 * 
	 * International Time-band Settings
	 * 
	 */
	Map<TimebandKeyInternationalPerGroup, Integer> timebandSettingsInternational();
	
	
	/**
	 * Elapsed Time Settings
	 * @return
	 */
	Table<TravelType, Integer, Map<SAEFT, Integer>> elapsedTimeSettings();
	
	/**
	 * Gets Approved Partners
	 * @return
	 */
	Set<String> approvedPartners();
}

