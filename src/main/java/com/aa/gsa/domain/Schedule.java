package com.aa.gsa.domain;

import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.ServiceLevel;

public class Schedule {
	private String serviceType;
	private Integer ODkey;
	private String NSflag;
	private String IBflag;
	private String departureAirportCode;
	private String departureCityCode;
	private String departureAirportCountryCode;
	private String arrivalAirportCode;
	private String arrivalCityCode;
	private String arrivalAirportCountryCode;
	private String arrivalHour;
	private String arrivalMinute;
	private String arrivalGMTHour;
	private String arrivalGMTMinute;
	private String departureHour;
	private String departureMinute;
	private String departureGMTHour;
	private String departureGMTMinute;
	private Integer elapsedMinutes;
	private Integer serviceTypeCode;
	private Integer numberOfConnections;
	private Integer numberOfStops;
	private String categoryType;
	private Integer frequencyCount;
	private Integer GCDMiles;
	private Integer itineraryMiles;

	private Integer legKey1;
	private String legAirline1;
	private Integer legFlightNumber1;
	private Integer legItineraryNumber1;
	private Integer legNumber1;
	private String departureAirportCode1;
	private String arrivalAirportCode1;
	private String departureHour1;
	private String departureMinute1;
	private String arrivalHour1;
	private String arrivalMinute1;
	private String equipCode1;

	private Integer legKey2;
	private String legAirline2;
	private Integer legFlightNumber2;
	private Integer legItineraryNumber2;
	private Integer legNumber2;
	private String departureAirportCode2;
	private String arrivalAirportCode2;
	private String departureHour2;
	private String departureMinute2;
	private String arrivalHour2;
	private String arrivalMinute2;
	private String equipCode2;

	private Integer legKey3;
	private String legAirline3;
	private Integer legFlightNumber3;
	private Integer legItineraryNumber3;
	private Integer legNumber3;
	private String departureAirportCode3;
	private String arrivalAirportCode3;
	private String departureHour3;
	private String departureMinute3;
	private String arrivalHour3;
	private String arrivalMinute3;
	private String equipCode3;

	private Integer legKey4;
	private String legAirline4;
	private Integer legFlightNumber4;
	private Integer legItineraryNumber4;
	private Integer legNumber4;
	private String departureAirportCode4;
	private String arrivalAirportCode4;
	private String departureHour4;
	private String departureMinute4;
	private String arrivalHour4;
	private String arrivalMinute4;
	private String equipCode4;

	private Integer legKey5;
	private String legAirline5;
	private Integer legFlightNumber5;
	private Integer legItineraryNumber5;
	private Integer legNumber5;
	private String departureAirportCode5;
	private String arrivalAirportCode5;
	private String departureHour5;
	private String departureMinute5;
	private String arrivalHour5;
	private String arrivalMinute5;
	private String equipCode5;

	private Integer legKey6;
	private String legAirline6;
	private Integer legFlightNumber6;
	private Integer legItineraryNumber6;
	private Integer legNumber6;
	private String departureAirportCode6;
	private String arrivalAirportCode6;
	private String departureHour6;
	private String departureMinute6;
	private String arrivalHour6;
	private String arrivalMinute6;
	private String equipCode6;

	private String daysOfOperation;
	private Integer operationalSchedSvcKey;

	private String legType1;
	private String duplicateType1;

	private String legType2;
	private String duplicateType2;

	private String legType3;
	private String duplicateType3;

	private String legType4;
	private String duplicateType4;

	private String legType5;
	private String duplicateType5;

	private String legType6;
	private String duplicateType6;

	private Integer operationalODKey;

	private String _id;
	private String _rev;

	//derived from the view
	private ServiceLevel serviceLevel;

	//derived: When duplicate records found, days Of Operation are merged
	private Set<DayOfOperation> operatingDays;

	private Timeband timeband;
	
	/*
	 * This is used in case of repeat legs which one to pick
	 */
	
	private Integer points;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getODkey() {
		return ODkey;
	}

	public void setODkey(Integer oDkey) {
		ODkey = oDkey;
	}

	public String getNSflag() {
		return NSflag;
	}

	public void setNSflag(String nSflag) {
		NSflag = nSflag;
	}

	public String getIBflag() {
		return IBflag;
	}

	public void setIBflag(String iBflag) {
		IBflag = iBflag;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getDepartureCityCode() {
		return departureCityCode;
	}

	public void setDepartureCityCode(String departureCityCode) {
		this.departureCityCode = departureCityCode;
	}

	public String getDepartureAirportCountryCode() {
		return departureAirportCountryCode;
	}

	public void setDepartureAirportCountryCode(String departureAirportCountryCode) {
		this.departureAirportCountryCode = departureAirportCountryCode;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public String getArrivalCityCode() {
		return arrivalCityCode;
	}

	public void setArrivalCityCode(String arrivalCityCode) {
		this.arrivalCityCode = arrivalCityCode;
	}

	public String getArrivalAirportCountryCode() {
		return arrivalAirportCountryCode;
	}

	public void setArrivalAirportCountryCode(String arrivalAirportCountryCode) {
		this.arrivalAirportCountryCode = arrivalAirportCountryCode;
	}

	public String getArrivalHour() {
		return arrivalHour;
	}

	public void setArrivalHour(String arrivalHour) {
		this.arrivalHour = arrivalHour;
	}

	public String getArrivalMinute() {
		return arrivalMinute;
	}

	public void setArrivalMinute(String arrivalMinute) {
		this.arrivalMinute = arrivalMinute;
	}

	public String getArrivalGMTHour() {
		return arrivalGMTHour;
	}

	public void setArrivalGMTHour(String arrivalGMTHour) {
		this.arrivalGMTHour = arrivalGMTHour;
	}

	public String getArrivalGMTMinute() {
		return arrivalGMTMinute;
	}

	public void setArrivalGMTMinute(String arrivalGMTMinute) {
		this.arrivalGMTMinute = arrivalGMTMinute;
	}

	public String getDepartureHour() {
		return departureHour;
	}

	public void setDepartureHour(String departureHour) {
		this.departureHour = departureHour;
	}

	public String getDepartureMinute() {
		return departureMinute;
	}

	public void setDepartureMinute(String departureMinute) {
		this.departureMinute = departureMinute;
	}

	public String getDepartureGMTHour() {
		return departureGMTHour;
	}

	public void setDepartureGMTHour(String departureGMTHour) {
		this.departureGMTHour = departureGMTHour;
	}

	public String getDepartureGMTMinute() {
		return departureGMTMinute;
	}

	public void setDepartureGMTMinute(String departureGMTMinute) {
		this.departureGMTMinute = departureGMTMinute;
	}

	public Integer getElapsedMinutes() {
		return elapsedMinutes;
	}

	public void setElapsedMinutes(Integer elapsedMinutes) {
		this.elapsedMinutes = elapsedMinutes;
	}

	public Integer getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(Integer serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public Integer getNumberOfConnections() {
		return numberOfConnections;
	}

	public void setNumberOfConnections(Integer numberOfConnections) {
		this.numberOfConnections = numberOfConnections;
	}

	public Integer getNumberOfStops() {
		return numberOfStops;
	}

	public void setNumberOfStops(Integer numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * Use getOperatingFrequency() instead, this is a derived field obtained by merging duplicate schedules
	 * @return
	 */
	@Deprecated  
	public Integer getFrequencyCount() {
		return frequencyCount;
	}

	public void setFrequencyCount(Integer frequencyCount) {
		this.frequencyCount = frequencyCount;
	}

	public Integer getGCDMiles() {
		return GCDMiles;
	}

	public void setGCDMiles(Integer gCDMiles) {
		GCDMiles = gCDMiles;
	}

	public Integer getItineraryMiles() {
		return itineraryMiles;
	}

	public void setItineraryMiles(Integer itineraryMiles) {
		this.itineraryMiles = itineraryMiles;
	}

	public Integer getLegKey1() {
		return legKey1;
	}

	public void setLegKey1(Integer legKey1) {
		this.legKey1 = legKey1;
	}

	public String getLegAirline1() {
		return legAirline1;
	}

	public Airline getLegAirline1Enum() {
		return Airline.valueOf(legAirline1);
	}

	public void setLegAirline1(String legAirline1) {
		this.legAirline1 = legAirline1;
	}

	public Integer getLegFlightNumber1() {
		return legFlightNumber1;
	}

	public void setLegFlightNumber1(Integer legFlightNumber1) {
		this.legFlightNumber1 = legFlightNumber1;
	}

	public Integer getLegItineraryNumber1() {
		return legItineraryNumber1;
	}

	public void setLegItineraryNumber1(Integer legItineraryNumber1) {
		this.legItineraryNumber1 = legItineraryNumber1;
	}

	public Integer getLegNumber1() {
		return legNumber1;
	}

	public void setLegNumber1(Integer legNumber1) {
		this.legNumber1 = legNumber1;
	}

	public String getDepartureAirportCode1() {
		return departureAirportCode1;
	}

	public void setDepartureAirportCode1(String departureAirportCode1) {
		this.departureAirportCode1 = departureAirportCode1;
	}

	public String getArrivalAirportCode1() {
		return arrivalAirportCode1;
	}

	public void setArrivalAirportCode1(String arrivalAirportCode1) {
		this.arrivalAirportCode1 = arrivalAirportCode1;
	}

	public String getDepartureHour1() {
		return departureHour1;
	}

	public void setDepartureHour1(String departureHour1) {
		this.departureHour1 = departureHour1;
	}

	public String getDepartureMinute1() {
		return departureMinute1;
	}

	public void setDepartureMinute1(String departureMinute1) {
		this.departureMinute1 = departureMinute1;
	}

	public String getArrivalHour1() {
		return arrivalHour1;
	}

	public void setArrivalHour1(String arrivalHour1) {
		this.arrivalHour1 = arrivalHour1;
	}

	public String getArrivalMinute1() {
		return arrivalMinute1;
	}

	public void setArrivalMinute1(String arrivalMinute1) {
		this.arrivalMinute1 = arrivalMinute1;
	}

	public String getEquipCode1() {
		return equipCode1;
	}

	public void setEquipCode1(String equipCode1) {
		this.equipCode1 = equipCode1;
	}

	public Integer getLegKey2() {
		return legKey2;
	}

	public void setLegKey2(Integer legKey2) {
		this.legKey2 = legKey2;
	}

	public String getLegAirline2() {
		return legAirline2;
	}

	public void setLegAirline2(String legAirline2) {
		this.legAirline2 = legAirline2;
	}

	public Integer getLegFlightNumber2() {
		return legFlightNumber2;
	}

	public void setLegFlightNumber2(Integer legFlightNumber2) {
		this.legFlightNumber2 = legFlightNumber2;
	}

	public Integer getLegItineraryNumber2() {
		return legItineraryNumber2;
	}

	public void setLegItineraryNumber2(Integer legItineraryNumber2) {
		this.legItineraryNumber2 = legItineraryNumber2;
	}

	public Integer getLegNumber2() {
		return legNumber2;
	}

	public void setLegNumber2(Integer legNumber2) {
		this.legNumber2 = legNumber2;
	}

	public String getDepartureAirportCode2() {
		return departureAirportCode2;
	}

	public void setDepartureAirportCode2(String departureAirportCode2) {
		this.departureAirportCode2 = departureAirportCode2;
	}

	public String getArrivalAirportCode2() {
		return arrivalAirportCode2;
	}

	public void setArrivalAirportCode2(String arrivalAirportCode2) {
		this.arrivalAirportCode2 = arrivalAirportCode2;
	}

	public String getDepartureHour2() {
		return departureHour2;
	}

	public void setDepartureHour2(String departureHour2) {
		this.departureHour2 = departureHour2;
	}

	public String getDepartureMinute2() {
		return departureMinute2;
	}

	public void setDepartureMinute2(String departureMinute2) {
		this.departureMinute2 = departureMinute2;
	}

	public String getArrivalHour2() {
		return arrivalHour2;
	}

	public void setArrivalHour2(String arrivalHour2) {
		this.arrivalHour2 = arrivalHour2;
	}

	public String getArrivalMinute2() {
		return arrivalMinute2;
	}

	public void setArrivalMinute2(String arrivalMinute2) {
		this.arrivalMinute2 = arrivalMinute2;
	}

	public String getEquipCode2() {
		return equipCode2;
	}

	public void setEquipCode2(String equipCode2) {
		this.equipCode2 = equipCode2;
	}

	public Integer getLegKey3() {
		return legKey3;
	}

	public void setLegKey3(Integer legKey3) {
		this.legKey3 = legKey3;
	}

	public String getLegAirline3() {
		return legAirline3;
	}

	public void setLegAirline3(String legAirline3) {
		this.legAirline3 = legAirline3;
	}

	public Integer getLegFlightNumber3() {
		return legFlightNumber3;
	}

	public void setLegFlightNumber3(Integer legFlightNumber3) {
		this.legFlightNumber3 = legFlightNumber3;
	}

	public Integer getLegItineraryNumber3() {
		return legItineraryNumber3;
	}

	public void setLegItineraryNumber3(Integer legItineraryNumber3) {
		this.legItineraryNumber3 = legItineraryNumber3;
	}

	public Integer getLegNumber3() {
		return legNumber3;
	}

	public void setLegNumber3(Integer legNumber3) {
		this.legNumber3 = legNumber3;
	}

	public String getDepartureAirportCode3() {
		return departureAirportCode3;
	}

	public void setDepartureAirportCode3(String departureAirportCode3) {
		this.departureAirportCode3 = departureAirportCode3;
	}

	public String getArrivalAirportCode3() {
		return arrivalAirportCode3;
	}

	public void setArrivalAirportCode3(String arrivalAirportCode3) {
		this.arrivalAirportCode3 = arrivalAirportCode3;
	}

	public String getDepartureHour3() {
		return departureHour3;
	}

	public void setDepartureHour3(String departureHour3) {
		this.departureHour3 = departureHour3;
	}

	public String getDepartureMinute3() {
		return departureMinute3;
	}

	public void setDepartureMinute3(String departureMinute3) {
		this.departureMinute3 = departureMinute3;
	}

	public String getArrivalHour3() {
		return arrivalHour3;
	}

	public void setArrivalHour3(String arrivalHour3) {
		this.arrivalHour3 = arrivalHour3;
	}

	public String getArrivalMinute3() {
		return arrivalMinute3;
	}

	public void setArrivalMinute3(String arrivalMinute3) {
		this.arrivalMinute3 = arrivalMinute3;
	}

	public String getEquipCode3() {
		return equipCode3;
	}

	public void setEquipCode3(String equipCode3) {
		this.equipCode3 = equipCode3;
	}

	public Integer getLegKey4() {
		return legKey4;
	}

	public void setLegKey4(Integer legKey4) {
		this.legKey4 = legKey4;
	}

	public String getLegAirline4() {
		return legAirline4;
	}

	public void setLegAirline4(String legAirline4) {
		this.legAirline4 = legAirline4;
	}

	public Integer getLegFlightNumber4() {
		return legFlightNumber4;
	}

	public void setLegFlightNumber4(Integer legFlightNumber4) {
		this.legFlightNumber4 = legFlightNumber4;
	}

	public Integer getLegItineraryNumber4() {
		return legItineraryNumber4;
	}

	public void setLegItineraryNumber4(Integer legItineraryNumber4) {
		this.legItineraryNumber4 = legItineraryNumber4;
	}

	public Integer getLegNumber4() {
		return legNumber4;
	}

	public void setLegNumber4(Integer legNumber4) {
		this.legNumber4 = legNumber4;
	}

	public String getDepartureAirportCode4() {
		return departureAirportCode4;
	}

	public void setDepartureAirportCode4(String departureAirportCode4) {
		this.departureAirportCode4 = departureAirportCode4;
	}

	public String getArrivalAirportCode4() {
		return arrivalAirportCode4;
	}

	public void setArrivalAirportCode4(String arrivalAirportCode4) {
		this.arrivalAirportCode4 = arrivalAirportCode4;
	}

	public String getDepartureHour4() {
		return departureHour4;
	}

	public void setDepartureHour4(String departureHour4) {
		this.departureHour4 = departureHour4;
	}

	public String getDepartureMinute4() {
		return departureMinute4;
	}

	public void setDepartureMinute4(String departureMinute4) {
		this.departureMinute4 = departureMinute4;
	}

	public String getArrivalHour4() {
		return arrivalHour4;
	}

	public void setArrivalHour4(String arrivalHour4) {
		this.arrivalHour4 = arrivalHour4;
	}

	public String getArrivalMinute4() {
		return arrivalMinute4;
	}

	public void setArrivalMinute4(String arrivalMinute4) {
		this.arrivalMinute4 = arrivalMinute4;
	}

	public String getEquipCode4() {
		return equipCode4;
	}

	public void setEquipCode4(String equipCode4) {
		this.equipCode4 = equipCode4;
	}

	public Integer getLegKey5() {
		return legKey5;
	}

	public void setLegKey5(Integer legKey5) {
		this.legKey5 = legKey5;
	}

	public String getLegAirline5() {
		return legAirline5;
	}

	public void setLegAirline5(String legAirline5) {
		this.legAirline5 = legAirline5;
	}

	public Integer getLegFlightNumber5() {
		return legFlightNumber5;
	}

	public void setLegFlightNumber5(Integer legFlightNumber5) {
		this.legFlightNumber5 = legFlightNumber5;
	}

	public Integer getLegItineraryNumber5() {
		return legItineraryNumber5;
	}

	public void setLegItineraryNumber5(Integer legItineraryNumber5) {
		this.legItineraryNumber5 = legItineraryNumber5;
	}

	public Integer getLegNumber5() {
		return legNumber5;
	}

	public void setLegNumber5(Integer legNumber5) {
		this.legNumber5 = legNumber5;
	}

	public String getDepartureAirportCode5() {
		return departureAirportCode5;
	}

	public void setDepartureAirportCode5(String departureAirportCode5) {
		this.departureAirportCode5 = departureAirportCode5;
	}

	public String getArrivalAirportCode5() {
		return arrivalAirportCode5;
	}

	public void setArrivalAirportCode5(String arrivalAirportCode5) {
		this.arrivalAirportCode5 = arrivalAirportCode5;
	}

	public String getDepartureHour5() {
		return departureHour5;
	}

	public void setDepartureHour5(String departureHour5) {
		this.departureHour5 = departureHour5;
	}

	public String getDepartureMinute5() {
		return departureMinute5;
	}

	public void setDepartureMinute5(String departureMinute5) {
		this.departureMinute5 = departureMinute5;
	}

	public String getArrivalHour5() {
		return arrivalHour5;
	}

	public void setArrivalHour5(String arrivalHour5) {
		this.arrivalHour5 = arrivalHour5;
	}

	public String getArrivalMinute5() {
		return arrivalMinute5;
	}

	public void setArrivalMinute5(String arrivalMinute5) {
		this.arrivalMinute5 = arrivalMinute5;
	}

	public String getEquipCode5() {
		return equipCode5;
	}

	public void setEquipCode5(String equipCode5) {
		this.equipCode5 = equipCode5;
	}

	public Integer getLegKey6() {
		return legKey6;
	}

	public void setLegKey6(Integer legKey6) {
		this.legKey6 = legKey6;
	}

	public String getLegAirline6() {
		return legAirline6;
	}

	public void setLegAirline6(String legAirline6) {
		this.legAirline6 = legAirline6;
	}

	public Integer getLegFlightNumber6() {
		return legFlightNumber6;
	}

	public void setLegFlightNumber6(Integer legFlightNumber6) {
		this.legFlightNumber6 = legFlightNumber6;
	}

	public Integer getLegItineraryNumber6() {
		return legItineraryNumber6;
	}

	public void setLegItineraryNumber6(Integer legItineraryNumber6) {
		this.legItineraryNumber6 = legItineraryNumber6;
	}

	public Integer getLegNumber6() {
		return legNumber6;
	}

	public void setLegNumber6(Integer legNumber6) {
		this.legNumber6 = legNumber6;
	}

	public String getDepartureAirportCode6() {
		return departureAirportCode6;
	}

	public void setDepartureAirportCode6(String departureAirportCode6) {
		this.departureAirportCode6 = departureAirportCode6;
	}

	public String getArrivalAirportCode6() {
		return arrivalAirportCode6;
	}

	public void setArrivalAirportCode6(String arrivalAirportCode6) {
		this.arrivalAirportCode6 = arrivalAirportCode6;
	}

	public String getDepartureHour6() {
		return departureHour6;
	}

	public void setDepartureHour6(String departureHour6) {
		this.departureHour6 = departureHour6;
	}

	public String getDepartureMinute6() {
		return departureMinute6;
	}

	public void setDepartureMinute6(String departureMinute6) {
		this.departureMinute6 = departureMinute6;
	}

	public String getArrivalHour6() {
		return arrivalHour6;
	}

	public void setArrivalHour6(String arrivalHour6) {
		this.arrivalHour6 = arrivalHour6;
	}

	public String getArrivalMinute6() {
		return arrivalMinute6;
	}

	public void setArrivalMinute6(String arrivalMinute6) {
		this.arrivalMinute6 = arrivalMinute6;
	}

	public String getEquipCode6() {
		return equipCode6;
	}

	public void setEquipCode6(String equipCode6) {
		this.equipCode6 = equipCode6;
	}

	public String getDaysOfOperation() {
		return daysOfOperation;
	}

	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}

	public Integer getOperationalSchedSvcKey() {
		return operationalSchedSvcKey;
	}

	public void setOperationalSchedSvcKey(Integer operationalSchedSvcKey) {
		this.operationalSchedSvcKey = operationalSchedSvcKey;
	}

	public String getLegType1() {
		return legType1;
	}

	public void setLegType1(String legType1) {
		this.legType1 = legType1;
	}

	public String getDuplicateType1() {
		return duplicateType1;
	}

	public void setDuplicateType1(String duplicateType1) {
		this.duplicateType1 = duplicateType1;
	}

	public String getLegType2() {
		return legType2;
	}

	public void setLegType2(String legType2) {
		this.legType2 = legType2;
	}

	public String getDuplicateType2() {
		return duplicateType2;
	}

	public void setDuplicateType2(String duplicateType2) {
		this.duplicateType2 = duplicateType2;
	}

	public String getLegType3() {
		return legType3;
	}

	public void setLegType3(String legType3) {
		this.legType3 = legType3;
	}

	public String getDuplicateType3() {
		return duplicateType3;
	}

	public void setDuplicateType3(String duplicateType3) {
		this.duplicateType3 = duplicateType3;
	}

	public String getLegType4() {
		return legType4;
	}

	public void setLegType4(String legType4) {
		this.legType4 = legType4;
	}

	public String getDuplicateType4() {
		return duplicateType4;
	}

	public void setDuplicateType4(String duplicateType4) {
		this.duplicateType4 = duplicateType4;
	}

	public String getLegType5() {
		return legType5;
	}

	public void setLegType5(String legType5) {
		this.legType5 = legType5;
	}

	public String getDuplicateType5() {
		return duplicateType5;
	}

	public void setDuplicateType5(String duplicateType5) {
		this.duplicateType5 = duplicateType5;
	}

	public String getLegType6() {
		return legType6;
	}

	public void setLegType6(String legType6) {
		this.legType6 = legType6;
	}

	public String getDuplicateType6() {
		return duplicateType6;
	}

	public void setDuplicateType6(String duplicateType6) {
		this.duplicateType6 = duplicateType6;
	}

	public Integer getOperationalODKey() {
		return operationalODKey;
	}

	public void setOperationalODKey(Integer operationalODKey) {
		this.operationalODKey = operationalODKey;
	}

	public ServiceLevel getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(ServiceLevel serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Timeband getTimeband() {
		return timeband;
	}

	public void setTimeband(Timeband timeband) {
		this.timeband = timeband;
	}
	
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	//_id and rev
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}	

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}	

	public Set<DayOfOperation> getOperatingDays() {
		return operatingDays;
	}

	public void setOperatingDays(Set<DayOfOperation> operatingDays) {
		this.operatingDays = operatingDays;
	}	

	public int getOperatingFrequency() {
		return !CollectionUtils.isEmpty(operatingDays) ?  operatingDays.size() : 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((GCDMiles == null) ? 0 : GCDMiles.hashCode());
		result = prime * result + ((IBflag == null) ? 0 : IBflag.hashCode());
		result = prime * result + ((NSflag == null) ? 0 : NSflag.hashCode());
		result = prime * result + ((arrivalAirportCode == null) ? 0 : arrivalAirportCode.hashCode());
		result = prime * result + ((arrivalAirportCode1 == null) ? 0 : arrivalAirportCode1.hashCode());
		result = prime * result + ((arrivalAirportCode2 == null) ? 0 : arrivalAirportCode2.hashCode());
		result = prime * result + ((arrivalAirportCode3 == null) ? 0 : arrivalAirportCode3.hashCode());
		result = prime * result + ((arrivalAirportCode4 == null) ? 0 : arrivalAirportCode4.hashCode());
		result = prime * result + ((arrivalAirportCode5 == null) ? 0 : arrivalAirportCode5.hashCode());
		result = prime * result + ((arrivalAirportCode6 == null) ? 0 : arrivalAirportCode6.hashCode());
		result = prime * result + ((arrivalAirportCountryCode == null) ? 0 : arrivalAirportCountryCode.hashCode());
		result = prime * result + ((arrivalCityCode == null) ? 0 : arrivalCityCode.hashCode());
		result = prime * result + ((arrivalGMTHour == null) ? 0 : arrivalGMTHour.hashCode());
		result = prime * result + ((arrivalGMTMinute == null) ? 0 : arrivalGMTMinute.hashCode());
		result = prime * result + ((arrivalHour == null) ? 0 : arrivalHour.hashCode());
		result = prime * result + ((arrivalHour1 == null) ? 0 : arrivalHour1.hashCode());
		result = prime * result + ((arrivalHour2 == null) ? 0 : arrivalHour2.hashCode());
		result = prime * result + ((arrivalHour3 == null) ? 0 : arrivalHour3.hashCode());
		result = prime * result + ((arrivalHour4 == null) ? 0 : arrivalHour4.hashCode());
		result = prime * result + ((arrivalHour5 == null) ? 0 : arrivalHour5.hashCode());
		result = prime * result + ((arrivalHour6 == null) ? 0 : arrivalHour6.hashCode());
		result = prime * result + ((arrivalMinute == null) ? 0 : arrivalMinute.hashCode());
		result = prime * result + ((arrivalMinute1 == null) ? 0 : arrivalMinute1.hashCode());
		result = prime * result + ((arrivalMinute2 == null) ? 0 : arrivalMinute2.hashCode());
		result = prime * result + ((arrivalMinute3 == null) ? 0 : arrivalMinute3.hashCode());
		result = prime * result + ((arrivalMinute4 == null) ? 0 : arrivalMinute4.hashCode());
		result = prime * result + ((arrivalMinute5 == null) ? 0 : arrivalMinute5.hashCode());
		result = prime * result + ((arrivalMinute6 == null) ? 0 : arrivalMinute6.hashCode());
		result = prime * result + ((categoryType == null) ? 0 : categoryType.hashCode());
		result = prime * result + ((departureAirportCode == null) ? 0 : departureAirportCode.hashCode());
		result = prime * result + ((departureAirportCode1 == null) ? 0 : departureAirportCode1.hashCode());
		result = prime * result + ((departureAirportCode2 == null) ? 0 : departureAirportCode2.hashCode());
		result = prime * result + ((departureAirportCode3 == null) ? 0 : departureAirportCode3.hashCode());
		result = prime * result + ((departureAirportCode4 == null) ? 0 : departureAirportCode4.hashCode());
		result = prime * result + ((departureAirportCode5 == null) ? 0 : departureAirportCode5.hashCode());
		result = prime * result + ((departureAirportCode6 == null) ? 0 : departureAirportCode6.hashCode());
		result = prime * result + ((departureAirportCountryCode == null) ? 0 : departureAirportCountryCode.hashCode());
		result = prime * result + ((departureCityCode == null) ? 0 : departureCityCode.hashCode());
		result = prime * result + ((departureGMTHour == null) ? 0 : departureGMTHour.hashCode());
		result = prime * result + ((departureGMTMinute == null) ? 0 : departureGMTMinute.hashCode());
		result = prime * result + ((departureHour == null) ? 0 : departureHour.hashCode());
		result = prime * result + ((departureHour1 == null) ? 0 : departureHour1.hashCode());
		result = prime * result + ((departureHour2 == null) ? 0 : departureHour2.hashCode());
		result = prime * result + ((departureHour3 == null) ? 0 : departureHour3.hashCode());
		result = prime * result + ((departureHour4 == null) ? 0 : departureHour4.hashCode());
		result = prime * result + ((departureHour5 == null) ? 0 : departureHour5.hashCode());
		result = prime * result + ((departureHour6 == null) ? 0 : departureHour6.hashCode());
		result = prime * result + ((departureMinute == null) ? 0 : departureMinute.hashCode());
		result = prime * result + ((departureMinute1 == null) ? 0 : departureMinute1.hashCode());
		result = prime * result + ((departureMinute2 == null) ? 0 : departureMinute2.hashCode());
		result = prime * result + ((departureMinute3 == null) ? 0 : departureMinute3.hashCode());
		result = prime * result + ((departureMinute4 == null) ? 0 : departureMinute4.hashCode());
		result = prime * result + ((departureMinute5 == null) ? 0 : departureMinute5.hashCode());
		result = prime * result + ((departureMinute6 == null) ? 0 : departureMinute6.hashCode());
		result = prime * result + ((duplicateType1 == null) ? 0 : duplicateType1.hashCode());
		result = prime * result + ((duplicateType2 == null) ? 0 : duplicateType2.hashCode());
		result = prime * result + ((duplicateType3 == null) ? 0 : duplicateType3.hashCode());
		result = prime * result + ((duplicateType4 == null) ? 0 : duplicateType4.hashCode());
		result = prime * result + ((duplicateType5 == null) ? 0 : duplicateType5.hashCode());
		result = prime * result + ((duplicateType6 == null) ? 0 : duplicateType6.hashCode());
		result = prime * result + ((elapsedMinutes == null) ? 0 : elapsedMinutes.hashCode());
		result = prime * result + ((equipCode1 == null) ? 0 : equipCode1.hashCode());
		result = prime * result + ((equipCode2 == null) ? 0 : equipCode2.hashCode());
		result = prime * result + ((equipCode3 == null) ? 0 : equipCode3.hashCode());
		result = prime * result + ((equipCode4 == null) ? 0 : equipCode4.hashCode());
		result = prime * result + ((equipCode5 == null) ? 0 : equipCode5.hashCode());
		result = prime * result + ((equipCode6 == null) ? 0 : equipCode6.hashCode());
		result = prime * result + ((itineraryMiles == null) ? 0 : itineraryMiles.hashCode());
		result = prime * result + ((legAirline1 == null) ? 0 : legAirline1.hashCode());
		result = prime * result + ((legAirline2 == null) ? 0 : legAirline2.hashCode());
		result = prime * result + ((legAirline3 == null) ? 0 : legAirline3.hashCode());
		result = prime * result + ((legAirline4 == null) ? 0 : legAirline4.hashCode());
		result = prime * result + ((legAirline5 == null) ? 0 : legAirline5.hashCode());
		result = prime * result + ((legAirline6 == null) ? 0 : legAirline6.hashCode());
		result = prime * result + ((legFlightNumber1 == null) ? 0 : legFlightNumber1.hashCode());
		result = prime * result + ((legFlightNumber2 == null) ? 0 : legFlightNumber2.hashCode());
		result = prime * result + ((legFlightNumber3 == null) ? 0 : legFlightNumber3.hashCode());
		result = prime * result + ((legFlightNumber4 == null) ? 0 : legFlightNumber4.hashCode());
		result = prime * result + ((legFlightNumber5 == null) ? 0 : legFlightNumber5.hashCode());
		result = prime * result + ((legFlightNumber6 == null) ? 0 : legFlightNumber6.hashCode());
		result = prime * result + ((legNumber1 == null) ? 0 : legNumber1.hashCode());
		result = prime * result + ((legNumber2 == null) ? 0 : legNumber2.hashCode());
		result = prime * result + ((legNumber3 == null) ? 0 : legNumber3.hashCode());
		result = prime * result + ((legNumber4 == null) ? 0 : legNumber4.hashCode());
		result = prime * result + ((legNumber5 == null) ? 0 : legNumber5.hashCode());
		result = prime * result + ((legNumber6 == null) ? 0 : legNumber6.hashCode());
		result = prime * result + ((legType1 == null) ? 0 : legType1.hashCode());
		result = prime * result + ((legType2 == null) ? 0 : legType2.hashCode());
		result = prime * result + ((legType3 == null) ? 0 : legType3.hashCode());
		result = prime * result + ((legType4 == null) ? 0 : legType4.hashCode());
		result = prime * result + ((legType5 == null) ? 0 : legType5.hashCode());
		result = prime * result + ((legType6 == null) ? 0 : legType6.hashCode());
		result = prime * result + ((numberOfConnections == null) ? 0 : numberOfConnections.hashCode());
		result = prime * result + ((numberOfStops == null) ? 0 : numberOfStops.hashCode());
		result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
		result = prime * result + ((serviceTypeCode == null) ? 0 : serviceTypeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (GCDMiles == null) {
			if (other.GCDMiles != null)
				return false;
		} else if (!GCDMiles.equals(other.GCDMiles))
			return false;
		if (IBflag == null) {
			if (other.IBflag != null)
				return false;
		} else if (!IBflag.equals(other.IBflag))
			return false;
		if (NSflag == null) {
			if (other.NSflag != null)
				return false;
		} else if (!NSflag.equals(other.NSflag))
			return false;
		if (arrivalAirportCode == null) {
			if (other.arrivalAirportCode != null)
				return false;
		} else if (!arrivalAirportCode.equals(other.arrivalAirportCode))
			return false;
		if (arrivalAirportCode1 == null) {
			if (other.arrivalAirportCode1 != null)
				return false;
		} else if (!arrivalAirportCode1.equals(other.arrivalAirportCode1))
			return false;
		if (arrivalAirportCode2 == null) {
			if (other.arrivalAirportCode2 != null)
				return false;
		} else if (!arrivalAirportCode2.equals(other.arrivalAirportCode2))
			return false;
		if (arrivalAirportCode3 == null) {
			if (other.arrivalAirportCode3 != null)
				return false;
		} else if (!arrivalAirportCode3.equals(other.arrivalAirportCode3))
			return false;
		if (arrivalAirportCode4 == null) {
			if (other.arrivalAirportCode4 != null)
				return false;
		} else if (!arrivalAirportCode4.equals(other.arrivalAirportCode4))
			return false;
		if (arrivalAirportCode5 == null) {
			if (other.arrivalAirportCode5 != null)
				return false;
		} else if (!arrivalAirportCode5.equals(other.arrivalAirportCode5))
			return false;
		if (arrivalAirportCode6 == null) {
			if (other.arrivalAirportCode6 != null)
				return false;
		} else if (!arrivalAirportCode6.equals(other.arrivalAirportCode6))
			return false;
		if (arrivalAirportCountryCode == null) {
			if (other.arrivalAirportCountryCode != null)
				return false;
		} else if (!arrivalAirportCountryCode.equals(other.arrivalAirportCountryCode))
			return false;
		if (arrivalCityCode == null) {
			if (other.arrivalCityCode != null)
				return false;
		} else if (!arrivalCityCode.equals(other.arrivalCityCode))
			return false;
		if (arrivalGMTHour == null) {
			if (other.arrivalGMTHour != null)
				return false;
		} else if (!arrivalGMTHour.equals(other.arrivalGMTHour))
			return false;
		if (arrivalGMTMinute == null) {
			if (other.arrivalGMTMinute != null)
				return false;
		} else if (!arrivalGMTMinute.equals(other.arrivalGMTMinute))
			return false;
		if (arrivalHour == null) {
			if (other.arrivalHour != null)
				return false;
		} else if (!arrivalHour.equals(other.arrivalHour))
			return false;
		if (arrivalHour1 == null) {
			if (other.arrivalHour1 != null)
				return false;
		} else if (!arrivalHour1.equals(other.arrivalHour1))
			return false;
		if (arrivalHour2 == null) {
			if (other.arrivalHour2 != null)
				return false;
		} else if (!arrivalHour2.equals(other.arrivalHour2))
			return false;
		if (arrivalHour3 == null) {
			if (other.arrivalHour3 != null)
				return false;
		} else if (!arrivalHour3.equals(other.arrivalHour3))
			return false;
		if (arrivalHour4 == null) {
			if (other.arrivalHour4 != null)
				return false;
		} else if (!arrivalHour4.equals(other.arrivalHour4))
			return false;
		if (arrivalHour5 == null) {
			if (other.arrivalHour5 != null)
				return false;
		} else if (!arrivalHour5.equals(other.arrivalHour5))
			return false;
		if (arrivalHour6 == null) {
			if (other.arrivalHour6 != null)
				return false;
		} else if (!arrivalHour6.equals(other.arrivalHour6))
			return false;
		if (arrivalMinute == null) {
			if (other.arrivalMinute != null)
				return false;
		} else if (!arrivalMinute.equals(other.arrivalMinute))
			return false;
		if (arrivalMinute1 == null) {
			if (other.arrivalMinute1 != null)
				return false;
		} else if (!arrivalMinute1.equals(other.arrivalMinute1))
			return false;
		if (arrivalMinute2 == null) {
			if (other.arrivalMinute2 != null)
				return false;
		} else if (!arrivalMinute2.equals(other.arrivalMinute2))
			return false;
		if (arrivalMinute3 == null) {
			if (other.arrivalMinute3 != null)
				return false;
		} else if (!arrivalMinute3.equals(other.arrivalMinute3))
			return false;
		if (arrivalMinute4 == null) {
			if (other.arrivalMinute4 != null)
				return false;
		} else if (!arrivalMinute4.equals(other.arrivalMinute4))
			return false;
		if (arrivalMinute5 == null) {
			if (other.arrivalMinute5 != null)
				return false;
		} else if (!arrivalMinute5.equals(other.arrivalMinute5))
			return false;
		if (arrivalMinute6 == null) {
			if (other.arrivalMinute6 != null)
				return false;
		} else if (!arrivalMinute6.equals(other.arrivalMinute6))
			return false;
		if (categoryType == null) {
			if (other.categoryType != null)
				return false;
		} else if (!categoryType.equals(other.categoryType))
			return false;
		if (departureAirportCode == null) {
			if (other.departureAirportCode != null)
				return false;
		} else if (!departureAirportCode.equals(other.departureAirportCode))
			return false;
		if (departureAirportCode1 == null) {
			if (other.departureAirportCode1 != null)
				return false;
		} else if (!departureAirportCode1.equals(other.departureAirportCode1))
			return false;
		if (departureAirportCode2 == null) {
			if (other.departureAirportCode2 != null)
				return false;
		} else if (!departureAirportCode2.equals(other.departureAirportCode2))
			return false;
		if (departureAirportCode3 == null) {
			if (other.departureAirportCode3 != null)
				return false;
		} else if (!departureAirportCode3.equals(other.departureAirportCode3))
			return false;
		if (departureAirportCode4 == null) {
			if (other.departureAirportCode4 != null)
				return false;
		} else if (!departureAirportCode4.equals(other.departureAirportCode4))
			return false;
		if (departureAirportCode5 == null) {
			if (other.departureAirportCode5 != null)
				return false;
		} else if (!departureAirportCode5.equals(other.departureAirportCode5))
			return false;
		if (departureAirportCode6 == null) {
			if (other.departureAirportCode6 != null)
				return false;
		} else if (!departureAirportCode6.equals(other.departureAirportCode6))
			return false;
		if (departureAirportCountryCode == null) {
			if (other.departureAirportCountryCode != null)
				return false;
		} else if (!departureAirportCountryCode.equals(other.departureAirportCountryCode))
			return false;
		if (departureCityCode == null) {
			if (other.departureCityCode != null)
				return false;
		} else if (!departureCityCode.equals(other.departureCityCode))
			return false;
		if (departureGMTHour == null) {
			if (other.departureGMTHour != null)
				return false;
		} else if (!departureGMTHour.equals(other.departureGMTHour))
			return false;
		if (departureGMTMinute == null) {
			if (other.departureGMTMinute != null)
				return false;
		} else if (!departureGMTMinute.equals(other.departureGMTMinute))
			return false;
		if (departureHour == null) {
			if (other.departureHour != null)
				return false;
		} else if (!departureHour.equals(other.departureHour))
			return false;
		if (departureHour1 == null) {
			if (other.departureHour1 != null)
				return false;
		} else if (!departureHour1.equals(other.departureHour1))
			return false;
		if (departureHour2 == null) {
			if (other.departureHour2 != null)
				return false;
		} else if (!departureHour2.equals(other.departureHour2))
			return false;
		if (departureHour3 == null) {
			if (other.departureHour3 != null)
				return false;
		} else if (!departureHour3.equals(other.departureHour3))
			return false;
		if (departureHour4 == null) {
			if (other.departureHour4 != null)
				return false;
		} else if (!departureHour4.equals(other.departureHour4))
			return false;
		if (departureHour5 == null) {
			if (other.departureHour5 != null)
				return false;
		} else if (!departureHour5.equals(other.departureHour5))
			return false;
		if (departureHour6 == null) {
			if (other.departureHour6 != null)
				return false;
		} else if (!departureHour6.equals(other.departureHour6))
			return false;
		if (departureMinute == null) {
			if (other.departureMinute != null)
				return false;
		} else if (!departureMinute.equals(other.departureMinute))
			return false;
		if (departureMinute1 == null) {
			if (other.departureMinute1 != null)
				return false;
		} else if (!departureMinute1.equals(other.departureMinute1))
			return false;
		if (departureMinute2 == null) {
			if (other.departureMinute2 != null)
				return false;
		} else if (!departureMinute2.equals(other.departureMinute2))
			return false;
		if (departureMinute3 == null) {
			if (other.departureMinute3 != null)
				return false;
		} else if (!departureMinute3.equals(other.departureMinute3))
			return false;
		if (departureMinute4 == null) {
			if (other.departureMinute4 != null)
				return false;
		} else if (!departureMinute4.equals(other.departureMinute4))
			return false;
		if (departureMinute5 == null) {
			if (other.departureMinute5 != null)
				return false;
		} else if (!departureMinute5.equals(other.departureMinute5))
			return false;
		if (departureMinute6 == null) {
			if (other.departureMinute6 != null)
				return false;
		} else if (!departureMinute6.equals(other.departureMinute6))
			return false;
		if (duplicateType1 == null) {
			if (other.duplicateType1 != null)
				return false;
		} else if (!duplicateType1.equals(other.duplicateType1))
			return false;
		if (duplicateType2 == null) {
			if (other.duplicateType2 != null)
				return false;
		} else if (!duplicateType2.equals(other.duplicateType2))
			return false;
		if (duplicateType3 == null) {
			if (other.duplicateType3 != null)
				return false;
		} else if (!duplicateType3.equals(other.duplicateType3))
			return false;
		if (duplicateType4 == null) {
			if (other.duplicateType4 != null)
				return false;
		} else if (!duplicateType4.equals(other.duplicateType4))
			return false;
		if (duplicateType5 == null) {
			if (other.duplicateType5 != null)
				return false;
		} else if (!duplicateType5.equals(other.duplicateType5))
			return false;
		if (duplicateType6 == null) {
			if (other.duplicateType6 != null)
				return false;
		} else if (!duplicateType6.equals(other.duplicateType6))
			return false;
		if (elapsedMinutes == null) {
			if (other.elapsedMinutes != null)
				return false;
		} else if (!elapsedMinutes.equals(other.elapsedMinutes))
			return false;
		if (equipCode1 == null) {
			if (other.equipCode1 != null)
				return false;
		} else if (!equipCode1.equals(other.equipCode1))
			return false;
		if (equipCode2 == null) {
			if (other.equipCode2 != null)
				return false;
		} else if (!equipCode2.equals(other.equipCode2))
			return false;
		if (equipCode3 == null) {
			if (other.equipCode3 != null)
				return false;
		} else if (!equipCode3.equals(other.equipCode3))
			return false;
		if (equipCode4 == null) {
			if (other.equipCode4 != null)
				return false;
		} else if (!equipCode4.equals(other.equipCode4))
			return false;
		if (equipCode5 == null) {
			if (other.equipCode5 != null)
				return false;
		} else if (!equipCode5.equals(other.equipCode5))
			return false;
		if (equipCode6 == null) {
			if (other.equipCode6 != null)
				return false;
		} else if (!equipCode6.equals(other.equipCode6))
			return false;
		if (itineraryMiles == null) {
			if (other.itineraryMiles != null)
				return false;
		} else if (!itineraryMiles.equals(other.itineraryMiles))
			return false;
		if (legAirline1 == null) {
			if (other.legAirline1 != null)
				return false;
		} else if (!legAirline1.equals(other.legAirline1))
			return false;
		if (legAirline2 == null) {
			if (other.legAirline2 != null)
				return false;
		} else if (!legAirline2.equals(other.legAirline2))
			return false;
		if (legAirline3 == null) {
			if (other.legAirline3 != null)
				return false;
		} else if (!legAirline3.equals(other.legAirline3))
			return false;
		if (legAirline4 == null) {
			if (other.legAirline4 != null)
				return false;
		} else if (!legAirline4.equals(other.legAirline4))
			return false;
		if (legAirline5 == null) {
			if (other.legAirline5 != null)
				return false;
		} else if (!legAirline5.equals(other.legAirline5))
			return false;
		if (legAirline6 == null) {
			if (other.legAirline6 != null)
				return false;
		} else if (!legAirline6.equals(other.legAirline6))
			return false;
		if (legFlightNumber1 == null) {
			if (other.legFlightNumber1 != null)
				return false;
		} else if (!legFlightNumber1.equals(other.legFlightNumber1))
			return false;
		if (legFlightNumber2 == null) {
			if (other.legFlightNumber2 != null)
				return false;
		} else if (!legFlightNumber2.equals(other.legFlightNumber2))
			return false;
		if (legFlightNumber3 == null) {
			if (other.legFlightNumber3 != null)
				return false;
		} else if (!legFlightNumber3.equals(other.legFlightNumber3))
			return false;
		if (legFlightNumber4 == null) {
			if (other.legFlightNumber4 != null)
				return false;
		} else if (!legFlightNumber4.equals(other.legFlightNumber4))
			return false;
		if (legFlightNumber5 == null) {
			if (other.legFlightNumber5 != null)
				return false;
		} else if (!legFlightNumber5.equals(other.legFlightNumber5))
			return false;
		if (legFlightNumber6 == null) {
			if (other.legFlightNumber6 != null)
				return false;
		} else if (!legFlightNumber6.equals(other.legFlightNumber6))
			return false;
		if (legNumber1 == null) {
			if (other.legNumber1 != null)
				return false;
		} else if (!legNumber1.equals(other.legNumber1))
			return false;
		if (legNumber2 == null) {
			if (other.legNumber2 != null)
				return false;
		} else if (!legNumber2.equals(other.legNumber2))
			return false;
		if (legNumber3 == null) {
			if (other.legNumber3 != null)
				return false;
		} else if (!legNumber3.equals(other.legNumber3))
			return false;
		if (legNumber4 == null) {
			if (other.legNumber4 != null)
				return false;
		} else if (!legNumber4.equals(other.legNumber4))
			return false;
		if (legNumber5 == null) {
			if (other.legNumber5 != null)
				return false;
		} else if (!legNumber5.equals(other.legNumber5))
			return false;
		if (legNumber6 == null) {
			if (other.legNumber6 != null)
				return false;
		} else if (!legNumber6.equals(other.legNumber6))
			return false;
		if (legType1 == null) {
			if (other.legType1 != null)
				return false;
		} else if (!legType1.equals(other.legType1))
			return false;
		if (legType2 == null) {
			if (other.legType2 != null)
				return false;
		} else if (!legType2.equals(other.legType2))
			return false;
		if (legType3 == null) {
			if (other.legType3 != null)
				return false;
		} else if (!legType3.equals(other.legType3))
			return false;
		if (legType4 == null) {
			if (other.legType4 != null)
				return false;
		} else if (!legType4.equals(other.legType4))
			return false;
		if (legType5 == null) {
			if (other.legType5 != null)
				return false;
		} else if (!legType5.equals(other.legType5))
			return false;
		if (legType6 == null) {
			if (other.legType6 != null)
				return false;
		} else if (!legType6.equals(other.legType6))
			return false;
		if (numberOfConnections == null) {
			if (other.numberOfConnections != null)
				return false;
		} else if (!numberOfConnections.equals(other.numberOfConnections))
			return false;
		if (numberOfStops == null) {
			if (other.numberOfStops != null)
				return false;
		} else if (!numberOfStops.equals(other.numberOfStops))
			return false;
		if (serviceType == null) {
			if (other.serviceType != null)
				return false;
		} else if (!serviceType.equals(other.serviceType))
			return false;
		if (serviceTypeCode == null) {
			if (other.serviceTypeCode != null)
				return false;
		} else if (!serviceTypeCode.equals(other.serviceTypeCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [serviceType=" + serviceType + ", ODkey=" + ODkey + ", NSflag=" + NSflag + ", IBflag=" + IBflag
				+ ", departureAirportCode=" + departureAirportCode + ", departureCityCode=" + departureCityCode
				+ ", departureAirportCountryCode=" + departureAirportCountryCode + ", arrivalAirportCode="
				+ arrivalAirportCode + ", arrivalCityCode=" + arrivalCityCode + ", arrivalAirportCountryCode="
				+ arrivalAirportCountryCode + ", arrivalHour=" + arrivalHour + ", arrivalMinute=" + arrivalMinute
				+ ", arrivalGMTHour=" + arrivalGMTHour + ", arrivalGMTMinute=" + arrivalGMTMinute + ", departureHour="
				+ departureHour + ", departureMinute=" + departureMinute + ", departureGMTHour=" + departureGMTHour
				+ ", departureGMTMinute=" + departureGMTMinute + ", elapsedMinutes=" + elapsedMinutes
				+ ", serviceTypeCode=" + serviceTypeCode + ", numberOfConnections=" + numberOfConnections
				+ ", numberOfStops=" + numberOfStops + ", categoryType=" + categoryType + ", frequencyCount="
				+ frequencyCount + ", GCDMiles=" + GCDMiles + ", itineraryMiles=" + itineraryMiles + ", legKey1="
				+ legKey1 + ", legAirline1=" + legAirline1 + ", legFlightNumber1=" + legFlightNumber1
				+ ", legItineraryNumber1=" + legItineraryNumber1 + ", legNumber1=" + legNumber1
				+ ", departureAirportCode1=" + departureAirportCode1 + ", arrivalAirportCode1=" + arrivalAirportCode1
				+ ", departureHour1=" + departureHour1 + ", departureMinute1=" + departureMinute1 + ", arrivalHour1="
				+ arrivalHour1 + ", arrivalMinute1=" + arrivalMinute1 + ", equipCode1=" + equipCode1 + ", legKey2="
				+ legKey2 + ", legAirline2=" + legAirline2 + ", legFlightNumber2=" + legFlightNumber2
				+ ", legItineraryNumber2=" + legItineraryNumber2 + ", legNumber2=" + legNumber2
				+ ", departureAirportCode2=" + departureAirportCode2 + ", arrivalAirportCode2=" + arrivalAirportCode2
				+ ", departureHour2=" + departureHour2 + ", departureMinute2=" + departureMinute2 + ", arrivalHour2="
				+ arrivalHour2 + ", arrivalMinute2=" + arrivalMinute2 + ", equipCode2=" + equipCode2 + ", legKey3="
				+ legKey3 + ", legAirline3=" + legAirline3 + ", legFlightNumber3=" + legFlightNumber3
				+ ", legItineraryNumber3=" + legItineraryNumber3 + ", legNumber3=" + legNumber3
				+ ", departureAirportCode3=" + departureAirportCode3 + ", arrivalAirportCode3=" + arrivalAirportCode3
				+ ", departureHour3=" + departureHour3 + ", departureMinute3=" + departureMinute3 + ", arrivalHour3="
				+ arrivalHour3 + ", arrivalMinute3=" + arrivalMinute3 + ", equipCode3=" + equipCode3 + ", legKey4="
				+ legKey4 + ", legAirline4=" + legAirline4 + ", legFlightNumber4=" + legFlightNumber4
				+ ", legItineraryNumber4=" + legItineraryNumber4 + ", legNumber4=" + legNumber4
				+ ", departureAirportCode4=" + departureAirportCode4 + ", arrivalAirportCode4=" + arrivalAirportCode4
				+ ", departureHour4=" + departureHour4 + ", departureMinute4=" + departureMinute4 + ", arrivalHour4="
				+ arrivalHour4 + ", arrivalMinute4=" + arrivalMinute4 + ", equipCode4=" + equipCode4 + ", legKey5="
				+ legKey5 + ", legAirline5=" + legAirline5 + ", legFlightNumber5=" + legFlightNumber5
				+ ", legItineraryNumber5=" + legItineraryNumber5 + ", legNumber5=" + legNumber5
				+ ", departureAirportCode5=" + departureAirportCode5 + ", arrivalAirportCode5=" + arrivalAirportCode5
				+ ", departureHour5=" + departureHour5 + ", departureMinute5=" + departureMinute5 + ", arrivalHour5="
				+ arrivalHour5 + ", arrivalMinute5=" + arrivalMinute5 + ", equipCode5=" + equipCode5 + ", legKey6="
				+ legKey6 + ", legAirline6=" + legAirline6 + ", legFlightNumber6=" + legFlightNumber6
				+ ", legItineraryNumber6=" + legItineraryNumber6 + ", legNumber6=" + legNumber6
				+ ", departureAirportCode6=" + departureAirportCode6 + ", arrivalAirportCode6=" + arrivalAirportCode6
				+ ", departureHour6=" + departureHour6 + ", departureMinute6=" + departureMinute6 + ", arrivalHour6="
				+ arrivalHour6 + ", arrivalMinute6=" + arrivalMinute6 + ", equipCode6=" + equipCode6
				+ ", daysOfOperation=" + daysOfOperation + ", operationalSchedSvcKey=" + operationalSchedSvcKey
				+ ", legType1=" + legType1 + ", duplicateType1=" + duplicateType1 + ", legType2=" + legType2
				+ ", duplicateType2=" + duplicateType2 + ", legType3=" + legType3 + ", duplicateType3=" + duplicateType3
				+ ", legType4=" + legType4 + ", duplicateType4=" + duplicateType4 + ", legType5=" + legType5
				+ ", duplicateType5=" + duplicateType5 + ", legType6=" + legType6 + ", duplicateType6=" + duplicateType6
				+ ", operationalODKey=" + operationalODKey + ", _id=" + _id + ", _rev=" + _rev + ", serviceLevel="
				+ serviceLevel + "]";
	}
}
