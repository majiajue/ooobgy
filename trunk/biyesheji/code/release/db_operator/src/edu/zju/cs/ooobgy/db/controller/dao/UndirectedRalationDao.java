package edu.zju.cs.ooobgy.db.controller.dao;

import java.util.List;

import edu.zju.cs.ooobgy.db.entity.UndirectedRalation;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-2-22
 * @Email frogcherry@gmail.com
 */
public interface UndirectedRalationDao extends
		TDataDao<UndirectedRalation, Integer> {
	public List<UndirectedRalation> findAllInTimeRange(String time_range);
	
}
