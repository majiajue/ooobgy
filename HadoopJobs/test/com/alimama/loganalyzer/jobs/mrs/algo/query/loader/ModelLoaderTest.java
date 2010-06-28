package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.ModelJudger;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


public class ModelLoaderTest {
	ModelLoader modelLoader = new ModelLoader();
	String dataPath = "types";
	
	@Test
	public void testLoad(){
		modelLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(dataPath).getFile());
		Map models = modelLoader.getData();
		int i = 0;
		
		System.out.println("Model BrandId size: " + models.size());
		System.out.println("Duplicate num: " + modelLoader.duplicate);
		System.out.println("File num: " + modelLoader.dataLineNum);

		assertEquals(true, models.size() == (modelLoader.dataLineNum - modelLoader.duplicate));
		
		ModelJudger modelJudger = new ModelJudger(modelLoader.getData());
		int modelId = modelJudger.judge("Çå½àÁù¼þÌ×", KQConst.NOT_EXIST_INT);
		System.out.println(modelId);
		
		
	}
}
