package edu.zju.cs.ooobgy.graph.weight;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

/**
 * 实现边的权重持久化类
 * @author frogcherry 周晓龙
 * @created 2010-12-12
 * @Email frogcherry@gmail.com
 */
public class EdgesWeight<E, W extends Number> implements Transformer<E, W>{
	private Map<E, W> edgeWeights;
	
	public EdgesWeight() {
		this.edgeWeights = new HashMap<E, W>();
	}
	
	public EdgesWeight(Set<E> edges) {
		this.edgeWeights = new HashMap<E, W>(edges.size()*2);
	}

	public EdgesWeight(Map<E, W> edgeWeights) {
		super();
		this.edgeWeights = edgeWeights;
	}
	
	public void addEdgeWeight(E edge, W weight){
		edgeWeights.put(edge, weight);
	}

	@Override
	public W transform(E edge) {
		W weight = edgeWeights.get(edge);
		if (weight == null) {
			throw new IllegalArgumentException("edge " + edge + " is NOT exist.");
		}
		
		return weight;
	}
}
