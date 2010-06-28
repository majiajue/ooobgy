package com.alimama.loganalyzer.jobs.mrs.algo.query.po;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类目
 * 
 * @author 明风
 * @date 2010-04-29
 *
 */
public class Cat {
	public String catName;
	public List<Integer> catIds = new ArrayList<Integer>(5);

	public Cat(String catName2, Integer catId2) {
		this.catName = catName2;
		add(catId2);
	}
	public void add(Integer catId2) {
		this.catIds.add(catId2);
	}	

	public String toString(){
		return this.catName + "\nCatIds:" + catIds;
	}
}
