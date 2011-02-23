package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.UndirectedRalationDao;
import edu.zju.cs.ooobgy.db.entity.UndirectedRalation;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-2-22
 * @Email frogcherry@gmail.com
 */
public class UndirectedRalationDaoImpl extends TDataDaoImpl<UndirectedRalation, Integer> implements
		UndirectedRalationDao {

	@Override
	public List<UndirectedRalation> findAll() {
		return findAll(UndirectedRalation.class, "");
	}

	@Override
	public int findCount() {
		return findCount(UndirectedRalation.class);
	}

	@Override
	public UndirectedRalation findWithId(Integer id) {
		return findWithID(id, UndirectedRalation.class);
	}
	
	@Override
	public List<UndirectedRalation> findAllInTimeRange(String time_range){
		return findAll(UndirectedRalation.class, " p where p.time_range='"+ time_range +"'");
	}
}
