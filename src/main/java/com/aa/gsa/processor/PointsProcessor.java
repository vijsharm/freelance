package com.aa.gsa.processor;

import static com.aa.gsa.enums.Direction.INBOUND;
import static com.aa.gsa.enums.Direction.OUTBOUND;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Payload;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.domain.result.DrilldownResult;
import com.aa.gsa.domain.result.PointsResult;
import com.aa.gsa.domain.result.Result;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.Direction;
import com.aa.gsa.enums.PointsCategory;
import com.aa.gsa.service.PointsService;
import com.google.common.collect.Table;

public class PointsProcessor implements ItemProcessor<Payload, Payload> {
	
	private PointsService pointsService;

	@Autowired
	public PointsProcessor(PointsService pointsService) {
		this.pointsService = pointsService;
	}

	@Override
	public Payload process(Payload payload) throws Exception {
		/**
		 * Calculate points for Master Report 
		 */
		calculatePoints(payload, false);

		/**
		 * Calculate points for NoCodeshare 
		 */			
		calculatePoints(payload, true);	

		return payload;
	}

	/**
	 * @param payload
	 * @param excludeCodeshare
	 * 
	 */
	protected void calculatePoints(Payload payload, boolean excludeCodeshare) {
		Table<Airline, Direction, Set<Schedule>> schedules = !excludeCodeshare ? payload.getSchedules() : payload.getSchedulesNoCodeshare();
		Map<Airline, Result> results = !excludeCodeshare ? payload.getResults() : payload.getResultsNoCodeshare();   
		CPP cpp = payload.getCpp();

		/**
		 * Used in elapsed time point calculation
		 * Key  =  airline
		 * value = true/false -> true = airline has no non-stop flights, false = has non-stop flights
		 */
		Map<Airline, Boolean> airlinesWithoutNonStopFlights = new HashMap<>(Airline.values().length);
		int airlinesWithoutNonStopCount = 0;

		if (schedules == null) {
			return;
		}

		int shortestElapsedTime = Integer.MAX_VALUE;
		for (Airline airline : Airline.values()) {
			final Set<Schedule> outbound = schedules.get(airline, OUTBOUND);
			final Set<Schedule> inbound = schedules.get(airline, INBOUND);
			Result result = results.get(airline);
			
			if (result.isEligible() || airline.equals(Airline.AA)) {
				//calculate points
				PointsResult pointsResult = pointsService.calcluatePoints(outbound, inbound, cpp);

				if (pointsResult.getNonStopCounts().get(Direction.OUTBOUND) == 0 || pointsResult.getNonStopCounts().get(Direction.INBOUND) == 0) {
					airlinesWithoutNonStopFlights.put(airline, true);
					airlinesWithoutNonStopCount++;
				} else {
					airlinesWithoutNonStopFlights.put(airline, false);
				}

				//update counts
				if (result instanceof DrilldownResult) {
					((DrilldownResult) result).setTimebands(pointsResult.getTimebands());		
					((DrilldownResult) result).setTotal(pointsResult.getTotal());
					((DrilldownResult) result).setJets(pointsResult.getJets());
				}

				//update points
				for (Map.Entry<PointsCategory, Integer> points: pointsResult.getPointsPerCategory().entrySet()) {
					result.getPointsPerCategory().put(points.getKey(), points.getValue());
				}

				//sum up all points
				int totalPoints = 0;
				for (PointsCategory pointsCategory : result.getPointsPerCategory().keySet()) {
					totalPoints += result.getPointsPerCategory().get(pointsCategory);
				}
				result.setPoints(totalPoints);

				//find shortest elapsed time 
				int elapsedTime = pointsResult.getElaspedTime();
				result.setElaspedTime(elapsedTime);
				if (elapsedTime < shortestElapsedTime) {
					shortestElapsedTime = elapsedTime;
				}
			}
		}

		//calculate elapsed time points
		for (Airline airline : Airline.values()) {
			Result result = results.get(airline);
			if ((result.isEligible() || airline.equals(Airline.AA)) && result.getElaspedTime() > 0) {
				boolean nonStopFlag = airlinesWithoutNonStopCount > 0 && airlinesWithoutNonStopFlights.get(airline).equals(Boolean.FALSE);
				int elapsedTimePoints = pointsService.calculateElapsedTimePoints(result.getElaspedTime(), shortestElapsedTime, nonStopFlag, cpp);
				result.getPointsPerCategory().put(PointsCategory.AVERAGE_ELAPASED_FLIGHT_TIME, elapsedTimePoints);
				result.setPoints(elapsedTimePoints + result.getPoints());
			}	
		}
	}
}
