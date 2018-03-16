package com.aa.gsa.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aa.gsa.domain.CPP;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.CPPReader;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class CPPReaderNoSQLStore implements CPPReader {

	final List<CPP> cppList;

	@Autowired
	public CPPReaderNoSQLStore(CloudantClient cloudantClient, String databaseName) {
		Database database = cloudantClient.database(databaseName, false);
		try {
			cppList = database
					.getAllDocsRequestBuilder()
					.includeDocs(true)
					.build()
					.getResponse()
					.getDocsAs(CPP.class);
		} catch (IOException e) {
			throw new PointsProcessorException("Error loading the CPP from "+databaseName, e);
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
