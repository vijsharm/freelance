package com.aa.gsa.exception;

@SuppressWarnings("serial")
public class ODKeyNotFoundException extends PointsProcessorException {

	public ODKeyNotFoundException(Integer ODKey) {
		super("No schedule found with ODKey = "+ODKey);
	}	
	
}
