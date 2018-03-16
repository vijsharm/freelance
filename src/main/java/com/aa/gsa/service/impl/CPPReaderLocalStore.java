package com.aa.gsa.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.CPPReader;
import com.aa.gsa.util.JSONFileReader;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CPPReaderLocalStore implements CPPReader {

	final List<CPP> cppList;
		
	public CPPReaderLocalStore(String jsonFile) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			cppList = Collections.unmodifiableList(
					Arrays.asList(objectMapper.readValue(
							JSONFileReader.class.getResourceAsStream(jsonFile), CPP[].class)));
		} catch (IOException e) {
			throw new PointsProcessorException("Error reading CPP json file", e);
		}				
	}
	
	@Override
	public Integer getCount() {
		return cppList.size();
	}

	@Override
	public List<CPP> findWithRange(int start, int end) {
		return cppList.subList(start - 1, end);
	}

	@Override
	public List<CPP> findAll() {
		return cppList;
	}
}
