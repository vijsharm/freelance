package com.aa.gsa.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.ResultsWriter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileResultsWriter implements ResultsWriter {

	private FileOutputStream outputStream;
	
	private ObjectMapper objectMapper;
	
	public FileResultsWriter(String fileName) {
		try {
			outputStream = new FileOutputStream(fileName, true);
		} catch (FileNotFoundException e) {
			throw new PointsProcessorException("Error creating output file", e);
		}
		objectMapper = new ObjectMapper();
	}

	@Override
	public void saveResults(List<?> results) {
		try {
			objectMapper.writeValue(outputStream, results);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
