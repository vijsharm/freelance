package com.aa.gsa.util;

/**
 * 
 * @author 940914
 */
public class PointsProcessorConstants {
	private PointsProcessorConstants() {}
	
	public static final String GSA_RULES_PROCESSOR_JOB = "GSA_RULES_PROCESSOR_JOB";
	public static final String CREATE_VIEWS_JOB = "CREATE_VIEWS_JOB";

	//Job Parameters
	public static final String SCHEDULE_FILE_ID = "schedule_file_id"; //maps to the schedule database
	public static final String CPP_FILE_ID = "cpp_file_id"; //maps to the CPP database
	public static final String SETTINGS_DOCUMENT_NAME = "settings_document_name"; //maps to a Settings Document in Settings database
	public static final String RUN_DOCUMENT_ID = "run_document_id"; //maps to run document in Run database
	public static final String RUN_NAME = "run_name"; //maps to run document in Run database

	public static final String RUN_DATE = "run_date"; 

	//Partition Constants
	public static final String FROM = "from";
	public static final String TO = "to";
	public static final String PARTITION = "partition";
	
	//Late Binding Constants
	public static final String STR_EXPR = null;
	public static final int INT_EXPR = 0;
}
