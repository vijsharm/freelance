package com.aa.gsa.service;

import com.aa.gsa.domain.Schedule;
import com.aa.gsa.enums.ServiceLevel;
import com.aa.gsa.service.impl.ScheduleQueryServiceImpl;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.RequestBuilder;
import com.cloudant.client.api.views.UnpaginatedRequestBuilder;
import com.cloudant.client.api.views.ViewRequestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ScheduleQueryServiceImpl.class)
public class ScheduleQueryServiceTest {
    @Mock
    private CloudantClient cloudantClient;

    @Mock
    private Database database;

    private ScheduleQueryServiceImpl serviceToTest;

    Schedule schedule = new Schedule();

    ScheduleQueryServiceImpl serviceToTestSpy;

    @Before
    public void setUp() throws Exception {

        when(cloudantClient.database("dbname", false)).thenReturn(database);
        serviceToTest = new ScheduleQueryServiceImpl(cloudantClient, "dbname");
        serviceToTestSpy = PowerMockito.spy(serviceToTest);

        List<Schedule> response = Arrays.asList(schedule);
        Set<String> stationCodePairs = new HashSet<>(Arrays.asList("JFK-MUC", "PHX-FRA"));
        Set<String> stationCodePairsLevels = new HashSet<>(Arrays.asList("JFK-MUC-N", "JFK-MUC-D"));
        PowerMockito.doReturn(response).when(serviceToTestSpy, "queryView", "orig-dest", stationCodePairs);
        PowerMockito.doReturn(response).when(serviceToTestSpy, "queryView", "orig-dest-serviceLevel", stationCodePairsLevels);
    }

    @Test
    public void getSchedulesByStationCodePairsTest() throws Exception {
        Set<String> stationCodePairs = new HashSet<>(Arrays.asList("JFK-MUC", "PHX-FRA"));
        List<Schedule> schedules = serviceToTestSpy.getSchedulesByStationCodePairs(stationCodePairs);
        assertEquals(1, schedules.size());
        assertEquals(schedule, schedules.get(0));
        verify(cloudantClient, times(1)).database("dbname", false);

    }

    @Test
    public void getSchedulesForStationCodePairsByServiceLevels() throws Exception {
        Set<String> stationCodePairs = new HashSet<>(Arrays.asList("JFK-MUC"));
        ServiceLevel[] serviceLevels = new ServiceLevel[2];
        serviceLevels[0] = ServiceLevel.NON_STOP;
        serviceLevels[1] = ServiceLevel.DIRECT;
        List<Schedule> schedules = serviceToTestSpy.getSchedulesForStationCodePairsByServiceLevels(stationCodePairs, serviceLevels);
        assertEquals(1, schedules.size());
        assertEquals(schedule, schedules.get(0));
        verify(cloudantClient, times(1)).database("dbname", false);

    }
}
