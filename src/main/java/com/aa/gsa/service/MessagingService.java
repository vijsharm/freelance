package com.aa.gsa.service;

import com.aa.gsa.domain.CPP;

public interface MessagingService {

	void sendErrorMessage(CPP cpp, Exception ex);
	
	void sendInfoMessage(long runId, String message);
	
	void sendWarningMessage(long runId, String message);

    void sendProgressMessage(int marketsProcessed, int totalProcessed);
}
