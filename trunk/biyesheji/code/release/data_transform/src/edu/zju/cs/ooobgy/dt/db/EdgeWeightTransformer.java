package edu.zju.cs.ooobgy.dt.db;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;

/**
 * 保存无向图的边权重的容器实现
 * @author frogcherry 周晓龙
 * @created 2011-2-23
 * @Email frogcherry@gmail.com
 */
class EdgeWeightTransformer implements Transformer<Integer, Integer>{
	/**
	 * 
	 */
	private ClusterGraphDBLoader EdgeWeightTransformer;

	/**
	 * @param clusterGraphDBLoader
	 */
	EdgeWeightTransformer(ClusterGraphDBLoader clusterGraphDBLoader) {
		EdgeWeightTransformer = clusterGraphDBLoader;
	}

	private Map<Integer, Integer> udr_weights = new HashMap<Integer, Integer>();
	
	public void addUdrWeight(Integer udr_id, Integer udr_weight){
		udr_weights.put(udr_id, udr_weight);
	}

	@Override
	public Integer transform(Integer udr_id) {
		return udr_weights.get(udr_id);
	}
	
}