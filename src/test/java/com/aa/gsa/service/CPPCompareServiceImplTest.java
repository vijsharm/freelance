package com.aa.gsa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.aa.gsa.domain.RunCompareMsg;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.service.impl.RunCompareServiceImpl;
import com.cloudant.client.api.CloudantClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class CPPCompareServiceImplTest {
	@Autowired
	private CloudantClient cloudantClient;

	@Autowired
	private RunCompareProperties cppCompareProperties;

	@Autowired
	private BatchJobProperties batchJobProperties;

	@Test
	public void test() {
		/*		
		
		CPPCompareServiceImpl compare = new CPPCompareServiceImpl(cloudantClient, cppCompareProperties, batchJobProperties);
		CPPCompareRequest request = new CPPCompareRequest();
		request.setRunId1(7);
		request.setRunId2(8);
		compare.compare(request);
		
		 */	
	}
}