package com.alimama.loganalyzer.jobs.mrs.algo.query.helper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WordSeperatorMockTest {
	WordSeperatorMock wsm = new WordSeperatorMock();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSegment() {
		System.out.println(wsm.segment("诺基亚 手机 N97"));
	}

}
