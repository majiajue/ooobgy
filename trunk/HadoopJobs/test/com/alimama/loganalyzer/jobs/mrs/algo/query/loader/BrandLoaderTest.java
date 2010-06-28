package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;



import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.BrandJudger;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


public class BrandLoaderTest extends TestCase{

	BrandLoader brandLoader = new BrandLoader();
	String dataPath = "brands";

	public void testLoadFromPath() {
		brandLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(dataPath).getFile());
		Map brands = brandLoader.getData();
		//System.out.println(brands);
		
		System.out.println("Brand CatId size: " + brands.size());
		System.out.println("Duplicated: " + brandLoader.duplicated);
		//assertEquals(true,  brands.size() > 70000);
		
		BrandJudger brandJudger = new BrandJudger(brands);
		int brandId1 = brandJudger.judge("в©яе", KQConst.NOT_EXIST_INT);
		System.out.println(brandId1);
		assertEquals(29883, brandId1);
		
	}

}
