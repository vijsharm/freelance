package com.aa.gsa.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aa.gsa.domain.CPP;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONFileReader {

	private List<CPP> readJson(String fileName) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<CPP> list = Arrays.asList(mapper.readValue(JSONFileReader
				.class.getResourceAsStream("/cpp/"+fileName+".json"), CPP[].class));
		for(CPP cpp : list) {
			cpp.setGroupType(fileName);
		}

		return list;
	}

	public void read() {
		try {
			String[] groupTypes = {
					"group1Domestic",
					"group1International", 
					"group1DomEC", 
					"group1IntlEC", 
					"group1IntlECWithBusinessclass", 
					"group15thFreedom", 
					"group1Havana", 
					"group2Domestic", 
					"group2International"
			};


			List<CPP> finalList = new ArrayList<>();
			for (String groupType : groupTypes) {
				finalList.addAll(readJson(groupType));
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File("CPP.json"), finalList);

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
