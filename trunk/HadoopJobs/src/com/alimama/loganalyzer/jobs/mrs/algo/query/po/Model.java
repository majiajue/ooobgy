package com.alimama.loganalyzer.jobs.mrs.algo.query.po;

import java.util.ArrayList;
import java.util.List;

/**
 * ÐÍºÅ
 * 
 * @author Ã÷·ç
 * @date 2010-04-29
 *
 */
public class Model {
	public String modelName;
	public List<Integer> modelIds = new ArrayList<Integer>(5);
	public List<Integer> brandIds = new ArrayList<Integer>(5);
	public List<Integer> catIds = new ArrayList<Integer>(5);

	public Model(String modelName2, Integer modelId2, Integer brandId2,Integer catId2) {
		modelName = modelName2;
		modelIds.add(modelId2);
		brandIds.add(brandId2);
		catIds.add(catId2);
	}
	
	public void add(Integer modelId2, Integer brandId2, Integer catId2) {
		this.modelIds.add(modelId2);
		this.brandIds.add(brandId2);
		this.catIds.add(catId2);		
	}
	
	public String toString(){
		return modelName + "\nModel Ids:" + modelIds + "\nBrand Ids:" + brandIds + "\nCat Ids:" +catIds;
	}
	
}
