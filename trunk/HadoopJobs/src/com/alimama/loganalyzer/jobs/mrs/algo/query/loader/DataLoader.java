package com.alimama.loganalyzer.jobs.mrs.algo.query.loader;

import java.util.Map;

/**
 * 数据加载类
 * 
 * @author 明风
 * @date 2010-04-26
 */
public interface DataLoader {
	public void loadFromPath(String dataPath);
	public Map getData();
	public void releaseData();	
}
