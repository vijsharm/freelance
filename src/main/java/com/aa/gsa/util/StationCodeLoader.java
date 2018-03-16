package com.aa.gsa.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.aa.gsa.domain.StationCode;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.opencsv.bean.CsvToBeanBuilder;

public class StationCodeLoader {

	public static final String CLOUDANT_URI = "https://8e04e643-ef2c-4433-8d5d-7bdfad79fb2f-bluemix:ff23f2bea84ecd81255b42aa6e7af1611769899afcb579cc069ac5aae0d04ae0@8e04e643-ef2c-4433-8d5d-7bdfad79fb2f-bluemix.cloudant.com";
	
	public static final String GSA_STATIONS_FILE = "/stations/gsa_stations.json";
	
	public static final String STATION_CODES_DATABASE = "station_codes";
	
	public static void load() throws JsonParseException, JsonMappingException, IOException {	

		final CloudantClient cloudantClient = ClientBuilder
				.url(new URL(CLOUDANT_URI))
				.build();
		
	     List<StationCode> stations = new CsvToBeanBuilder(new FileReader("C:\\Users\\ivc15115adm\\Documents\\gsa-workspace\\SP_GSA_PointsProcessor\\src\\main\\resources\\stations\\gsa_stations.csv"))
	    	       .withType(StationCode.class)
	    	       .build()
	    	       .parse();

		Database database = cloudantClient.database(STATION_CODES_DATABASE, true);

		int i = 1;
		int writtenCount = 0;

		final int chunkSize = 200;
		List<StationCode> stationsChunk = new ArrayList<>(chunkSize);

		for (StationCode station : stations) {
			stationsChunk.add(station);
			if (i % chunkSize == 0) {
				database.bulk(stationsChunk);
				stationsChunk.clear();
				writtenCount = writtenCount + chunkSize;
			}
			i++;
		}

		if (writtenCount < stations.size()) {
			List<StationCode> lastChunk =  stations.subList(writtenCount, stations.size());
			database.bulk(lastChunk);
		}
	}

/*	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException {
		load();
	}*/
}