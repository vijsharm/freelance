package com.aa.gsa.processor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Payload;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.result.DrilldownResult;
import com.aa.gsa.domain.result.Result;
import com.aa.gsa.eligibility.filters.CircuityFilter;
import com.aa.gsa.eligibility.filters.EligibilityFilter;
import com.aa.gsa.eligibility.filters.GroundTimeFilter;
import com.aa.gsa.eligibility.filters.ServiceLevelFilter;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.google.common.collect.Table;

/**
 * 
 * @author 940914
 * 
 */
public class ScheduleFilterProcessor implements ItemProcessor<Payload,Payload> {
	
    private EligibilityFilter filter;
    private boolean initializeResult = false;
	
	@Autowired
	public ScheduleFilterProcessor(EligibilityFilter filter) {
		this.filter = filter;
	}

	@Autowired
    public ScheduleFilterProcessor(EligibilityFilter filter, boolean initializeResult) {
        this.filter = filter;
        this.initializeResult = initializeResult;
    }
	
	@Override
	public Payload process(Payload payload) throws Exception {
		for (Airline airline : Airline.values()) {
		    
		    if(initializeResult)
            {
                //initialize result
                payload.getResults().put(airline, initializeResult(airline));
                payload.getResultsNoCodeshare().put(airline, initializeResult(airline));
            }
		    
			filterSchedules(payload.getSchedules(), payload.getResults().get(airline), airline, payload.getCpp());
            filterSchedules(payload.getSchedulesNoCodeshare(), payload.getResultsNoCodeshare().get(airline), airline, payload.getCpp());
		}
		return payload;
	}
	
	private void filterSchedules(Table<Airline, Direction, Set<Schedule>> schedules, Result result, Airline airline, CPP cpp) {
		if (schedules == null) {
			return;
		}

		if (schedules.rowKeySet().contains(airline)) {
			Set<Schedule> outbound = schedules.get(airline, Direction.OUTBOUND);
			Set<Schedule> inbound = schedules.get(airline, Direction.INBOUND);
			
			Set<Schedule> filteredOutbound = filter.filter(outbound, cpp);
			Set<Schedule> filteredInbound = filter.filter(inbound, cpp);
			
            if (result != null && (CollectionUtils.isEmpty(filteredOutbound) || CollectionUtils.isEmpty(filteredInbound))) {
                result.setEligible(false);
                
                if(filter instanceof ServiceLevelFilter)
                {
                    result.setEligibilityReasonCode(EligibilityReasonCode.ONE.name());
                    result.setEligibilityReason("Non-Stop/Direct/Connection req failure");
                }
                if(filter instanceof GroundTimeFilter)
                {
                    result.setEligibilityReasonCode(EligibilityReasonCode.FOUR.name());
                    result.setEligibilityReason("Ground time req failure");
                }
                else if(filter instanceof CircuityFilter)
                {
                    result.setEligibilityReasonCode(EligibilityReasonCode.FIVE.name());
                    result.setEligibilityReason("Circuity req Failure");
                }
            }
			
			schedules.put(airline, Direction.OUTBOUND, (CollectionUtils.isEmpty(filteredOutbound) ? new HashSet<>(1) : filteredOutbound));
			schedules.put(airline, Direction.INBOUND, (CollectionUtils.isEmpty(filteredInbound) ? new HashSet<>(1) : filteredInbound));
		}
	}
	
	private Result initializeResult(Airline airline) {
        return airline.equals(Airline.AA) ? new DrilldownResult() : new Result();
    }
}