package com.aa.gsa.service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.domain.Timeband;
import com.aa.gsa.domain.settings.Settings;
import com.aa.gsa.enums.Group;
import com.aa.gsa.properties.BatchJobProperties;
import com.aa.gsa.service.impl.SettingsPerGroupServiceImpl;
import com.aa.gsa.util.JSONFileReader;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettingsPerGroupServiceTest {

	@Autowired
	private BatchJobProperties batchJobProperties;

	@Autowired
	private CloudantClient cloudantClient;

	private Database settingsDatabase;

	private static final String TEST_SETTINGS_NAME = "test_settings";

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException {
		settingsDatabase = cloudantClient.database(batchJobProperties.getSettingsDatabaseName(), true);
	}

	@After
	public void after() {
		List<Settings> settingDoc = settingsDatabase.findByIndex("\"selector\": {\"name\": \""+TEST_SETTINGS_NAME+"\"}", Settings.class);
		if (!CollectionUtils.isEmpty(settingDoc)) {
			settingsDatabase.remove(settingDoc.get(0));
		}
	}		

	@Test
	public void validSettingsTest() {
		createSettingsDocument("/settings/valid_settings.json");
		SettingsPerGroupService settingsService = new SettingsPerGroupServiceImpl(cloudantClient, batchJobProperties.getSettingsDatabaseName(), TEST_SETTINGS_NAME);

		Assert.assertNotNull("Ground Time settings cannot be empty", settingsService.groundTimeSettings());
		Assert.assertNotNull("Circuity settings cannot be empty", settingsService.circuitySettings());
		Assert.assertNotNull("Elapsed Time settings cannot be empty", settingsService.elapsedTimeSettings());
		
		Set<Timeband> group1Timebands = settingsService.timebandsByGroup(Group.Group1);
		Set<Timeband> group2Timebands = settingsService.timebandsByGroup(Group.Group2);
		
		Assert.assertNotNull("Group1 Timebands cant be null", group1Timebands);
		Assert.assertNotNull("Group2 Timebands cant be null", group2Timebands);

		Assert.assertTrue("Group1 contains timeband with FROM = 6.00 AM and TO = 09.30 AM", 
				group1Timebands.contains(new Timeband(LocalTime.of(6, 00), LocalTime.of(9, 30))));
		
		Timeband timeband1 =  settingsService.findTimebandByRange("06", "30", Group.Group1);
		Assert.assertTrue("Group1 contains timeband with 06.30 AM departure time", timeband1 != null);
		Assert.assertTrue("06.30AM departure time falls in Timeband with ordinal = 1", timeband1.getOrdinal() == 1);
		
		
		Timeband timeband3 =  settingsService.findTimebandByRange("15", "30", Group.Group2);
		Assert.assertTrue("Group2 contains timeband with 03.30 PM departure time", timeband3 != null);
		Assert.assertTrue("03.30 PM departure time falls into timeband with ordinal = 1",timeband3.getOrdinal() == 3);
	}
	
	//@Test
	public void sevenAMTimebandTest() {
		createSettingsDocument("/settings/7am_timeband.json");
		SettingsPerGroupService settingsService = new SettingsPerGroupServiceImpl(cloudantClient, batchJobProperties.getSettingsDatabaseName(), TEST_SETTINGS_NAME);

		Assert.assertNotNull("Ground Time settings cannot be empty", settingsService.groundTimeSettings());
		Assert.assertNotNull("Circuity settings cannot be empty", settingsService.circuitySettings());
		Assert.assertNotNull("Elapsed Time settings cannot be empty", settingsService.elapsedTimeSettings());
		
		Set<Timeband> group1Timebands = settingsService.timebandsByGroup(Group.Group1);
		Set<Timeband> group2Timebands = settingsService.timebandsByGroup(Group.Group2);
		
		Assert.assertNotNull("Group1 Timebands cant be null", group1Timebands);
		Assert.assertNotNull("Group2 Timebands cant be null", group2Timebands);

		Assert.assertTrue("Group1 contains timeband with FROM = 7.00 AM and TO = 09.30 AM", 
				group1Timebands.contains(new Timeband(LocalTime.of(7, 00), LocalTime.of(9, 30))));

		
		Timeband timeband1 =  settingsService.findTimebandByRange("07", "01", Group.Group1);
		Assert.assertTrue("Group1 contains timeband with 07.01 AM departure time", timeband1 != null);
		Assert.assertTrue("07.01 AM departure time falls in Timeband with ordinal = 1", timeband1.getOrdinal() == 1);
		
		
		Timeband timeband5 =  settingsService.findTimebandByRange("06", "50", Group.Group2);
		Assert.assertTrue("Group2 contains timeband with 06.50 AM departure time", timeband5 != null);
		Assert.assertTrue("63.50 AM departure time falls into timeband with ordinal = 5", timeband5.getOrdinal() == 5);
	}
	
	//@Test(expected = PointsProcessorException.class)
	public void mimsmatchTimebandsTest() {
		createSettingsDocument("/settings/mismatch_timebands.json");
		new SettingsPerGroupServiceImpl(cloudantClient, batchJobProperties.getSettingsDatabaseName(), TEST_SETTINGS_NAME);
	}
	
	//@Test(expected = PointsProcessorException.class)
	public void invalidTimebandTest() {
		createSettingsDocument("/settings/invalid_timeband.json");
		new SettingsPerGroupServiceImpl(cloudantClient, batchJobProperties.getSettingsDatabaseName(), TEST_SETTINGS_NAME);
	}
	
	private void createSettingsDocument(String settingsfilePath) {
		Object json;
		try {
			json = new ObjectMapper().readValue(JSONFileReader.class.getResourceAsStream(settingsfilePath), Object.class);
			settingsDatabase.save(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
