package com.aa.gsa.domain.settings;

import java.util.List;
import java.util.Map;

import com.aa.gsa.domain.settings.Settings.ApprovedPartner;
import com.aa.gsa.enums.Group;

public class SettingsPerGroup {

	private String _id;

	private String _rev;

	private String name;

	private int numberOfSeatsForJet;

	private List<ApprovedPartner> approvedPartners;

	private Map<Group, Settings> groupSettings;

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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfSeatsForJet() {
		return numberOfSeatsForJet;
	}
	
	public void setNumberOfSeatsForJet(int numberOfSeatsForJet) {
		this.numberOfSeatsForJet = numberOfSeatsForJet;
	}
	
	public List<ApprovedPartner> getApprovedPartners() {
		return approvedPartners;
	}
	
	public void setApprovedPartners(List<ApprovedPartner> approvedPartners) {
		this.approvedPartners = approvedPartners;
	}
	
	public Map<Group, Settings> getGroupSettings() {
		return groupSettings;
	}
	
	public void setGroupSettings(Map<Group, Settings> groupSettings) {
		this.groupSettings = groupSettings;
	}
}
