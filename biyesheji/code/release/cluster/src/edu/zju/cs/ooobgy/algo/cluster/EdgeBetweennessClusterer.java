package edu.zju.cs.ooobgy.algo.cluster;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.scorer.BetweennessCentrality;
import edu.zju.cs.ooobgy.algo.util.Pair;
import edu.zju.cs.ooobgy.graph.Graph;


/**
 * 经典的G&N的betweenness聚类算法，该算法的复杂度为o(kmn)，
 * 算法的复杂度很大程度上依赖于betweenness的计算速度，后者使用了优化过的betweenness计算方式
 * 已支持加权和未加权的图的计算
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @see "Community structure in social and biological networks by Michelle Girvan and Mark Newman"
 */
public class EdgeBetweennessClusterer<V,E> implements Transformer<Graph<V,E>,Set<Set<V>>> {
    private int mNumEdgesToRemove;
    private Map<E, Pair<V>> edges_removed;
    private Transformer<E, ? extends Number> edge_weights;

   /**
    * 无权图的初始化操作
    * @param numEdgesToRemove 想要移去的边的条数
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public EdgeBetweennessClusterer(int numEdgesToRemove) {
        mNumEdgesToRemove = numEdgesToRemove;
        edges_removed = new LinkedHashMap<E, Pair<V>>();
        edge_weights = new ConstantTransformer(1);
    }

    /**
     * 加权图的初始化操作
     * @param mNumEdgesToRemove
     * @param edge_weights
     */
    public EdgeBetweennessClusterer(int mNumEdgesToRemove,
		Transformer<E, ? extends Number> edge_weights) {
    	this.mNumEdgesToRemove = mNumEdgesToRemove;
    	edges_removed = new LinkedHashMap<E, Pair<V>>();
    	this.edge_weights = edge_weights;
    }


	/**
	 * 初始化后对待处理的图进行聚类操作
     * @param graph 待处理的图
     */
    public Set<Set<V>> transform(Graph<V,E> graph) {
                
        if (mNumEdgesToRemove < 0 || mNumEdgesToRemove > graph.getEdgeCount()) {
            throw new IllegalArgumentException("Invalid number of edges passed in.");
        }
        
        edges_removed.clear();

        for (int k=0;k<mNumEdgesToRemove;k++) {
            BetweennessCentrality<V,E> bc = new BetweennessCentrality<V,E>(graph,edge_weights);
            E to_remove = null;
            double score = 0;
            for (E e : graph.getEdges())
                if (bc.getEdgeScore(e) > score)
                {
                    to_remove = e;
                    score = bc.getEdgeScore(e);
                }
            edges_removed.put(to_remove, graph.getEndpoints(to_remove));
            graph.removeEdge(to_remove);
        }

        WeakComponentClusterer<V,E> wcSearch = new WeakComponentClusterer<V,E>();
        Set<Set<V>> clusterSet = wcSearch.transform(graph);

        //恢复图
        for (Map.Entry<E, Pair<V>> entry : edges_removed.entrySet())
        {
            Pair<V> endpoints = entry.getValue();
            graph.addEdge(entry.getKey(), endpoints.getFirst(), endpoints.getSecond());
        }
        return clusterSet;
    }

    /**
     * 调用前记得先用{@link EdgeBetweennessClusterer#transform(Graph)}计算
     * @return List 被移去的边
     */
    public List<E> getEdgesRemoved() 
    {
        return new ArrayList<E>(edges_removed.keySet());
    }

    /**
     * 调用前记得先用{@link EdgeBetweennessClusterer#transform(Graph)}计算
     * @return Map 被移去的边
     */
	public Map<E, Pair<V>> getEdges_removed() {
		return edges_removed;
	}
}
