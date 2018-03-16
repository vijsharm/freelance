package com.aa.gsa.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.exception.ViewQueryException;
import com.aa.gsa.service.ScheduleQueryService;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;

public class ScheduleQueryServiceImpl implements ScheduleQueryService {
	
	private static final String DESIGN_DOC = "cpp";

	private static final String VIEW_ORIG_DEST = "orig-dest";

	private static final String VIEW_ORIG_DEST_SERVICE_TYPE = "orig-dest-serviceLevel";

	private Database database;

	@Autowired
	public ScheduleQueryServiceImpl(CloudantClient cloudantClient, String databaseName) {
		database = cloudantClient.database(databaseName, false);
	}
	
	@Override
	public List<Schedule> getSchedulesByStationCodePairs(Set<String> stationCodePairs) {
		return queryView(VIEW_ORIG_DEST, stationCodePairs);
	}

	@Override
	public List<Schedule> getSchedulesForStationCodePairsByServiceLevels(Set<String> stationPairs, ServiceLevel[] serviceLevels) {
		return queryView(VIEW_ORIG_DEST_SERVICE_TYPE, stationCodeServiceLevelPairs(stationPairs, serviceLevels));
	}
	
	
	/**
	 * 
	 * @param viewName
	 * @param keys
	 * @return
	 */
	private List<Schedule> queryView(String viewName, Set<String> keys) {
		try {
			List<Schedule> response =  database
					.getViewRequestBuilder(DESIGN_DOC, viewName)
					.newRequest(Key.Type.STRING, Object.class)
					.includeDocs(true)
					.keys(keys.toArray(new String[keys.size()]))
					.build()
					.getResponse()
					.getDocsAs(Schedule.class);
			return response;
		} catch(Exception ex) {
			throw new ViewQueryException(viewName, keys, ex);
		}
	}
	
	/**
	 * 
	 * @param stationCodePairs
	 * @param serviceTypes
	 * @return
	 */
	private Set<String> stationCodeServiceLevelPairs(Set<String> stationCodePairs, ServiceLevel[] serviceLevels) {
		Set<String> keys = new HashSet<>(stationCodePairs.size() * serviceLevels.length);
		for (ServiceLevel serviceLevel : serviceLevels) {
			for (String stationCodePair : stationCodePairs) {
				keys.add(stationCodePair + "-" + serviceLevel.value());
			}
		}		

		return keys;
	}
}
