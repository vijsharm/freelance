package com.aa.gsa.exception;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class ViewQueryException extends PointsProcessorException {

	private Log log = LogFactory.getLog(ViewQueryException.class);
		
	public ViewQueryException(String viewName, Set<String> keys, Throwable cause) {
		super("System error occured. Please contact IT support.");
		log.error("Cloudant database error querying view = "+viewName+ " with keys = "+keys, cause);
	}	
}
