package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Brand;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 品牌库加载器

        	品牌库中，每行数据的样本： 162116^A20000^A品牌^A11596^A三菱钻石珑管U2
 			
 			第1位：类目id
 			第4位：品牌id
 			第5位：品牌名
 			
 			以类目Id(CatId为一维)，品牌Id(BrandId)为二维，品牌词序列为值
				{catId1, {brandId1, [brandName1,brandName2]}, {brandId2, [brandName3,brandName4]}...}
				
				
  
 * @author 明风
 * @date 2010-04-27
  */

public class BrandLoader extends DataLoader{
//	private static final int CAT_SIZE = 4000;
//	private static final int BRAND_PER_CAT_SIZE = 40;

	private static final int BRAND_SIZE = 100000;
	
	
	/**
	 * Map初始化大小的考虑
	 * 
	 * 根据20100428的数据，brand文件的行数大概是72,246
	 * 加载到Map中后， 第一维的大小为2537，第二维基本上是71978/2537=28，加上Java中，HashMap默认的容量因子，0.75
	 * 一维的大小为 2537/ 0.75 = 3382 -> 4000,
	 * 二维的大小为28 /0.75 = 37 -> 40
	 * 
	 * 	Brand CatId size: 2537
 	 *	Brand size: 71978
 	 *
 	 *	后来考虑了一下，改进数据结构，直接用BrandName为Key，方便查找，那么大小改为1维，直接是72,246/0.75~100,000
	 */
	private Map<String, Brand> brands = new HashMap<String, Brand>(BRAND_SIZE);	
	public int duplicated;
	
	@Override
	public void loadFromPath(String dataPath) {
		BufferedReader reader = null;
		String line = null;
		int dataLineNum = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath), KQConst.UTF_8_ENCODING));
			while ((line = reader.readLine())!= null) {
				try {
					String[] items = line.split(KQConst.SPLIT);
					if (items.length != 5) {
						continue;
					}

					String catId = items[0];
					String brandId = items[3];
					String brandName = items[4];

					if (brandName.indexOf(KQConst.SLASH_SPLIT) > 0) {					
						String[] subNames = brandName.split(KQConst.SLASH_SPLIT);
						for (String subName : subNames) {
							addBrand(subName, Integer.valueOf(catId), Integer.valueOf(brandId));
						}
					} else {
						addBrand(brandName, Integer.valueOf(catId), Integer.valueOf(brandId));
					}
					
					dataLineNum++;
				} catch (Throwable e) {
					System.err.println("品牌库解析过程出错：" + line);
					e.printStackTrace();
				}
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.printf("From Brand file [%s], load %d line data. \n", dataPath, dataLineNum);
	}

	/**
	 * 添加品牌
	 * 
	 * @param brandName 品牌名称
	 * @param brandCatId 品牌所属类目ID
	 * @param brandId 品牌ID
	 */
	private void addBrand(String brandName, Integer catId, Integer brandId) {		
		if (super.normalizer != null){
			brandName = super.normalizer.normalize(brandName);
		}
		Brand brand = brands.get(brandName);
		if (brand == null) {
			brand = new Brand(brandName, catId, brandId);
			brands.put(brandName, brand);
		}else{
			brand.add(catId, brandId);
			duplicated++;
		}
	}

	@Override
	public void releaseData() {
		this.brands.clear();
	}


	@Override
	public Map getData() {
		return this.brands;
	}

}
