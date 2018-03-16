package com.aa.gsa.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aa.gsa.enums.EquipmentType;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.EquipmentReader;

public class EquipmentReaderLocalStore implements EquipmentReader {

	public static final String EQIPMENT_FILE_PATH = "/equipment/gsa_equipment_table.csv";

	private final Map<String, EquipmentType> equipmentCodeMap;

	public EquipmentReaderLocalStore() {
		try (Stream<String> lines = Files.lines(Paths.get(getClass().getResource(EQIPMENT_FILE_PATH).toURI()))) {
			
			equipmentCodeMap = lines
					.map(line -> line.split(","))
					.collect(Collectors.toMap(line -> line[0], line -> EquipmentType.findByValue(line[1])));

			Collections.unmodifiableMap(equipmentCodeMap);
			
		} catch (IOException ex) {
			throw new PointsProcessorException("IO Error reading the Equipment code file using path ="+EQIPMENT_FILE_PATH, ex);
		} catch (URISyntaxException ex) {
			throw new PointsProcessorException("Invalid eqipment code file path ="+EQIPMENT_FILE_PATH, ex);
		} catch (Exception ex) {
			throw new PointsProcessorException(ex.getMessage(), ex);
		}
	}

	@Override
	public Map<String, EquipmentType> getEquipmentCodes() {
		return equipmentCodeMap;
	}
}
