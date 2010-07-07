package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.w3c.dom.stylesheets.LinkStyle;

import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Model;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;
import com.alimama.loganalyzer.jobs.mrs.util.StringUtils;

/**
 * 型号库加载器
 * 
 * 型号库中的数据：
 * 				50010368^A4536334^A2740262^A型号^A3294690^AP801
 *				500037741047734189按三星型号108328SCH-S259 
 * 
 *				第一位：类目ID 
 * 				第二位：品牌ID
 * 				第五位：型号ID
 * 				第六位：型号名
 * 
 * 
 * @author 明风
 * @date  2010-04-27
 */
public class ModelLoader extends DataLoader{
//	private static final int CAT_SIZE = 9000;
//	private static final int BRAND_SIZE = 9000;
//	private static final int MODEL_PER_BRAND_SIZE = 70;	
	
	private static final int MODEL_SIZE = 330000;
	/**
	 * Map初始化大小的考虑
	 * 
	 * 根据20100428的数据，model文件的行数大概是294,313，加载到Map中后，第一维的大小为6303，第二维基本上是241,030/6303=47，加入HashMap默认的容量因子的考虑，0.75
	 * 一维的大小为 6303 / 0.75 = 8404, 设定为9000
	 * 二维的大小为47 /0.75 = 62，设定为70
	 * 
	 * 	Brand CatId size: 6303
 	 *	Brand size: 241,030
	 */
	private Map<String, Model> models = new HashMap<String, Model>(MODEL_SIZE);

	public int duplicate;
	public int dataLineNum;

	public void loadFromPath(String dataPath) {
		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath), KQConst.UTF_8_ENCODING));
			while ((line = reader.readLine())!= null) {
				try {
					dataLineNum++;
					String[] items = line.split(KQConst.SPLIT);
					String catId = items[0];
					String brandId = items[1];
					String modelId = items[4];
					String modelName = items[5];
					
					if (modelName.indexOf(KQConst.SPACE_SPLIT) > 0) {					
						String[] subNames = modelName.split(KQConst.SPACE_SPLIT);
						for (String subName : subNames) {
							if (StringUtils.isValid(subName)){
								addModel(subName, Integer.valueOf(modelId), Integer.valueOf(brandId), Integer.valueOf(catId));
							}
						}
					} else {
						addModel(modelName, Integer.valueOf(modelId), Integer.valueOf(brandId), Integer.valueOf(catId));
					}					
				} catch (Throwable e) {
					System.err.println("型号库解析过程出错：" + line);
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		System.out.printf("From Model file [%s], load %d line data. \n", dataPath, dataLineNum);
		
	}

	/**
	 * 添加型号
	 */
	private void addModel(String modelName, Integer modelId, Integer brandId, Integer catId) {
		if (super.normalizer != null){
			modelName = super.normalizer.normalize(modelName);
		}		
		if (models.containsKey(modelName)){
			Model model= models.get(modelName);
			duplicate++;
			model.add(modelId, brandId, catId);
		}else{
			Model model = new Model(modelName, modelId, brandId, catId);		
			models.put(modelName, model);
		}
	}

	@Override
	public void releaseData() {
		this.models.clear();		
	}

	@Override
	public Map getData() {
		return this.models;
	}	

}
