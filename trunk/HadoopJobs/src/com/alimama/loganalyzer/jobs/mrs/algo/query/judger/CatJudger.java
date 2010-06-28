package com.alimama.loganalyzer.jobs.mrs.algo.query.judger;

import java.util.HashMap;
import java.util.Map;

import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Cat;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 类目判断器
 * 
 * @author 明风
 * @date 2010-04-29
 */
public class CatJudger extends DataJudger {
	private Map<String, Cat> cats;
	
	private CatJudger(){
		super();
	}

	public CatJudger(Map cats) {
		super();
		this.cats = cats;
	}
	
	public int judge(String word, int predictedCatId) {
		Cat cat = cats.get(word);
		if (cat == null) {
			return KQConst.NOT_EXIST_INT;
		}

		/*
		 * 没有类目预测ID，既然有匹配上的类目，那么就随意返回第一个 
		 */
		if (predictedCatId <= 0) {
			if (cat.catIds.size() > 0) {
				return cat.catIds.get(0);
			} else {
				return KQConst.NOT_EXIST_INT;
			}
		}

		/**
		 * 有类目预测ID，那么就取出类目词对应的所有类目，按照顺序匹配，返回匹配到的第一个ID
		 */
		for (int i = 0, j = cat.catIds.size(); i < j; i++) {
			int catId = cat.catIds.get(i);
			//暂时不考虑类目继承树的问题，只判断是否相等
			if (catId == predictedCatId){
			//if (isChild(catId, predictedCatId)) {
				return cat.catIds.get(i);
			}
		}

		return KQConst.NOT_EXIST_INT;

	}

}
