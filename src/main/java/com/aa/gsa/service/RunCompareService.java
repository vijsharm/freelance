package com.aa.gsa.service;

import com.aa.gsa.domain.RunCompareMsg;
import com.aa.gsa.enums.RunCompareStatus;

public interface RunCompareService {

	/**
	 * 
	 * Performs compare for a given set of runs
	 * @param cppCompareMsg
	 * 
	 */
	void compare(RunCompareMsg cppCompareMsg);
	
	/**
	 * 
	 * @param cppCompareMsg
	 * @param status
	 * 
	 */
	void setStatus(RunCompareMsg cppCompareMsg, RunCompareStatus status);
}
