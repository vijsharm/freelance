package com.aa.gsa.service;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.Run;
import com.aa.gsa.domain.RunCompare;
import com.aa.gsa.domain.RunCompareMsg;
import com.aa.gsa.domain.result.CPPResult;
import com.aa.gsa.domain.result.Result;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.RunCompareStatus;
import com.aa.gsa.exception.InvalidRunCompareException;
import com.aa.gsa.exception.RunCompareException;
import com.aa.gsa.exception.RunCompareExistsException;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.service.impl.RunCompareServiceImpl;
import com.aa.gsa.service.impl.ScheduleQueryServiceImpl;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.views.ViewResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RunCompareServiceImpl.class)
public class RunCompareServiceTest {
    @Mock
    CloudantClient cloudantClient;
    @Mock
    RunCompareProperties runCompareProps;
    @Mock
    BatchJobProperties batchJobProperties;
    @Mock
    Database cppResultsDatabase;

    @Mock
    Database runCompareDatabase;

    @Mock
    Database runCompareStatusDatabase;

    @Mock
    Database runDatabase;

    @Mock
    Database cppDatabase;

    RunCompareService serviceToTest;

    @Before
    public void setUp() {
        when(batchJobProperties.getCppResultsDatabaseName()).thenReturn("A");
        when(runCompareProps.getDatabaseName()).thenReturn("B");
        when(runCompareProps.getStatusDatabaseName()).thenReturn("C");
        when(batchJobProperties.getRunDatabaseName()).thenReturn("D");
        when(cloudantClient.database("A", false)).thenReturn(cppResultsDatabase);
        when(cloudantClient.database("B", true)).thenReturn(runCompareDatabase);
        when(cloudantClient.database("C", true)).thenReturn(runCompareStatusDatabase);
        when(cloudantClient.database("D", false)).thenReturn(runDatabase);

        serviceToTest = new RunCompareServiceImpl(cloudantClient, runCompareProps, batchJobProperties);

    }

    @Test(expected = InvalidRunCompareException.class)
    public void compareExceptionTest() {
        RunCompareMsg msg = new RunCompareMsg();
        when(runDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(new ArrayList<>());
        serviceToTest.compare(msg);
        verify(runDatabase, times(1)).findByIndex(anyString(), any(Class.class));
    }

    @Test(expected = RunCompareExistsException.class)
    public void compareExistingTest() {
        RunCompareMsg msg = new RunCompareMsg();
        Run run = new Run();
        when(runDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(Arrays.asList(run));
        RunCompareMsg msg2 = new RunCompareMsg();
        msg2.setStatus(RunCompareStatus.COMPLETED);
        List<RunCompareMsg> runCompareMessages = Arrays.asList(msg2);
        when(runCompareStatusDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(runCompareMessages);
        serviceToTest.compare(msg);
        verify(runDatabase, times(2)).findByIndex(anyString(), any(Class.class));
    }

    @Test
    public void compareTest() {
        RunCompareMsg msg = new RunCompareMsg();
        Run run = new Run();
        run.setCppFileGroupName("G1");
        run.setRunId("1");
        when(runDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(Arrays.asList(run));
        when(cloudantClient.database("G1", false)).thenReturn(cppDatabase);
        when(cppDatabase.findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class))).thenReturn(Arrays.asList(new Class[2]));


        Map<Airline, Result> map = new HashMap<>();
        Result rs = new Result();
        rs.setEligible(true);
        rs.setPoints(1);
        map.put(Airline.AA, rs);
        Map<Airline, Result> map2 = new HashMap<>();
        Result rs2 = new Result();
        rs2.setEligible(true);
        rs2.setPoints(2);
        map2.put(Airline.AA, rs2);

        CPPResult result = new CPPResult();
        result.setItemNo(1);
        result.setRunId(1);
        result.setAirlineResults(map);
        CPPResult result2 = new CPPResult();
        result2.setItemNo(1);
        result2.setAirlineResults(map2);
        List<CPPResult> results = Arrays.asList(result, result2);

        when(runCompareProps.getReadSize()).thenReturn(1);




        when(cppResultsDatabase.findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class))).thenReturn(results);

        RunCompareMsg msg2 = new RunCompareMsg();
        msg2.setStatus(RunCompareStatus.FAILED);
        List<RunCompareMsg> runCompareMessages = Arrays.asList(msg2);
        when(runCompareStatusDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(runCompareMessages);
        serviceToTest.compare(msg);
        verify(runDatabase, times(2)).findByIndex(anyString(), any(Class.class));
        verify(runCompareStatusDatabase, times(2)).findByIndex(anyString(), any(Class.class));
        verify(runCompareDatabase, times(1)).findByIndex(anyString(), any(Class.class));
        verify(runCompareStatusDatabase, times(1)).remove(any(RunCompareMsg.class));
        verify(runCompareStatusDatabase, times(1)).save(any(RunCompareMsg.class));
        verify(runCompareStatusDatabase, times(1)).update(any(RunCompareMsg.class));
        assertEquals(RunCompareStatus.RUNNING, msg.getStatus());
        verify(cloudantClient, times(1)).database("G1", false);
        verify(cppDatabase, times(1)).findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class));
        verify(cppResultsDatabase, times(1)).findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class));
        verify(runCompareDatabase, times(2)).bulk(any(List.class));
    }

    @Test
    public void compareTest2() throws Exception {
        RunCompareMsg msg = new RunCompareMsg();
        Run run = new Run();
        run.setCppFileGroupName("G1");
        run.setRunId("1");
        Run run2 = new Run();
        run2.setCppFileGroupName("G2");
        run2.setRunId("1");
        when(runDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(Arrays.asList(run)).thenReturn(Arrays.asList(run2));
        when(cloudantClient.database("G1", false)).thenReturn(cppDatabase);
        when(cppDatabase.findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class))).thenReturn(Arrays.asList(new Class[0]));


        Map<Airline, Result> map = new HashMap<>();
        Result rs = new Result();
        rs.setEligible(true);
        rs.setPoints(1);
        map.put(Airline.AA, rs);
        Map<Airline, Result> map2 = new HashMap<>();
        Result rs2 = new Result();
        rs2.setEligible(true);
        rs2.setPoints(2);
        map2.put(Airline.AA, rs2);

        CPPResult result = new CPPResult();
        result.setItemNo(1);
        result.setRunId(1);
        result.setAirlineResults(map);
        CPPResult result2 = new CPPResult();
        result2.setItemNo(1);
        result2.setAirlineResults(map2);
        List<CPPResult> results = Arrays.asList(result, result2);

        when(runCompareProps.getReadSize()).thenReturn(1);

        RunCompareService serviceSpy = PowerMockito.spy(serviceToTest);

        List<CPP> cpp = new ArrayList<>();
        ViewResponse res = mock(ViewResponse.class);
        PowerMockito.doReturn(cpp).when(serviceSpy, "getCPP", cppDatabase, 1, 0);
        PowerMockito.doReturn(res).when(serviceSpy, "queryView", cpp, 1, 1);

        when(cppResultsDatabase.findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class))).thenReturn(results);

        RunCompareMsg msg2 = new RunCompareMsg();
        msg2.setStatus(RunCompareStatus.FAILED);
        List<RunCompareMsg> runCompareMessages = Arrays.asList(msg2);
        when(runCompareStatusDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(runCompareMessages);
        serviceSpy.compare(msg);
        verify(runDatabase, times(2)).findByIndex(anyString(), any(Class.class));
        verify(runCompareStatusDatabase, times(2)).findByIndex(anyString(), any(Class.class));
        verify(runCompareDatabase, times(1)).findByIndex(anyString(), any(Class.class));
        verify(runCompareStatusDatabase, times(1)).remove(any(RunCompareMsg.class));
        verify(runCompareStatusDatabase, times(1)).save(any(RunCompareMsg.class));
        verify(runCompareStatusDatabase, times(1)).update(any(RunCompareMsg.class));
        assertEquals(RunCompareStatus.RUNNING, msg.getStatus());


        verify(cloudantClient, times(2)).database("G1", false);
        verify(cppDatabase, times(1)).findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class));
        verify(cppResultsDatabase, never()).findByIndex(anyString(), any(Class.class), any(FindByIndexOptions.class));

        verify(runCompareDatabase, never()).bulk(any(List.class));
    }

    @Test(expected = RunCompareException.class)
    public void setStatusEmptyTest() {
        RunCompareMsg msg = new RunCompareMsg();
        List<RunCompareMsg> runCompareMsgs = new ArrayList<>();
        //runCompareMsgs.add(msg);
        when(runCompareStatusDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(runCompareMsgs);
        serviceToTest.setStatus(msg, RunCompareStatus.FAILED);
        verify(runCompareStatusDatabase, times(1)).findByIndex(anyString(), any(Class.class));

    }

    @Test
    public void setStatusTest() {
        RunCompareMsg msg = new RunCompareMsg();
        List<RunCompareMsg> runCompareMsgs = new ArrayList<>();
        runCompareMsgs.add(msg);
        when(runCompareStatusDatabase.findByIndex(anyString(), any(Class.class))).thenReturn(runCompareMsgs);
        serviceToTest.setStatus(msg, RunCompareStatus.FAILED);
        verify(runCompareStatusDatabase, times(1)).findByIndex(anyString(), any(Class.class));
        verify(runCompareStatusDatabase, times(1)).update(any(RunCompareMsg.class));
        assertEquals(RunCompareStatus.FAILED, msg.getStatus());
    }
}
