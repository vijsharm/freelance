package com.aa.gsa.domain.result;

import java.util.Map;

import com.aa.gsa.enums.Airline;

/**
 * Domain object to hold the CPP result
 * @author 940914
 */
public class CPPResult {
	
	private int runId;
	
	private int itemNo;

	private String orig;

	private String dest;
	
	private Map<Airline, Result> airlineResults;
	
	private Boolean domestic;
	
	private Boolean excludeCodeshare;
	
	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getOrig() {
		return orig;
	}

	public void setOrig(String orig) {
		this.orig = orig;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}	

	public Map<Airline, Result> getAirlineResults() {
		return airlineResults;
	}

	public void setAirlineResults(Map<Airline, Result> airlineResults) {
		this.airlineResults = airlineResults;
	}

	public Boolean getDomestic() {
		return domestic;
	}

	public void setDomestic(Boolean domestic) {
		this.domestic = domestic;
	}

	public Boolean getExcludeCodeshare() {
		return excludeCodeshare;
	}

	public void setExcludeCodeshare(Boolean excludeCodeshare) {
		this.excludeCodeshare = excludeCodeshare;
	}

	@Override
	public String toString() {
		return "CPPResult [runId=" + runId + ", itemNo=" + itemNo + ", orig=" + orig + ", dest=" + dest
				+ ", airlineResults=" + airlineResults + ", domestic=" + domestic + ", excludeCodeshare="
				+ excludeCodeshare + "]";
	}    	
}
