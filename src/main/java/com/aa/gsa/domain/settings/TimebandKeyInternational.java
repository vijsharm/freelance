package com.aa.gsa.domain.settings;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.enums.GroupType;
import com.aa.gsa.enums.ServiceLevel;

public class TimebandKeyInternational {

	private final Timeband timeband;
	
	private final ServiceLevel serviceLevel;

    private final GroupType groupType;

	public TimebandKeyInternational(Timeband timeband, ServiceLevel serviceLevel, GroupType groupType) {
		super();
		this.timeband = timeband;
		this.serviceLevel = serviceLevel;
		this.groupType = groupType;
	}

	public Timeband getTimeband() {
		return timeband;
	}

	public ServiceLevel getServiceLevel() {
		return serviceLevel;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((serviceLevel == null) ? 0 : serviceLevel.hashCode());
		result = prime * result + ((timeband == null) ? 0 : timeband.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimebandKeyInternational other = (TimebandKeyInternational) obj;
		if (groupType != other.groupType)
			return false;
		if (serviceLevel != other.serviceLevel)
			return false;
		if (timeband != other.timeband)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimebandKeyInternational [timeband=" + timeband + ", serviceLevel=" + serviceLevel + ", groupType="
				+ groupType + "]";
	}
}
