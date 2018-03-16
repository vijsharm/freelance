package com.aa.gsa.eligibility.filters;

import java.util.Set;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.EligibilityCategory;

public interface EligibilityFilter {

	Set<Schedule> filter(Set<Schedule> schedules, CPP cpp);
	
	EligibilityCategory getCategory();
}
