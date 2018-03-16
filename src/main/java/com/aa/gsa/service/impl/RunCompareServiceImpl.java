package com.aa.gsa.service.impl;

import static com.aa.gsa.enums.RunCompareStatus.COMPLETED;
import static com.aa.gsa.enums.RunCompareStatus.RUNNING;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.domain.RunCompare;
import com.aa.gsa.domain.RunCompareMsg;
import com.aa.gsa.domain.Run;
import com.aa.gsa.domain.result.CPPResult;
import com.aa.gsa.domain.result.Result;
import com.aa.gsa.enums.Airline;
import com.aa.gsa.enums.RunCompareStatus;
import com.aa.gsa.exception.RunCompareException;
import com.aa.gsa.exception.RunCompareExistsException;
import com.aa.gsa.exception.InvalidRunCompareException;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.service.RunCompareService;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.FindByIndexOptions;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.Key.ComplexKey;
import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Compares two runs.
 * @author 940914
 */
public class RunCompareServiceImpl implements RunCompareService {

	private CloudantClient cloudantClient;

	private RunCompareProperties runCompareProps;

	private Database cppResultsDatabase;

	private Database runCompareDatabase;

	private Database runCompareStatusDatabase;

	private Database runDatabase;

	public RunCompareServiceImpl(CloudantClient cloudantClient, RunCompareProperties runCompareProps, BatchJobProperties batchJobProperties) {
		this.cloudantClient = cloudantClient;
		this.runCompareProps = runCompareProps;

		cppResultsDatabase = cloudantClient.database(batchJobProperties.getCppResultsDatabaseName(), false);
		runCompareDatabase = cloudantClient.database(runCompareProps.getDatabaseName(), true);
		runCompareStatusDatabase = cloudantClient.database(runCompareProps.getStatusDatabaseName(), true);
		runDatabase = cloudantClient.database(batchJobProperties.getRunDatabaseName(), false);
	}

	@Override
	public void compare(RunCompareMsg runCompareMsg) {
		Run run1 = fetchRunById(runCompareMsg.getRunId1());
		Run run2 = fetchRunById(runCompareMsg.getRunId2());

		checkIfAlreadyExists(runCompareMsg);

		start(runCompareMsg);

		compare(run1, run2);

		finish(runCompareMsg);
	}

	@Override
	public void setStatus(RunCompareMsg runCompareMsg, RunCompareStatus status) {
		List<RunCompareMsg> runCompareMsgs = runCompareStatusDatabase.findByIndex(byRunId1RunId2Selector(runCompareMsg), RunCompareMsg.class);

		if (CollectionUtils.isEmpty(runCompareMsgs)) {
			throw new RunCompareException("No compare exists with runIDs "+"["+runCompareMsg.getRunId1()+","+runCompareMsg.getRunId2()+"]");
		}

		RunCompareMsg runCompareMsgLatest = runCompareMsgs.get(0);
		runCompareMsgLatest.setStatus(status);
		runCompareStatusDatabase.update(runCompareMsgLatest);
	}

	private Run fetchRunById(int runId) {
		List<Run> runs =  runDatabase.findByIndex("\"selector\":{\"runId\": \""+runId+"\" }", Run.class);
		if (CollectionUtils.isEmpty(runs)) {
			throw new InvalidRunCompareException("Run with id = "+runId+ " is not present");
		}
		return runs.get(0);
	}

	private void compare(Run run1, Run run2) {
		if (run1.getCppFileGroupName() != null && run1.getCppFileGroupName().equalsIgnoreCase(run2.getCppFileGroupName())) {
			comareRunsWithSameCPP(run1, run2);
		} else {
			compareRunsWithDifferentCPP(run1, run2);
		}
	}

	private void start(RunCompareMsg runCompareMsg) {
		runCompareMsg.setStatus(RUNNING);
		runCompareStatusDatabase.save(runCompareMsg);
	}

	private void finish(RunCompareMsg runCompareMsg) {
		setStatus(runCompareMsg, COMPLETED);
	}

	private void checkIfAlreadyExists(RunCompareMsg runCompareMsg) {
		List<RunCompareMsg> runCompareMessages = runCompareStatusDatabase.findByIndex(byRunId1RunId2Selector(runCompareMsg), RunCompareMsg.class);
		if (!CollectionUtils.isEmpty(runCompareMessages)) {
			RunCompareMsg compareMessage = runCompareMessages.get(0);
			if (!compareMessage.getStatus().equals(RunCompareStatus.FAILED)) { //there is an existing run compare
				throw new RunCompareExistsException(compareMessage);
			} else {
				cleanUpCompare(runCompareMsg);
				for(RunCompareMsg runCompareMessageObj : runCompareMessages) {
					runCompareStatusDatabase.remove(runCompareMessageObj);
				}
			}
		}
	}

	/**
	 * 
	 * Compares two runs that have same CPP input file
	 * @param run1
	 * @param run2
	 * 
	 */
	private void comareRunsWithSameCPP(Run run1, Run run2) {
		final int runId1 = Integer.parseInt(run1.getRunId()); 
		final int runId2 = Integer.parseInt(run2.getRunId());		

		final int cppMarketSize = getCPPSize(run1.getCppFileGroupName());

		int startItem = 1;
		int endItem = runCompareProps.getReadSize();

		List<RunCompare> runCompares = new ArrayList<>();

		while (startItem < cppMarketSize) {

			List<CPPResult> results = cppResultsDatabase.findByIndex(
					byItemNumberRangeSelector(runId1, runId2, startItem, endItem), 
					CPPResult.class, 
					new FindByIndexOptions()
					.fields("runId")
					.fields("orig")
					.fields("dest")
					.fields("itemNo")
					.fields("airlineResults.AA.eligible")
					.fields("airlineResults.AA.points")
					);

			if (!CollectionUtils.isEmpty(results)) {
				Map<Integer, List<CPPResult>> groupByItemNumber = 
						results
						.stream()
						.collect(Collectors.groupingBy(CPPResult :: getItemNo));				

				for (Integer itemNo : groupByItemNumber.keySet()) {
					List<CPPResult> compareResults = groupByItemNumber.get(itemNo);
					if (!CollectionUtils.isEmpty(compareResults) && compareResults.size() == 2) {
						CPPResult result1, result2;

						if (compareResults.get(0).getRunId() == runId1) {
							result1 = compareResults.get(0);
							result2 = compareResults.get(1);
						} else {
							result1 = compareResults.get(1);
							result2 = compareResults.get(0);
						}

						RunCompare diff;
						if ((diff = compare(result1, result2)) != null) {
							if (runCompares.size() == runCompareProps.getWriteSize()) {
								runCompareDatabase.bulk(runCompares);
								runCompares.clear();
							} 
							runCompares.add(diff);
						}

					}
				}
			}

			startItem += runCompareProps.getReadSize();
			endItem += runCompareProps.getReadSize();
		}

		if (!CollectionUtils.isEmpty(runCompares)) {//committing last chunk
			runCompareDatabase.bulk(runCompares);
		}
	}

	/**
	 * Compares two runs that have different CPP input files
	 * @param run1
	 * @param run2
	 * 
	 */
	private void compareRunsWithDifferentCPP(Run run1, Run run2)  {
		final int runId1 = Integer.parseInt(run1.getRunId());
		final int runId2 = Integer.parseInt(run2.getRunId());
		final int cppMarketSize = getCPPSize(run1.getCppFileGroupName());

		List<RunCompare> runCompares = new ArrayList<>();
		Database cppDatabase = cloudantClient.database(run1.getCppFileGroupName(), false);

		int skip = 0;
		while (skip <= cppMarketSize) {
			List<CPP> cppList = getCPP(cppDatabase, runCompareProps.getReadSize(), skip);
			ViewResponse<ComplexKey, Object> viewResponse = queryView(cppList, runId1, runId2);
			Table<Integer, String, CPPResult> resultTable = HashBasedTable.create();
			for (Row<ComplexKey, Object> row : viewResponse.getRows()) {

				ViewKey key = parseComplexKey(row.getKey());
				String ndmarket = key.getNdmarket();
				int runId = key.getRunId();

				resultTable.put(runId, ndmarket, row.getDocumentAsType(CPPResult.class));
				if (resultTable.columnMap().get(ndmarket).size() == 2) {
					CPPResult cppResult1 = resultTable.get(runId1, ndmarket);
					CPPResult cppResult2 = resultTable.get(runId2, ndmarket);

					RunCompare diff;
					if ((diff = compare(cppResult1, cppResult2)) != null) {
						if (runCompares.size() == runCompareProps.getWriteSize()) {
							runCompareDatabase.bulk(runCompares);
							runCompares.clear();
						} 
						runCompares.add(diff);
					}
				}
			}
			skip += runCompareProps.getReadSize();
		}

		if (!CollectionUtils.isEmpty(runCompares)) {//committing last chunk
			runCompareDatabase.bulk(runCompares);
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private ViewKey parseComplexKey(ComplexKey key) {
		try {
			JSONArray jsonArr = new JSONArray(key.toJson());
			String ndmarket = String.valueOf(jsonArr.get(0));
			int runId = (int) jsonArr.get(1);
			return new ViewKey(ndmarket, runId);
		} catch (JSONException e) {
			throw new RunCompareException("Error parsing complex key "+key.toJson(), e);
		}
	}

	/**
	 * Compares two CPPResults : returns difference, returns null when both are same 
	 * 
	 * @param cppResult1
	 * @param cppResult2
	 * @return
	 */
	private RunCompare compare(CPPResult cppResult1, CPPResult cppResult2) {
		Result aaResult1 = cppResult1.getAirlineResults().get(Airline.AA);
		Result aaResult2 = cppResult2.getAirlineResults().get(Airline.AA);
		
		boolean elig1 = aaResult1.isEligible();
		boolean elig2 = aaResult2.isEligible();

		int points1 = aaResult1.getPoints();
		int points2 = aaResult2.getPoints();

		int itemNo1 = cppResult1.getItemNo();
		int itemNo2 = cppResult2.getItemNo();

		if ((elig1 != elig2) || (points1 != points2)) {
			final RunCompare runCompare = new RunCompare();
			runCompare.setOrig(cppResult1.getOrig());
			runCompare.setDest(cppResult1.getDest());
			
			runCompare.setRunId1(cppResult1.getRunId());
			runCompare.setRunId2(cppResult2.getRunId());

			runCompare.setItemNo1(itemNo1);
			runCompare.setItemNo2(itemNo2);

			runCompare.setEligible1(elig1);
			runCompare.setEligible2(elig2);

			runCompare.setPoints1(points1);
			runCompare.setPoints2(points2);

			return runCompare;
		}

		return null;
	}

	private List<CPP> getCPP(Database cppDatabase, int limit, int skip) {
		try {
			return cppDatabase
					.getAllDocsRequestBuilder()
					.limit(limit)
					.skip(skip)
					.includeDocs(true)
					.build()
					.getResponse()
					.getDocsAs(CPP.class);
		} catch (IOException e) {
			throw new RunCompareException("Error reading from "+cppDatabase.info().getDbName(), e);
		}	
	}

	/**
	 * Queries view 'ndmarkets' with a given list of CPP and runIds
	 * 
	 * @param cppList
	 * @param runId1
	 * @param runId2
	 * @return
	 * 
	 */
	private ViewResponse<ComplexKey, Object> queryView(List<CPP> cppList, int runId1, int runId2) {
		final String CPP_DESIGN_DOC = "cpp";
		final String VIEW_NDMARKETS = "ndmarkets";
		try {
			return cppResultsDatabase
					.getViewRequestBuilder(CPP_DESIGN_DOC, VIEW_NDMARKETS)
					.newRequest(Key.Type.COMPLEX, Object.class)
					.includeDocs(true)
					.keys(generateKeys(cppList, runId1, runId2))
					.build()
					.getResponse();
		} catch (IOException e) {
			throw new RunCompareException("Error querying view "+VIEW_NDMARKETS, e);
		}
	}

	/**
	 * 
	 * Generates complex keys for the view 'ndmarkets' with a given list of CPP and runIds
	 * @param cppList
	 * @param runId1
	 * @param runId2
	 * 
	 */
	private ComplexKey[] generateKeys(List<CPP> cppList, int runId1, int runId2) {
		List<ComplexKey> keys = new ArrayList<>(cppList.size() * 2);
		cppList
		.stream()
		.forEach(
				cpp -> {
					String ndmarket = cpp.getOriginAirport() + cpp.getDestinationAirport();
					ComplexKey complexKey1 = Key
							.complex(ndmarket)
							.add(runId1);

					ComplexKey complexKey2 = Key
							.complex(ndmarket)
							.add(runId2);

					keys.add(complexKey1);
					keys.add(complexKey2);
				});
		ComplexKey[] complexKeys = new ComplexKey[keys.size()];
		return keys.toArray(complexKeys);
	}	

	private int getCPPSize(String cppDatabaseName) {
		Database cppDatabase = cloudantClient.database(cppDatabaseName, false);
		return cppDatabase.findByIndex("\"selector\": {\"_id\":{\"$gt\": 0 } }", CPP.class, new FindByIndexOptions().fields("_id")).size();
	}

	private String byItemNumberRangeSelector(int runId1, int runId2, int startItem, int endItem) {
		return "\"selector\":{\"runId\":{\"$in\":["+runId1+","+runId2+"]},"
				+ "\"$and\":[{\"itemNo\":{\"$gte\":"+startItem+"}},{\"itemNo\":{\"$lte\":"+endItem+"}}],"
				+ "\"excludeCodeshare\":{\"$exists\":false}}";
	}

	private String byRunId1RunId2Selector(RunCompareMsg runCompareMsg) {
		return "\"selector\":{\"runId1\":"+runCompareMsg.getRunId1()+",\"runId2\":"+runCompareMsg.getRunId2()+"}";		
	}

	/**
	 * Cleans run-compare database when re-running a FAILED run 
	 * @param runCompareMessage
	 */
	private void cleanUpCompare(RunCompareMsg runCompareMessage) {
		List<RunCompare> runCompares =  runCompareDatabase.findByIndex("\"selector\":{\"runId1\":"
				+runCompareMessage.getRunId1()+",\"runId2\":"
				+runCompareMessage.getRunId2()+"}", RunCompare.class);
		for (RunCompare runCompareObj : runCompares) {
			runCompareDatabase.remove(runCompareObj);
		}
	}

	/**
	 * Complex Key Structure
	 * @author 940914
	 */
	static class ViewKey {
		final String ndmarket;
		final int runId;

		public ViewKey(String ndmarket, int runId) {
			super();
			this.ndmarket = ndmarket;
			this.runId = runId;
		}

		public String getNdmarket() {
			return ndmarket;
		}

		public int getRunId() {
			return runId;
		}
	}
}