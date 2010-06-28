package com.alimama.loganalyzer.jobs.mrs.algo.query.judger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 数据判断器
 * 
 * @author 明风
 * @date 2010-04-29
 */
public abstract class DataJudger {
	private Map<Integer, Set<Integer>> catIdTree;

	public void setCatIdTree(Map _catIdTree){
		this.catIdTree = _catIdTree;
	}

	/**
	 * 判断类目是否是预测类目下的子类目或者同级类目
	 * 
	 * @param catId
	 * @param predictedCatId
	 * @return
	 */
	public boolean isChild(int catId, int predictedCatId) {
		if (catId == KQConst.NOT_EXIST_INT) {
			return false;
		}
		if (predictedCatId == KQConst.NOT_EXIST_INT) {
			return true;
		}
		
		if (catId == predictedCatId){
			return true;
		}
		
		if (catIdTree == null) {
			return false;
		}

		Set<Integer> parentCatIds = catIdTree.get(catId);
		if (parentCatIds == null) {
			return false;
		}

		return parentCatIds.contains(predictedCatId);
	}
	
	/**
	 * 判断一个词是否在预测类目下，属于某种词
	 * 
	 * @param word
	 * @param predictedCatId
	 * 
	 * @return 对应ID，-99为无对应项，匹配不上
	 */
	public abstract int judge(String word, int predictedCatId);
	
}