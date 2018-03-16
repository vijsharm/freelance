package com.aa.gsa.domain;

import com.aa.gsa.enums.RunCompareStatus;

/**
 * 
 * CPPCompareMessage sent & received from RabbitMQ
 * @author 940914
 * 
 */
public class RunCompareMsg {

	private int runId1;
	
	private int runId2;

	private RunCompareStatus status;
	
	private String errorMsg;
	
	private String _id;
	
	private String _rev;
	
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

	public RunCompareStatus getStatus() {
		return status;
	}

	public void setStatus(RunCompareStatus status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

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
}
