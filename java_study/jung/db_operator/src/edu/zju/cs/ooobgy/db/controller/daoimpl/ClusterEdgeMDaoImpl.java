package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.ClusterEdgeMDao;
import edu.zju.cs.ooobgy.db.entity.ClusterEdgeM;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-2-22
 * @Email frogcherry@gmail.com
 */
public class ClusterEdgeMDaoImpl extends TDataDaoImpl<ClusterEdgeM, Integer> implements
		ClusterEdgeMDao {

	@Override
	public List<ClusterEdgeM> findAll() {
		return findAll(ClusterEdgeM.class, "");
	}

	@Override
	public int findCount() {
		return findCount(ClusterEdgeM.class);
	}

	@Override
	public ClusterEdgeM findWithId(Integer id) {
		return findWithID(id, ClusterEdgeM.class);
	}

}
