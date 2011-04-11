package edu.zju.cs.ooobgy.dt.db;

import java.util.List;


import edu.zju.cs.ooobgy.db.controller.dao.UndirectedRalationDao;
import edu.zju.cs.ooobgy.db.controller.daoimpl.UndirectedRalationDaoImpl;
import edu.zju.cs.ooobgy.db.entity.UndirectedRalation;
import edu.zju.cs.ooobgy.graph.ClusterGraph;

/**
 * 从数据库载入{@link ClusterGraph}
 * 主要关系信息表为{@link UndirectedRalation}
 * 该类载入聚类前的原始数据
 * @author frogcherry 周晓龙
 * @created 2011-2-23
 * @Email frogcherry@gmail.com
 */
public class ClusterGraphDBLoader implements DBLoader<ClusterGraph<String, Integer>>{

	@Override
	public ClusterGraph<String, Integer> load(String time_range) {
		ClusterGraph<String, Integer> graph = new ClusterGraph<String, Integer>();
		UndirectedRalationDao udrDataDao = new UndirectedRalationDaoImpl();
		List<UndirectedRalation> udr_edges = udrDataDao.findAllInTimeRange(time_range);
		EdgeWeightTransformer udr_edge_weights = new EdgeWeightTransformer(this);
		for (UndirectedRalation edge : udr_edges) {
			udr_edge_weights.addUdrWeight(edge.getUdr_id(), edge.getUdr_weight());
			graph.addEdge(edge.getUdr_id(), edge.getPnode_1(), edge.getPnode_2());
		}
		
		graph.setEdge_weights(udr_edge_weights);
		
		return graph;
	}
}
