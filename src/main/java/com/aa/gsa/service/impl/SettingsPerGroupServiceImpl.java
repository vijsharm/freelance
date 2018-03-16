package com.aa.gsa.service.impl;

import static com.aa.gsa.enums.SAEFT.BETWEEN_31_45;
import static com.aa.gsa.enums.SAEFT.BETWEEN_46_60;
import static com.aa.gsa.enums.SAEFT.BETWEEN_61_75;
import static com.aa.gsa.enums.SAEFT.BETWEEN_76_90;
import static com.aa.gsa.enums.SAEFT.MORE_THAN_90;
import static com.aa.gsa.enums.SAEFT.WITHIN_30;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.domain.settings.CircuitySettings;
import com.aa.gsa.domain.settings.GroundTimeSettings;
import com.aa.gsa.domain.settings.Settings;
import com.aa.gsa.domain.settings.Settings.ApprovedPartner;
import com.aa.gsa.domain.settings.Settings.MiscSettings;
import com.aa.gsa.domain.settings.Settings.TimebandDetail;
import com.aa.gsa.domain.settings.SettingsPerGroup;
import com.aa.gsa.domain.settings.TimebandKeyDomestic;
import com.aa.gsa.domain.settings.TimebandKeyDomesticPerGroup;
import com.aa.gsa.domain.settings.TimebandKeyInternational;
import com.aa.gsa.domain.settings.TimebandKeyInternationalPerGroup;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.GroupType;
import com.aa.gsa.enums.SAEFT;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.SettingsPerGroupService;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class SettingsPerGroupServiceImpl implements SettingsPerGroupService {
	private SettingsPerGroup settings;
	private final Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup;
	private final Table<Group, TravelType, Set<CircuitySettings>> circuitySettingsPerGroup;
	private final Map<Group, Set<Timeband>> timebands;
	private final Map<TimebandKeyDomesticPerGroup, Integer> timebandSettingsDomesticPerGroup;
	private final Map<TimebandKeyInternationalPerGroup, Integer> timebandSettingsInternationalPerGroup;
	private final Set<String> approvedPartners;

	public static final String DOMESTIC = "Domestic";
	public static final  String INTERNATIONAL = "International";
	public static final String NON_STOP = "NON_STOP";
	public static final  String DIRECT = "DIRECT";
	public static final String CONNECTING = "CONNECTING";

	public SettingsPerGroupServiceImpl(CloudantClient cloudantClient, String databaseName, String settingsName) {
		Database database = cloudantClient.database(databaseName, false);
		List<SettingsPerGroup> settingsDoc = database.findByIndex("\"selector\": {\"name\": \""+settingsName+"\"}", SettingsPerGroup.class);

		if (CollectionUtils.isEmpty(settingsDoc)) {
			throw new PointsProcessorException("No valid settings document found with name = "+settingsName);
		} else if (settingsDoc.size() > 1) {
			throw new PointsProcessorException("Multiple settings found with name = "+settingsName);
		}

		settings = settingsDoc.get(0);
		groundTimeSettingsPerGroup = populateGroundTimeSettings();
		circuitySettingsPerGroup = populateCircuitySettings();
		timebands = populateTimebands();
		timebandSettingsDomesticPerGroup = poupulateDomesticTimebandSettings();
		timebandSettingsInternationalPerGroup = populateInternationalTimebandSettings();
		approvedPartners = populateApprovedPartners();
	}

	@Override
	public Map<Group, GroundTimeSettings> groundTimeSettings() {
		return groundTimeSettingsPerGroup;
	}

	@Override
	public Table<Group, TravelType, Set<CircuitySettings>> circuitySettings() {
		return circuitySettingsPerGroup;
	}

	@Override
	public Map<TimebandKeyDomesticPerGroup, Integer> timebandSettingsDomestic() {
		return timebandSettingsDomesticPerGroup;
	}

	@Override
	public Map<TimebandKeyInternationalPerGroup, Integer> timebandSettingsInternational() {
		return timebandSettingsInternationalPerGroup;
	}

	@Override
	public Table<TravelType, Integer, Map<SAEFT, Integer>> elapsedTimeSettings() {
		final Map<SAEFT, Integer> DOMESTIC_6 = new HashMap<>();
		DOMESTIC_6.put(WITHIN_30, 96);
		DOMESTIC_6.put(BETWEEN_31_45, 72);
		DOMESTIC_6.put(BETWEEN_46_60, 48);
		DOMESTIC_6.put(BETWEEN_61_75, 24);
		DOMESTIC_6.put(BETWEEN_76_90, 12);
		DOMESTIC_6.put(MORE_THAN_90, 0);

		final Map<SAEFT, Integer> DOMESTIC_4 = new HashMap<>();
		DOMESTIC_4.put(WITHIN_30, 68);
		DOMESTIC_4.put(BETWEEN_31_45, 51);
		DOMESTIC_4.put(BETWEEN_46_60, 34);
		DOMESTIC_4.put(BETWEEN_61_75, 17);
		DOMESTIC_4.put(BETWEEN_76_90, 8);
		DOMESTIC_4.put(MORE_THAN_90, 0);

		final Map<SAEFT, Integer> DOMESTIC_3 = new HashMap<>();
		DOMESTIC_3.put(WITHIN_30, 48);
		DOMESTIC_3.put(BETWEEN_31_45, 36);
		DOMESTIC_3.put(BETWEEN_46_60, 24);
		DOMESTIC_3.put(BETWEEN_61_75, 12);
		DOMESTIC_3.put(BETWEEN_76_90, 6);
		DOMESTIC_3.put(MORE_THAN_90, 0);

		final Map<SAEFT, Integer> DOMESTIC_1_OR_2 = new HashMap<>();
		DOMESTIC_1_OR_2.put(WITHIN_30, 40);
		DOMESTIC_1_OR_2.put(BETWEEN_31_45, 30);
		DOMESTIC_1_OR_2.put(BETWEEN_46_60, 20);
		DOMESTIC_1_OR_2.put(BETWEEN_61_75, 10);
		DOMESTIC_1_OR_2.put(BETWEEN_76_90, 5);
		DOMESTIC_1_OR_2.put(MORE_THAN_90, 0);		


		final Map<SAEFT, Integer> INTERNATIONAL = new HashMap<>();
		INTERNATIONAL.put(WITHIN_30, 10);
		INTERNATIONAL.put(BETWEEN_31_45, 7);
		INTERNATIONAL.put(BETWEEN_46_60, 5);
		INTERNATIONAL.put(BETWEEN_61_75, 3);
		INTERNATIONAL.put(BETWEEN_76_90, 2);
		INTERNATIONAL.put(MORE_THAN_90, 0);		

		final Table<TravelType, Integer, Map<SAEFT, Integer>> settings = HashBasedTable.create();
		settings.put(TravelType.DOMESTIC, 6, DOMESTIC_6);
		settings.put(TravelType.DOMESTIC, 4, DOMESTIC_4);
		settings.put(TravelType.DOMESTIC, 3, DOMESTIC_3);
		settings.put(TravelType.DOMESTIC, 2, DOMESTIC_1_OR_2);
		settings.put(TravelType.DOMESTIC, 1, DOMESTIC_1_OR_2);
		settings.put(TravelType.INTERNATIONAL, 1, INTERNATIONAL);

		return settings;
	}

	@Override
	public Set<String> approvedPartners() {
		return approvedPartners;
	}

	private Map<Group, GroundTimeSettings> populateGroundTimeSettings() {
		Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup = new HashMap<>(settings.getGroupSettings().size());
		settings
		.getGroupSettings()
		.keySet()
		.stream()
		.forEach(
				group -> {
					MiscSettings miscSettings = settings.getGroupSettings().get(group).getMiscSettings();
					GroundTimeSettings groundTimeSettings = new GroundTimeSettings();
					groundTimeSettings.setDomestic1ConnectionTime(miscSettings.getGroundTimeDomestic());
					groundTimeSettings.setDomesticEC2ConnectionTime(miscSettings.getGroundTimeDomesticEC());
					groundTimeSettings.setInternational1ConnectionTime(miscSettings.getGroundTimeIntExcMexicoAndCanada());
					groundTimeSettings.setInternationalEC2ConnectionTime(miscSettings.getGroundTimeIntEC());
					groundTimeSettings.setCanadaMexico1ConnectionTime(miscSettings.getGroundTimeCanadaAndMexico());
					
					groundTimeSettings.setGroundTimeRulesApplied(miscSettings.getGroundTimeRulesApplied());
					groundTimeSettings.setCircuityRulesApplied(miscSettings.getCircuityRulesApplied());
					
					groundTimeSettings.setNoOfDaysForInternational(miscSettings.getNoOfDaysForInternational());
					groundTimeSettings.setReqOnMondayInt(miscSettings.getReqOnMondayInt());
					groundTimeSettings.setReqOnTuesdayInt(miscSettings.getReqOnTuesdayInt());
                    groundTimeSettings.setReqOnWednesdayInt(miscSettings.getReqOnWednesdayInt());
                    groundTimeSettings.setReqOnThursdayInt(miscSettings.getReqOnThursdayInt());
                    groundTimeSettings.setReqOnFridayInt(miscSettings.getReqOnFridayInt());
                    groundTimeSettings.setReqOnSaturdayInt(miscSettings.getReqOnSaturdayInt());
                    groundTimeSettings.setReqOnSundayInt(miscSettings.getReqOnSundayInt());
					
					groundTimeSettings.setNoOfDaysForDomestic(miscSettings.getNoOfDaysForDomestic());
					groundTimeSettings.setReqOnMonday(miscSettings.getReqOnMonday());
					groundTimeSettings.setReqOnTuesday(miscSettings.getReqOnTuesday());
					groundTimeSettings.setReqOnWednesday(miscSettings.getReqOnWednesday());
					groundTimeSettings.setReqOnThursday(miscSettings.getReqOnThursday());
					groundTimeSettings.setReqOnFriday(miscSettings.getReqOnFriday());
					
					groundTimeSettingsPerGroup.put(group, groundTimeSettings);
				} 
				);
		return groundTimeSettingsPerGroup;
	}

	private Table<Group, TravelType, Set<CircuitySettings>> populateCircuitySettings() {
		Table<Group, TravelType, Set<CircuitySettings>> circuitySettingsPerGroup = HashBasedTable.create();
		settings
		.getGroupSettings()
		.keySet()
		.stream()
		.forEach(group -> {
			final Set<CircuitySettings> domesticCircuitySettings = new HashSet<>();
			final Set<CircuitySettings> internationalCircuitySettings = new HashSet<>();
			settings
			.getGroupSettings()
			.get(group)
			.getCircuities()
			.stream()
			.forEach(circuity -> {
				switch (circuity.getTravelType()) {
				case DOMESTIC: 
					domesticCircuitySettings.add(new CircuitySettings(circuity.getFrom(), circuity.getTo(), circuity.getPercentage()));
					break;

				case INTERNATIONAL:
					internationalCircuitySettings.add(new CircuitySettings(circuity.getFrom(), circuity.getTo(), circuity.getPercentage()));
					break;

				default: throw new PointsProcessorException("Unknown Travel type = "+circuity.getTravelType()+" specified in Circuity Settings");
				}
			});
			circuitySettingsPerGroup.put(group, TravelType.DOMESTIC, domesticCircuitySettings);
			circuitySettingsPerGroup.put(group, TravelType.INTERNATIONAL, internationalCircuitySettings);
		} 
				);
		return circuitySettingsPerGroup;
	}

	private Map<Group, Set<Timeband>> populateTimebands() {
		final Map<Group, Set<Timeband>> timebandsPerGroup = new HashMap<>();
		settings
		.getGroupSettings()
		.keySet()
		.stream()
		.forEach(group -> {
			List<Settings.TimebandObject> timebandObjects = settings.getGroupSettings().get(group).getTimebands();

			TreeSet<Timeband> timebands = new TreeSet<>(new Comparator<Timeband>() {
				@Override
				public int compare(Timeband t1, Timeband t2) {
					return t1.getFrom().compareTo(t2.getFrom());
				}
			});

			for (int outer=0; outer<timebandObjects.size(); outer++) {
				Set<Timeband> uniqueTimebands = new HashSet<>();
				
				for (int inner=0; inner < timebandObjects.get(outer).getTimebandDetails().size(); inner++) {
					TimebandDetail timebandDetail = timebandObjects.get(outer).getTimebandDetails().get(inner);
					LocalTime from = LocalTime.parse(timebandDetail.getFromTime(), DateTimeFormatter.ofPattern("hh:mm a"));
					LocalTime to = LocalTime.parse(timebandDetail.getToTime(), DateTimeFormatter.ofPattern("hh:mm a"));
					
					if (from.equals(to)) {
						throw new PointsProcessorException(group + " "+ timebandObjects.get(outer).getName() + " has invalid timeband with FROM = "+timebandDetail.getFromTime()+ " TO = "+timebandDetail.getToTime());	
					}
					
					Timeband timeband = new Timeband(from, to);
					
					if (!uniqueTimebands.add(timeband)) {
						throw new PointsProcessorException(group + " "+ timebandObjects.get(outer).getName() + " has duplicate timeband with FROM = "+timebandDetail.getFromTime()+ " TO = "+timebandDetail.getToTime());	
					} 
					
					if (outer > 0 && !timebands.contains(timeband)) {
						throw new PointsProcessorException(group + " "+ timebandObjects.get(outer).getName() + " has mismatching timeband with FROM = "+timebandDetail.getFromTime()+ " TO = "+timebandDetail.getToTime());	
					}
					
					timebands.add(new Timeband(from, to));
				}
			}
			
			//set the ordinal from the sorted set
			int ordinal = 1;
			for (Timeband timeband : timebands) {
				timeband.setOrdinal(ordinal++);
			}
			timebandsPerGroup.put(group, timebands);
		});
		return timebandsPerGroup;	
	}

	@Override
	public Set<Timeband> timebandsByGroup(Group group) {
		return timebands.get(group);
	}

	@Override
	public Timeband findTimebandByFromTo(String from, String to, Group group) {
		LocalTime fromTime = LocalTime.parse(from, DateTimeFormatter.ofPattern("hh:mm a"));
		LocalTime toTime = LocalTime.parse(to, DateTimeFormatter.ofPattern("hh:mm a"));
		return timebands
				.get(group)
				.stream()
				.filter(timeband -> timeband.getFrom().equals(fromTime) &&  timeband.getTo().equals(toTime))
				.findFirst()
				.get();
	}

	@Override
	public Timeband findTimebandByRange(String hour, String minute, Group group) {
		for (Timeband timeband : timebands.get(group)) {
			LocalTime fromTime =  timeband.getFrom();
			LocalTime toTime = timeband.getTo();
			LocalTime flightTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));

			if (fromTime.isBefore(toTime)) {//time comparison
				if (flightTime.equals(fromTime) || flightTime.equals(toTime) || (flightTime.isAfter(fromTime) && flightTime.isBefore(toTime))) {
					return timeband;
				}
			} else {//date comparison
				LocalDateTime fromDateTime =  LocalDateTime.of(LocalDate.now(), fromTime);
				LocalDateTime toDateTime =  LocalDateTime.of(LocalDate.now().plusDays(1), toTime);
				LocalDateTime flightDateTime = flightTime.isBefore(fromTime) ? LocalDateTime.of(LocalDate.now().plusDays(1), flightTime) : LocalDateTime.of(LocalDate.now(), flightTime);

				if (flightDateTime.equals(fromDateTime) ||  flightDateTime.equals(toDateTime) || (flightDateTime.isAfter(fromDateTime) && flightDateTime.isBefore(toDateTime))) {
					return timeband;
				}
			}
		}
		return null;
	}

	private Map<TimebandKeyDomesticPerGroup, Integer> poupulateDomesticTimebandSettings() {
		final Map<TimebandKeyDomesticPerGroup, Integer> timebandSettingsPerGroup = new HashMap<>(50);
		settings
		.getGroupSettings()
		.keySet()
		.stream()
		.forEach(group -> {
			settings
			.getGroupSettings()
			.get(group)
			.getTimebands()
			.stream()
			.filter(timeband -> timeband.getTravelType().equals(DOMESTIC))
			.forEach(timeband -> {
				timeband
				.getTimebandDetails()
				.stream()
				.forEach(timebandDetail -> {
					final Timeband timebandEnum = findTimebandByFromTo(timebandDetail.getFromTime(), timebandDetail.getToTime(), group);
					if (timebandEnum == null) {
						throw new PointsProcessorException("Invalid Timeband specified with FROM = "+timebandDetail.getFromTime()+ " TO = "+ timebandDetail.getToTime());  
					}
					TimebandKeyDomestic key = new TimebandKeyDomestic(timebandEnum, getServiceLevel(timeband.getServiceType()), !timeband.isEastToWest(), timeband.isEastToWestIncThreeTimezones());
					TimebandKeyDomesticPerGroup timebandKeyDomesticPerGroup = new TimebandKeyDomesticPerGroup(group, key);
					timebandSettingsPerGroup.put(timebandKeyDomesticPerGroup, timebandDetail.getPoints());
				});
			});
		});	
		return timebandSettingsPerGroup;
	}

	private Map<TimebandKeyInternationalPerGroup, Integer> populateInternationalTimebandSettings() {
		final Map<TimebandKeyInternationalPerGroup, Integer> timebandSettingsPerGroup = new HashMap<>(50);
		settings
		.getGroupSettings()
		.keySet()
		.stream()
		.forEach(group -> {
			settings
			.getGroupSettings()
			.get(group)
			.getTimebands()
			.stream()
			.filter(timeband -> timeband.getTravelType().equals(INTERNATIONAL))
			.forEach(timeband -> {
				timeband
				.getTimebandDetails()
				.stream()
				.forEach(timebandDetail -> {
					final Timeband timebandEnum = findTimebandByFromTo(timebandDetail.getFromTime(), timebandDetail.getToTime(), group);
					if (timebandEnum == null) {
						throw new PointsProcessorException("Invalid Timeband specified with FROM = "+timebandDetail.getFromTime()+ " TO = "+ timebandDetail.getToTime());  
					}
					GroupType groupType =  GroupType.findByValue(timeband.getGroupType());
					if (groupType == null) {
						throw new PointsProcessorException("Invalid GroupType = "+timeband.getGroupType()+" specified in Timeband settings");  
					}
					TimebandKeyInternational key = new TimebandKeyInternational(timebandEnum, getServiceLevel(timeband.getServiceType()), groupType);
					TimebandKeyInternationalPerGroup timebandKeyInternationalPerGroup = new TimebandKeyInternationalPerGroup(group, key);
					timebandSettingsPerGroup.put(timebandKeyInternationalPerGroup, timebandDetail.getPoints());
				});
			});
		});	
		return timebandSettingsPerGroup;
	}


	private ServiceLevel getServiceLevel(String serviceType) {
		ServiceLevel serviceLevel = null;
		if (serviceType.equals(NON_STOP)) {
			serviceLevel = ServiceLevel.NON_STOP;
		} else if(serviceType.equals(DIRECT)) {
			serviceLevel = ServiceLevel.DIRECT;
		}  else if(serviceType.equals(CONNECTING)) {
			serviceLevel = ServiceLevel.CONNECTION;
		} else {
			throw new PointsProcessorException("Unknown Service Level = "+serviceType+" specifed in Timebands");  
		}
		return serviceLevel;
	}

	private Set<String> populateApprovedPartners() {
		Set<String> approvedPartners = new HashSet<>(settings.getApprovedPartners().size());
		for(ApprovedPartner approvedPartner : settings.getApprovedPartners()) {
			approvedPartners.add(approvedPartner.getName());
		}

		if (CollectionUtils.isEmpty(approvedPartners)) {
			throw new PointsProcessorException("Approved partners list is empty");
		}

		return approvedPartners;
	}
}
