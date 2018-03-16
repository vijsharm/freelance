package com.aa.gsa.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aa.gsa.domain.StationCode;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.service.impl.CloudantService;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StationCodesTest {

	private CloudantClient cloudantClient;
	
	@Autowired
	private BatchJobProperties batchJobProperties;
	
	public static final String LOCAL_CLOUDANT_URL = "http://admin:mysecretpassword@localhost:5986";
	
	@Before
	public void before() throws MalformedURLException {
		cloudantClient = ClientBuilder
				.url(new URL(LOCAL_CLOUDANT_URL))
				.build();
	}
	
	@Test
	public void getAllDocumentsTest() {
		CloudantService cloudantService = new CloudantService(cloudantClient, batchJobProperties.getStationsDatabaseName());
		List<StationCode> stationCodes = cloudantService.getAllDocuments(StationCode.class);
		Assert.assertNotNull(stationCodes);
	}	
}
