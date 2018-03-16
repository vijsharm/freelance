package com.aa.gsa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CPPCompareTest {

@Test
	public void simpleJunitTestForPipeline() {
    System.out.println("CPPCompareTest: simpleJunitTestForPipeline() invoked.");
  }

}

//package com.aa.gsa;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.CollectionUtils;
//
//import com.aa.gsa.domain.RunCompare;
//import com.aa.gsa.domain.result.CPPResult;
//import com.aa.gsa.enums.Airline;
//import com.cloudant.client.api.CloudantClient;
//import com.cloudant.client.api.Database;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration
//public class CPPCompareTest {
//
//	private Log log = LogFactory.getLog(CPPCompareTest.class);
//	
//	@Autowired
//	private CloudantClient cloudantClient;
//
//	final int cppMarketSize = 9922;
//
//	final int queryBatchSize = 1000;
//
//	final int writeBatchSize = 500;
//
//	//@Test
//	public void cppCompareTest() {
//		int runId1 = 2; 
//		int runId2 = 1124;		
//
//		Database cppResultsDatabase = cloudantClient.database("cpp-results", false);
//		Database cppDiffDatabase = cloudantClient.database("cpp-0925", true);
//
//		int startItem = 1;
//		int endItem = queryBatchSize;
//		List<RunCompare> cppDiff = new ArrayList<>();
//
//		int diffCount = 0;
//		int writeCount = 0;
//
//		while (startItem < cppMarketSize) {
//			log.info("querying with startItem = "+startItem+ " endItem = "+endItem);
//			List<CPPResult> results = cppResultsDatabase.findByIndex(selectorJson(runId1, runId2, startItem, endItem), CPPResult.class);
//
//			if (!CollectionUtils.isEmpty(results)) {
//				log.info("results size =  "+results.size());
//				Map<Integer, List<CPPResult>> groupByItemNumber = 
//						results
//						.stream()
//						.collect(Collectors.groupingBy(CPPResult :: getItemNo));				
//				
//				for (Integer itemNo : groupByItemNumber.keySet()) {
//					List<CPPResult> compareResults = groupByItemNumber.get(itemNo);
//					if (!CollectionUtils.isEmpty(compareResults) && compareResults.size() == 2) {
//						CPPResult result1 = compareResults.get(0);
//						CPPResult result2 = compareResults.get(1);
//						
//						boolean elig1 = result1.getAirlineResults().get(Airline.AA).isEligible();
//						boolean elig2 = result2.getAirlineResults().get(Airline.AA).isEligible();
//						
//						int points1 = result1.getAirlineResults().get(Airline.AA).getPoints();
//						int points2 = result2.getAirlineResults().get(Airline.AA).getPoints();
//			
//						if((elig1 != elig2) || (points1 != points2)) {
//							log.info("Diff found");
//							RunCompare diff = new RunCompare();
//							diff.setRunId1(runId1);
//							diff.setRunId2(runId2);
//							
//							/**
//							 * ItemNo : 2 
//							 */
//							diff.setItemNo1(result1.getItemNo());
//							diff.setItemNo2(result2.getItemNo());
//							
//							diff.setEligible1(elig1);
//							diff.setEligible2(elig2);
//							diff.setPoints1(points1);
//							diff.setPoints2(points2);
//							
//							cppDiff.add(diff);
//
//							if (diffCount % writeBatchSize == 0) {
//								log.info("writing diff");
//								cppDiffDatabase.bulk(cppDiff);
//								cppDiff.clear();
//								writeCount += writeBatchSize;
//							}
//							
//							diffCount++;
//						}
//					}
//				}
//			}
//			
//			startItem += queryBatchSize;
//			endItem += queryBatchSize;
//		}
//
//		log.info(" diffCount "+diffCount);
//		log.info(" writeCount "+writeCount);
//		
//		if (diffCount != writeCount) {
//			List<RunCompare> lastChunk =  cppDiff.subList(writeCount, diffCount);
//			cppDiffDatabase.bulk(lastChunk);
//		}
//	}
//
//	private String selectorJson(int runId1, int runId2, int startItem, int endItem) {
//		return "{\"selector\":{\"runId\":{\"$in\":["+runId1+","+runId2+"]},"
//				+ "\"$and\":[{\"itemNo\":{\"$gte\":"+startItem+"}},{\"itemNo\":{\"$lte\":"+endItem+"}}],"
//				+ "\"excludeCodeshare\":{\"$exists\":false}},\"fields\":[\"runId\","
//				+ "\"orig\",\"dest\",\"itemNo\",\"airlineResults.AA.eligible\",\"airlineResults.AA.points\"]}";
//	}
//}
