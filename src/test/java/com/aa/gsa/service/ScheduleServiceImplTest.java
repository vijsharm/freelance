package com.aa.gsa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.service.impl.ScheduleServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class ScheduleServiceImplTest {

	private ObjectMapper mapper;

	private ScheduleService scheduleService;

	@Before
	public void before() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

		scheduleService = new ScheduleServiceImpl();
	}

	@Test
	public void mergeDuplicateRecordsTest() throws JsonParseException, JsonMappingException, IOException {
		List<Schedule> allSchedules = mapper.readValue(this.getClass().getResourceAsStream("/duplicate_schedules.json"), new TypeReference<List<Schedule>>(){});

		assertEquals(8, allSchedules.size());

		Set<Schedule> uniqueSchedules = scheduleService.mergeDuplicateRecords(allSchedules);

		assertEquals(7, uniqueSchedules.size());

		List<Schedule> duplicateSchedules = uniqueSchedules
				.stream()
				.filter(schedule -> (schedule.getDepartureHour().equals("08") && schedule.getDepartureMinute().equals("25")))
				.collect(Collectors.toList());

		assertEquals(duplicateSchedules.size(), 1);

		Set<DayOfOperation> mergedOperatingDays = duplicateSchedules.get(0).getOperatingDays();

		assertTrue(mergedOperatingDays.contains(DayOfOperation.MONDAY) &&
				mergedOperatingDays.contains(DayOfOperation.TUESDAY) &&
				mergedOperatingDays.contains(DayOfOperation.WEDNESDAY) &&
				mergedOperatingDays.contains(DayOfOperation.THURSDAY) &&
				mergedOperatingDays.contains(DayOfOperation.FRIDAY));
	}
	
	
	@Test
	public void mergeDuplicateRecordsTest2() throws JsonParseException, JsonMappingException, IOException {
		List<Schedule> allSchedules = mapper.readValue(this.getClass().getResourceAsStream("/DCA-ACK.json"), new TypeReference<List<Schedule>>(){});

		assertEquals(5, allSchedules.size());

		Set<Schedule> uniqueSchedules = scheduleService.mergeDuplicateRecords(allSchedules);

		assertEquals(4, uniqueSchedules.size());

		List<Schedule> duplicateSchedules = uniqueSchedules
				.stream()
				.filter(schedule -> (schedule.getDepartureHour().equals("10") && schedule.getDepartureMinute().equals("05")))
				.collect(Collectors.toList());

		assertEquals(duplicateSchedules.size(), 1);

		Schedule mergedSchedule = duplicateSchedules.get(0);

		Set<DayOfOperation> mergedOperatingDays = mergedSchedule.getOperatingDays();

		
		assertTrue(mergedOperatingDays.contains(DayOfOperation.MONDAY) &&
				mergedOperatingDays.contains(DayOfOperation.TUESDAY) &&
				mergedOperatingDays.contains(DayOfOperation.WEDNESDAY) &&
				mergedOperatingDays.contains(DayOfOperation.THURSDAY) &&
				mergedOperatingDays.contains(DayOfOperation.FRIDAY));
		

		assertTrue(mergedOperatingDays.size() == 7);
		assertTrue(mergedSchedule.getOperatingFrequency() == 7);
		
	}
	
	@Test
	public void isWestBoundTest() throws JsonParseException, JsonMappingException, IOException {
		List<Schedule> allSchedules = mapper.readValue(this.getClass().getResourceAsStream("/ABE-ATL.json"), new TypeReference<List<Schedule>>(){});
		Schedule schedule = allSchedules.get(0);
		assertFalse(scheduleService.isWestBound(schedule));
	}

}
