package com.aa.gsa.service.impl;

import java.io.IOException;
import java.util.List;

import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.NoSQLService;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

public class CloudantService implements NoSQLService {

	private CloudantClient cloudantClient;

	private Database database;

	private boolean assertUpdates = true;

	public CloudantService(CloudantClient cloudantClient, String databaseName) {
		this.cloudantClient = cloudantClient;
		database = cloudantClient.database(databaseName, true);
	}

	@Override
	public void createDatabase(String databaseName, boolean create) {
		database = cloudantClient.database(databaseName, create);
	}

	@Override
	public void saveCollection(List<?> collection) {
		final List<Response> responses = database.bulk(collection);
		if (assertUpdates) {
			for (Response response : responses) {
				if (response.getError() != null) {
					throw new RuntimeException("An error occurred during save "+response.getError());
				}
			}
		}
	}

	@Override
	public void save(Object object) {
		database.save(object);
	}

	@Override
	public void update(Object object) {
		database.update(object);
	}

	@Override
	public <T> T findById(Class<T> classType, String id) {
		return database.find(classType, id);
	}

	@Override
	public long getNumberOfDocuments() {
		return database.info().getDocCount();
	}

	public <T> List<T> getAllDocuments(Class<T> classType) {
		try {
			return  database
					.getAllDocsRequestBuilder()
					.includeDocs(true)
					.build()
					.getResponse()
					.getDocsAs(classType);
		} catch (IOException ex) {
			throw new PointsProcessorException("Error while fetching documents from the database ", ex);
		}
	}

	public void setAssertUpdates(boolean assertUpdates) {
		this.assertUpdates = assertUpdates;
	}
}
