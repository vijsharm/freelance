package com.aa.gsa.service;

import java.util.List;

import com.aa.gsa.exception.InvalidCityCodeException;

/**
 * 
 * Station Code Reader
 * @author 940914
 * 
 */
public interface StationCodeReader {

	/**
	 * 
	 * @param cityCode
	 * @return List of StationCodes for a cityCode
	 */
	List<String> getStationsByCityCode(String cityCode) throws InvalidCityCodeException;


	/**
	 * Determines if @param stationCode is a valid Station Code
	 * 
	 * @param stationCode
	 * @return
	 */
	public boolean isValidStation(String stationCode);
	
	
	
	public int getGMTOffset(String stationCode);
}
