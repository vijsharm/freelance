package com.aa.gsa.exception;

import com.aa.gsa.domain.Schedule;

@SuppressWarnings("serial")
public class UnknownServiceLevelException extends PointsProcessorException {
	
	public UnknownServiceLevelException(Schedule schedule) {
		super("ServiceLevel could not be determined for schedule with ODkey = "+schedule.getODkey() + " with departureAirportCode = "+schedule.getDepartureAirportCode()+ " and arrivalAirportCode = "+schedule.getArrivalAirportCode());
	}

}
