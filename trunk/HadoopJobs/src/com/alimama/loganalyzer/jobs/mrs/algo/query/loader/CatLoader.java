package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Cat;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 类目库加载器
 * 
 * 样例数据 ：450004994音乐类期刊订阅33书籍/杂志/
 * 报纸50001378报刊订阅50004979期刊订阅50004994音乐类期刊订阅\N\N01
 * 
 * 第一位： 类目级别 第二位： 类目ID 第三位： 类目名词
 * 
 * 后面： 父类目ID，父类目名称
 * 
 * @author 明风
 * @date 2010-04-29
 * 
 */
public class CatLoader extends DataLoader {
	/**
	 * 类目库大小为11419，根据这个确定初始大小为11419/0.75 = 15,225~=16000
	 */
	private static final int CAT_SIZE = 16000;

	/**
	 * 类目名称和ID的对应关系映射
	 */
	private Map<String, Cat> cats = new HashMap<String, Cat>(CAT_SIZE);
	/**
	 * 类目ID和类目父ID的映射
	 */
	private Map<Integer, Set<Integer>> catIdTree = new HashMap<Integer, Set<Integer>>(CAT_SIZE);
	
	@Override
	public void loadFromPath(String dataPath) {
		BufferedReader reader = null;
		String line = null;
		int dataLineNum = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(dataPath), KQConst.UTF_8_ENCODING));
			while ((line = reader.readLine()) != null) {
				dataLineNum++;
				String[] items = line.split(KQConst.SPLIT);
				String levelStr = items[0];
				String idStr = items[1];
				String name = items[2];

				int level = Integer.parseInt(levelStr);
				Integer catId = Integer.valueOf(idStr);
				Set<Integer> parentCatIds = new HashSet<Integer>(10);
				// 非一级类目，后面是它的层次结构
				if (level > 1) {
					for (int k = 3; k < 2 * level + 1; k += 2) {
						parentCatIds.add(Integer.valueOf(items[k]));
					}
				}

				if (!name.isEmpty()) {
					if (name.indexOf("/") > 0) {
						String[] subNames = name.split("/");
						for (String subName : subNames) {
							addCat(subName, catId);
						}
					} else {
						addCat(name, catId);
					}
				}
				
				catIdTree.put(catId, parentCatIds);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		System.out.printf("From Cats file [%s], load %d line data. \n", dataPath, dataLineNum);
		
	}

	/**
	 * 添加类目
	 * 
	 * @param catName
	 * @param catId
	 * @param childCatIds
	 */
	private void addCat(String catName, Integer catId) {
		if (super.normalizer != null){
			catName = super.normalizer.normalize(catName);
		}		
		Cat cat = cats.get(catName);
		if (cat == null) {
			cat = new Cat(catName, catId);
			cats.put(catName, cat);
		} else {
			cat.add(catId);
		}
	}

	@Override
	public void releaseData() {
		this.cats.clear();
	}

	@Override
	public Map getData() {
		return this.cats;
	}
	
	public Map getCatIdTree(){
		return this.catIdTree;
	}

}
