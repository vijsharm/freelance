package com.aa.gsa.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.EligibilityResult.EligibilityReasonCode;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.InvalidNumberOfStopsException;
import com.aa.gsa.exception.UnknownServiceLevelException;
import com.aa.gsa.service.EligibilityResultService;
import com.aa.gsa.service.ScheduleService;

/**
 * 
 * @author 940914
 * 
 */
public class ScheduleServiceImpl implements ScheduleService {

	private static final LocalTime SIX_AM = LocalTime.of(6, 00); 
	private static final LocalTime ELEVEN_PM = LocalTime.of(23, 00); 
	private static final String ALASKA = "AK";
	private static final String HAWAII = "HI";

	public ScheduleServiceImpl() {
	}

	@Override
	public boolean isEastBound(Schedule schedule) {
		return !isWestBound(schedule);
	}

	@Override
	public boolean isWestBound(Schedule schedule) {
		LocalDateTime departureTime = LocalDateTime.of(LocalDate.now(), timeOf(schedule.getDepartureHour(), schedule.getDepartureMinute()));
		LocalDateTime arrivalTime = LocalDateTime.of(LocalDate.now(), timeOf(schedule.getArrivalHour(), schedule.getArrivalMinute()));
		if (arrivalTime.isBefore(departureTime)) {
			arrivalTime = arrivalTime.plusDays(1);
		}
		return schedule.getElapsedMinutes() > (ChronoUnit.MINUTES.between(departureTime, arrivalTime));
	}

	@Override
	public boolean isDepartingBetweenSixAndElevenPM(Schedule schedule) {
		LocalTime departureTime = timeOf(schedule.getDepartureHour(), schedule.getDepartureMinute());
		return departureTime.equals(SIX_AM) ||  departureTime.equals(ELEVEN_PM) || (departureTime.isAfter(SIX_AM) && departureTime.isBefore(ELEVEN_PM));
	}

	private LocalTime timeOf(String hour, String minute) {
		return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
	}

	@Override
	public long calculateGroundTimeInMinutes(Schedule schedule) {
		if (schedule.getNumberOfStops() == null) {//non-stop
			return 0;
		}

		LocalTime departureTime = timeOf(schedule.getDepartureHour(), schedule.getDepartureMinute());
		LocalTime arrivalTime = timeOf(schedule.getArrivalHour(), schedule.getArrivalMinute());

		switch (schedule.getNumberOfStops()) {
		case 1: 
			departureTime = timeOf(schedule.getDepartureHour2(), schedule.getDepartureMinute2());
			arrivalTime = timeOf(schedule.getArrivalHour1(), schedule.getArrivalMinute1());
			break;
		case 2: 
			departureTime = timeOf(schedule.getDepartureHour3(), schedule.getDepartureMinute3());
			arrivalTime = timeOf(schedule.getArrivalHour2(), schedule.getArrivalMinute2());
			break;
		case 3: 
			departureTime = timeOf(schedule.getDepartureHour4(), schedule.getDepartureMinute4());
			arrivalTime = timeOf(schedule.getArrivalHour3(), schedule.getArrivalMinute3());
			break;
		case 4: 
			departureTime = timeOf(schedule.getDepartureHour5(), schedule.getDepartureMinute5());
			arrivalTime = timeOf(schedule.getArrivalHour4(), schedule.getArrivalMinute4());
			break;
		case 5: 
			departureTime = timeOf(schedule.getDepartureHour6(), schedule.getDepartureMinute6());
			arrivalTime = timeOf(schedule.getArrivalHour5(), schedule.getArrivalMinute5());
			break;

		default:
			throw new InvalidNumberOfStopsException(schedule);
		}

		long groundTime = differenceInMinutes(arrivalTime, departureTime);

		return groundTime;
	}

	private long differenceInMinutes(LocalTime startTime, LocalTime endTime) {
		LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
		LocalDateTime endDateTime = endTime.isAfter(startTime) ? LocalDateTime.of(LocalDate.now(), endTime) : LocalDateTime.of(LocalDate.now().plusDays(1), endTime);

		long difference = ChronoUnit.MINUTES.between(startDateTime, endDateTime);

		return difference;
	}

	@Override
	public ServiceLevel getServiceLevel(Schedule schedule) {
		if (schedule.getNumberOfStops() == null || schedule.getNumberOfConnections() == null) {
			return ServiceLevel.NON_STOP;
		} else if (schedule.getNumberOfStops() == 1 && schedule.getLegFlightNumber1().equals(schedule.getLegFlightNumber2())) {
			return ServiceLevel.DIRECT;
		} else if(schedule.getNumberOfConnections() != null) {
			return ServiceLevel.CONNECTION;
		} else {
			throw new UnknownServiceLevelException(schedule);
		}
	}

	@Override
	public void populateServiceLevels(Collection<Schedule> schedules) {
		schedules
		.stream()
		.forEach(schedule -> schedule.setServiceLevel(getServiceLevel(schedule)));
	}

	@Override
	public Map<ServiceLevel, Set<Schedule>> groupByServiceLevels(Set<Schedule> schedules) {
		return schedules
				.stream()
				.collect(Collectors.groupingBy(Schedule::getServiceLevel, Collectors.toSet()));
	}

	@Override
	public boolean isCodeshare(Schedule schedule) {
		if (schedule.getNumberOfStops() == null) {
			return false;
		} else {
			switch (schedule.getNumberOfStops()) {
			case 1: return schedule.getLegAirline1().equals(schedule.getLegAirline2());

			case 2: return schedule.getLegAirline1().equals(schedule.getLegAirline2()) && 
					schedule.getLegAirline2().equals(schedule.getLegAirline3());

			case 3: return schedule.getLegAirline1().equals(schedule.getLegAirline2()) && 
					schedule.getLegAirline2().equals(schedule.getLegAirline3()) && 
					schedule.getLegAirline3().equals(schedule.getLegAirline4());

			case 4:  return schedule.getLegAirline1().equals(schedule.getLegAirline2()) && 
					schedule.getLegAirline2().equals(schedule.getLegAirline3()) && 
					schedule.getLegAirline3().equals(schedule.getLegAirline4()) &&
					schedule.getLegAirline4().equals(schedule.getLegAirline5());

			case 5: return schedule.getLegAirline1().equals(schedule.getLegAirline2()) && 
					schedule.getLegAirline2().equals(schedule.getLegAirline3()) && 
					schedule.getLegAirline3().equals(schedule.getLegAirline4()) &&
					schedule.getLegAirline4().equals(schedule.getLegAirline5()) &&
					schedule.getLegAirline5().equals(schedule.getLegAirline6());			
			default:
				throw new InvalidNumberOfStopsException(schedule);	
			}
		}
	}

	@Override
	public Set<Schedule> mergeDuplicateRecords(Collection<Schedule> schedules) {
		if (schedules == null) {
			return null;
		}

		Map<Schedule, Set<DayOfOperation>> mapOfSchedules = new HashMap<>(schedules.size());

		for (Schedule schedule : schedules) {
			if (mapOfSchedules.get(schedule) == null) {
				mapOfSchedules.put(schedule, new HashSet<>(5));
			}

			Set<DayOfOperation> operatingDays = mapOfSchedules.get(schedule);

			populateOperatingDays(operatingDays, schedule.getDaysOfOperation());

			//update the operating days and frequency after the merge
			schedule.setOperatingDays(operatingDays);
		}
		return mapOfSchedules.keySet();

	}

	@Override
	public Map<DayOfOperation, Integer> calculateNumberOfFlightsPerDay(Set<Schedule> schedules, CPP cpp, EligibilityResultService eligibilityResultService) {
		Map<DayOfOperation, Integer> numberOfFlightsPerDay = initializeNumberOfFlightsPerDay();
		schedules
		.stream()
		.forEach(schedule -> {
			boolean eligibleSchedule = false;	
			//check the departure time   
			if(cpp.getTypeOfTravel().equals(TravelType.DOMESTIC) && 
					(!(isEastBound(schedule) && cpp.getIsCrossingThreeTimeZones().equals(Boolean.TRUE)) || 
							cpp.getOriginState().equals(ALASKA) || cpp.getOriginState().equals(HAWAII))) 

			{
			    eligibleSchedule =  isDepartingBetweenSixAndElevenPM(schedule);
			    
			    if(!eligibleSchedule)
			    {
			        eligibilityResultService.save(cpp, schedule, false, EligibilityReasonCode.TWO, "Departure is not between 6:00 AM to 11:00 PM");
			    }
			} else {
				eligibleSchedule = true;
			}

			if (eligibleSchedule) {
				schedule
				.getOperatingDays()
				.stream()
				.forEach(operatingDay -> {
					int previousCount = numberOfFlightsPerDay.get(operatingDay);
					int numberOfFlights = 0;
					//if has non-stop when minimum-service is Connection
					if ((cpp.minServiceLevel().equals(ServiceLevel.CONNECTION) || cpp.minServiceLevel().equals(ServiceLevel.DIRECT)) 
							&& schedule.getServiceLevel().equals(ServiceLevel.NON_STOP)) {
						numberOfFlights = 2;
					} else {
						numberOfFlights = 1;
					}

					numberOfFlightsPerDay.put(operatingDay, previousCount + numberOfFlights);
				});					
			}
		});

		return numberOfFlightsPerDay;
	}

	private void populateOperatingDays(Set<DayOfOperation> operatingDays, String daysOfOperation) {
		for (char day : daysOfOperation.toCharArray()) {
			if (!Character.isWhitespace(day)) {
				operatingDays.add(DayOfOperation.findByValue(day));
			}
		}
	}

	private Map<DayOfOperation, Integer> initializeNumberOfFlightsPerDay() {
		Map<DayOfOperation, Integer> numberOfFlightsPerDay = new HashMap<>(DayOfOperation.values().length);
		for(DayOfOperation dayOfOperation : DayOfOperation.values()) {
			numberOfFlightsPerDay.put(dayOfOperation, 0);
		}
		return numberOfFlightsPerDay;
	}
}