package edu.zju.cs.ooobgy.algo.cluster;

import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.graph.Graph;

public interface AutoEdgeRemovalCluster<V,E> extends Transformer<Graph<V,E>,Set<Set<V>>>{

}
