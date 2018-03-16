package com.aa.gsa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class CPPCompareUtil {

	private ObjectMapper mapper;

	@Before
	public void before() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		String inputResource = "/data_validation/1141-input.json";
		List<Input> input = mapper.readValue(this.getClass().getResourceAsStream(inputResource), new TypeReference<List<Input>>(){});

		List<Output> output = new ArrayList<>(input.size());
		for (Input inputObj : input) {
			Output outputObj = new Output();
			outputObj.setItemNo(inputObj.getItemNo());
			outputObj.setAirline("AA");

			outputObj.setEligible(inputObj.getAirlineResults().get("AA").isEligible());
			outputObj.setPoints(inputObj.getAirlineResults().get("AA").getPoints());
			outputObj.setElaspedTime(inputObj.getAirlineResults().get("AA").getElaspedTime());
			output.add(outputObj);
		}		
		String outputFilePath = this.getClass().getResource(inputResource).getPath().replace("-input.json", "-output.json");
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), output);
	}	
}

class Output {
	int itemNo;
	String airline;
	boolean eligible;
	int points;
	int elaspedTime;

	public int getItemNo() {
		return itemNo;
	}
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public boolean isEligible() {
		return eligible;
	}
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getElaspedTime() {
		return elaspedTime;
	}
	public void setElaspedTime(int elaspedTime) {
		this.elaspedTime = elaspedTime;
	}
}

class Input {
	int itemNo;

	Map<String, DataHolder> airlineResults;

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public Map<String, DataHolder> getAirlineResults() {
		return airlineResults;
	}

	public void setAirlineResults(Map<String, DataHolder> airlineResults) {
		this.airlineResults = airlineResults;
	}
}

class DataHolder {
	boolean eligible;
	int points;
	int elaspedTime;

	public boolean isEligible() {
		return eligible;
	}

	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getElaspedTime() {
		return elaspedTime;
	}

	public void setElaspedTime(int elaspedTime) {
		this.elaspedTime = elaspedTime;
	}
}
