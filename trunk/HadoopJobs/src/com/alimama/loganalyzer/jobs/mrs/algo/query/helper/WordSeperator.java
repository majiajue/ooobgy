package com.alimama.loganalyzer.jobs.mrs.algo.query.helper;

import java.util.List;

public interface WordSeperator {

	List<String> segment(String query);

	public List<String> getBrands();

	public List<String> getKeyWords();

	public List<String> getStyles();
	
	public List<String> getCatWords();
	
	public List<String> getDescWords();
}
