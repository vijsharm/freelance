package com.aa.gsa.domain;

public class EligibilityResult {
    
    public static enum EligibilityReasonCode {ZERO, ONE, TWO, THREE, FOUR, FIVE}
    
    private String _id;
    private String _rev;

    private int runId;
    private int itemNo;
    private String orig;
    private String dest;
    
    private boolean eligible;
    private String  eligiblilityReason;
    private String eligibilityReasonCode;
    
	private String departureAirportCode;
	private String arrivalAirportCode;
	private String arrivalHour;
	private String arrivalMinute;
	private String arrivalGMTHour;
	private String arrivalGMTMinute;
	private String departureHour;
	private String departureMinute;
	private String departureGMTHour;
	private String departureGMTMinute;
	private Integer numberOfConnections;
	private Integer numberOfStops;
	private String categoryType;
	private Integer frequencyCount;
	
	private String legAirline1;
	private Integer legFlightNumber1;
	private String departureAirportCode1;
	private String arrivalAirportCode1;

	private String legAirline2;
	private Integer legFlightNumber2;
	private String departureAirportCode2;
	private String arrivalAirportCode2;

	//_id and rev
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

    public int getRunId()
    {
        return runId;
    }

    public void setRunId(int runId)
    {
        this.runId = runId;
    }

    public int getItemNo()
    {
        return itemNo;
    }

    public void setItemNo(int itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getOrig()
    {
        return orig;
    }

    public void setOrig(String orig)
    {
        this.orig = orig;
    }

    public String getDest()
    {
        return dest;
    }

    public void setDest(String dest)
    {
        this.dest = dest;
    }

    public boolean isEligible()
    {
        return eligible;
    }

    public void setEligible(boolean eligible)
    {
        this.eligible = eligible;
    }

    public String getEligiblilityReason()
    {
        return eligiblilityReason;
    }

    public void setEligiblilityReason(String eligiblilityReason)
    {
        this.eligiblilityReason = eligiblilityReason;
    }

    public String getEligibilityReasonCode()
    {
        return eligibilityReasonCode;
    }

    public void setEligibilityReasonCode(String eligibilityReasonCode)
    {
        this.eligibilityReasonCode = eligibilityReasonCode;
    }

    public String getDepartureAirportCode()
    {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode)
    {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode()
    {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode)
    {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getArrivalHour()
    {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour)
    {
        this.arrivalHour = arrivalHour;
    }

    public String getArrivalMinute()
    {
        return arrivalMinute;
    }

    public void setArrivalMinute(String arrivalMinute)
    {
        this.arrivalMinute = arrivalMinute;
    }

    public String getArrivalGMTHour()
    {
        return arrivalGMTHour;
    }

    public void setArrivalGMTHour(String arrivalGMTHour)
    {
        this.arrivalGMTHour = arrivalGMTHour;
    }

    public String getArrivalGMTMinute()
    {
        return arrivalGMTMinute;
    }

    public void setArrivalGMTMinute(String arrivalGMTMinute)
    {
        this.arrivalGMTMinute = arrivalGMTMinute;
    }

    public String getDepartureHour()
    {
        return departureHour;
    }

    public void setDepartureHour(String departureHour)
    {
        this.departureHour = departureHour;
    }

    public String getDepartureMinute()
    {
        return departureMinute;
    }

    public void setDepartureMinute(String departureMinute)
    {
        this.departureMinute = departureMinute;
    }

    public String getDepartureGMTHour()
    {
        return departureGMTHour;
    }

    public void setDepartureGMTHour(String departureGMTHour)
    {
        this.departureGMTHour = departureGMTHour;
    }

    public String getDepartureGMTMinute()
    {
        return departureGMTMinute;
    }

    public void setDepartureGMTMinute(String departureGMTMinute)
    {
        this.departureGMTMinute = departureGMTMinute;
    }

    public Integer getNumberOfConnections()
    {
        return numberOfConnections;
    }

    public void setNumberOfConnections(Integer numberOfConnections)
    {
        this.numberOfConnections = numberOfConnections;
    }

    public Integer getNumberOfStops()
    {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops)
    {
        this.numberOfStops = numberOfStops;
    }

    public String getCategoryType()
    {
        return categoryType;
    }

    public void setCategoryType(String categoryType)
    {
        this.categoryType = categoryType;
    }

    public Integer getFrequencyCount()
    {
        return frequencyCount;
    }

    public void setFrequencyCount(Integer frequencyCount)
    {
        this.frequencyCount = frequencyCount;
    }

    public String getLegAirline1()
    {
        return legAirline1;
    }

    public void setLegAirline1(String legAirline1)
    {
        this.legAirline1 = legAirline1;
    }

    public Integer getLegFlightNumber1()
    {
        return legFlightNumber1;
    }

    public void setLegFlightNumber1(Integer legFlightNumber1)
    {
        this.legFlightNumber1 = legFlightNumber1;
    }

    public String getDepartureAirportCode1()
    {
        return departureAirportCode1;
    }

    public void setDepartureAirportCode1(String departureAirportCode1)
    {
        this.departureAirportCode1 = departureAirportCode1;
    }

    public String getArrivalAirportCode1()
    {
        return arrivalAirportCode1;
    }

    public void setArrivalAirportCode1(String arrivalAirportCode1)
    {
        this.arrivalAirportCode1 = arrivalAirportCode1;
    }

    public String getLegAirline2()
    {
        return legAirline2;
    }

    public void setLegAirline2(String legAirline2)
    {
        this.legAirline2 = legAirline2;
    }

    public Integer getLegFlightNumber2()
    {
        return legFlightNumber2;
    }

    public void setLegFlightNumber2(Integer legFlightNumber2)
    {
        this.legFlightNumber2 = legFlightNumber2;
    }

    public String getDepartureAirportCode2()
    {
        return departureAirportCode2;
    }

    public void setDepartureAirportCode2(String departureAirportCode2)
    {
        this.departureAirportCode2 = departureAirportCode2;
    }

    public String getArrivalAirportCode2()
    {
        return arrivalAirportCode2;
    }

    public void setArrivalAirportCode2(String arrivalAirportCode2)
    {
        this.arrivalAirportCode2 = arrivalAirportCode2;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((arrivalAirportCode == null) ? 0 : arrivalAirportCode.hashCode());
        result = prime * result + ((arrivalAirportCode1 == null) ? 0 : arrivalAirportCode1.hashCode());
        result = prime * result + ((arrivalAirportCode2 == null) ? 0 : arrivalAirportCode2.hashCode());
        result = prime * result + ((arrivalGMTHour == null) ? 0 : arrivalGMTHour.hashCode());
        result = prime * result + ((arrivalGMTMinute == null) ? 0 : arrivalGMTMinute.hashCode());
        result = prime * result + ((arrivalHour == null) ? 0 : arrivalHour.hashCode());
        result = prime * result + ((arrivalMinute == null) ? 0 : arrivalMinute.hashCode());
        result = prime * result + ((categoryType == null) ? 0 : categoryType.hashCode());
        result = prime * result + ((departureAirportCode == null) ? 0 : departureAirportCode.hashCode());
        result = prime * result + ((departureAirportCode1 == null) ? 0 : departureAirportCode1.hashCode());
        result = prime * result + ((departureAirportCode2 == null) ? 0 : departureAirportCode2.hashCode());
        result = prime * result + ((departureGMTHour == null) ? 0 : departureGMTHour.hashCode());
        result = prime * result + ((departureGMTMinute == null) ? 0 : departureGMTMinute.hashCode());
        result = prime * result + ((departureHour == null) ? 0 : departureHour.hashCode());
        result = prime * result + ((departureMinute == null) ? 0 : departureMinute.hashCode());
        result = prime * result + ((dest == null) ? 0 : dest.hashCode());
        result = prime * result + itemNo;
        result = prime * result + ((legAirline1 == null) ? 0 : legAirline1.hashCode());
        result = prime * result + ((legAirline2 == null) ? 0 : legAirline2.hashCode());
        result = prime * result + ((legFlightNumber1 == null) ? 0 : legFlightNumber1.hashCode());
        result = prime * result + ((legFlightNumber2 == null) ? 0 : legFlightNumber2.hashCode());
        result = prime * result + ((orig == null) ? 0 : orig.hashCode());
        result = prime * result + runId;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EligibilityResult other = (EligibilityResult) obj;
        if (arrivalAirportCode == null)
        {
            if (other.arrivalAirportCode != null)
                return false;
        }
        else if (!arrivalAirportCode.equals(other.arrivalAirportCode))
            return false;
        if (arrivalAirportCode1 == null)
        {
            if (other.arrivalAirportCode1 != null)
                return false;
        }
        else if (!arrivalAirportCode1.equals(other.arrivalAirportCode1))
            return false;
        if (arrivalAirportCode2 == null)
        {
            if (other.arrivalAirportCode2 != null)
                return false;
        }
        else if (!arrivalAirportCode2.equals(other.arrivalAirportCode2))
            return false;
        if (arrivalGMTHour == null)
        {
            if (other.arrivalGMTHour != null)
                return false;
        }
        else if (!arrivalGMTHour.equals(other.arrivalGMTHour))
            return false;
        if (arrivalGMTMinute == null)
        {
            if (other.arrivalGMTMinute != null)
                return false;
        }
        else if (!arrivalGMTMinute.equals(other.arrivalGMTMinute))
            return false;
        if (arrivalHour == null)
        {
            if (other.arrivalHour != null)
                return false;
        }
        else if (!arrivalHour.equals(other.arrivalHour))
            return false;
        if (arrivalMinute == null)
        {
            if (other.arrivalMinute != null)
                return false;
        }
        else if (!arrivalMinute.equals(other.arrivalMinute))
            return false;
        if (categoryType == null)
        {
            if (other.categoryType != null)
                return false;
        }
        else if (!categoryType.equals(other.categoryType))
            return false;
        if (departureAirportCode == null)
        {
            if (other.departureAirportCode != null)
                return false;
        }
        else if (!departureAirportCode.equals(other.departureAirportCode))
            return false;
        if (departureAirportCode1 == null)
        {
            if (other.departureAirportCode1 != null)
                return false;
        }
        else if (!departureAirportCode1.equals(other.departureAirportCode1))
            return false;
        if (departureAirportCode2 == null)
        {
            if (other.departureAirportCode2 != null)
                return false;
        }
        else if (!departureAirportCode2.equals(other.departureAirportCode2))
            return false;
        if (departureGMTHour == null)
        {
            if (other.departureGMTHour != null)
                return false;
        }
        else if (!departureGMTHour.equals(other.departureGMTHour))
            return false;
        if (departureGMTMinute == null)
        {
            if (other.departureGMTMinute != null)
                return false;
        }
        else if (!departureGMTMinute.equals(other.departureGMTMinute))
            return false;
        if (departureHour == null)
        {
            if (other.departureHour != null)
                return false;
        }
        else if (!departureHour.equals(other.departureHour))
            return false;
        if (departureMinute == null)
        {
            if (other.departureMinute != null)
                return false;
        }
        else if (!departureMinute.equals(other.departureMinute))
            return false;
        if (dest == null)
        {
            if (other.dest != null)
                return false;
        }
        else if (!dest.equals(other.dest))
            return false;
        if (itemNo != other.itemNo)
            return false;
        if (legAirline1 == null)
        {
            if (other.legAirline1 != null)
                return false;
        }
        else if (!legAirline1.equals(other.legAirline1))
            return false;
        if (legAirline2 == null)
        {
            if (other.legAirline2 != null)
                return false;
        }
        else if (!legAirline2.equals(other.legAirline2))
            return false;
        if (legFlightNumber1 == null)
        {
            if (other.legFlightNumber1 != null)
                return false;
        }
        else if (!legFlightNumber1.equals(other.legFlightNumber1))
            return false;
        if (legFlightNumber2 == null)
        {
            if (other.legFlightNumber2 != null)
                return false;
        }
        else if (!legFlightNumber2.equals(other.legFlightNumber2))
            return false;
        if (orig == null)
        {
            if (other.orig != null)
                return false;
        }
        else if (!orig.equals(other.orig))
            return false;
        if (runId != other.runId)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "EligibilityResult [_id=" + _id + ", _rev=" + _rev + ", runId=" + runId + ", itemNo=" + itemNo + ", orig=" + orig + ", dest=" + dest
                + ", eligible=" + eligible + ", eligiblilityReason=" + eligiblilityReason + ", departureAirportCode=" + departureAirportCode
                + ", arrivalAirportCode=" + arrivalAirportCode + ", arrivalHour=" + arrivalHour + ", arrivalMinute=" + arrivalMinute + ", arrivalGMTHour="
                + arrivalGMTHour + ", arrivalGMTMinute=" + arrivalGMTMinute + ", departureHour=" + departureHour + ", departureMinute=" + departureMinute
                + ", departureGMTHour=" + departureGMTHour + ", departureGMTMinute=" + departureGMTMinute + ", numberOfConnections=" + numberOfConnections
                + ", numberOfStops=" + numberOfStops + ", categoryType=" + categoryType + ", frequencyCount=" + frequencyCount + ", legAirline1=" + legAirline1
                + ", legFlightNumber1=" + legFlightNumber1 + ", departureAirportCode1=" + departureAirportCode1 + ", arrivalAirportCode1=" + arrivalAirportCode1
                + ", legAirline2=" + legAirline2 + ", legFlightNumber2=" + legFlightNumber2 + ", departureAirportCode2=" + departureAirportCode2
                + ", arrivalAirportCode2=" + arrivalAirportCode2 + "]";
    }
}	
