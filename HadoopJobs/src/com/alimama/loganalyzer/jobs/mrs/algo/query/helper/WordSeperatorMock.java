package com.alimama.loganalyzer.jobs.mrs.algo.query.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordSeperatorMock implements WordSeperator{

	List<String> brands;
	List<String> keyWords;
	List<String> styles;
	List<String> catWords;
	List<String> descWords;
	
	public static WordSeperator getInstance() {
		return new WordSeperatorMock();
	}

	@Override
	public List<String> getBrands() {		
		return brands;
	}

	@Override
	public List<String> getCatWords() {
		return catWords;
	}

	@Override
	public List<String> getDescWords() {
		return descWords;
	}

	@Override
	public List<String> getKeyWords() {
		return keyWords;
	}

	@Override
	public List<String> getStyles() {
		return styles;
	}

	@Override
	public List<String> segment(String query) {
		List l = Arrays.asList(query.split("\\s+"));
		
		brands = l;
		catWords = l;
		descWords = l;
		keyWords = l;
		styles = l;
		
		return this.keyWords;
		
	}

}
