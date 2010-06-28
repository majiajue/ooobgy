package com.alimama.loganalyzer.jobs.mrs.algo.query.helper;

import java.util.List;

import com.alimama.loganalyzer.jobs.mrs.algo.TwsTokenization;

public class TwsWordSeperator implements WordSeperator{
	private static TwsTokenization tws;
	private static TwsWordSeperator _instance;
	static{
		_instance = new TwsWordSeperator();
	}
	
	private TwsWordSeperator(){
		super();
	}
	
	public static WordSeperator getInstance() {
		return _instance;
	}
	
	@SuppressWarnings("static-access")
	public void setTws(TwsTokenization _tws){
		this.tws = _tws;
	}

	@Override
	public List<String> getBrands() {
		return tws.getBrands();
	}

	@Override
	public List<String> getCatWords() {
		return tws.getCatWords();
	}

	@Override
	public List<String> getDescWords() {
		return tws.getDescWords();
	}

	@Override
	public List<String> getKeyWords() {
		return tws.getKeyWords();
	}

	@Override
	public List<String> getStyles() {
		return tws.getStyles();
	}

	@Override
	public List<String> segment(String query) {
		tws.segment(query);
		return tws.getKeyWords();
	}

}
