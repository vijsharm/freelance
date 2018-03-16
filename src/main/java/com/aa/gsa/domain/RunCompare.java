package com.aa.gsa.domain;

/**
 * 
 * @author 940914
 * 
 */
public class RunCompare {

	private int runId1; 
	
	private int runId2;
	
	private String orig;
	
	private String dest;
	
	private int itemNo1;
	
	private int itemNo2;
	
	private boolean eligible1;

	private boolean eligible2;
	
	private int points1; 
	
	private int points2;
	
	public int getRunId1() {
		return runId1;
	}

	public void setRunId1(int runId1) {
		this.runId1 = runId1;
	}

	public int getRunId2() {
		return runId2;
	}

	public void setRunId2(int runId2) {
		this.runId2 = runId2;
	}    
	
	public int getItemNo1() {
		return itemNo1;
	}

	public void setItemNo1(int itemNo1) {
		this.itemNo1 = itemNo1;
	}

	public int getItemNo2() {
		return itemNo2;
	}

	public void setItemNo2(int itemNo2) {
		this.itemNo2 = itemNo2;
	}

	public int getPoints1() {
		return points1;
	}

	public void setPoints1(int points1) {
		this.points1 = points1;
	}

	public int getPoints2() {
		return points2;
	}

	public void setPoints2(int points2) {
		this.points2 = points2;
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

	public boolean isEligible1() {
		return eligible1;
	}

	public void setEligible1(boolean eligible1) {
		this.eligible1 = eligible1;
	}

	public boolean isEligible2() {
		return eligible2;
	}

	public void setEligible2(boolean eligible2) {
		this.eligible2 = eligible2;
	}
}
