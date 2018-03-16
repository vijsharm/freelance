package com.aa.gsa.exception;

import com.aa.gsa.domain.CPP;

@SuppressWarnings("serial")
public class CircuitySettingNotFoundException extends PointsProcessorException {

	public CircuitySettingNotFoundException(CPP cpp, Integer gcdMiles) {
		super("No Circuity settings found with GCD Miles = "+gcdMiles +" for CPP with item_no "+cpp.getItemNumber() + " between "+cpp.getOriginAirport() + " <=> "+cpp.getDestinationAirport());
	}
	
}
