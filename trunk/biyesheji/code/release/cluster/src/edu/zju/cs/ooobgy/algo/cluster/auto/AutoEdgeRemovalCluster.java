package edu.zju.cs.ooobgy.algo.cluster.auto;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.graph.Graph;

/**
 * 自动切边算法接口
 * @author frogcherry 周晓龙
 * @created 2010-12-14
 * @Email frogcherry@gmail.com
 */
public interface AutoEdgeRemovalCluster<V,E> extends Transformer<Graph<V,E>,Set<Set<V>>>, AutoCluster<V, E>{
	public List<E> getRemovedEdges();
}
