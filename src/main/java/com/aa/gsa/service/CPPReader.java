package com.aa.gsa.service;

import java.util.List;

import com.aa.gsa.domain.CPP;

public interface CPPReader {
	/**
	 * Returns the count
	 * 
	 * @return
	 * 
	 */
	Integer getCount();
	
	/**
	 * Gets a list of CPP pairs with the given [start-end] range
	 * 
	 * @param start
	 * @param end
	 * @return
	 * 
	 */
	List<CPP> findWithRange(int start , int end);	
	
	
	/**
	 * 
	 * @return
	 * 
	 */
	List<CPP> findAll();	
}
