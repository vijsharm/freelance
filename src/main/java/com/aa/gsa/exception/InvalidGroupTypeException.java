package com.aa.gsa.exception;

import com.aa.gsa.domain.CPP;

@SuppressWarnings("serial")
public class InvalidGroupTypeException extends RuntimeException {

	public InvalidGroupTypeException(CPP cpp) {
		super("'"+cpp.getGroupType() +"'"+ " is an invalid 'GroupType' for CPP with item_no="+cpp.getItemNumber() + " between "+cpp.getOriginAirport() + " <=> "+cpp.getDestinationAirport());
	}	
	
}
