package com.aa.gsa.eligibility.filters;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.settings.CircuitySettings;
import com.aa.gsa.domain.settings.GroundTimeSettings;
import com.aa.gsa.enums.EligibilityCategory;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.CircuitySettingNotFoundException;
import com.aa.gsa.service.EligibilityResultService;
import com.google.common.collect.Table;

public class CircuityFilter implements EligibilityFilter {

	private Table<Group, TravelType, Set<CircuitySettings>> circuitySettingsPerGroup;
	
	private EligibilityResultService eligibilityResultService;
	
	private Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup;
	
	public CircuityFilter(Table<Group, TravelType, Set<CircuitySettings>> circuitySettingsPerGroup, EligibilityResultService eligibilityResultService, Map<Group, GroundTimeSettings> groundTimeSettingsPerGroup) {
		this.circuitySettingsPerGroup = circuitySettingsPerGroup;		
		this.eligibilityResultService = eligibilityResultService;
		this.groundTimeSettingsPerGroup = groundTimeSettingsPerGroup;
	}
	
	@Override
	public Set<Schedule> filter(Set<Schedule> schedules, CPP cpp) {
		if (CollectionUtils.isEmpty(schedules) || !groundTimeSettingsPerGroup.get(cpp.group()).getCircuityRulesApplied()) {
			return schedules;
		}
		
		return schedules
				.stream()
				.filter(schedule -> predicate(schedule, cpp))
				.collect(Collectors.toSet());
	}
	
	boolean predicate(Schedule schedule, CPP cpp) {
		TravelType travelType = cpp.travelType(); 
		Set<CircuitySettings> circuitySettings = circuitySettingsPerGroup.get(cpp.group(), travelType);
		
		List<Integer> percentages = circuitySettings
				.stream()
				.filter(circuitySetting -> circuitySetting.getFrom() <= schedule.getGCDMiles() && circuitySetting.getTo() >= schedule.getGCDMiles())
				.map(circuitySetting -> circuitySetting.getPercentage())
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(percentages) || percentages.size() > 1) {
			throw new CircuitySettingNotFoundException(cpp, schedule.getGCDMiles());
		}
		
		cpp.setMaxCircuity(percentages.get(0));
		
		double percentage = ((double) schedule.getItineraryMiles()/schedule.getGCDMiles()) * 100;
		
		int actualCircuityPercentage = (int) Math.floor(percentage) ;
			
		if(actualCircuityPercentage <= cpp.getMaxCircuity())
        {
            return true;
        }
        
        eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.FIVE, "Required circuity is " + cpp.getMaxCircuity() + " but actual circuity is " + actualCircuityPercentage);
        return false;
	}

	@Override
	public EligibilityCategory getCategory() {
		return EligibilityCategory.CIRCUITY;
	};
}