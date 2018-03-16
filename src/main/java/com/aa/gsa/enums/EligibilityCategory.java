package com.aa.gsa.enums;

public enum EligibilityCategory {
	
	INVALID_SCHEDULES("Invalid Schedule Filter"),
	
	MIN_SERVICE("Minimum service requirement Non-stop/Direct/Connection"),
	
	NO_OF_FLIGHTS("Minimum number of flights need to qualify"),
	
	FREQUENCY("Frequency of flights needed per week"),
	
	GROUND_TIME("Ground/Connect time restriction"),
	
	CIRCUITY("Maximum Ciruity Requirement");
	
	private String description;
	
	EligibilityCategory(String description) {
		this.description = description;
	}
	
    public String description() {
        return description;
    }
}
