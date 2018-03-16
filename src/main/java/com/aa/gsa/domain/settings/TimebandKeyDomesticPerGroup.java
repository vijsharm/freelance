package com.aa.gsa.domain.settings;

import com.aa.gsa.enums.Group;

public class TimebandKeyDomesticPerGroup {
	
	private final Group group;
	
	private final TimebandKeyDomestic timebandKeyDomestic;

	public TimebandKeyDomesticPerGroup(Group group, TimebandKeyDomestic timebandKeyDomestic) {
		super();
		this.group = group;
		this.timebandKeyDomestic = timebandKeyDomestic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((timebandKeyDomestic == null) ? 0 : timebandKeyDomestic.hashCode());
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
		TimebandKeyDomesticPerGroup other = (TimebandKeyDomesticPerGroup) obj;
		if (group != other.group)
			return false;
		if (timebandKeyDomestic == null) {
			if (other.timebandKeyDomestic != null)
				return false;
		} else if (!timebandKeyDomestic.equals(other.timebandKeyDomestic))
			return false;
		return true;
	}

	public Group getGroup() {
		return group;
	}

	public TimebandKeyDomestic getTimebandKeyDomestic() {
		return timebandKeyDomestic;
	}	
}
