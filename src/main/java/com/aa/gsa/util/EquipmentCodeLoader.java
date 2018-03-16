package com.aa.gsa.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.Database;

public class EquipmentCodeLoader {

	public static final String EQIOPMENT_CODES_DATABASE = "equipment_codes";

	public static final String EQIOPMENT_CODES_FILE_PATH = "C:\\Users\\ivc15115adm\\Documents\\gsa-workspace\\SP_GSA_PointsProcessor\\src\\main\\resources\\equipment\\gsa_equipment_table.csv";

	public static void load(String cloudantUrl) throws IllegalStateException, IOException {
		Map<String, String> equipmentCodes = readEquipmentCodes();
	
		Database database = ClientBuilder
				.url(new URL(cloudantUrl))
				.build()
				.database(EQIOPMENT_CODES_DATABASE, true);
		
		database.save(equipmentCodes);
	}

	private static Map<String, String> readEquipmentCodes() throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(EQIOPMENT_CODES_FILE_PATH))) {
			return lines
				.map(line -> line.split(","))
				.collect(Collectors.toMap(line-> line[0], line -> line[1]));
		} 		
	}
	
/*	public static void main(String args[]) throws IllegalStateException, IOException {
		load("https://8e04e643-ef2c-4433-8d5d-7bdfad79fb2f-bluemix:ff23f2bea84ecd81255b42aa6e7af1611769899afcb579cc069ac5aae0d04ae0@8e04e643-ef2c-4433-8d5d-7bdfad79fb2f-bluemix.cloudant.com");
	}*/
}

