package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.RelationWeightedDao;
import edu.zju.cs.ooobgy.db.entity.RelationWeighted;

public class RelationWeightedDaoImpl extends TDataDaoImpl<RelationWeighted,Integer> implements RelationWeightedDao{

	@Override
	public List<RelationWeighted> findAll() {
		return findAll(RelationWeighted.class, "");
	}

	@Override
	public int findCount() {
		return findCount(RelationWeighted.class);
	}

	@Override
	public RelationWeighted findWithId(Integer id) {
		return findWithID(id, RelationWeighted.class);
	}

}
