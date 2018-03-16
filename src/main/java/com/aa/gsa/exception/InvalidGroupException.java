package com.aa.gsa.exception;

import com.aa.gsa.domain.CPP;

@SuppressWarnings("serial")
public class InvalidGroupException extends RuntimeException {
	
	public InvalidGroupException(CPP cpp) {
		super("'"+cpp.getGroupNumber() +"'"+ " is an invalid 'GroupNumber' for CPP with item_no="+cpp.getItemNumber() + " between "+cpp.getOriginAirport() + " <=> "+cpp.getDestinationAirport());
	}	
}
