package com.aa.gsa.service.impl;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.NoSQLService;

public class EligibilityResultServiceImpl implements EligibilityResultService
{
    private NoSQLService noSQLService;
    private int runId;

    public EligibilityResultServiceImpl(NoSQLService noSQLService, int runId)
    {
        this.noSQLService   = noSQLService;
        this.runId          = runId;
    }

    @Override
    public void save(CPP cpp, Schedule schedule, boolean eligible, EligibilityReasonCode code, String eligiblilityReason)
    {
        /*EligibilityResult result = new EligibilityResult();
        result.setRunId(runId);
        result.setItemNo(cpp.getItemNumber());
        result.setOrig(cpp.getOriginAirport());
        result.setDest(cpp.getDestinationAirport());

        result.setEligible(eligible);
        result.setEligibilityReasonCode(code != null ? code.name() : null);
        result.setEligiblilityReason(eligiblilityReason);
        
        result.setDepartureAirportCode(schedule.getDepartureAirportCode());
        result.setArrivalAirportCode(schedule.getArrivalAirportCode());
        result.setArrivalHour(schedule.getArrivalHour());
        result.setArrivalMinute(schedule.getArrivalMinute());
        result.setArrivalGMTHour(schedule.getArrivalGMTHour());
        result.setArrivalGMTMinute(schedule.getArrivalGMTMinute());
        result.setDepartureHour(schedule.getDepartureHour());
        result.setDepartureMinute(schedule.getDepartureMinute());
        result.setDepartureGMTHour(schedule.getDepartureGMTHour());
        result.setDepartureGMTMinute(schedule.getDepartureGMTMinute());
        
        result.setNumberOfConnections(schedule.getNumberOfConnections());
        result.setNumberOfStops(schedule.getNumberOfStops());
        result.setCategoryType(schedule.getCategoryType());
        result.setFrequencyCount(schedule.getFrequencyCount());
        
        result.setLegAirline1(schedule.getLegAirline1());
        result.setLegFlightNumber1(schedule.getLegFlightNumber1());
        result.setDepartureAirportCode1(schedule.getDepartureAirportCode1());
        result.setArrivalAirportCode1(schedule.getArrivalAirportCode1());
        
        result.setLegAirline2(schedule.getLegAirline2());
        result.setLegFlightNumber2(schedule.getLegFlightNumber2());
        result.setDepartureAirportCode2(schedule.getDepartureAirportCode2());
        result.setArrivalAirportCode2(schedule.getArrivalAirportCode2());
        
        noSQLService.save(result);*/
    }
}
