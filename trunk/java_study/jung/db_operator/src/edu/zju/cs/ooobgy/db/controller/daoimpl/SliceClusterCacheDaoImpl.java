package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.SliceClusterCacheDao;
import edu.zju.cs.ooobgy.db.entity.SliceClusterCache;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class SliceClusterCacheDaoImpl extends TDataDaoImpl<SliceClusterCache, Integer> implements
		SliceClusterCacheDao {

	@Override
	public List<SliceClusterCache> findAll() {
		return findAll(SliceClusterCache.class, "");
	}

	@Override
	public int findCount() {
		return findCount(SliceClusterCache.class);
	}

	@Override
	public SliceClusterCache findWithId(Integer id) {
		return findWithID(id, SliceClusterCache.class);
	}
	
	@Override
	public List<SliceClusterCache> findAllInTimeRange(String time_range){
		return findAll(SliceClusterCache.class, " p where p.time_range='"+ time_range +"'");
	}

	@Override
	public void removeSlice(String time_range) {
		int removeLines = delete(SliceClusterCache.class, " p where p.time_range='"+ time_range +"'");
		System.out.println(removeLines + " rows deleted from SliceClusterCache.");
	}
}
