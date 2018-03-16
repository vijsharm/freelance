package com.aa.gsa.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aa.gsa.domain.CPP;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CPPLoader {

	public static void load(String cloudantUrl, String filePath, String databaseName) throws JsonParseException, JsonMappingException, IOException {	
		final CloudantClient cloudantClient = ClientBuilder
				.url(new URL(cloudantUrl))
				.build();

		ObjectMapper mapper = new ObjectMapper();

		List<CPP> list = Arrays.asList(mapper.readValue(JSONFileReader.class.getResourceAsStream(filePath), CPP[].class));

		Database database = cloudantClient.database(databaseName, true);

		int i = 1;
		int writtenCount = 0;

		final int chunkSize = 200;
		List<CPP> cppChunk = new ArrayList<>(chunkSize);

		for (CPP cpp : list) {
			cppChunk.add(cpp);
			if (i % chunkSize == 0) {
				database.bulk(cppChunk);
				cppChunk.clear();
				writtenCount = writtenCount + chunkSize;
			}
			i++;
		}

		if (writtenCount < list.size()) {
			List<CPP> lastChunk =  list.subList(writtenCount, list.size());
			database.bulk(lastChunk);
		}
	}

/*	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException {
		final String CLOUDANT_URI = "http://admin:mysecretpassword@localhost:5986";
		final String CPP_FILE = "/cpp/cpp.json";
		final String CPP_DATABASE = "cpp_2017";

		load(CLOUDANT_URI, CPP_FILE, CPP_DATABASE);
	}*/
}
