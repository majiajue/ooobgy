package com.alimama.loganalyzer.jobs.mrs.algo.query.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Æ·ÅÆ
 * 
 * @author Ã÷·ç
 * @date 2010-04-29
 *
 */
public class Brand {
	public String brandName;
	public List<Integer> brandIds = new ArrayList<Integer>(5);
	public List<Integer> catIds = new ArrayList<Integer>(5);

	public Brand(String brandName2, Integer catId2, Integer brandId2) {
		this.brandName = brandName2;
		add(catId2, brandId2);
	}

	public void add(Integer catId, Integer brandId) {
		this.catIds.add(catId);
		this.brandIds.add(brandId);
	}

	public String toString(){
		return this.brandName + "\nBrandIds:" + brandIds + "\nCatIds:" + catIds;
	}
}
