package com.aa.gsa.processor;

import static com.aa.gsa.enums.Direction.INBOUND;
import static com.aa.gsa.enums.Direction.OUTBOUND;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Payload;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.result.Result;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.EligibilityService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Table;

public class EligibilityProcessor implements ItemProcessor<Payload,Payload> {

	private Log log = LogFactory.getLog(EligibilityProcessor.class);

	public static final String ALASKA = "AK";

	public static final String HAWAII = "HI";

	private EligibilityService eligibilityService;
	
	private EligibilityResultService eligibilityResultService;

	public EligibilityProcessor(EligibilityService eligibilityService, EligibilityResultService eligibilityResultService) {
		this.eligibilityService = eligibilityService;
		this.eligibilityResultService = eligibilityResultService;
	}

	@Override
	public Payload process(Payload payload) throws Exception {		
		for (Airline airline : Airline.values()) {
			//initialize result
			//payload.getResults().put(airline, initializeResult(airline));
			//payload.getResultsNoCodeshare().put(airline, initializeResult(airline));
			
			log.debug("master eligibility");
			evaluateEligibility(payload.getSchedules(), payload.getResults().get(airline), airline, payload.getCpp());
			log.debug("no codeshare eligibility");
			evaluateEligibility(payload.getSchedulesNoCodeshare(), payload.getResultsNoCodeshare().get(airline), airline, payload.getCpp());
		}	
		return payload;
	}

//	private Result initializeResult(Airline airline) {
//		return airline.equals(Airline.AA) ? new DrilldownResult() : new Result();
//	}

	protected void evaluateEligibility(Table<Airline, Direction, Set<Schedule>> schedules, Result result, Airline airline, CPP cpp) {
        //#1, #4 & #5
        if (schedules == null) {
            if(result.getEligibilityReason() == null)
            {
                result.setEligible(false);
                result.setEligibilityReasonCode(EligibilityReasonCode.ZERO.name());
                result.setEligibilityReason("Unexpected condition");
            }
            return;
        }

        Set<Schedule> outbound = schedules.get(airline, OUTBOUND);
        Set<Schedule> inbound = schedules.get(airline, INBOUND);
        
        //#1, #4 & #5
        if (CollectionUtils.isEmpty(outbound) || CollectionUtils.isEmpty(inbound)) {
            if(result.getEligibilityReason() == null)
            {
                result.setEligible(false);
                result.setEligibilityReasonCode(EligibilityReasonCode.ZERO.name());
                result.setEligibilityReason("No schedules found");
            }
            return;
        }

        Set<DayOfOperation> outboundFlightTimes = new LinkedHashSet<>();
        Set<DayOfOperation> inboundFlightTimes = new LinkedHashSet<>();
        boolean outboundEligible = eligibilityService.hasMinNoOfFlights(outbound,cpp,outboundFlightTimes);
        boolean inboundEligible = eligibilityService.hasMinNoOfFlights(inbound,cpp,inboundFlightTimes);
        
        //#2
        if(!outboundEligible || !inboundEligible)
        {
            result.setEligible(false);
            result.setEligibilityReasonCode(EligibilityReasonCode.TWO.name());
            result.setEligibilityReason("Minimum no of flights requirement failure");
            return;
        }

        outboundEligible  = eligibilityService.hasMetMinimumService(outbound,cpp,outboundFlightTimes);
        inboundEligible   = eligibilityService.hasMetMinimumService(inbound,cpp,inboundFlightTimes);
        
        if (log.isDebugEnabled()) {
            if (outboundEligible && inboundEligible && airline.equals(Airline.AA)) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.setSerializationInclusion(Include.NON_NULL);
                    log.debug("outbound eligible = "+mapper.writeValueAsString(outbound));
                    log.debug("inbound eligible = "+mapper.writeValueAsString(inbound));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //#3
        if(!outboundEligible || !inboundEligible)
        {
            result.setEligible(false);
            result.setEligibilityReasonCode(EligibilityReasonCode.THREE.name());
            result.setEligibilityReason("Frequency of flights per week req failure");
            return;
        }
        
        result.setEligible(true);
        
        Set<Schedule> passedSchedules = new HashSet<>(outbound);
        passedSchedules.addAll(inbound);
        
        for(Schedule schedule: outbound)
        {
            eligibilityResultService.save(cpp, schedule, true, null, null);
        }
    }
}