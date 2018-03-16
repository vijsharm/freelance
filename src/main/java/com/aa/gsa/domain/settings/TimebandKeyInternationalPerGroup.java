package com.aa.gsa.domain.settings;

import com.aa.gsa.enums.Group;

public class TimebandKeyInternationalPerGroup {
	
	private final Group group;
	
	private final TimebandKeyInternational timebandKeyInternational;

	public TimebandKeyInternationalPerGroup(Group group, TimebandKeyInternational timebandKeyInternational) {
		super();
		this.group = group;
		this.timebandKeyInternational = timebandKeyInternational;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((timebandKeyInternational == null) ? 0 : timebandKeyInternational.hashCode());
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
		TimebandKeyInternationalPerGroup other = (TimebandKeyInternationalPerGroup) obj;
		if (group != other.group)
			return false;
		if (timebandKeyInternational == null) {
			if (other.timebandKeyInternational != null)
				return false;
		} else if (!timebandKeyInternational.equals(other.timebandKeyInternational))
			return false;
		return true;
	}

	public Group getGroup() {
		return group;
	}

	public TimebandKeyInternational getTimebandKeyInternational() {
		return timebandKeyInternational;
	}	
}