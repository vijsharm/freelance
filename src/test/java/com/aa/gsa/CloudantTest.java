/*package com.aa.gsa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CPPCompareTest {

@Test
	public void simpleJunitTestForPipeline() {
    System.out.println("CloudantTest: simpleJunitTestForPipeline() invoked.");
  }

}


//package com.aa.gsa;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.CollectionUtils;
//
//import com.aa.gsa.domain.CPP;
//import com.cloudant.client.api.CloudantClient;
//import com.cloudant.client.api.Database;
//import com.cloudant.client.api.views.Key;
//import com.cloudant.client.api.views.Key.ComplexKey;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration
//public class CloudantTest {
//
//	@Autowired
//	private CloudantClient cloudantClient;
//
//	//@Test
//	public void test() {
//		String cppDatabaseName = "goodgroupfiletouse";
//		Database cppDatabase = cloudantClient.database(cppDatabaseName, false);
//
//		int limit = 100;
//		int skip = 100;
//		
//		String selectorJson = selectorJson(limit, skip);
//		
//		System.out.println("selectorJson = "+selectorJson);
//		
//		List<CPP> results = cppDatabase.findByIndex(selectorJson, CPP.class);
//
//		System.out.println(" results size = "+results.size());
//			
//		
//		if (!CollectionUtils.isEmpty(results)) {
//			for (CPP cpp : results) {
//				System.out.println(cpp.getDestinationAirport());
//			}
//		}
//	}
//	
//	//@Test
//	public void secondTest() throws IOException {
//		String cppDatabaseName = "goodgroupfiletouse";
//		Database cppDatabase = cloudantClient.database(cppDatabaseName, false);
//
//		final int CPP_COUNT = 9922;
//		final int LIMIT = 100;
//		int skip = 0;
//
//		while (skip <= CPP_COUNT) {
//			System.out.println("skip = "+skip);
//			
//			List<CPP> cppList = 
//					cppDatabase
//							.getAllDocsRequestBuilder()
//							.limit(LIMIT)
//							.skip(skip)
//							.includeDocs(true)
//							.build()
//							.getResponse()
//							.getDocsAs(CPP.class);
//			
//			System.out.println("cppList size = "+cppList.size());
//
//
//			for (CPP cpp : cppList) {
//				System.out.println(cpp.getItemNumber());
//			}
//
//			skip += LIMIT;
//		}
//	}
//	
//	@Test
//	public void thirdTest() {
//		ComplexKey complexKey =  Key.complex("ABEATL")
//				   .add(1);
//		System.out.println(complexKey.toJson());
//		
//		
//		String jsonStr = complexKey.toJson();
//		
//		try {
//			JSONArray jsonArr = new JSONArray(jsonStr);
//			String ndmarket = String.valueOf(jsonArr.get(0));
//			int runId = (int) jsonArr.get(1);
//			
//			System.out.println("ndmarket = "+ndmarket);
//			System.out.println("runId = "+runId);
//
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
//		
//	private String selectorJson(int limit, int skip) {
//		return "{\"selector\":{\"_id\": {\"$gt\": 0} }, "
//				+ "  \"fields\" : [ \"originAirport\", \"destinationAirport\" ], "
//				+ " \"limit\" : "+limit + ", "
//				+ " \"skip\" : "+skip + " } ";
//	}
//}
*/