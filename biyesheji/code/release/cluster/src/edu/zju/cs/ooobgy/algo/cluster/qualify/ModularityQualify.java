package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.math.Matrix;
import edu.zju.cs.ooobgy.graph.WeakComponentGraph;
import edu.zju.cs.ooobgy.graph.util.Pair;

/**
 * Edge Betweenness切边算法的质量度量</br>
 * M. E. J. Newman and M. Girvan的度量算法(2004)的Java实现
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @see "M. E. J. Newman and M. Girvan: Finding and evaluating community structure in networks (2004)"
 */
public class ModularityQualify<V, E> implements ClusterQualify<V, E>{
	private Map<E, Pair<V>> originEdges;
	private Transformer<E, ? extends Number> edge_weights;

	/**
	 * 对于Modularity Qualify评价算法，必须还要已知原始的图结构，可选传入边的权重
	 * @param originEdges
	 * @param edge_weights
	 */
	public ModularityQualify(Map<E, Pair<V>> originEdges,
			Transformer<E, ? extends Number> edge_weights) {
		super();
		this.originEdges = originEdges;
		this.edge_weights = edge_weights;
	}

	/**
	 * 对于Modularity Qualify评价算法，必须还要已知原始的图结构，可选传入边的权重
	 * @param originEdges
	 * @param edge_weights
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModularityQualify(Map<E, Pair<V>> originEdges) {
		super();
		this.originEdges = originEdges;
		this.edge_weights = new ConstantTransformer(1);
	}

	@Override
	public double qualify(WeakComponentGraph<V, E> graph) {
		return qualify(graph.getComponents());
	}

	@Override
	public double qualify(Set<Set<V>> clusters) {
		//1.生成判定矩阵
		int clusterCount = clusters.size();
		Matrix modulMatrix = new Matrix(clusterCount, clusterCount);
		Map<V, Integer> vertexClusterMap = mapVertices(clusters);
		int edgeCount = originEdges.size();
		
		for (E e : originEdges.keySet()) {
			
		}
		// TODO Auto-generated method stub
		return 0;
	}

	private Map<V, Integer> mapVertices(Set<Set<V>> clusters) {
		//50% hash填充率，加速
		Map<V, Integer> vertexClusterMap = new HashMap<V, Integer>(clusters.size()*2);
		Integer cid = 0;
		for (Set<V> cluster : clusters) {
			for (V v : cluster) {
				vertexClusterMap.put(v, cid);
			}
			cid++;
		}
		
		return vertexClusterMap;
	}

}
