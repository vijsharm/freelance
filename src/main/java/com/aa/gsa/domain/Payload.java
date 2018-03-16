package com.aa.gsa.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.result.Result;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.google.common.collect.Table;

/**
 * DTO between processors
 * @author 940914
 */
public class Payload {
	/**
	 * CPP that's being processed
	 */
	private final CPP cpp;
	
	/**
	 * Filtered schedules from previous step
	 */
	private final Table<Airline, Direction, Set<Schedule>> schedules;
	
	
	/**
	 * Filtered noCodeshare schedules from previous step
	 */
	private final Table<Airline, Direction, Set<Schedule>> schedulesNoCodeshare;
	

	/**
	 * Calculated results from previous step 
	 */
	private Map<Airline, Result> results = new HashMap<>(Airline.values().length);
	
	
	/**
	 * Calculated noCodeshare results from previous step 
	 */
	private Map<Airline, Result> resultsNoCodeshare = new HashMap<>(Airline.values().length);
	

	public Payload(CPP cpp, Table<Airline, Direction, Set<Schedule>> schedules) {
		super();
		this.cpp = cpp;
		this.schedules = schedules;
		this.schedulesNoCodeshare = null;
	}
	
	public Payload(CPP cpp, Table<Airline, Direction, Set<Schedule>> schedules, Table<Airline, Direction, Set<Schedule>> schedulesNoCodeshare) {
		super();
		this.cpp = cpp;
		this.schedules = schedules;
		this.schedulesNoCodeshare = schedulesNoCodeshare;
	}
	
	public CPP getCpp() {
		return cpp;
	}	

	public Table<Airline, Direction, Set<Schedule>> getSchedules() {
		return schedules;
	}	

	public Table<Airline, Direction, Set<Schedule>> getSchedulesNoCodeshare() {
		return schedulesNoCodeshare;
	}

	public Map<Airline, Result> getResults() {
		return results;
	}

	public void setResults(Map<Airline, Result> results) {
		this.results = results;
	}

	public Map<Airline, Result> getResultsNoCodeshare() {
		return resultsNoCodeshare;
	}

	public void setResultsNoCodeshare(Map<Airline, Result> resultsNoCodeshare) {
		this.resultsNoCodeshare = resultsNoCodeshare;
	}
}
