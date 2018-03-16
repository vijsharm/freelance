package com.aa.gsa.enums;

/**
 * 
 * SAEFT Acronym for (Shortest Average Elapsed Flight Time) 
 * 
 * @author 940914
 */
public enum SAEFT {
	WITHIN_30(0,30),
	BETWEEN_31_45(31, 45),
	BETWEEN_46_60(46, 60),
	BETWEEN_61_75(61, 75),
	BETWEEN_76_90(76, 90),
	MORE_THAN_90(90, 1000000);

	private int start;
	
	private int end;
	
	SAEFT(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}	
	
	public static SAEFT findBy(int shortestAvgElapsedTime, int avgElapsedTime) {
		for (SAEFT saeft : SAEFT.values()) {
			if ((avgElapsedTime >= shortestAvgElapsedTime + saeft.start) && (avgElapsedTime <= shortestAvgElapsedTime + saeft.end)) {
				return saeft;
			}
		}
		return null;
	}
}
