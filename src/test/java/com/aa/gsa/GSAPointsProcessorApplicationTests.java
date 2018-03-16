package com.aa.gsa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GSAPointsProcessorApplicationTests {

@Test
	public void simpleJunitTestForPipeline() {
    System.out.println("GSAPointsProcessorApplicationTests: simpleJunitTestForPipeline() invoked.");
  }

}


//package com.aa.gsa;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.support.SimpleJobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.task.SyncTaskExecutor;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.CollectionUtils;
//
//import com.aa.gsa.domain.Run;
//import com.aa.gsa.domain.result.CPPResult;
//import com.aa.gsa.domain.settings.Settings;
//import com.aa.gsa.enums.Airline;
//import com.aa.gsa.properties.BatchJobProperties;
//import com.aa.gsa.properties.CloudantProperties;
//import com.aa.gsa.util.CPPLoader;
//import com.aa.gsa.util.JSONFileReader;
//import com.aa.gsa.util.PointsProcessorConstants;
//import com.cloudant.client.api.CloudantClient;
//import com.cloudant.client.api.Database;
//import com.cloudant.client.api.model.Response;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration
//public class GSAPointsProcessorApplicationTests {
//
//	@Autowired
//	private Job job;
//
//	@Autowired
//	private JobRepository jobRepository;
//
//	@Autowired
//	private CloudantClient cloudantClient;
//
//	@Autowired
//	private CloudantProperties cloudantProperties;
//
//	@Autowired
//	private BatchJobProperties batchJobProperties;
//
//	private  static final String SCHEDULE_DATABASE_NAME = "schedule_021317";
//
//	private static final String CPP_RESULTS_DATABASE = "cpp-results-test";
//
//	private static final String CPP_FILE_DATABASE = "cpp_test";
//
//	private static final String RUN_NAME = "run_test";
//	
//	private static final String SETTINGS_FILE_PATH = "/settings/settings_per_group_test.json";
//
//	private String RULESET_NAME;
//
//	private Database settingsDatabase;
//
//	private Database runDatabase;
//
//	private Database resultsDatabase;
//
//	@BeforeClass
//	public static void beforeClass() {}
//
//	@Before
//	public void before() throws JsonParseException, JsonMappingException, IOException {
//		//if results database exists from previous run then delete it.
//		if (cloudantClient.getAllDbs().contains(CPP_RESULTS_DATABASE)) {
//			cloudantClient.deleteDB(CPP_RESULTS_DATABASE);
//		}
//
//		settingsDatabase = cloudantClient.database(batchJobProperties.getSettingsDatabaseName(), true);
//		runDatabase = cloudantClient.database(batchJobProperties.getRunDatabaseName(), true);
//		resultsDatabase = cloudantClient.database(CPP_RESULTS_DATABASE, true);
//
//		@SuppressWarnings("rawtypes")
//		Map settings =  new ObjectMapper().readValue(JSONFileReader.class.getResourceAsStream(SETTINGS_FILE_PATH), Map.class);
//		RULESET_NAME = (String) settings.get("name");
//		List<Settings> settingDoc = settingsDatabase.findByIndex("\"selector\": {\"name\": \""+RULESET_NAME+"\"}", Settings.class);
//		if (CollectionUtils.isEmpty(settingDoc)) {
//			ObjectMapper mapper = new ObjectMapper();
//			Object json =  mapper.readValue(JSONFileReader.class.getResourceAsStream(SETTINGS_FILE_PATH), Object.class);
//			settingsDatabase.save(json);
//		} 		
//	}
//
//	@After
//	public void after() {
//		cloudantClient.deleteDB(CPP_FILE_DATABASE);
//		List<Run> runs = runDatabase.findByIndex("\"selector\": {\"name\": \""+RUN_NAME+"\"}", Run.class);
//		Assert.assertFalse(runs.isEmpty());
//		Assert.assertTrue(runs.size() == 1);
//		runDatabase.remove(runs.get(0));
//	}		
//
//	@Test
//	public void group1Domestic() throws JsonParseException, JsonMappingException, IOException {
//		CPPLoader.load(cloudantProperties.getUrl(), "/cpp/group1Domestic.json", CPP_FILE_DATABASE);
//		run();
//		validateResult(1, Airline.AA, true, 49, 211, false);
//	}
//
//	@Test
//	public void group1International() throws JsonParseException, JsonMappingException, IOException {
//		CPPLoader.load(cloudantProperties.getUrl(), "/cpp/group1International.json", CPP_FILE_DATABASE);
//		run();
//		validateResult(2600, Airline.AA, true, 28, 704, false);
//	}
//	
//	@Test
//	public void group1InternationalNoCodeshare() throws JsonParseException, JsonMappingException, IOException {
//		CPPLoader.load(cloudantProperties.getUrl(), "/cpp/group1International.json", CPP_FILE_DATABASE);
//		run();
//		validateResult(2600, Airline.AA, true, 21, 716, true);
//	}
//	
//	@Test
//	public void group1DomesticEC() throws JsonParseException, JsonMappingException, IOException {
//		CPPLoader.load(cloudantProperties.getUrl(), "/cpp/group1DomesticEC.json", CPP_FILE_DATABASE);
//		run();
//		validateResult(3200, Airline.AA, false, 0, 0, true);
//	}
//
//	private void run() {
//		Run run = createRun();
//		Response response = runDatabase.save(run);
//		Run runCreated = runDatabase.find(Run.class, response.getId());
//		submitRun(runCreated);
//	}
//
//	private Run createRun() {
//		Run run = new Run();
//		run.setScheduleName(SCHEDULE_DATABASE_NAME);
//		run.setCppFileGroupName(CPP_FILE_DATABASE);
//		run.setRulesetName(RULESET_NAME);
//		run.setYear(Calendar.getInstance().get(Calendar.YEAR) + 1);
//		run.setSubmittedBy("940914");
//		run.setSubmittedOn(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//		run.setName(RUN_NAME);
//		return run;
//	}
//
//	private JobExecution submitRun(Run run)  {
//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString(PointsProcessorConstants.SCHEDULE_FILE_ID, SCHEDULE_DATABASE_NAME)
//				.addString(PointsProcessorConstants.CPP_FILE_ID, CPP_FILE_DATABASE)
//				.addString(PointsProcessorConstants.SETTINGS_DOCUMENT_NAME, RULESET_NAME)
//				.addString(PointsProcessorConstants.RUN_DOCUMENT_ID, run.get_id())
//				.addString(PointsProcessorConstants.RUN_NAME, run.getName())
//				.addDate(PointsProcessorConstants.RUN_DATE, new Date())
//				.toJobParameters();
//
//		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//		jobLauncher.setJobRepository(jobRepository);
//		jobLauncher.setTaskExecutor(new SyncTaskExecutor());
//
//		try {
//			return jobLauncher.run(job, jobParameters);
//		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private void validateResult(int itemNo, Airline airline, boolean eligible, int actualPoints, int elapsedTime, boolean excludeCodeshare) {
//		List<CPPResult> results = resultsDatabase.findByIndex("\"selector\": {\"itemNo\": "+itemNo+" }", CPPResult.class);
//		Assert.assertFalse(results.isEmpty());
//		if (results.get(0).getDomestic()) {
//			Assert.assertTrue("Domestic market should only have one result", results.size() == 1);
//			CPPResult result = results.get(0);
//			Assert.assertFalse("Airline Results can not be empty", result.getAirlineResults().isEmpty());
//			Assert.assertTrue("Eligibilty should be "+eligible, result.getAirlineResults().get(airline).isEligible() == eligible);
//			Assert.assertTrue("Points should be equal to "+actualPoints, result.getAirlineResults().get(airline).getPoints() == actualPoints);
//			Assert.assertTrue("Elapsed time should be equal to "+elapsedTime, result.getAirlineResults().get(airline).getElaspedTime() == elapsedTime);
//		} else {//validate international
//			Assert.assertTrue("International market should have 2 results", results.size() == 2);//one for master and one for no-code share
//			CPPResult result = null;
//			if (!excludeCodeshare) { 
//				result = results.get(0).getExcludeCodeshare() == null ? results.get(0) : results.get(1);
//			} else {
//				result = results.get(0).getExcludeCodeshare() == null ? results.get(1) : results.get(0);
//			}
//			Assert.assertFalse("Airline Results can not be empty", result.getAirlineResults().isEmpty());
//			Assert.assertTrue("Eligibilty should be "+eligible, result.getAirlineResults().get(airline).isEligible() == eligible);
//			Assert.assertTrue("Points should be equal to "+actualPoints, result.getAirlineResults().get(airline).getPoints() == actualPoints);
//			Assert.assertTrue("Elapsed time should be equal to "+elapsedTime, result.getAirlineResults().get(airline).getElaspedTime() == elapsedTime);
//		}
//	}
//}
