package com.aa.gsa.exception;

import com.aa.gsa.domain.Schedule;

@SuppressWarnings("serial")
public class InvalidNumberOfStopsException extends PointsProcessorException {

	public InvalidNumberOfStopsException(Schedule schedule) {
		super(schedule.getNumberOfStops() + " is not a valid 'numberOfStops' for schedule with ODkey = "+schedule.getODkey() + " with departureAirportCode = "+schedule.getDepartureAirportCode()+ " and arrivalAirportCode = "+schedule.getArrivalAirportCode());
	}
}
