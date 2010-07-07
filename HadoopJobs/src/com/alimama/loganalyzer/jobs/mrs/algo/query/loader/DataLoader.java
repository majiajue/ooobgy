package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import java.util.Map;

import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;

/**
 * 数据加载类
 * 
 * @author 明风
 * @date 2010-04-26
 */
public abstract class DataLoader {
	Normalizer normalizer;
	
	public void setNormalizer(Normalizer _normalizer) {
		this.normalizer = _normalizer;
	}
	
	public abstract void loadFromPath(String dataPath);
	public abstract Map getData();
	public abstract void releaseData();	
}
