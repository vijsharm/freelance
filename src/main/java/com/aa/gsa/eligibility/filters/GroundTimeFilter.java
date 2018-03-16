package com.aa.gsa.eligibility.filters;

import static com.aa.gsa.enums.GroupType.GROUP_1_5TH_FREEDOM;
import static com.aa.gsa.enums.GroupType.GROUP_1_DOMESTIC_EC;
import static com.aa.gsa.enums.GroupType.GROUP_1_HAVANA;
import static com.aa.gsa.enums.GroupType.GROUP_1_INTERNATIONAL_EC;
import static com.aa.gsa.enums.GroupType.GROUP_1_INTERNATIONAL_EC_WITH_BUSINESS_CLASS;
import static com.aa.gsa.enums.TravelType.DOMESTIC;
import static com.aa.gsa.enums.TravelType.INTERNATIONAL;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.settings.GroundTimeSettings;
import com.aa.gsa.enums.EligibilityCategory;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.GroupType;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.InvalidMinServiceLevelException;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.ScheduleService;

public class GroundTimeFilter implements EligibilityFilter {

	private ScheduleService scheduleService;
	
	private EligibilityResultService eligibilityResultService;
	
	private Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup;

	public GroundTimeFilter(ScheduleService scheduleService, EligibilityResultService eligibilityResultService, Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup) {
		this.scheduleService = scheduleService;
		this.eligibilityResultService = eligibilityResultService;
		this.groundTimeSettingsPerGroup = groundTimeSettingsPerGroup;
	}

	@Override
	public Set<Schedule> filter(Set<Schedule> schedules, CPP cpp) {
		if (CollectionUtils.isEmpty(schedules) || !groundTimeSettingsPerGroup.get(cpp.group()).getGroundTimeRulesApplied()) {
			return schedules;
		}

		int requiredGroundTime = requiredGroundTime(cpp);
		
		cpp.setMaxGroundTime(requiredGroundTime);

		return schedules
				.stream()
				.filter(schedule -> predicate(cpp, schedule, requiredGroundTime))
				.collect(Collectors.toSet());
	}
	
	private boolean predicate(CPP cpp, Schedule schedule, int requiredGroundTime) {
		int actualGroundTime = (int) scheduleService.calculateGroundTimeInMinutes(schedule);
		
		if(actualGroundTime <= requiredGroundTime)
		{
		    return true;
		}
		
		eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.FOUR, "Required groud time is " + requiredGroundTime + " but actual groud time is " + actualGroundTime);
		return false;
	}

	private int requiredGroundTime(CPP cpp) {
		Group group = cpp.group();
		GroupType groupType = cpp.groupType(); 
		TravelType travelType =  cpp.travelType();
		ServiceLevel serviceType = cpp.minServiceLevel(); 
		GroundTimeSettings groundTimeSettings = groundTimeSettingsPerGroup.get(group);

		switch (serviceType) {
			case NON_STOP : return 0;
			case DIRECT:
			case CONNECTION:
				if (DOMESTIC.equals(travelType)) {
					if (GROUP_1_DOMESTIC_EC.equals(groupType)) {
						return groundTimeSettings.getDomesticEC2ConnectionTime();	
					} else {
						return groundTimeSettings.getDomestic1ConnectionTime();
					}
				} else if (INTERNATIONAL.equals(travelType)) {
					if ((cpp.getDestinationCountry() !=null &&(cpp.getDestinationCountry().equalsIgnoreCase("CANADA") || cpp.getDestinationCountry().equalsIgnoreCase("MEXICO")))) {//TODO: May need to re-visit Canada-Mexico clause
						return groundTimeSettings.getCanadaMexico1ConnectionTime();
					} else if (GROUP_1_INTERNATIONAL_EC.equals(groupType) || 
							GROUP_1_INTERNATIONAL_EC_WITH_BUSINESS_CLASS.equals(groupType) ||
							GROUP_1_5TH_FREEDOM.equals(groupType) || 
							GROUP_1_HAVANA.equals(groupType)) {
						return groundTimeSettings.getInternationalEC2ConnectionTime();
					} else {
						return groundTimeSettings.getInternational1ConnectionTime();
					}
				}
			default:
				throw new InvalidMinServiceLevelException(cpp);
		}
	}

	@Override
	public EligibilityCategory getCategory() {
		return EligibilityCategory.GROUND_TIME;
	}
}