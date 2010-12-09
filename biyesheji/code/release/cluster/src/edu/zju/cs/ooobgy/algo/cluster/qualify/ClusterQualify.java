package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.Map;
import java.util.Set;

import edu.zju.cs.ooobgy.graph.WeakComponentGraph;
import edu.zju.cs.ooobgy.graph.util.Pair;

public interface ClusterQualify<V, E> {
	public double qualify(WeakComponentGraph<V, E> graph, Map<E, Pair<V>> originEdges);
	public double qualify(Set<Set<V>> clusters, Map<E, Pair<V>> originEdges);
}
