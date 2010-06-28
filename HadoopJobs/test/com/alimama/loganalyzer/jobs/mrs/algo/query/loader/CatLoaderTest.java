package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.CatJudger;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

public class CatLoaderTest {
	CatLoader catLoader = new CatLoader();
	String dataPath = "cats";
	
	@Test
	public void testLoadFromPath() {
		catLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(dataPath).getFile());
		//System.out.println(catLoader.getData());
		System.out.println(catLoader.getData().size());
		
		CatJudger catJudger = new CatJudger(catLoader.getData());
		int catId1 = catJudger.judge("см╨Ь", KQConst.NOT_EXIST_INT);
		System.out.println(catId1);
		
		Map catIdTree = catLoader.getCatIdTree();
		System.out.println(catIdTree.get(new Integer(50000852)));
	}	

}
