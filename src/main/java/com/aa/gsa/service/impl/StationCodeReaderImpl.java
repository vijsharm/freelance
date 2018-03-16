package com.aa.gsa.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aa.gsa.domain.StationCode;
import com.aa.gsa.exception.InvalidCityCodeException;
import com.aa.gsa.exception.InvalidStationCodeException;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.StationCodeReader;

public class StationCodeReaderImpl implements StationCodeReader {
	
	public StationCodeReaderImpl(List<StationCode> stationCodes) {
		populateCityStationPairs(stationCodes);
	}

	/***
	 * Key = cityCodes
	 * Value = List of Station Codes
	 * One-to-Many mapping
	 */
	private Map<String, List<String>> cityStationPairs; 


	/**
	 * key = stationCode
	 * value = cityCode
	 * One-to-One mapping
	 * 
	 */
	private Map<String, String> stationCityPairs;
	
	
	/**
	 * key = stationCode
	 * value = GTM Offsets
	 * One-to-One mapping
	 * 
	 */
	private Map<String, Integer> stationCodeGMTOffsets;


	@Override
	public List<String> getStationsByCityCode(String cityCode) {
		if (!cityStationPairs.containsKey(cityCode) && !stationCityPairs.containsKey(cityCode)) {
			throw new InvalidCityCodeException(cityCode);
		}  else if (!cityStationPairs.containsKey(cityCode) && stationCityPairs.containsKey(cityCode)) {
			return Arrays.asList(cityCode);
		}

		return cityStationPairs.get(cityCode);
	}

	@Override
	public boolean isValidStation(String stationCode) {
		return stationCityPairs.containsKey(stationCode);
	}

	private void populateCityStationPairs(List<StationCode> stationCodes) {
		if (stationCodes == null || stationCodes.isEmpty()) {
			throw new PointsProcessorException("No Station Codes found");
		}
		
		try {
			cityStationPairs = Collections.unmodifiableMap(
					stationCodes
					.stream()
					.collect(Collectors.groupingBy(StationCode :: getCity, Collectors.mapping(StationCode :: getStation, Collectors.toList()))));

			stationCityPairs = Collections.unmodifiableMap(
					stationCodes
					.stream()
					.collect(Collectors.toMap(StationCode :: getStation, StationCode :: getCity)));
			
			stationCodeGMTOffsets = Collections.unmodifiableMap(
					stationCodes
					.stream()
					.collect(Collectors.toMap(StationCode :: getStation, StationCode :: getGmtOffset)));
		} catch(Exception ex) {
			throw new PointsProcessorException("Invalid Station Codes are present");
		}
	}

	@Override
	public int getGMTOffset(String stationCode) {
		if (!isValidStation(stationCode)) {
			throw new InvalidStationCodeException(stationCode);
		}
		
		return stationCodeGMTOffsets.get(stationCode);
	}
}
