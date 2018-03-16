package com.aa.gsa.domain.settings;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Settings Document Structure stored in database 
 */
public class Settings {
	private String _id;
	private String _rev;
	private String name;
	private List<Circuity> circuities; 
	private List<ApprovedPartner> approvedPartners;
	private List<TimebandObject> timebands;
	private MiscSettings miscSettings;

	public Settings(){
		this.circuities = new ArrayList<>();
		this.approvedPartners = new ArrayList<>();
	}

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
	
	public List<Circuity> getCircuities() {
		return circuities;
	}
	
	public void setCircuities(List<Circuity> circuities) {
		this.circuities = circuities;
	}

	public List<ApprovedPartner> getApprovedPartners() {
		return approvedPartners;
	}

	public void setApprovedPartners(List<ApprovedPartner> approvedPartners) {
		this.approvedPartners = approvedPartners;
	}

	public List<TimebandObject> getTimebands() {
		return timebands;
	}

	public void setTimebands(List<TimebandObject> timebands) {
		this.timebands = timebands;
	}

	public MiscSettings getMiscSettings() {
		return miscSettings;
	}

	public void setMiscSettings(MiscSettings miscSettings) {
		this.miscSettings = miscSettings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class Circuity {
		@NotNull
		private int from;
		@NotNull
		private int to;
		@NotNull
		private int percentage;
		@NotNull
		private String travelType;

		public int getFrom() {
			return from;
		}
		public void setFrom(int from) {
			this.from = from;
		}
		public int getTo() {
			return to;
		}
		public void setTo(int to) {
			this.to = to;
		}
		public int getPercentage() {
			return percentage;
		}
		public void setPercentage(int percentage) {
			this.percentage = percentage;
		}
		public String getTravelType() {
			return travelType;
		}
		public void setTravelType(String travelType) {
			this.travelType = travelType;
		}
	}

	public static class ApprovedPartner {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}	
	}

	public static class TimebandObject {
		private String name;
		private String serviceType; 
		private String travelType;
		private String groupType;
		private boolean eastToWest;
		private boolean eastToWestIncThreeTimezones;
		private List<TimebandDetail> timebandDetails;
		
		public TimebandObject(){
			this.timebandDetails = new ArrayList<>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isEastToWest() {
			return eastToWest;
		}

		public void setEastToWest(boolean eastToWest) {
			this.eastToWest = eastToWest;
		}

		public List<TimebandDetail> getTimebandDetails() {
			return timebandDetails;
		}

		public void setTimebandDetails(List<TimebandDetail> timebandDetails) {
			this.timebandDetails = timebandDetails;
		}
		
		public String getGroupType() {
			return groupType;
		}

		public void setGroupType(String groupType) {
			this.groupType = groupType;
		}

		public String getServiceType() {
			return serviceType;
		}

		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}

		public boolean isEastToWestIncThreeTimezones() {
			return eastToWestIncThreeTimezones;
		}

		public void setEastToWestIncThreeTimezones(boolean eastToWestIncThreeTimezones) {
			this.eastToWestIncThreeTimezones = eastToWestIncThreeTimezones;
		}

		public String getTravelType() {
			return travelType;
		}

		public void setTravelType(String travelType) {
			this.travelType = travelType;
		}
	}

	public static class TimebandDetail {
		private String fromTime;
		private String toTime;
		private int points;

		public String getFromTime() {
			return fromTime;
		}
		public void setFromTime(String fromTime) {
			this.fromTime = fromTime;
		}
		public String getToTime() {
			return toTime;
		}
		public void setToTime(String toTime) {
			this.toTime = toTime;
		}
		public int getPoints() {
			return points;
		}
		public void setPoints(int points) {
			this.points = points;
		}
	}

	public static class MiscSettings {
		private int numberOfSeatsForJet;
		private int numberOfConnectionsForEC;
		private int groundTimeDomestic;
		private int groundTimeDomesticEC;
		private int groundTimeIntExcMexicoAndCanada;
		private int groundTimeIntEC;
		private int groundTimeCanadaAndMexico;
		private boolean groundTimeRulesApplied = true;
		private boolean circuityRulesApplied = true;
		private int noOfDaysForInternational = 3;
		private Integer noOfDaysForDomestic = 5;
	    private boolean reqOnMonday = true;
	    private boolean reqOnTuesday = true;
	    private boolean reqOnWednesday = true;
	    private boolean reqOnThursday = true;
	    private boolean reqOnFriday = true;
	    private boolean reqOnMondayInt = true;
        private boolean reqOnTuesdayInt = true;
        private boolean reqOnWednesdayInt = true;
        private boolean reqOnThursdayInt = true;
        private boolean reqOnFridayInt = true;
        private boolean reqOnSaturdayInt = true;
        private boolean reqOnSundayInt = true;
        

		public int getNumberOfSeatsForJet() {
			return numberOfSeatsForJet;
		}

		public void setNumberOfSeatsForJet(int numberOfSeatsForJet) {
			this.numberOfSeatsForJet = numberOfSeatsForJet;
		}

		public int getNumberOfConnectionsForEC() {
			return numberOfConnectionsForEC;
		}

		public void setNumberOfConnectionsForEC(int numberOfConnectionsForEC) {
			this.numberOfConnectionsForEC = numberOfConnectionsForEC;
		}

		public int getGroundTimeDomestic() {
			return groundTimeDomestic;
		}

		public void setGroundTimeDomestic(int groundTimeDomestic) {
			this.groundTimeDomestic = groundTimeDomestic;
		}

		public int getGroundTimeDomesticEC() {
			return groundTimeDomesticEC;
		}

		public void setGroundTimeDomesticEC(int groundTimeDomesticEC) {
			this.groundTimeDomesticEC = groundTimeDomesticEC;
		}

		public int getGroundTimeIntExcMexicoAndCanada() {
			return groundTimeIntExcMexicoAndCanada;
		}

		public void setGroundTimeIntExcMexicoAndCanada(int groundTimeIntExcMexicoAndCanada) {
			this.groundTimeIntExcMexicoAndCanada = groundTimeIntExcMexicoAndCanada;
		}

		public int getGroundTimeIntEC() {
			return groundTimeIntEC;
		}

		public void setGroundTimeIntEC(int groundTimeIntEC) {
			this.groundTimeIntEC = groundTimeIntEC;
		}

		public int getGroundTimeCanadaAndMexico() {
			return groundTimeCanadaAndMexico;
		}

		public void setGroundTimeCanadaAndMexico(int groundTimeCanadaAndMexico) {
			this.groundTimeCanadaAndMexico = groundTimeCanadaAndMexico;
		}

		public boolean getGroundTimeRulesApplied() {
		    return groundTimeRulesApplied;
		  }

		  public void setGroundTimeRulesApplied(boolean groundTimeRulesApplied) {
		    this.groundTimeRulesApplied = groundTimeRulesApplied;
		  }
    
        public boolean getCircuityRulesApplied() {
          return circuityRulesApplied;
        }
    
        public void setCircuityRulesApplied(boolean circuityRulesApplied) {
          this.circuityRulesApplied = circuityRulesApplied;
        }

        public int getNoOfDaysForInternational()
        {
            return noOfDaysForInternational;
        }

        public void setNoOfDaysForInternational(int noOfDaysForInternational)
        {
            this.noOfDaysForInternational = noOfDaysForInternational;
        }

        public Integer getNoOfDaysForDomestic()
        {
            return noOfDaysForDomestic;
        }

        public void setNoOfDaysForDomestic(Integer noOfDaysForDomestic)
        {
            this.noOfDaysForDomestic = noOfDaysForDomestic;
        }

        public boolean getReqOnMonday() {
          return reqOnMonday;
        }

        public void setReqOnMonday(boolean reqOnMonday) {
          this.reqOnMonday = reqOnMonday;
        }

        public boolean getReqOnTuesday() {
          return reqOnTuesday;
        }

        public void setReqOnTuesday(boolean reqOnTuesday) {
          this.reqOnTuesday = reqOnTuesday;
        }

        public boolean getReqOnWednesday() {
          return reqOnWednesday;
        }

        public void setReqOnWednesday(boolean reqOnWednesday) {
          this.reqOnWednesday = reqOnWednesday;
        }

        public boolean getReqOnThursday() {
          return reqOnThursday;
        }

        public void setReqOnThursday(boolean reqOnThursday) {
          this.reqOnThursday = reqOnThursday;
        }

        public boolean getReqOnFriday() {
          return reqOnFriday;
        }

        public void setReqOnFriday(boolean reqOnFriday) {
          this.reqOnFriday = reqOnFriday;
        }

        public boolean getReqOnMondayInt()
        {
            return reqOnMondayInt;
        }

        public void setReqOnMondayInt(boolean reqOnMondayInt)
        {
            this.reqOnMondayInt = reqOnMondayInt;
        }

        public boolean getReqOnTuesdayInt()
        {
            return reqOnTuesdayInt;
        }

        public void setReqOnTuesdayInt(boolean reqOnTuesdayInt)
        {
            this.reqOnTuesdayInt = reqOnTuesdayInt;
        }

        public boolean getReqOnWednesdayInt()
        {
            return reqOnWednesdayInt;
        }

        public void setReqOnWednesdayInt(boolean reqOnWednesdayInt)
        {
            this.reqOnWednesdayInt = reqOnWednesdayInt;
        }

        public boolean getReqOnThursdayInt()
        {
            return reqOnThursdayInt;
        }

        public void setReqOnThursdayInt(boolean reqOnThursdayInt)
        {
            this.reqOnThursdayInt = reqOnThursdayInt;
        }

        public boolean getReqOnFridayInt()
        {
            return reqOnFridayInt;
        }

        public void setReqOnFridayInt(boolean reqOnFridayInt)
        {
            this.reqOnFridayInt = reqOnFridayInt;
        }

        public boolean getReqOnSaturdayInt()
        {
            return reqOnSaturdayInt;
        }

        public void setReqOnSaturdayInt(boolean reqOnSaturdayInt)
        {
            this.reqOnSaturdayInt = reqOnSaturdayInt;
        }

        public boolean getReqOnSundayInt()
        {
            return reqOnSundayInt;
        }

        public void setReqOnSundayInt(boolean reqOnSundayInt)
        {
            this.reqOnSundayInt = reqOnSundayInt;
        }
	}
	
	public static enum ServiceType {
		NON_STOP("NONSTOP"),
	    DIRECT("DIRECT"),
		CONNECTING("CONNECTING");
		
	    private String name;
	    private String description;
	    
	    private ServiceType(String name){
	    	this.name = name;
	    }
	    public String getName(){
	    	return name;
	    }
	    public void setName(String name){
	    	this.name = name;
	    }
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
