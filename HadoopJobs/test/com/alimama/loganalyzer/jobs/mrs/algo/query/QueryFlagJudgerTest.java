package com.alimama.loganalyzer.jobs.mrs.algo.query;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.QueryCatPredictMock;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.WordSeperatorMock;

public class QueryFlagJudgerTest {
	QueryFlagJudger queryFlagJudger;

	@Before
	public void setUp() throws Exception {
		String _catPath = Thread.currentThread().getContextClassLoader().getResource("cats").getFile();
		String _brandPath = Thread.currentThread().getContextClassLoader().getResource("brands").getFile(); 
		String _modelPath = Thread.currentThread().getContextClassLoader().getResource("types").getFile();
		
		queryFlagJudger = new QueryFlagJudger(_catPath, _brandPath, _modelPath);
		queryFlagJudger.setWordSperator(new WordSeperatorMock());
		queryFlagJudger.setQueryCatPredict(new QueryCatPredictMock());
	}
	
	/**
	 * 测试类目词，品牌词，型号词
	 */
	@Test
	public void testCast(){
		String queryFlag = queryFlagJudger.judge("手机 诺基亚  N97");
		System.out.println(queryFlag);
		//assertEqual("11100", queryFlag);		
		queryFlag = queryFlagJudger.judge("魅族");
		System.out.println(queryFlag);
	}
	

	@After
	public void tearDown() throws Exception {
	}

}
