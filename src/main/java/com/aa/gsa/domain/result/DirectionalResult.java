package com.aa.gsa.domain.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.PointsCategory;
import com.aa.gsa.enums.ServiceLevel;
import com.google.common.collect.Table;

/**
 * 
 * Partial result calculated for a direction Outbound/Inbound
 * 
 * @author 940914
 */
public class DirectionalResult {
	private Table<Timeband, ServiceLevel, Integer> timebandCounts;
	private int totalCount;
	private int jetsCount;
	private Set<String> connections;
	private Map<DayOfOperation, Integer> avgElapsedTimes;
	private int nonStopCount;
	private int directCount;
	private int connectionCount;
	
	@SuppressWarnings("serial")
	private Map<PointsCategory, Integer> points = new HashMap<PointsCategory, Integer>(){{
	    put(PointsCategory.TIMEBAND, 0);
	    put(PointsCategory.AVERAGE_ELAPASED_FLIGHT_TIME, 0);
	    put(PointsCategory.NUMBER_OF_FLIGHTS, 0);
	    put(PointsCategory.EQUIPMENT, 0);
	}};;
	
	public Table<Timeband, ServiceLevel, Integer> getTimebandCounts() {
		return timebandCounts;
	}
	public void setTimebandCounts(Table<Timeband, ServiceLevel, Integer> timebandCounts) {
		this.timebandCounts = timebandCounts;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getJetsCount() {
		return jetsCount;
	}
	public void setJetsCount(int jetsCount) {
		this.jetsCount = jetsCount;
	}
	public Set<String> getConnections() {
		return connections;
	}
	public void setConnections(Set<String> connections) {
		this.connections = connections;
	}
	public Map<DayOfOperation, Integer> getAvgElapsedTimes() {
		return avgElapsedTimes;
	}
	public void setAvgElapsedTimes(Map<DayOfOperation, Integer> avgElapsedTimes) {
		this.avgElapsedTimes = avgElapsedTimes;
	}
	public int getNonStopCount() {
		return nonStopCount;
	}
	public void setNonStopCount(int nonStopCount) {
		this.nonStopCount = nonStopCount;
	}
	public int getDirectCount() {
		return directCount;
	}
	public void setDirectCount(int directCount) {
		this.directCount = directCount;
	}
	public int getConnectionCount() {
		return connectionCount;
	}
	public void setConnectionCount(int connectionCount) {
		this.connectionCount = connectionCount;
	}
	public Map<PointsCategory, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<PointsCategory, Integer> points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "DirectionalResult [timebandCounts=" + timebandCounts + ", totalCount=" + totalCount + ", jetsCount="
				+ jetsCount + ", connections=" + connections + ", avgElapsedTimes=" + avgElapsedTimes
				+ ", nonStopCount=" + nonStopCount + ", directCount=" + directCount + ", connectionCount="
				+ connectionCount + ", points=" + points + "]";
	}
}
