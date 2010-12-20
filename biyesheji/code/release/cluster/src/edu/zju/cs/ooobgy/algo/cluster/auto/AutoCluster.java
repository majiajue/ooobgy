package edu.zju.cs.ooobgy.algo.cluster.auto;

import java.util.Set;

import edu.zju.cs.ooobgy.graph.Graph;

public interface AutoCluster<V, E> {

	public Set<Set<V>> autoCluster(Graph<V, E> graph);

}