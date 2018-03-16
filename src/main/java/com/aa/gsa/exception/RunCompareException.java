package com.aa.gsa.exception;

@SuppressWarnings("serial")
public class RunCompareException extends RuntimeException {

	public RunCompareException() {

	}

	public RunCompareException(String message) {
		super(message);		
	}
	
	public RunCompareException(Throwable cause) {
		super(cause);
	}

	public RunCompareException(String message, Throwable cause) {
		super(message, cause);
	}
}
