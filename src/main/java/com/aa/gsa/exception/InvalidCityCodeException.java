package com.aa.gsa.exception;

@SuppressWarnings("serial")
public class InvalidCityCodeException extends PointsProcessorException {

	private String cityCode;
	
	public InvalidCityCodeException(String cityCode) {
		super("Station Code not found for City Code = "+cityCode);
		this.cityCode = cityCode;
	}
	
	public String getCityCode() {
		return cityCode;
	}
}
