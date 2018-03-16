package com.aa.gsa.domain;

public class StationCode {

	private String station;
	
	private String city;
	
	private String state;
	
	private String province;
	
	private String nation;
	
	private String world;
	
	private String stationName;
	
	private int gmtOffset;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	public int getGmtOffset() {
		return gmtOffset;
	}

	public void setGmtOffset(int gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + gmtOffset;
		result = prime * result + ((nation == null) ? 0 : nation.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
		result = prime * result + ((world == null) ? 0 : world.hashCode());
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
		StationCode other = (StationCode) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (gmtOffset != other.gmtOffset)
			return false;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		if (stationName == null) {
			if (other.stationName != null)
				return false;
		} else if (!stationName.equals(other.stationName))
			return false;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StationCode [station=" + station + ", city=" + city + ", state=" + state + ", province=" + province
				+ ", nation=" + nation + ", world=" + world + ", stationName=" + stationName + ", gmtOffset="
				+ gmtOffset + "]";
	}
}
