package com.aa.gsa.domain.result;

import java.util.HashMap;
import java.util.Map;

import com.aa.gsa.enums.PointsCategory;

public class Result {
	/**
	 * Flag to determine if eligible
	 */
	private boolean eligible;
	
	/**
	 * Total number of points for this airline
	 */
	private int points;

	/**
	 * 
	 * Elapsed time
	 * 
	 */
	private int elaspedTime;
		
	private String eligibilityReason;
	
	private String eligibilityReasonCode;
	
	/**
	 * Points Per Category
	 */
	@SuppressWarnings("serial")
	private final Map<PointsCategory, Integer> pointsPerCategory = new HashMap<PointsCategory, Integer>(){{
	    put(PointsCategory.TIMEBAND, 0);
	    put(PointsCategory.AVERAGE_ELAPASED_FLIGHT_TIME, 0);
	    put(PointsCategory.NUMBER_OF_FLIGHTS, 0);
	    put(PointsCategory.EQUIPMENT, 0);
	}};
	
	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getElaspedTime() {
		return elaspedTime;
	}

	public void setElaspedTime(int elaspedTime) {
		this.elaspedTime = elaspedTime;
	}

	public Map<PointsCategory, Integer> getPointsPerCategory() {
		return pointsPerCategory;
	}

    public String getEligibilityReason()
    {
        return eligibilityReason;
    }

    public void setEligibilityReason(String eligibilityReason)
    {
        this.eligibilityReason = eligibilityReason;
    }

    public String getEligibilityReasonCode()
    {
        return eligibilityReasonCode;
    }

    public void setEligibilityReasonCode(String eligibilityReasonCode)
    {
        this.eligibilityReasonCode = eligibilityReasonCode;
    }
}