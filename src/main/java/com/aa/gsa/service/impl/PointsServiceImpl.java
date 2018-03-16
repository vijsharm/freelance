package com.aa.gsa.service.impl;

import static com.aa.gsa.enums.DayOfOperation.FRIDAY;
import static com.aa.gsa.enums.DayOfOperation.MONDAY;
import static com.aa.gsa.enums.DayOfOperation.THURSDAY;
import static com.aa.gsa.enums.DayOfOperation.TUESDAY;
import static com.aa.gsa.enums.DayOfOperation.WEDNESDAY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.Timeband;
import com.aa.gsa.domain.result.DirectionalResult;
import com.aa.gsa.domain.result.PointsResult;
import com.aa.gsa.domain.settings.TimebandKeyDomestic;
import com.aa.gsa.domain.settings.TimebandKeyDomesticPerGroup;
import com.aa.gsa.domain.settings.TimebandKeyInternational;
import com.aa.gsa.domain.settings.TimebandKeyInternationalPerGroup;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.GroupType;
import com.aa.gsa.enums.PointsCategory;
import com.aa.gsa.enums.SAEFT;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.service.EquipmentService;
import com.aa.gsa.service.PointsService;
import com.aa.gsa.service.ScheduleService;
import com.aa.gsa.service.SettingsPerGroupService;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

@SuppressWarnings("serial")
public class PointsServiceImpl implements PointsService {
	
	private ScheduleService scheduleService;

	private EquipmentService equipmentService;

	private SettingsPerGroupService settingsService;

	public static final int MAX_NUMBER_OF_FLIGHTS = 12;

	public static final int ADDITIONAL_POINTS_FOR_NON_STOP = 50;

	public static final int POINTS_FOR_JET = 1;

	public static final Map<ServiceLevel, Double> POINTS_ALL_DESTINATIONS = new HashMap<ServiceLevel, Double>() {{
		put(ServiceLevel.NON_STOP, 3.00);
		put(ServiceLevel.DIRECT, 1.25);
		put(ServiceLevel.CONNECTION, 1.0);
	}};

	public static final Map<ServiceLevel, Double> POINTS_SPECIFIC_DESTINATIONS = new HashMap<ServiceLevel, Double>() {{
		put(ServiceLevel.NON_STOP, 3.00);
		put(ServiceLevel.DIRECT, 1.0);
		put(ServiceLevel.CONNECTION, 1.0);
	}};

	@Autowired
	public PointsServiceImpl(SettingsPerGroupService settingsService, EquipmentService equipmentService, ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
		this.equipmentService = equipmentService;
		this.settingsService = settingsService;
	}

	@Override
	public PointsResult calcluatePoints(Set<Schedule> outbound, Set<Schedule> inbound, CPP cpp) {
		PointsResult result = new PointsResult();

		DirectionalResult outboundResult = calculateCounts(outbound, cpp);
		DirectionalResult inboundResult = calculateCounts(inbound, cpp);

		//time bands
		result.setTimebands(mergeTimebands(outboundResult.getTimebandCounts(), inboundResult.getTimebandCounts()));

		//total count
		final Map<Direction, Integer> total = new HashMap<>(Direction.values().length);
		total.put(Direction.OUTBOUND, outboundResult.getTotalCount());
		total.put(Direction.INBOUND, inboundResult.getTotalCount());
		result.setTotal(total);

		//jets count
		final Map<Direction, Integer> jets = new HashMap<>(Direction.values().length);
		jets.put(Direction.OUTBOUND, outboundResult.getJetsCount());
		jets.put(Direction.INBOUND, inboundResult.getJetsCount());
		result.setJets(jets);

		//non-stop counts
		final Map<Direction, Integer> nonStopCounts = new HashMap<>(Direction.values().length);
		nonStopCounts.put(Direction.OUTBOUND, outboundResult.getNonStopCount());
		nonStopCounts.put(Direction.INBOUND, inboundResult.getNonStopCount());
		result.setNonStopCounts(nonStopCounts);

		//direct counts
		final Map<Direction, Integer> directCounts = new HashMap<>(Direction.values().length);
		directCounts.put(Direction.OUTBOUND, outboundResult.getDirectCount());
		directCounts.put(Direction.INBOUND, inboundResult.getDirectCount());
		result.setDirectCounts(directCounts);

		//connect counts
		final Map<Direction, Integer> connectCounts = new HashMap<>(Direction.values().length);
		connectCounts.put(Direction.OUTBOUND, outboundResult.getConnectionCount());
		connectCounts.put(Direction.INBOUND, inboundResult.getConnectionCount());
		result.setConnectCounts(connectCounts);

		//connections
		Set<String> connections = new HashSet<>(3);
		if (!CollectionUtils.isEmpty(outboundResult.getConnections())) {
			connections.addAll(outboundResult.getConnections());
		}
		if (!CollectionUtils.isEmpty(inboundResult.getConnections())) {
			connections.addAll(inboundResult.getConnections());
		}
		result.setConnections(connections);

		//populate elapsed time
		if (!CollectionUtils.isEmpty(outboundResult.getAvgElapsedTimes()) && !CollectionUtils.isEmpty(inboundResult.getAvgElapsedTimes())) {
			int elapsedTime = calculateAverageElapsedTime(outboundResult.getAvgElapsedTimes(), inboundResult.getAvgElapsedTimes(), cpp);
			result.setElaspedTime(elapsedTime);
		}

		for (Map.Entry<PointsCategory, Integer> entry : result.getPointsPerCategory().entrySet()) {
			result.getPointsPerCategory().put(entry.getKey(), outboundResult.getPoints().get(entry.getKey()) + inboundResult.getPoints().get(entry.getKey()));
		}

		return result;
	}

	@Override
	public DirectionalResult calculateCounts(Set<Schedule> schedules, CPP cpp) {
		Table<Timeband, ServiceLevel, Integer> timebandCounts = initializeTimebandCounts(cpp.group());
		int totalCount = 0;
		int jetsCount = 0;
		int nonStopCount = 0;
		int directCount = 0;
		int connectionCount = 0;
		Set<String> connections = null;
		Map<DayOfOperation, List<Integer>> elapsedTimes = new HashMap<>(DayOfOperation.values().length);
		DirectionalResult directionalResult = new DirectionalResult();

		if(CollectionUtils.isEmpty(schedules)) {
			return emptyResult(cpp.group());
		}		

		Map<TimebandKeyDomesticPerGroup, Integer> timebandDomesticKeys = new HashMap<>();
		Map<TimebandKeyInternationalPerGroup, Integer> timebandInternationalKeys = new HashMap<>();
		Set<DayOfOperation> daysFlightsAvailable = daysFlightAvailable(schedules);

		for (Schedule schedule : schedules) {
			final Timeband timeband = settingsService.findTimebandByRange(schedule.getDepartureHour(), schedule.getDepartureMinute(), cpp.group());
			schedule.setTimeband(timeband);
			int prevCount =  timebandCounts.get(timeband, schedule.getServiceLevel());
			timebandCounts.put(timeband, schedule.getServiceLevel(), prevCount + schedule.getOperatingFrequency());

			if (cpp.travelType().equals(TravelType.DOMESTIC)) {
				boolean isWestbound = scheduleService.isWestBound(schedule);
				
				if (cpp.getIsCrossingThreeTimeZones() == null) {
					return emptyResult(cpp.group());
				}
				boolean isCrossing3Timezones = cpp.getIsCrossingThreeTimeZones().equals(Boolean.TRUE);
				
				TimebandKeyDomestic timebandKeyDomestic = new TimebandKeyDomestic(timeband, schedule.getServiceLevel(), isWestbound, isCrossing3Timezones);
				TimebandKeyDomesticPerGroup timebandKeyDomesticPerGroup = new TimebandKeyDomesticPerGroup(cpp.group(), timebandKeyDomestic);

				if (timebandDomesticKeys.get(timebandKeyDomesticPerGroup) == null) {
					timebandDomesticKeys.put(timebandKeyDomesticPerGroup, schedule.getOperatingFrequency());
				} else {
					timebandDomesticKeys.put(timebandKeyDomesticPerGroup, timebandDomesticKeys.get(timebandKeyDomesticPerGroup) + schedule.getOperatingFrequency());
				}
			} else if (cpp.travelType().equals(TravelType.INTERNATIONAL)) {
				TimebandKeyInternational timebandKeyInternational = new TimebandKeyInternational(timeband, schedule.getServiceLevel(), cpp.groupType());
				TimebandKeyInternationalPerGroup timebandKeyInternationalPerGroup = new TimebandKeyInternationalPerGroup(cpp.group(), timebandKeyInternational);

				if (timebandInternationalKeys.get(timebandKeyInternationalPerGroup) == null) {
					timebandInternationalKeys.put(timebandKeyInternationalPerGroup, schedule.getOperatingFrequency());
				} else {
					timebandInternationalKeys.put(timebandKeyInternationalPerGroup, timebandInternationalKeys.get(timebandKeyInternationalPerGroup) + schedule.getOperatingFrequency());
				}
			}

			totalCount += schedule.getOperatingFrequency();

			if (equipmentService.isJet(schedule)) {
				jetsCount += schedule.getOperatingFrequency();
			}

			//populate elapsed times
			for (DayOfOperation dayOfOperation : schedule.getOperatingDays()) {
				if (elapsedTimes.get(dayOfOperation) == null) {
					elapsedTimes.put(dayOfOperation, new ArrayList<>());
				}
				elapsedTimes.get(dayOfOperation).add(schedule.getElapsedMinutes());
			}

		} //end of schedule loop

		//apply per day average for  time-band counts
		for (Timeband timeband : timebandCounts.rowKeySet()) {
			for (ServiceLevel serviceLevel : timebandCounts.columnKeySet()) {
				int timebandCount = (int) Math.round((double) timebandCounts.get(timeband, serviceLevel) / daysFlightsAvailable.size());
				timebandCounts.put(timeband, serviceLevel, timebandCount);
				switch (serviceLevel) {
					case NON_STOP:
						nonStopCount += timebandCount;
						break;
	
					case DIRECT:
						directCount += timebandCount;
						break;
	
					case CONNECTION:
						connectionCount += timebandCount;
						break;	 
				}
			}	
		}

		//populate total count
		totalCount = Math.round((int) Math.round((double) totalCount/daysFlightsAvailable.size()));

		//populate jets count
		jetsCount = Math.round((int) Math.round((double) jetsCount/daysFlightsAvailable.size()));

		//populate average elapsed time
		Map<DayOfOperation, Integer> avgElapsedTimes = averageElapsedTimeForMinNumberOfFlights(elapsedTimes, cpp.getNumberOfFlights());

		connections = populateConnections(schedules);
		directionalResult.setTimebandCounts(timebandCounts);
		directionalResult.setTotalCount(totalCount);
		directionalResult.setJetsCount(jetsCount);
		directionalResult.setNonStopCount(nonStopCount);
		directionalResult.setDirectCount(directCount);
		directionalResult.setConnectionCount(connectionCount);
		directionalResult.setConnections(connections);
		directionalResult.setAvgElapsedTimes(avgElapsedTimes);
		
		int timebandPoints = cpp.travelType().equals(TravelType.DOMESTIC) ? calculateTimebandPointsDomestic(daysFlightsAvailable, timebandDomesticKeys)
				: calculateTimebandPointsInternational(daysFlightsAvailable, timebandInternationalKeys);

		//populate points
		directionalResult.getPoints().put(PointsCategory.TIMEBAND, timebandPoints);
		directionalResult.getPoints().put(PointsCategory.EQUIPMENT, Math.min(MAX_NUMBER_OF_FLIGHTS, jetsCount) * POINTS_FOR_JET);
		directionalResult.getPoints().put(PointsCategory.NUMBER_OF_FLIGHTS, numberOfFlightsPoints(nonStopCount, directCount, connectionCount, cpp));

		return directionalResult;
	}
	
	private int calculateTimebandPointsDomestic(Set<DayOfOperation> daysFlightsAvailable, Map<TimebandKeyDomesticPerGroup, Integer> timebandDomesticKeys) {
		int timebandPoints = 0;
		int nonStopTimebandPoints = 0;
		int directorConnectTimebandPoints = 0;

		//thresholds
		final int MAX_NON_STOP = 100;
		final int MAX_DIRECT_OR_CONNECT = 50;

		if (!CollectionUtils.isEmpty(timebandDomesticKeys)) {
			for (Map.Entry<TimebandKeyDomesticPerGroup, Integer> entry : timebandDomesticKeys.entrySet()) {
				int count = (int) Math.round((double) entry.getValue() / daysFlightsAvailable.size());
				ServiceLevel serviceLevel = entry.getKey().getTimebandKeyDomestic().getServiceLevel();
				switch (serviceLevel) {
					case NON_STOP:
						nonStopTimebandPoints += settingsService.timebandSettingsDomestic().get(entry.getKey()) * count;
						break;
					case DIRECT:
					case CONNECTION:
						directorConnectTimebandPoints += settingsService.timebandSettingsDomestic().get(entry.getKey()) * count;
 						break;
				}
			}
			nonStopTimebandPoints = Math.min(MAX_NON_STOP, nonStopTimebandPoints);
			directorConnectTimebandPoints = Math.min(MAX_DIRECT_OR_CONNECT, directorConnectTimebandPoints);
			timebandPoints = nonStopTimebandPoints + directorConnectTimebandPoints;
		}
		return timebandPoints;
	}	
	
	private int calculateTimebandPointsInternational(Set<DayOfOperation> daysFlightsAvailable, Map<TimebandKeyInternationalPerGroup, Integer> timebandInternationalKeys) {
		int timebandPoints = 0;
		int nonStopTimebandPoints = 0;
		int directorConnectTimebandPoints = 0;

		//thresholds
		final int MAX_NON_STOP = 100;
		final int MAX_DIRECT_OR_CONNECT = 50;

		if (!CollectionUtils.isEmpty(timebandInternationalKeys)) {
			for (Map.Entry<TimebandKeyInternationalPerGroup, Integer> entry : timebandInternationalKeys.entrySet()) {
				int count = (int) Math.round((double) entry.getValue() / daysFlightsAvailable.size());
				ServiceLevel serviceLevel = entry.getKey().getTimebandKeyInternational().getServiceLevel();
				switch (serviceLevel) {
					case NON_STOP:
						{
							if( settingsService.timebandSettingsInternational() != null && settingsService.timebandSettingsInternational().get(entry.getKey()) !=null) 
								{
								nonStopTimebandPoints += settingsService.timebandSettingsInternational().get(entry.getKey()) * count;
								}
						}
						break;
					case DIRECT:
					case CONNECTION:
					{
						   if( settingsService.timebandSettingsInternational() != null && settingsService.timebandSettingsInternational().get(entry.getKey()) !=null)
						   {
						   directorConnectTimebandPoints += settingsService.timebandSettingsInternational().get(entry.getKey()) * count;
						   }
						}
						break;
				}
			}
			nonStopTimebandPoints = Math.min(MAX_NON_STOP, nonStopTimebandPoints);
			directorConnectTimebandPoints = Math.min(MAX_DIRECT_OR_CONNECT, directorConnectTimebandPoints);
			timebandPoints = nonStopTimebandPoints + directorConnectTimebandPoints;
		}
		return timebandPoints;
	}

	@Override
	public int calculateAverageElapsedTime(Map<DayOfOperation, Integer> outboundElapsedTimes, Map<DayOfOperation, Integer> inboundElapsedTimes, CPP cpp) {
		Map<DayOfOperation, Integer> avgElapsedTimes = new HashMap<>(DayOfOperation.values().length);
		for (DayOfOperation dayOfOperation : DayOfOperation.values()) {
			if (outboundElapsedTimes.get(dayOfOperation) != null && inboundElapsedTimes.get(dayOfOperation) != null) {
				int outboundAvgElapsedTime = outboundElapsedTimes.get(dayOfOperation);
				int inboundAvgElapsedTime = inboundElapsedTimes.get(dayOfOperation);

				int avg =  (int) Math.floor((double) (outboundAvgElapsedTime + inboundAvgElapsedTime) / 2);

				avgElapsedTimes.put(dayOfOperation, avg);
			}
		}

		int count = 0;
		int sum = 0;
		if (cpp.travelType().equals(TravelType.DOMESTIC)) {
			DayOfOperation[] WEEK_DAYS = new DayOfOperation[] {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
			for (DayOfOperation dayOfOperation : WEEK_DAYS) {
				if (avgElapsedTimes.get(dayOfOperation) != null) {
					sum += avgElapsedTimes.get(dayOfOperation);
					count++;
				}
			}

			if (count == 0) {
				return 0;
			}
			return (int) Math.floor((double) (sum)/count);
		} else {
			if (CollectionUtils.isEmpty(avgElapsedTimes.values())) {
				return 0;
			}

			ArrayList<Integer> allAvgElapsedTimes = new ArrayList<>(avgElapsedTimes.values());
			Collections.sort(allAvgElapsedTimes);

			List<Integer> lowestThreeElapsedtimes = null;

			if (allAvgElapsedTimes.size() > 3) {
				lowestThreeElapsedtimes = allAvgElapsedTimes.subList(0, 3);
			} else {
				lowestThreeElapsedtimes = allAvgElapsedTimes;
			}

			return (int) Math.floor((double) lowestThreeElapsedtimes.stream().mapToInt(Integer::intValue).sum() / lowestThreeElapsedtimes.size());
		}
	}

	@Override
	public Map<DayOfOperation, Integer> averageElapsedTimeForMinNumberOfFlights(Map<DayOfOperation, List<Integer>> elapsedTimes, int minNumberOfFlights) {
		Map<DayOfOperation, Integer> avgElapsedTimes = new HashMap<>(DayOfOperation.values().length);
		for (Map.Entry<DayOfOperation, List<Integer>> entry : elapsedTimes.entrySet()) {
			List<Integer> allElapsedTimes = entry.getValue();
			Collections.sort(allElapsedTimes);
			List<Integer> elapsedTimesWithMinFlights = null;
			if (allElapsedTimes.size() >= minNumberOfFlights) {
				elapsedTimesWithMinFlights = allElapsedTimes.subList(0, minNumberOfFlights);
			} else {
				elapsedTimesWithMinFlights = allElapsedTimes;
			}		
			if (!CollectionUtils.isEmpty(elapsedTimesWithMinFlights)) {
				int average = (int) Math.floor((double) elapsedTimesWithMinFlights.stream().mapToInt(Integer::intValue).sum() / elapsedTimesWithMinFlights.size());
				avgElapsedTimes.put(entry.getKey(), average);
			}
		}

		return avgElapsedTimes;
	}


	/**
	 * Initialize time-band counts with zeros
	 * @return
	 */
	private Table<Timeband, ServiceLevel, Integer> initializeTimebandCounts(Group group) {
		Set<Timeband> timebands = settingsService.timebandsByGroup(group);
		Set<Timeband> rows = new HashSet<>(timebands);
		Set<ServiceLevel> cloumns = Sets.newSet(ServiceLevel.values());
		final Table<Timeband, ServiceLevel, Integer> timebandTable = ArrayTable.create(rows, cloumns);
		for (Timeband timeband : timebands) {
			for (ServiceLevel serviceLevel : ServiceLevel.values()) {
				timebandTable.put(timeband, serviceLevel, 0);
			}
		}		
		return timebandTable;
	}

	/**
	 * 
	 */
	@Override
	public int calculateElapsedTimePoints(int elapsedTime, int shortestElapsedTime, boolean nonStopFlag, CPP cpp) {
		final Map<SAEFT, Integer> saeftMap = settingsService.elapsedTimeSettings().get(cpp.travelType(), cpp.getNumberOfFlights());
		SAEFT saeft = SAEFT.findBy(shortestElapsedTime, elapsedTime);
		int points = saeftMap.get(saeft);

		if (nonStopFlag) {
			points += ADDITIONAL_POINTS_FOR_NON_STOP; 
		}
		return points;
	}

	private DirectionalResult emptyResult(Group group) {
		DirectionalResult directionalResult = new DirectionalResult();
		directionalResult .setTimebandCounts(initializeTimebandCounts(group));
		return directionalResult;
	}	

	/**
	 * @param outboundResult
	 * @param inboundResult
	 * @return
	 * 
	 */
	private Map<Timeband, Map<ServiceLevel,Map<Direction,Integer>>> mergeTimebands(Table<Timeband, ServiceLevel, Integer> outboundResult, Table<Timeband, ServiceLevel, Integer> inboundResult) {
		Table<Timeband, ServiceLevel, Map<Direction, Integer>> timebands = HashBasedTable.create();
		for (Timeband timeband : outboundResult.rowKeySet()) {
			for (ServiceLevel serviceLevel : ServiceLevel.values()) {
				Map<Direction, Integer> counts = new HashMap<>();
				counts.put(Direction.OUTBOUND, outboundResult.get(timeband, serviceLevel));
				counts.put(Direction.INBOUND, inboundResult.get(timeband, serviceLevel));
				timebands.put(timeband, serviceLevel, counts);
			}
		}
		return timebands.rowMap();
	}

	/**
	 * Days flights available for a given schedules
	 * @param schedules
	 * @return
	 */
	private Set<DayOfOperation> daysFlightAvailable(Set<Schedule> schedules) {
		Set<DayOfOperation> daysFlightsAvailable = new HashSet<>(DayOfOperation.values().length);
		schedules
		.stream()
		.forEach(schedule -> {
			schedule
			.getOperatingDays()
			.stream()
			.forEach(dayOfOperation -> {
				daysFlightsAvailable.add(dayOfOperation);
			});
		});
		return daysFlightsAvailable;
	}

	/**
	 * Calculate Points for Number of Flights 
	 * 
	 * @param nonStopCount
	 * @param directCount
	 * @param connectionCount
	 * @param cpp
	 * @return
	 */
	private int numberOfFlightsPoints(int nonStopCount, int directCount, int connectionCount, CPP cpp) {
		final Map<ServiceLevel, Double> pointsMap = isSpecificDestination(cpp) ? POINTS_SPECIFIC_DESTINATIONS : POINTS_ALL_DESTINATIONS;
		int points = 0;
		if (nonStopCount < MAX_NUMBER_OF_FLIGHTS) {
			points = (int) (Math.round(nonStopCount * pointsMap.get(ServiceLevel.NON_STOP)));
			if (directCount < (MAX_NUMBER_OF_FLIGHTS - nonStopCount)) {
				points += (int) (Math.round(directCount * pointsMap.get(ServiceLevel.DIRECT)));
				if (connectionCount < (MAX_NUMBER_OF_FLIGHTS - nonStopCount - directCount)) {
					points += (int) (Math.round(connectionCount * pointsMap.get(ServiceLevel.CONNECTION)));
				} else {
					points += (int) (Math.round((MAX_NUMBER_OF_FLIGHTS - nonStopCount - directCount) * pointsMap.get(ServiceLevel.CONNECTION)));
				}
			} else {
				points += (int) (Math.round((MAX_NUMBER_OF_FLIGHTS - nonStopCount)  * pointsMap.get(ServiceLevel.DIRECT)));
			}
		} else {
			points = (int) (Math.round(MAX_NUMBER_OF_FLIGHTS * pointsMap.get(ServiceLevel.NON_STOP)));
		}
		return points;
	}

	/**
	 * 
	 * @param cpp
	 * @return
	 */
	private boolean isSpecificDestination(CPP cpp) {
		return cpp.groupType().equals(GroupType.GROUP_1_DOMESTIC_EC) || cpp.groupType().equals(GroupType.GROUP_1_INTERNATIONAL_EC);
	}

	/**
	 * 
	 * @param schedules
	 * @return
	 * 
	 */
	private Set<String> populateConnections(Set<Schedule> schedules) {
		Set<String> connections = new HashSet<>(3);
		schedules
		.stream()
		.forEach(schedule -> {
			connections.add(schedule.getDepartureAirportCode2());
			connections.add(schedule.getDepartureAirportCode3());
			connections.add(schedule.getDepartureAirportCode4());
			connections.add(schedule.getDepartureAirportCode5());
			connections.add(schedule.getDepartureAirportCode6());
		});
		connections.remove(null);
		return connections;
	}
}
