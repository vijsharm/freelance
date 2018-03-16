package com.aa.gsa.service;

import java.util.List;

public interface NoSQLService {
	
	void createDatabase(String databaseName, boolean create);
	
	void saveCollection(List<?> collection);
	
	void save(Object object);
	
	void update(Object object);
	
	<T> T findById(Class<T> classType, String id);

	long getNumberOfDocuments();
	
	<T> List<T> getAllDocuments(Class<T> classType);
}
