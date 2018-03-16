package com.aa.gsa.exception;

import com.aa.gsa.domain.CPP;

@SuppressWarnings("serial")
public class InvalidMinServiceLevelException extends PointsProcessorException {
	
	public InvalidMinServiceLevelException(CPP cpp) {
		super("'"+cpp.getMinService()+"'" + " is an invalid 'min_service' value for CPP with item_no="+cpp.getItemNumber() + " between "+cpp.getOriginAirport() + " <=> "+cpp.getDestinationAirport());
	}	
	
}

