package com.alimama.loganalyzer.jobs.mrs.algo.query;

import junit.framework.TestCase;

public class QueryFlagTest extends TestCase {
	QueryFlag qf = new QueryFlag();
	
	public void testGetFlag() {
		qf.setCat(true);
		System.out.println(qf.getFlag());
		qf.setBrand(true);
		System.out.println(qf.getFlag());
		qf.setModel(true);
		System.out.println(qf.getFlag());
		qf.setDesc(true);
		System.out.println(qf.getFlag());
		qf.setNormal(true);
		System.out.println(qf.getFlag());

		qf.setModel(false);
		System.out.println(qf.getFlag());
		
	}

}
