package com.aa.gsa.domain;

/**
 * Run Document submitted from front-end
 * @author 940914
 * 
 */
public class Run {
	
	private String _id;
	
	private String _rev;
	
	private int year;
	
	private String runId;
	
	private String name;
	
	private String cppFileGroupName;
	
	private String scheduleName;
	
	private String rulesetName;
	
	private String submittedBy;

	private String submittedOn;
		
	private String modifiedOn;

	private String status;
	
	private String runBy;
	
	private String runByEmail;
	
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCppFileGroupName() {
		return cppFileGroupName;
	}

	public void setCppFileGroupName(String cppFileGroupName) {
		this.cppFileGroupName = cppFileGroupName;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getRulesetName() {
		return rulesetName;
	}

	public void setRulesetName(String rulesetName) {
		this.rulesetName = rulesetName;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRunBy() {
		return runBy;
	}
	public void setRunBy(String runBy) {
		this.runBy = runBy;
	}
	
	public String getRunByEmail() {
		return runByEmail;
	}
	public void setRunByEmail(String runByEmail) {
		this.runByEmail = runByEmail;
	}

	@Override
	public String toString() {
		return "Run [_id=" + _id + ", _rev=" + _rev + ", year=" + year + ", runId=" + runId + ", name=" + name
				+ ", cppFileGroupName=" + cppFileGroupName + ", scheduleName=" + scheduleName + ", rulesetName="
				+ rulesetName + ", submittedBy=" + submittedBy + ", submittedOn=" + submittedOn + ", modifiedOn="
				+ modifiedOn + ", status=" + status + "]";
	}	
}
