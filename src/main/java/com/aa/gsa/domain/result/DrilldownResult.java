package com.aa.gsa.domain.result;

import java.util.Map;
import java.util.Set;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.EligibilityCategory;
import com.aa.gsa.enums.ServiceLevel;

public class DrilldownResult extends Result {
	/**
	 * Eligibility drill-down for each @EligibilityCategory
	 */
	private Map<EligibilityCategory, EligibilityDrilldown> eligibilityDrilldown;

	/**
	 * TimeBand Points
	 */
	private Map<Timeband, Map<ServiceLevel, Map<Direction, Integer>>> timebands;
	
	/**
	 * Total Count for Inbound & Outbound
	 */
	private Map<Direction, Integer> total;
	
	
	/**
	 * Count of Inbound & Outbound Jets
	 */
	private Map<Direction, Integer> jets;
	
	/**
	 * Connections
	 */
	private Set<String> connections;
	
	public Map<EligibilityCategory, EligibilityDrilldown> getEligibilityDrilldown() {
		return eligibilityDrilldown;
	}

	public void setEligibilityDrilldown(Map<EligibilityCategory, EligibilityDrilldown> eligibilityDrilldown) {
		this.eligibilityDrilldown = eligibilityDrilldown;
	}

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

	public Set<String> getConnections() {
		return connections;
	}

	public void setConnections(Set<String> connections) {
		this.connections = connections;
	}

	public Map<Direction, Integer> getJets() {
		return jets;
	}

	public void setJets(Map<Direction, Integer> jets) {
		this.jets = jets;
	}
}
