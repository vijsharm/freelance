package com.aa.gsa.domain.settings;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.enums.ServiceLevel;

/**
 *
 * @author 940914
 * 
 */
public class TimebandKeyDomestic {

	private final Timeband timeband;
	
	private final ServiceLevel serviceLevel;

	private final boolean isWestbound;
	
	private final boolean isCrossing3Timezones;

	public TimebandKeyDomestic(Timeband timeband, ServiceLevel serviceLevel, boolean isWestbound,
			boolean isCrossing3Timezones) {
		super();
		this.timeband = timeband;
		this.serviceLevel = serviceLevel;
		this.isWestbound = isWestbound;
		this.isCrossing3Timezones = isCrossing3Timezones;
	}

	public Timeband getTimeband() {
		return timeband;
	}

	public ServiceLevel getServiceLevel() {
		return serviceLevel;
	}

	public boolean isWestbound() {
		return isWestbound;
	}

	public boolean isCrossing3Timezones() {
		return isCrossing3Timezones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isCrossing3Timezones ? 1231 : 1237);
		result = prime * result + (isWestbound ? 1231 : 1237);
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
		TimebandKeyDomestic other = (TimebandKeyDomestic) obj;
		if (isCrossing3Timezones != other.isCrossing3Timezones)
			return false;
		if (isWestbound != other.isWestbound)
			return false;
		if (serviceLevel != other.serviceLevel)
			return false;
		if (timeband != other.timeband)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimebandKeyDomestic [timeband=" + timeband + ", serviceLevel=" + serviceLevel + ", isWestbound="
				+ isWestbound + ", isCrossing3Timezones=" + isCrossing3Timezones + "]";
	}
}
