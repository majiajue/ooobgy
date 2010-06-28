package com.alimama.loganalyzer.jobs.mrs.algo.query.judger;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.loader.BrandLoader;
import com.alimama.loganalyzer.jobs.mrs.algo.query.loader.CatLoader;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


public class BrandJudgerTest {
	BrandLoader brandLoader = new BrandLoader();
	BrandJudger brandJudger;
	CatLoader catLoader = new CatLoader();

	String brandPath = "brands";
	String catPath = "cats";
	
	
	@Before
	public void setUp(){
		brandLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(brandPath).getFile());
		catLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(catPath).getFile());
		
		Map brands = brandLoader.getData();
		brandJudger = new BrandJudger(brands);
		brandJudger.setCatIdTree(catLoader.getCatIdTree());		
	}
	
	@Test
	public void testBrandInSubCat(){
		int brandId1 = brandJudger.judge("×¿ÑÅ", KQConst.NOT_EXIST_INT);
		System.out.println(brandId1);
		int brandId2 = brandJudger.judge("×¿ÑÅ", 16);
		System.out.println(brandId2);
		int brandId3 = brandJudger.judge("×¿ÑÅ", 100);
		System.out.println(brandId3);
		assertEquals(-99, brandId3);
		
		int brandId4 = brandJudger.judge("Åµ»ùÑÇ", KQConst.NOT_EXIST_INT);
		
	}
	
	@After
	public void tearDown(){
		
	}
}
