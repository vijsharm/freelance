package com.aa.gsa.exception;

import com.aa.gsa.domain.RunCompareMsg;

@SuppressWarnings("serial")
public class RunCompareExistsException extends RuntimeException {

	public RunCompareExistsException(RunCompareMsg compareMsg) {
		super("A compare with runs = ["+compareMsg.getRunId1()+", "+compareMsg.getRunId2()+ "] already exists with the status "+compareMsg.getStatus());		
	}	
}
