package com.alimama.loganalyzer.jobs.mrs.algo.query.judger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Brand;
import com.alimama.loganalyzer.jobs.mrs.util.GlobalInfo;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * Æ·ÅÆÅÐ¶ÏÆ÷
 * 
 * @author Ã÷·ç
 * @date 2010-04-29
 */
public class BrandJudger extends DataJudger {
	private Map<String, Brand> brands;

	public BrandJudger(Map _brands) {
		super();
		this.brands = _brands;
	}

	public int judge(String word, int predictCatId) {
		//GlobalInfo.debugMessage("1.Word:[" + word + "]CatId:" + predictCatId);
		
		Brand brand = brands.get(word);		
		if (brand == null) {
			//GlobalInfo.debugMessage("2.Brands:[" + brands+ "]");			
			return KQConst.NOT_EXIST_INT;
		}

		if (predictCatId <= 0) {
			if (brand.brandIds.size() > 0) {				
				return brand.brandIds.get(0);
			} else {
				return KQConst.NOT_EXIST_INT;
			}
		}

		for (int i = 0, j = brand.catIds.size(); i < j; i++) {
			int catId = brand.catIds.get(i);
			if (isChild(catId, predictCatId)) {
				//GlobalInfo.debugMessage("3.Brand Matched:[" + brand+ "]");
				return brand.brandIds.get(i);
			}
		}
		return KQConst.NOT_EXIST_INT;
	}
}
