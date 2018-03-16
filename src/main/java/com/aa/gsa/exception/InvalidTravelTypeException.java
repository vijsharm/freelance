package com.aa.gsa.exception;

import com.aa.gsa.domain.CPP;

@SuppressWarnings("serial")
public class InvalidTravelTypeException extends PointsProcessorException {

	public InvalidTravelTypeException(CPP cpp) {
		super("'"+cpp.getTypeOfTravel()+"'" + " is an invalid 'domestic' value for CPP with item_no="+cpp.getItemNumber() + " between "+cpp.getOriginAirport() + " <=> "+cpp.getDestinationAirport());
	}	

}
