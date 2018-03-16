package com.aa.gsa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.aa.gsa.enums.EquipmentType;
import com.aa.gsa.exception.PointsProcessorException;
import com.aa.gsa.service.EquipmentReader;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Document;

public class EquipmentReaderCloudant implements EquipmentReader {

	private Database database;

	@Autowired
	public EquipmentReaderCloudant(CloudantClient cloudantClient, @Value("${batch.equipment-codes-database-name}") String databaseName) {
		this.database = cloudantClient.database(databaseName, false);
	}

	@Override
	public Map<String, EquipmentType> getEquipmentCodes() {
		try {
			List<Document> docs = database
					.getAllDocsRequestBuilder()
					.includeDocs(true)
					.build()
					.getResponse()
					.getDocs();

			if (CollectionUtils.isEmpty(docs)) {
				throw new PointsProcessorException("Equipment Codes Not Found");
			}

			if (docs.size() > 1) {
				throw new PointsProcessorException("More than one document found in EquipmentCodes Database");
			}

			@SuppressWarnings("unchecked")
			Map<String, String> result = database.find(Map.class, docs.get(0).getId());

			Map<String, EquipmentType> equipCodes = new HashMap<>(result.size());

			for (Map.Entry<String, String> entry : result.entrySet()) {
				equipCodes.put(entry.getKey(), EquipmentType.findByValue(entry.getValue()));
			}

			return equipCodes;

		} catch (IOException ex) {
			throw new PointsProcessorException("Error reading equipment codes", ex);
		}
	}
}