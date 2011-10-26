package edu.zju.cs.ooobgy.db.controller.dao;

import java.util.List;

import edu.zju.cs.ooobgy.db.entity.SliceClusterCache;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public interface SliceClusterCacheDao extends
		TDataDao<SliceClusterCache, Integer> {
	public List<SliceClusterCache> findAllInTimeRange(String time_range);
	/**
	 * 删除一个时间切片
	 * @param time_range
	 */
	public void removeSlice(String time_range);
}
