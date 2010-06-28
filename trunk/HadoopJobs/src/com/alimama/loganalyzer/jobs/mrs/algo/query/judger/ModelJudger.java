package com.alimama.loganalyzer.jobs.mrs.algo.query.judger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Model;
import com.alimama.loganalyzer.jobs.mrs.util.GlobalInfo;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * ÐÍºÅÅÐ¶ÏÆ÷
 * 
 * @author Ã÷·ç
 * @date 2010-04-29
 *
 */
public class ModelJudger extends DataJudger{
	private Map<String, Model> models;

	public ModelJudger(Map _models){
		this.models = _models;		
	}
	
	public int judge(String word, int predictedCatId) {
		
		Model model = models.get(word);
		if (model == null){
			return KQConst.NOT_EXIST_INT;
		}
		
		if (predictedCatId <= 0){
			if (model.modelIds.size() > 0){
				return model.modelIds.get(0);
			}else{
				return KQConst.NOT_EXIST_INT;				
			}
		}
		
		for (int i=0,j=model.modelIds.size(); i<j; i++){
			int catId = model.catIds.get(i);
			if (isChild(catId, predictedCatId)){
				return model.modelIds.get(i);
			}
		}
		
		return KQConst.NOT_EXIST_INT;
	}
}
