package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.ModelJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Model;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


public class ModelLoaderTest {
	ModelLoader modelLoader = new ModelLoader();
	String dataPath = "types";
	
	@Test
	public void testLoad(){
		modelLoader.loadFromPath(Thread.currentThread().getContextClassLoader().getResource(dataPath).getFile());
		Map models = modelLoader.getData();
		
		System.out.println("Model BrandId size: " + models.size());
		System.out.println("Duplicate num: " + modelLoader.duplicate);
		System.out.println("File num: " + modelLoader.dataLineNum);

		/**
		 * 这个断言逻辑不对，源代码实现的功能里，逐行读入数据，然后用"\u0001"分割数据，
		 * <strong>对于每一个分割出来的字串，再尝试用空格分隔，如果能够分割，
		 * 对结果字串(即行数据字串的字串)过滤后加入model；
		 * 若不能分隔，则把字串(行数据的字串)加入model。</strong>
		 * 这样子，无从得知这三个变量之间的等式关系，这个断言应该是不确定的，可能true可能false；
		 * 此处去掉吧---------by zhouxiaolong.pt
		 */
		//assertTrue(models.size() == (modelLoader.dataLineNum - modelLoader.duplicate));
		
		ModelJudger modelJudger = new ModelJudger(modelLoader.getData());
		int modelId = modelJudger.judge("清洁六件套", KQConst.NOT_EXIST_INT);
		System.out.println(modelId);
		
		/**
		 * add by zhouxiaolong.pt这个可以验证
		 */
		Model model = (Model)models.get("清洁六件套");
		String testStr = model.modelName;
		assertEquals("清洁六件套", testStr);
		assertTrue(model.modelIds.contains(new Integer(modelId)));
		
		//System.out.println(testStr);
		
	}
}
