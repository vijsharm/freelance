package com.aa.gsa.exception;

@SuppressWarnings("serial")
public class PointsProcessorException extends RuntimeException {

	public PointsProcessorException() {

	}

	public PointsProcessorException(String message) {
		super(message);
	}

	public PointsProcessorException(Throwable cause) {
		super(cause);
	}

	public PointsProcessorException(String message, Throwable cause) {
		super(message, cause);
	} 
}
