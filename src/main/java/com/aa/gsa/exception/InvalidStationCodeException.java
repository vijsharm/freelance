package com.aa.gsa.exception;

@SuppressWarnings("serial")
public class InvalidStationCodeException extends PointsProcessorException {
	
	public InvalidStationCodeException(String stationCode) {
		super(stationCode + "is not a valid station code");
	}
	
}
