package edu.zju.cs.ooobgy.graph;

import org.apache.commons.collections15.Transformer;

/**
 * 加权图接口
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 * @see Graph
 */
public interface WeightedGraph<V, E> extends Graph<V, E> {
	public Transformer<E, ? extends Number> getEdgeWeights();
}
