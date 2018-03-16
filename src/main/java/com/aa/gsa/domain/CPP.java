package com.aa.gsa.domain;

import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.GroupType;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.InvalidGroupException;
import com.aa.gsa.exception.InvalidGroupTypeException;
import com.aa.gsa.exception.InvalidMinServiceLevelException;
import com.aa.gsa.exception.InvalidTravelTypeException;

public class CPP {
	
	private Integer itemNumber;
	
	private String groupType;

	private String originAirport;
	
	private String destinationAirport;
	
	private Integer groupNumber;
	
	private Integer numberOfFlights;
	
	private String minService;	
	
	private String typeOfTravel;
	
	private String paxLevel;
	
	private Integer paxCount;
	
	private String originCity;
	
	private String originState;
	
	private String originCountry;
	
	private String destinationCity;
	
	private String destinationState;

	private String destinationCountry;
	
	/**
	 * Derived fields for reporting purpose
	 */
	private int maxGroundTime;
	
	private int maxCircuity;
	
	private Boolean isCrossingThreeTimeZones;

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getOriginAirport() {
		return originAirport;
	}

	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public Integer getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}

	public Integer getNumberOfFlights() {
		return numberOfFlights;
	}

	public void setNumberOfFlights(Integer numberOfFlights) {
		this.numberOfFlights = numberOfFlights;
	}

	public String getMinService() {
		return minService;
	}

	public void setMinService(String minService) {
		this.minService = minService;
	}

	public String getTypeOfTravel() {
		return typeOfTravel;
	}

	public void setTypeOfTravel(String typeOfTravel) {
		this.typeOfTravel = typeOfTravel;
	}

	public String getPaxLevel() {
		return paxLevel;
	}

	public void setPaxLevel(String paxLevel) {
		this.paxLevel = paxLevel;
	}

	public Integer getPaxCount() {
		return paxCount;
	}

	public void setPaxCount(Integer paxCount) {
		this.paxCount = paxCount;
	}

	public String getOriginCity() {
		return originCity;
	}

	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	public String getOriginState() {
		return originState;
	}

	public void setOriginState(String originState) {
		this.originState = originState;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}   

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public int getMaxGroundTime() {
		return maxGroundTime;
	}

	public void setMaxGroundTime(int maxGroundTime) {
		this.maxGroundTime = maxGroundTime;
	}

	public int getMaxCircuity() {
		return maxCircuity;
	}

	public void setMaxCircuity(int maxCircuity) {
		this.maxCircuity = maxCircuity;
	}
	
	/**
	 *
	 * Derived methods
	 * 
	 */
	public ServiceLevel minServiceLevel() {
		ServiceLevel serviceLevel = ServiceLevel.findByValue(this.minService);
		if (serviceLevel == null) {
			throw new InvalidMinServiceLevelException(this);
		}
		return serviceLevel;
	}
	
	public TravelType travelType() {
		TravelType travelType = TravelType.findByValue(this.typeOfTravel);
		if (travelType == null) {
			throw new InvalidTravelTypeException(this);
		}
		return travelType;
	}
	
	public Group group() {
		Group group = Group.findByGroupNumber(this.groupNumber);
		if (groupType == null) {
			throw new InvalidGroupException(this);
		}
		return group;
	}
	
	public GroupType groupType() {
		GroupType groupType = GroupType.findByValue(this.groupType);
		if (groupType == null) {
			throw new InvalidGroupTypeException(this);
		}
		return groupType;
	}

	public Boolean getIsCrossingThreeTimeZones() {
		return isCrossingThreeTimeZones;
	}

	public void setIsCrossingThreeTimeZones(Boolean isCrossingThreeTimeZones) {
		this.isCrossingThreeTimeZones = isCrossingThreeTimeZones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationAirport == null) ? 0 : destinationAirport.hashCode());
		result = prime * result + ((destinationCity == null) ? 0 : destinationCity.hashCode());
		result = prime * result + ((destinationCountry == null) ? 0 : destinationCountry.hashCode());
		result = prime * result + ((destinationState == null) ? 0 : destinationState.hashCode());
		result = prime * result + ((groupNumber == null) ? 0 : groupNumber.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((itemNumber == null) ? 0 : itemNumber.hashCode());
		result = prime * result + maxCircuity;
		result = prime * result + maxGroundTime;
		result = prime * result + ((minService == null) ? 0 : minService.hashCode());
		result = prime * result + ((numberOfFlights == null) ? 0 : numberOfFlights.hashCode());
		result = prime * result + ((originAirport == null) ? 0 : originAirport.hashCode());
		result = prime * result + ((originCity == null) ? 0 : originCity.hashCode());
		result = prime * result + ((originCountry == null) ? 0 : originCountry.hashCode());
		result = prime * result + ((originState == null) ? 0 : originState.hashCode());
		result = prime * result + ((paxCount == null) ? 0 : paxCount.hashCode());
		result = prime * result + ((paxLevel == null) ? 0 : paxLevel.hashCode());
		result = prime * result + ((typeOfTravel == null) ? 0 : typeOfTravel.hashCode());
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
		CPP other = (CPP) obj;
		if (destinationAirport == null) {
			if (other.destinationAirport != null)
				return false;
		} else if (!destinationAirport.equals(other.destinationAirport))
			return false;
		if (destinationCity == null) {
			if (other.destinationCity != null)
				return false;
		} else if (!destinationCity.equals(other.destinationCity))
			return false;
		if (destinationCountry == null) {
			if (other.destinationCountry != null)
				return false;
		} else if (!destinationCountry.equals(other.destinationCountry))
			return false;
		if (destinationState == null) {
			if (other.destinationState != null)
				return false;
		} else if (!destinationState.equals(other.destinationState))
			return false;
		if (groupNumber == null) {
			if (other.groupNumber != null)
				return false;
		} else if (!groupNumber.equals(other.groupNumber))
			return false;
		if (groupType == null) {
			if (other.groupType != null)
				return false;
		} else if (!groupType.equals(other.groupType))
			return false;
		if (itemNumber == null) {
			if (other.itemNumber != null)
				return false;
		} else if (!itemNumber.equals(other.itemNumber))
			return false;
		if (maxCircuity != other.maxCircuity)
			return false;
		if (maxGroundTime != other.maxGroundTime)
			return false;
		if (minService == null) {
			if (other.minService != null)
				return false;
		} else if (!minService.equals(other.minService))
			return false;
		if (numberOfFlights == null) {
			if (other.numberOfFlights != null)
				return false;
		} else if (!numberOfFlights.equals(other.numberOfFlights))
			return false;
		if (originAirport == null) {
			if (other.originAirport != null)
				return false;
		} else if (!originAirport.equals(other.originAirport))
			return false;
		if (originCity == null) {
			if (other.originCity != null)
				return false;
		} else if (!originCity.equals(other.originCity))
			return false;
		if (originCountry == null) {
			if (other.originCountry != null)
				return false;
		} else if (!originCountry.equals(other.originCountry))
			return false;
		if (originState == null) {
			if (other.originState != null)
				return false;
		} else if (!originState.equals(other.originState))
			return false;
		if (paxCount == null) {
			if (other.paxCount != null)
				return false;
		} else if (!paxCount.equals(other.paxCount))
			return false;
		if (paxLevel == null) {
			if (other.paxLevel != null)
				return false;
		} else if (!paxLevel.equals(other.paxLevel))
			return false;
		if (typeOfTravel == null) {
			if (other.typeOfTravel != null)
				return false;
		} else if (!typeOfTravel.equals(other.typeOfTravel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CPP [itemNumber=" + itemNumber + ", groupType=" + groupType + ", originAirport=" + originAirport
				+ ", destinationAirport=" + destinationAirport + ", groupNumber=" + groupNumber + ", numberOfFlights="
				+ numberOfFlights + ", minService=" + minService + ", typeOfTravel=" + typeOfTravel + ", paxLevel="
				+ paxLevel + ", paxCount=" + paxCount + ", originCity=" + originCity + ", originState=" + originState
				+ ", originCountry=" + originCountry + ", destinationCity=" + destinationCity + ", destinationState="
				+ destinationState + ", destinationCountry=" + destinationCountry + ", maxGroundTime=" + maxGroundTime
				+ ", maxCircuity=" + maxCircuity + "]";
	}
}
