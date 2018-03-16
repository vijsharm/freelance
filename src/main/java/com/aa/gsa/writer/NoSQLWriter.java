package com.aa.gsa.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import com.aa.gsa.service.NoSQLService;

public class NoSQLWriter<T> implements ItemWriter<T>, InitializingBean {

	private NoSQLService noSQLService;
	
	public NoSQLWriter(NoSQLService noSQLService) {
		this.noSQLService = noSQLService;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void write(List<? extends T> items) throws Exception {
		noSQLService.saveCollection(items);
	}
}
