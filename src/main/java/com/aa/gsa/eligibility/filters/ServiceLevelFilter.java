package com.aa.gsa.eligibility.filters;

import static com.aa.gsa.enums.GroupType.GROUP_1_DOMESTIC_EC;
import static com.aa.gsa.enums.GroupType.GROUP_1_INTERNATIONAL_EC;
import static com.aa.gsa.enums.GroupType.GROUP_1_INTERNATIONAL_EC_WITH_BUSINESS_CLASS;
import static com.aa.gsa.enums.ServiceLevel.DIRECT;
import static com.aa.gsa.enums.ServiceLevel.NON_STOP;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.EligibilityCategory;
import com.aa.gsa.exception.InvalidMinServiceLevelException;
import com.aa.gsa.service.EligibilityResultService;

public class ServiceLevelFilter implements EligibilityFilter {

    private EligibilityResultService eligibilityResultService;

    public ServiceLevelFilter(EligibilityResultService eligibilityResultService)
    {
        this.eligibilityResultService = eligibilityResultService;
    }
    
	@Override
	public Set<Schedule> filter(Set<Schedule> schedules, CPP cpp) {
		if( CollectionUtils.isEmpty(schedules)) {
			return schedules;
		}
		
		return schedules
			.stream()
			.filter(schedule -> serviceLevelFilter(schedule, cpp))
			.collect(Collectors.toSet());
	}

	private boolean serviceLevelFilter(Schedule schedule, CPP cpp) {
		switch (cpp.minServiceLevel()) {
			case NON_STOP:
			{
			    if(NON_STOP.equals(schedule.getServiceLevel()))
			    {
			        return true;
			    }
			    
			    eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.ONE, "Required service level is " + NON_STOP 
			                                                                            + " but schedule has " + schedule.getServiceLevel() + " service level.");
			    return false;
			}
			case DIRECT:
			{
			    if(NON_STOP.equals(schedule.getServiceLevel()) || DIRECT.equals(schedule.getServiceLevel()))
			    {
			        return true;
			    }
			    
			    eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.ONE, "Required service level is " + NON_STOP + " or " + DIRECT
                                                                                            + " but schedule has " + schedule.getServiceLevel() + " service level.");
			    return false;
			}
			case CONNECTION:
				{
					if (NON_STOP.equals(schedule.getServiceLevel()) || DIRECT.equals(schedule.getServiceLevel())) {
						return true;
					} else {
						switch (cpp.travelType()) {
						case DOMESTIC:
							if (!GROUP_1_DOMESTIC_EC.equals(cpp.groupType())) {
								return filterByMaxNoOfConnections(cpp, schedule, 1);
							} else {
								return filterByMaxNoOfConnections(cpp, schedule, 2);
							}
							
						case INTERNATIONAL: 
							if (!GROUP_1_INTERNATIONAL_EC.equals(cpp.groupType()) && !GROUP_1_INTERNATIONAL_EC_WITH_BUSINESS_CLASS.equals(cpp.groupType())) {
								return filterByMaxNoOfConnections(cpp, schedule, 1);
							} else {
								return filterByMaxNoOfConnections(cpp, schedule, 2);
							}
				     }
				   }
				}
			default:
				throw new InvalidMinServiceLevelException(cpp);
		}
	}
	
	private boolean filterByMaxNoOfConnections(CPP cpp, Schedule schedule, int maxNumberOfConnections) {
		if (schedule.getNumberOfConnections() == null) {
			return true;
		}
		
		if(schedule.getNumberOfConnections() <= maxNumberOfConnections) {
			return true;
		}
		
		eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.ONE, "Maximum no of connection should be less than or equals to " + maxNumberOfConnections
		                                                                                + " but schedule has " + schedule.getNumberOfConnections() + " no of connections.");
		return false;
	}

	@Override
	public EligibilityCategory getCategory() {
		return EligibilityCategory.MIN_SERVICE;
	}
}