package com.aa.gsa.service;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.DayOfOperation;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.enums.TravelType;
import com.aa.gsa.exception.InvalidNumberOfStopsException;
import com.aa.gsa.service.impl.ScheduleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    ScheduleService serviceToTest = new ScheduleServiceImpl();

    @Mock
    EligibilityResultService eligibilityResultService;

    @Test
    public void isEastBoundTest() {
        ScheduleService serviceToTestSpy = spy(serviceToTest);
        doReturn(false).when(serviceToTestSpy).isWestBound(any(Schedule.class));
        assertTrue(serviceToTestSpy.isEastBound(new Schedule()));
        doReturn(true).when(serviceToTestSpy).isWestBound(any(Schedule.class));
        assertFalse(serviceToTestSpy.isEastBound(new Schedule()));
        verify(serviceToTestSpy, times(2)).isWestBound(any(Schedule.class));
    }

    @Test
    public void isWestBoundTest() {
        Schedule schedule = new Schedule();
        schedule.setDepartureHour("1");
        schedule.setDepartureMinute("0");
        schedule.setArrivalHour("3");
        schedule.setArrivalMinute("0");
        schedule.setElapsedMinutes(10);
        assertFalse(serviceToTest.isWestBound(schedule));
    }

    @Test
    public void isWestBoundFalseTest() {
        Schedule schedule = new Schedule();
        schedule.setDepartureHour("1");
        schedule.setDepartureMinute("0");
        schedule.setArrivalHour("3");
        schedule.setArrivalMinute("0");
        schedule.setElapsedMinutes(150);
        assertTrue(serviceToTest.isWestBound(schedule));

        // Day gap Positive test
        schedule.setDepartureHour("3");
        schedule.setDepartureMinute("0");
        schedule.setArrivalHour("1");
        schedule.setArrivalMinute("0");
        schedule.setElapsedMinutes(1500);
        assertTrue(serviceToTest.isWestBound(schedule));

        // Day gap Negative test
        schedule.setDepartureHour("3");
        schedule.setDepartureMinute("0");
        schedule.setArrivalHour("1");
        schedule.setArrivalMinute("0");
        schedule.setElapsedMinutes(150);
        assertFalse(serviceToTest.isWestBound(schedule));
    }

    @Test
    public void isDepartingBetweenSixAndElevenPMFalseTest() {
        Schedule schedule = new Schedule();
        schedule.setDepartureHour("5");
        schedule.setDepartureMinute("0");

        assertFalse(serviceToTest.isDepartingBetweenSixAndElevenPM(schedule));
    }

    @Test
    public void isDepartingBetweenSixAndElevenPMBoundaryTest() {
        //Exact 6
        Schedule schedule = new Schedule();
        schedule.setDepartureHour("6");
        schedule.setDepartureMinute("0");

        assertTrue(serviceToTest.isDepartingBetweenSixAndElevenPM(schedule));

        //Exact 11
        schedule.setDepartureHour("23");
        schedule.setDepartureMinute("0");

        assertTrue(serviceToTest.isDepartingBetweenSixAndElevenPM(schedule));

        //Between 11 and 6
        schedule.setDepartureHour("11");
        schedule.setDepartureMinute("0");

        assertTrue(serviceToTest.isDepartingBetweenSixAndElevenPM(schedule));
    }

    @Test
    public void calculateGroundTimeInMinutesTest() {

        Schedule schedule = new Schedule();
        schedule.setDepartureHour("6");
        schedule.setDepartureMinute("0");

        assertEquals(0, serviceToTest.calculateGroundTimeInMinutes(schedule));

        //no. of stops 1
        Schedule schedule1 = new Schedule();
        schedule1.setDepartureHour("6");
        schedule1.setDepartureMinute("0");
        schedule1.setArrivalHour("7");
        schedule1.setArrivalMinute("0");
        schedule1.setDepartureHour2("7");
        schedule1.setDepartureMinute2("0");
        schedule1.setArrivalHour1("6");
        schedule1.setArrivalMinute1("0");
        schedule1.setNumberOfStops(1);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule1));

        //no of stops 2
        Schedule schedule2 = new Schedule();
        schedule2.setDepartureHour("6");
        schedule2.setDepartureMinute("0");
        schedule2.setArrivalHour("7");
        schedule2.setArrivalMinute("0");
        schedule2.setDepartureHour3("7");
        schedule2.setDepartureMinute3("0");
        schedule2.setArrivalHour2("6");
        schedule2.setArrivalMinute2("0");
        schedule2.setNumberOfStops(2);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule2));

        //no of stops 3
        Schedule schedule3 = new Schedule();
        schedule3.setDepartureHour("6");
        schedule3.setDepartureMinute("0");
        schedule3.setArrivalHour("7");
        schedule3.setArrivalMinute("0");
        schedule3.setDepartureHour4("7");
        schedule3.setDepartureMinute4("0");
        schedule3.setArrivalHour3("6");
        schedule3.setArrivalMinute3("0");
        schedule3.setNumberOfStops(3);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule3));

        //no of stops 4
        Schedule schedule4 = new Schedule();
        schedule4.setDepartureHour("6");
        schedule4.setDepartureMinute("0");
        schedule4.setArrivalHour("7");
        schedule4.setArrivalMinute("0");
        schedule4.setDepartureHour5("7");
        schedule4.setDepartureMinute5("0");
        schedule4.setArrivalHour4("6");
        schedule4.setArrivalMinute4("0");
        schedule4.setNumberOfStops(4);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule4));

        //no of stops 4
        Schedule schedule5 = new Schedule();
        schedule5.setDepartureHour("6");
        schedule5.setDepartureMinute("0");
        schedule5.setArrivalHour("7");
        schedule5.setArrivalMinute("0");
        schedule5.setDepartureHour6("7");
        schedule5.setDepartureMinute6("0");
        schedule5.setArrivalHour5("6");
        schedule5.setArrivalMinute5("0");
        schedule5.setNumberOfStops(5);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule5));
    }

    @Test(expected = InvalidNumberOfStopsException.class)
    public void calculateGroundTimeInMinutesInvalidstopTest() {
        //no. of stops 1
        Schedule schedule1 = new Schedule();
        schedule1.setDepartureHour("6");
        schedule1.setDepartureMinute("0");
        schedule1.setArrivalHour("7");
        schedule1.setArrivalMinute("0");
        schedule1.setDepartureHour2("7");
        schedule1.setDepartureMinute2("0");
        schedule1.setArrivalHour1("6");
        schedule1.setArrivalMinute1("0");
        schedule1.setNumberOfStops(6);
        assertEquals(60, serviceToTest.calculateGroundTimeInMinutes(schedule1));
    }

    @Test
    public void getServiceLevelTest() {
        //Non Stop
        Schedule schedule = new Schedule();
        schedule.setNumberOfStops(1);
        assertEquals(ServiceLevel.NON_STOP, serviceToTest.getServiceLevel(schedule));

        //Direct
        schedule.setNumberOfConnections(1);
        schedule.setLegFlightNumber1(1);
        schedule.setLegFlightNumber2(1);
        assertEquals(ServiceLevel.DIRECT, serviceToTest.getServiceLevel(schedule));

        //Connection
        schedule.setLegFlightNumber2(2);
        assertEquals(ServiceLevel.CONNECTION, serviceToTest.getServiceLevel(schedule));

    }

    @Test
    public void populateServiceLevelsTest() {
        ScheduleService serviceSpy = spy(serviceToTest);
        doReturn(ServiceLevel.DIRECT).when(serviceSpy).getServiceLevel(any(Schedule.class));
        Schedule schedule = new Schedule();
        serviceSpy.populateServiceLevels(Arrays.asList(schedule));
        assertEquals(ServiceLevel.DIRECT, schedule.getServiceLevel());
    }

    @Test
    public void groupByServiceLevelsTest() {
        Schedule schedule = new Schedule();
        schedule.setServiceLevel(ServiceLevel.DIRECT);
        Schedule schedule1 = new Schedule();
        schedule1.setServiceLevel(ServiceLevel.DIRECT);
        schedule1.setLegFlightNumber2(3);
        Map<ServiceLevel, Set<Schedule>> map = serviceToTest.groupByServiceLevels(new HashSet<>(Arrays.asList(schedule,schedule1)));
        assertEquals(1, map.size());
        assertEquals(2, map.get(ServiceLevel.DIRECT).size());
    }

    @Test
    public void isCodeshareTest() {
        Schedule schedule = new Schedule();
        assertFalse(serviceToTest.isCodeshare(schedule));

        //Case 1
        schedule.setNumberOfStops(1);
        schedule.setLegAirline1("1");
        schedule.setLegAirline2("1");
        assertTrue(serviceToTest.isCodeshare(schedule));

        //Case 2
        schedule.setNumberOfStops(2);
        schedule.setLegAirline3("1");
        assertTrue(serviceToTest.isCodeshare(schedule));

        //Case 3
        schedule.setNumberOfStops(3);
        schedule.setLegAirline4("1");
        assertTrue(serviceToTest.isCodeshare(schedule));

        //Case 4
        schedule.setNumberOfStops(4);
        schedule.setLegAirline5("1");
        assertTrue(serviceToTest.isCodeshare(schedule));

        //Case 5
        schedule.setNumberOfStops(5);
        schedule.setLegAirline6("1");
        assertTrue(serviceToTest.isCodeshare(schedule));

    }

    @Test(expected = InvalidNumberOfStopsException.class)
    public void isCodeshareExceptionTest() {
        Schedule schedule = new Schedule();
        schedule.setNumberOfStops(7);
        serviceToTest.isCodeshare(schedule);
    }

    @Test
    public void mergeDuplicateRecordsTest() {
        assertNull(serviceToTest.mergeDuplicateRecords(null));

        Schedule schedule = new Schedule();
        schedule.setDaysOfOperation("1");
        Schedule schedule2 = new Schedule();
        schedule2.setDaysOfOperation("2");
        List<Schedule> list = Arrays.asList(schedule, schedule2);
        Set<Schedule> set = serviceToTest.mergeDuplicateRecords(list);
        assertEquals(1, set.size());
        assertEquals(2, set.stream().findFirst().get().getOperatingDays().size());
        assertTrue( set.stream().findFirst().get().getOperatingDays().contains(DayOfOperation.MONDAY));
        assertTrue( set.stream().findFirst().get().getOperatingDays().contains(DayOfOperation.TUESDAY));

    }

    @Test
    public void calculateNumberOfFlightsPerDayDomesticTest() {
        ScheduleService serviceSpy = spy(serviceToTest);
        doReturn(false).when(serviceSpy).isEastBound(any(Schedule.class));
        doReturn(true).when(serviceSpy).isDepartingBetweenSixAndElevenPM(any(Schedule.class));
        Schedule schedule = new Schedule();
        schedule.setServiceLevel(ServiceLevel.NON_STOP);
        schedule.setOperatingDays(new HashSet<>(Arrays.asList(DayOfOperation.MONDAY)));
        Set<Schedule> set = new HashSet<>(Arrays.asList(schedule));
        CPP cpp = new CPP();
        cpp.setMinService(ServiceLevel.CONNECTION.value());
        cpp.setTypeOfTravel(TravelType.DOMESTIC.value());

        Map<DayOfOperation, Integer> map = serviceSpy.calculateNumberOfFlightsPerDay(set, cpp, eligibilityResultService);
        assertEquals(7, map.size());
        assertEquals(2, map.get(DayOfOperation.MONDAY).longValue());
        assertEquals(0, map.get(DayOfOperation.TUESDAY).longValue());


        //Scenario2
        Schedule schedule2 = new Schedule();
        schedule2.setServiceLevel(ServiceLevel.DIRECT);
        schedule2.setOperatingDays(new HashSet<>(Arrays.asList(DayOfOperation.MONDAY)));
        Set<Schedule> set2 = new HashSet<>(Arrays.asList(schedule2));
        Map<DayOfOperation, Integer> map2 = serviceSpy.calculateNumberOfFlightsPerDay(set2, cpp, eligibilityResultService);
        assertEquals(7, map2.size());
        assertEquals(1, map2.get(DayOfOperation.MONDAY).longValue());
        assertEquals(0, map2.get(DayOfOperation.TUESDAY).longValue());

        //Scenario 3
        doReturn(false).when(serviceSpy).isDepartingBetweenSixAndElevenPM(any(Schedule.class));
        Map<DayOfOperation, Integer> map3 = serviceSpy.calculateNumberOfFlightsPerDay(set, cpp, eligibilityResultService);
        assertEquals(7, map2.size());
        assertEquals(0, map2.get(DayOfOperation.MONDAY).longValue());
        assertEquals(0, map2.get(DayOfOperation.TUESDAY).longValue());

    }



}
