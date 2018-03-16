package com.aa.gsa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "batch")
public class BatchJobProperties {

	private int noOfPartitions;
	
	private int chunkSize;
	
	private String gsaDatabaseName;
	
	private String cppResultsDatabaseName;
	
	private String eligibilityResultsDatabaseName;
	
	private String stationsDatabaseName;
	
	private String runDatabaseName;
	
	private String settingsDatabaseName;
	
	private String equipmentCodesDatabaseName;
	
	public int getNoOfPartitions() {
		return noOfPartitions;
	}

	public void setNoOfPartitions(int noOfPartitions) {
		this.noOfPartitions = noOfPartitions;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public String getGsaDatabaseName() {
		return gsaDatabaseName;
	}

	public void setGsaDatabaseName(String gsaDatabaseName) {
		this.gsaDatabaseName = gsaDatabaseName;
	}

	public String getCppResultsDatabaseName() {
		return cppResultsDatabaseName;
	}

	public void setCppResultsDatabaseName(String cppResultsDatabaseName) {
		this.cppResultsDatabaseName = cppResultsDatabaseName;
	}
	
	public String getEligibilityResultsDatabaseName()
    {
        return eligibilityResultsDatabaseName;
    }

    public void setEligibilityResultsDatabaseName(String eligibilityResultsDatabaseName)
    {
        this.eligibilityResultsDatabaseName = eligibilityResultsDatabaseName;
    }

    public String getStationsDatabaseName() {
		return stationsDatabaseName;
	}

	public void setStationsDatabaseName(String stationsDatabaseName) {
		this.stationsDatabaseName = stationsDatabaseName;
	}

	public String getRunDatabaseName() {
		return runDatabaseName;
	}

	public void setRunDatabaseName(String runDatabaseName) {
		this.runDatabaseName = runDatabaseName;
	}

	public String getSettingsDatabaseName() {
		return settingsDatabaseName;
	}

	public void setSettingsDatabaseName(String settingsDatabaseName) {
		this.settingsDatabaseName = settingsDatabaseName;
	}

	public String getEquipmentCodesDatabaseName() {
		return equipmentCodesDatabaseName;
	}

	public void setEquipmentCodesDatabaseName(String equipmentCodesDatabaseName) {
		this.equipmentCodesDatabaseName = equipmentCodesDatabaseName;
	}
}