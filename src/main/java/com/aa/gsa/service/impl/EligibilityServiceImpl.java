package com.aa.gsa.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.Timeband;
import com.aa.gsa.domain.settings.GroundTimeSettings;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.Group;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.EligibilityService;
import com.aa.gsa.service.ScheduleService;
import com.aa.gsa.service.SettingsPerGroupService;

public class EligibilityServiceImpl implements EligibilityService {

	private SettingsPerGroupService settingsPerGroupService;
	
	private ScheduleService scheduleService;	
	
	private EligibilityResultService eligibilityResultService;
		
	public EligibilityServiceImpl(SettingsPerGroupService settingsPerGroupService, ScheduleService scheduleService, EligibilityResultService eligibilityResultService) {
		this.settingsPerGroupService = settingsPerGroupService;
		this.scheduleService = scheduleService;
		this.eligibilityResultService = eligibilityResultService;
	}
	
    @Override
    public boolean hasMinNoOfFlights(Set<Schedule> schedules, CPP cpp, Set<DayOfOperation> flightTimes)
    {
        final Map<DayOfOperation, Integer> numberOfFlightsPerDay = scheduleService.calculateNumberOfFlightsPerDay(schedules, cpp, eligibilityResultService);

        flightTimes.clear();
        
        //days in which required number of flights available
        flightTimes.addAll(numberOfFlightsPerDay
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() >= cpp.getNumberOfFlights())
                            .map(entry -> entry.getKey())
                            .collect(Collectors.toSet()));

        if(!CollectionUtils.isEmpty(flightTimes))
        {
            return true;
        }
        
        for(Schedule schedule: schedules)
        {
            eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.TWO, cpp.getNumberOfFlights() + " no of flights are required but not found");
        }
        
        return false;
    }
	
	@Override
	public boolean hasMetMinimumService(Set<Schedule> schedules, CPP cpp, Set<DayOfOperation> noOfFlightsPerDay) {

		GroundTimeSettings settings = settingsPerGroupService.groundTimeSettings().get(cpp.group());

		switch (cpp.travelType()) {
		case DOMESTIC:
		{
		    if(settings.getNoOfDaysForDomestic() > 0)
		    {
		        int noOfFlights = 0;
		        
		        if(noOfFlightsPerDay.contains(DayOfOperation.MONDAY))
		            noOfFlights++;
		        if(noOfFlightsPerDay.contains(DayOfOperation.TUESDAY))
                    noOfFlights++;
		        if(noOfFlightsPerDay.contains(DayOfOperation.WEDNESDAY))
                    noOfFlights++;
		        if(noOfFlightsPerDay.contains(DayOfOperation.THURSDAY))
                    noOfFlights++;
		        if(noOfFlightsPerDay.contains(DayOfOperation.FRIDAY))
                    noOfFlights++;

		        if(noOfFlights >= settings.getNoOfDaysForDomestic())
		        {
		            return true;
		        }
		        
		        for(Schedule schedule: schedules)
		        {
		            eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.THREE, 
		                                            settings.getNoOfDaysForDomestic() + " no of days are required for domestic flights but found only " + noOfFlights);
		        }
		        
		        return false;
		    }

		    String    reason      = "Flights ";
		    boolean   eligible    = true;

		    if(settings.getReqOnMonday() && !noOfFlightsPerDay.contains(DayOfOperation.MONDAY))
		    {
		        reason += "required on monday but not found, ";
		        eligible = false;
		    }
		    if(settings.getReqOnTuesday() && !noOfFlightsPerDay.contains(DayOfOperation.TUESDAY))
		    {
		        reason += "required on tuesday but not found, ";
		        eligible = false;
		    }
		    if(settings.getReqOnWednesday() && !noOfFlightsPerDay.contains(DayOfOperation.WEDNESDAY))
		    {
		        reason += "required on wednesday but not found, ";
		        eligible = false;
		    }
		    if(settings.getReqOnThursday() && !noOfFlightsPerDay.contains(DayOfOperation.THURSDAY))
		    {
		        reason += "required on thursday but not found, ";
		        eligible = false;
		    }
		    if(settings.getReqOnFriday() && !noOfFlightsPerDay.contains(DayOfOperation.FRIDAY))
		    {
		        reason += "required on friday but not found, ";
		        eligible = false;
		    }
		    
		    if(eligible)
		    {
		        return true;
		    }
		    
		    for(Schedule schedule: schedules)
            {
                eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.THREE, reason.substring(0, reason.length() - 2));
            }
            
            return false;
		}
		case INTERNATIONAL:
		{
		    if(settings.getNoOfDaysForInternational() > 0)
            {
                int noOfFlights = 0;
                
                if(noOfFlightsPerDay.contains(DayOfOperation.MONDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.TUESDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.WEDNESDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.THURSDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.FRIDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.SATURDAY))
                    noOfFlights++;
                if(noOfFlightsPerDay.contains(DayOfOperation.SUNDAY))
                    noOfFlights++;

                if(noOfFlights >= settings.getNoOfDaysForInternational())
                {
                    return true;
                }
                
                for(Schedule schedule: schedules)
                {
                    eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.THREE, 
                                                    settings.getNoOfDaysForDomestic() + " no of days are required for international flights but found only " + noOfFlights);
                }
                
                return false;
            }
		    
		    String    reason      = "International Flights ";
            boolean   eligible    = true;

            if(settings.getReqOnMondayInt() && !noOfFlightsPerDay.contains(DayOfOperation.MONDAY))
            {
                reason += "required on monday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnTuesdayInt() && !noOfFlightsPerDay.contains(DayOfOperation.TUESDAY))
            {
                reason += "required on tuesday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnWednesdayInt() && !noOfFlightsPerDay.contains(DayOfOperation.WEDNESDAY))
            {
                reason += "required on wednesday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnThursdayInt() && !noOfFlightsPerDay.contains(DayOfOperation.THURSDAY))
            {
                reason += "required on thursday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnFridayInt() && !noOfFlightsPerDay.contains(DayOfOperation.FRIDAY))
            {
                reason += "required on friday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnSaturdayInt() && !noOfFlightsPerDay.contains(DayOfOperation.SATURDAY))
            {
                reason += "required on saturday but not found, ";
                eligible = false;
            }
            if(settings.getReqOnSundayInt() && !noOfFlightsPerDay.contains(DayOfOperation.SUNDAY))
            {
                reason += "required on sunday but not found, ";
                eligible = false;
            }
            
            if(eligible)
            {
                return true;
            }
            
            for(Schedule schedule: schedules)
            {
                eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.THREE, reason.substring(0, reason.length() - 2));
            }
		    
            return false;
		}
		default: 
			return false;
		}
	}
	
	@Override
	public void deleteRepeatedLegs(Set<Schedule> schedules, CPP cpp) {
		//check if has repeat legs
		List<Integer> leg1Keys = schedules.stream().map(Schedule :: getLegKey1).filter(key -> key != null).collect(Collectors.toList());
		List<Integer> leg2Keys = schedules.stream().map(Schedule :: getLegKey2).filter(key -> key != null).collect(Collectors.toList());
		List<Integer> leg3Keys = schedules.stream().map(Schedule :: getLegKey3).filter(key -> key != null).collect(Collectors.toList());

		if (hasDuplicates(leg1Keys) || hasDuplicates(leg2Keys) || hasDuplicates(leg3Keys)) {

			//populate schedules per day
			Map<DayOfOperation, Set<Schedule>> schedulesPerDayGroup = new HashMap<>(DayOfOperation.values().length); 
			for (Schedule schedule : schedules) {
				for (DayOfOperation dayOfOperation : schedule.getOperatingDays()) {
					if (schedulesPerDayGroup.get(dayOfOperation) == null) {
						schedulesPerDayGroup.put(dayOfOperation, new HashSet<>());	
					}
					schedulesPerDayGroup.get(dayOfOperation).add(schedule);
				}
			}

			//group schedules by repeated legs for each day
			for (DayOfOperation dayOfOperation : schedulesPerDayGroup.keySet()) {
				Set<Schedule> schedulesPerDay = schedulesPerDayGroup.get(dayOfOperation);

				Map<Integer, Set<Schedule>> legGroup1 = new HashMap<>(schedules.size());
				Map<Integer, Set<Schedule>> legGroup2 = new HashMap<>(schedules.size());
				Map<Integer, Set<Schedule>> legGroup3 = new HashMap<>(schedules.size());

				for (Schedule schedule : schedulesPerDay) {
					if (schedule.getLegKey1() != null) {
						if (legGroup1.get(schedule.getLegKey1()) == null) {
							legGroup1.put(schedule.getLegKey1(), new HashSet<>(schedules.size()));
						}
						legGroup1.get(schedule.getLegKey1()).add(schedule);
					}
					if (schedule.getLegKey2() != null) {
						if (legGroup2.get(schedule.getLegKey2()) == null) {
							legGroup2.put(schedule.getLegKey2(), new HashSet<>(schedules.size()));
						}
						legGroup2.get(schedule.getLegKey2()).add(schedule);
					}
					if (schedule.getLegKey3() != null) {
						if (legGroup3.get(schedule.getLegKey3()) == null) {
							legGroup3.put(schedule.getLegKey3(), new HashSet<>(schedules.size()));
						}
						legGroup3.get(schedule.getLegKey3()).add(schedule);
					}
				}

				//start deleting repeat leg
				if (!CollectionUtils.isEmpty(legGroup1)) {
					deleteDayOfOperationForTheRepeatLeg(legGroup1, dayOfOperation, cpp);
				}

				if (!CollectionUtils.isEmpty(legGroup2)) {
					deleteDayOfOperationForTheRepeatLeg(legGroup2, dayOfOperation, cpp);
				}

				if (!CollectionUtils.isEmpty(legGroup3)) {
					deleteDayOfOperationForTheRepeatLeg(legGroup3, dayOfOperation, cpp);
				}
			}
		}
	}
	
	private boolean hasDuplicates(List<Integer> list) {
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		Set<Integer> set = new HashSet<>(list);
		return set.size() < list.size();
	}
	
	private void deleteDayOfOperationForTheRepeatLeg(Map<Integer, Set<Schedule>> legGroup, DayOfOperation dayOfOperation, CPP cpp) {
		for (Integer legKey : legGroup.keySet()) {
			if (legGroup.get(legKey).size() > 1) {
				Set<Schedule> schedules = legGroup.get(legKey);
				Schedule scheduleWithMaxPoints = getScheduleWithMaxPoints(schedules, cpp);
				if (scheduleWithMaxPoints != null) {
					for (Schedule schedule : schedules) {
						if (!schedule.equals(scheduleWithMaxPoints)) {
							schedule.getOperatingDays().remove(dayOfOperation);
						}
					}
				}
			}
		}
	}
		
	private Schedule getScheduleWithMaxPoints(Set<Schedule> schedules, CPP cpp) {
		int avgElapsedTime = (int) Math.floor((double) schedules.stream().mapToInt(Schedule::getElapsedMinutes).sum() / schedules.size());

		int maxPoints = Integer.MIN_VALUE;
		Schedule scheduleWithMaxPoints = null;

		for (Schedule schedule : schedules) {
			int points = schedule.getPoints() == null ? calculatePointsForSchedule(schedule, avgElapsedTime, cpp) : schedule.getPoints();
			if (points > maxPoints) {
				maxPoints = points;
				scheduleWithMaxPoints = schedule;
			}
		}
		return scheduleWithMaxPoints;
	}
	
	@Override
	public int calculatePointsForSchedule(Schedule schedule, int avgElapsedTime, CPP cpp) {
		int points = 0;
		final Timeband timeband = settingsPerGroupService.findTimebandByRange(schedule.getDepartureHour(), schedule.getDepartureMinute(), cpp.group());

		//time-bands
		if (timeband.getOrdinal() == 1) {
			points += 30;
		} else if(timeband.getOrdinal() == 2) {
			points += 20;
		}  else if(timeband.getOrdinal() == 3 && (cpp.getIsCrossingThreeTimeZones() == null ||  cpp.getIsCrossingThreeTimeZones().equals(Boolean.FALSE))) {
			points += 10;
		}  else if(timeband.getOrdinal() == 4 && (cpp.getIsCrossingThreeTimeZones() == null ||  cpp.getIsCrossingThreeTimeZones().equals(Boolean.FALSE))) {
			points += 10;
		}

		//service level
		if (schedule.getServiceLevel().equals(ServiceLevel.DIRECT)) {
			points += 15;
		} else if(schedule.getServiceLevel().equals(ServiceLevel.CONNECTION)) {
			points += 10;
		}

		//elapsed time
		if (cpp.travelType().equals(TravelType.DOMESTIC)) {
			points += (avgElapsedTime - schedule.getElapsedMinutes())/2 ;
		} else if (cpp.travelType().equals(TravelType.INTERNATIONAL)) {
			points += (avgElapsedTime - schedule.getElapsedMinutes())/6 ;
		}

		return points;
	}
}