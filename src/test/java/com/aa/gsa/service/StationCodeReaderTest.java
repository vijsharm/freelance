package com.aa.gsa.service;

import com.aa.gsa.domain.StationCode;
import com.aa.gsa.exception.InvalidCityCodeException;
import com.aa.gsa.exception.InvalidStationCodeException;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.impl.StationCodeReaderImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.TestExecutionListeners;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StationCodeReaderTest {

    private StationCodeReader serviceToTest;

    @Before
    public void setUp() {
        List<StationCode> stationCodes = new ArrayList<>();
        StationCode codes = new StationCode();
        codes.setCity("NY");
        codes.setStation("JFK");
        codes.setGmtOffset(1);

        StationCode codes2 = new StationCode();
        codes2.setCity("France");
        codes2.setStation("FRA");
        codes2.setGmtOffset(1);
        stationCodes.add(codes);
        stationCodes.add(codes2);
        serviceToTest = new StationCodeReaderImpl(stationCodes);
    }

    @Test(expected = PointsProcessorException.class)
    public void emptyStationCodesTest()  {
        new StationCodeReaderImpl(new ArrayList<StationCode>());
    }

    @Test(expected = PointsProcessorException.class)
    public void nullStationCodesTest()  {
        new StationCodeReaderImpl(null);
    }

    @Test(expected = PointsProcessorException.class)
    public void instantiationWithInvalidCodesTest() {
        List<StationCode> stationCodes = new ArrayList<>();
        StationCode codes = new StationCode();
        stationCodes.add(codes);
        new StationCodeReaderImpl(stationCodes);
    }

    @Test
    public void instantiationTest() {
        List<StationCode> stationCodes = new ArrayList<>();
        StationCode codes = new StationCode();
        codes.setCity("NY");
        codes.setStation("JFK");
        codes.setGmtOffset(1);

        StationCode codes2 = new StationCode();
        codes2.setCity("France");
        codes2.setStation("FRA");
        codes2.setGmtOffset(1);
        stationCodes.add(codes);
        stationCodes.add(codes2);
        new StationCodeReaderImpl(stationCodes);
     }

    @Test
    public void isValidStationTest() {
        assertTrue(serviceToTest.isValidStation("FRA"));
    }

    @Test
    public void isValidStationFalseTest() {
        assertFalse(serviceToTest.isValidStation("GER"));
    }

    @Test(expected = InvalidStationCodeException.class)
    public void getGMTOffsetExceptionTest() {
        serviceToTest.getGMTOffset("GER");

    }

    @Test
    public void getGMTOffsetTest() {
        assertEquals(1, serviceToTest.getGMTOffset("FRA"));
    }

    @Test(expected = InvalidCityCodeException.class)
    public void getStationsByCityCodeInvalidTest() {
        serviceToTest.getStationsByCityCode("GER");
    }

    @Test
    public void getStationsByCityCodeTest() {
        assertEquals("JFK", serviceToTest.getStationsByCityCode("JFK").get(0));
        assertEquals("JFK", serviceToTest.getStationsByCityCode("NY").get(0));
    }

}
