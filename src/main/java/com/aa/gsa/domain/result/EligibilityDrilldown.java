package com.aa.gsa.domain.result;

import java.util.Arrays;
import java.util.List;

import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.EligibilityCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"eligibilityCategory"})
public class EligibilityDrilldown {

	private boolean eligible;
	
	private List<String> reasons;

	public EligibilityCategory eligibilityCategory;
	
	public EligibilityDrilldown(boolean eligible) {
		this.eligible = eligible;
	}
	
	public EligibilityDrilldown(EligibilityCategory eligibilityCategory, boolean eligible) {
		this.eligibilityCategory = eligibilityCategory;
		this.eligible = eligible;
	}
	
	public EligibilityDrilldown(EligibilityCategory eligibilityCategory, boolean eligible, List<String> reasons) {
		this(eligibilityCategory, eligible);
		this.reasons = reasons;
	}
	
	public EligibilityDrilldown(boolean eligible, List<String> reasons) {
		this.eligible = eligible;
		this.reasons = reasons;
	}
	
	public EligibilityDrilldown(EligibilityCategory eligibilityCategory, boolean eligible, String reason) {
		this(eligibilityCategory, eligible);
		this.reasons = Arrays.asList(reason);
	}
	
	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	public List<String> getReasons() {
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}

	@JsonIgnore
	public EligibilityCategory getEligibilityCategory() {
		return eligibilityCategory;
	}

	public void setEligibilityCategory(EligibilityCategory eligibilityCategory) {
		this.eligibilityCategory = eligibilityCategory;
	}
	
	/**
	 * 
	 * @param airline
	 * @param eligibilityCategory
	 * @param eligible
	 * @param errorMessage
	 * @param drilldown
	 * @return
	 */
	public static EligibilityDrilldown createInstance(Airline airline, EligibilityCategory eligibilityCategory, boolean eligible, String errorMessage, boolean drilldown) {
		if (!drilldown) {
			return new EligibilityDrilldown(eligible);
		}
		switch (airline) {
			case AA: return new EligibilityDrilldown(eligibilityCategory, eligible, errorMessage);
			default: return new EligibilityDrilldown(eligible);
		}
	}
	
	public EligibilityDrilldown truncateCategory() {
		return new EligibilityDrilldown(this.eligible, this.reasons);	
	}
}
