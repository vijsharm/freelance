package com.aa.gsa.domain.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.PointsCategory;
import com.aa.gsa.enums.ServiceLevel;

public class PointsResult {

	private Map<Timeband, Map<ServiceLevel,Map<Direction,Integer>>> timebands;
	
	private Map<Direction, Integer> total;
	
	private Map<Direction, Integer> jets;
	
	private int elaspedTime;
	
	private Set<String> connections;
	
	private Map<Direction, Integer> nonStopCounts;
	
	private Map<Direction, Integer> directCounts;
	
	private Map<Direction, Integer> connectCounts;
	
	@SuppressWarnings("serial")
	private Map<PointsCategory, Integer> pointsPerCategory = new HashMap<PointsCategory, Integer>(){{
	    put(PointsCategory.TIMEBAND, 0);
	    put(PointsCategory.AVERAGE_ELAPASED_FLIGHT_TIME, 0);
	    put(PointsCategory.NUMBER_OF_FLIGHTS, 0);
	    put(PointsCategory.EQUIPMENT, 0);
	}};;;

	public Map<Timeband, Map<ServiceLevel, Map<Direction, Integer>>> getTimebands() {
		return timebands;
	}

	public void setTimebands(Map<Timeband, Map<ServiceLevel, Map<Direction, Integer>>> timebands) {
		this.timebands = timebands;
	}

	public Map<Direction, Integer> getTotal() {
		return total;
	}

	public void setTotal(Map<Direction, Integer> total) {
		this.total = total;
	}

	public Map<Direction, Integer> getJets() {
		return jets;
	}

	public void setJets(Map<Direction, Integer> jets) {
		this.jets = jets;
	}

	public int getElaspedTime() {
		return elaspedTime;
	}

	public void setElaspedTime(int elaspedTime) {
		this.elaspedTime = elaspedTime;
	}

	public Set<String> getConnections() {
		return connections;
	}

	public void setConnections(Set<String> connections) {
		this.connections = connections;
	}

	public Map<PointsCategory, Integer> getPointsPerCategory() {
		return pointsPerCategory;
	}

	public void setPointsPerCategory(Map<PointsCategory, Integer> pointsPerCategory) {
		this.pointsPerCategory = pointsPerCategory;
	}

	public Map<Direction, Integer> getNonStopCounts() {
		return nonStopCounts;
	}

	public void setNonStopCounts(Map<Direction, Integer> nonStopCounts) {
		this.nonStopCounts = nonStopCounts;
	}

	public Map<Direction, Integer> getDirectCounts() {
		return directCounts;
	}

	public void setDirectCounts(Map<Direction, Integer> directCounts) {
		this.directCounts = directCounts;
	}

	public Map<Direction, Integer> getConnectCounts() {
		return connectCounts;
	}

	public void setConnectCounts(Map<Direction, Integer> connectCounts) {
		this.connectCounts = connectCounts;
	}

	@Override
	public String toString() {
		return "PointsResult [timebands=" + timebands + ", total=" + total + ", jets=" + jets + ", elaspedTime="
				+ elaspedTime + ", connections=" + connections + ", nonStopCounts=" + nonStopCounts + ", directCounts="
				+ directCounts + ", connectCounts=" + connectCounts + ", pointsPerCategory=" + pointsPerCategory + "]";
	}
}
