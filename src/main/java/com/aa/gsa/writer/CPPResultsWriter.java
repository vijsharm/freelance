package com.aa.gsa.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import com.aa.gsa.domain.result.CPPResult;
import com.aa.gsa.service.NoSQLService;

public class CPPResultsWriter implements ItemWriter<List<CPPResult>>, InitializingBean {

	private NoSQLService noSQLService;

	private int runId;

	public CPPResultsWriter(NoSQLService noSQLService, int runId) {
		this.noSQLService = noSQLService;
		this.runId = runId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void write(List<? extends List<CPPResult>> items) throws Exception {
		List<CPPResult> results = new ArrayList<>(items.size());
		for (List<CPPResult> list : items) {
			for(CPPResult result : list) {
				result.setRunId(runId);
				results.add(result);
			}
		}
		noSQLService.saveCollection(results);
	}
}
