package edu.zju.cs.ooobgy.algo.cluster.qualify;

import edu.zju.cs.ooobgy.graph.WeakComponentGraph;

public interface ClusterQualify<V, E> {
	public double qualify(WeakComponentGraph<V, E> graph);
}
