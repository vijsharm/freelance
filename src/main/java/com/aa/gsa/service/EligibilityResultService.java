package com.aa.gsa.service;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;

public interface EligibilityResultService {

	void save(CPP cpp, Schedule schedule, boolean eligible, EligibilityReasonCode code, String eligibilityReason);
}

