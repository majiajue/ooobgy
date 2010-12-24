package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.algo.util.Pair;
import edu.zju.cs.ooobgy.graph.WeakComponentGraph;

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
	 * 对于Modularity Qualify评价算法，必须还要已知原始的图结构
	 * @param originEdges
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
		Matrix moduleMatrix = genQMatrix(clusters);
		//2.计算quality值
		double quality = 0;
		for (int i = 0; i < clusters.size(); i++) {
			quality += moduleMatrix.element(i, i);
			double ai = moduleMatrix.sumRow(i);
			quality -= ai*ai;
		}
		
		return quality;
	}

	/**
	 * 生成判定矩阵
	 * @param clusters
	 * @return
	 */
	private Matrix genQMatrix(Set<Set<V>> clusters) {
		int clusterCount = clusters.size();
		Matrix moduleMatrix = new Matrix(clusterCount, clusterCount);
		Map<V, Integer> vertexClusterMap = mapVertices(clusters);
		
		double weightSum = 0;
		for (E e : originEdges.keySet()){
			weightSum += edge_weights.transform(e).doubleValue();
		}
		
		for (E e : originEdges.keySet()) {
			Pair<V> vertices = originEdges.get(e);
			Integer v1Cid = vertexClusterMap.get(vertices.getFirst());
			Integer v2Cid = vertexClusterMap.get(vertices.getSecond());
			double addi = edge_weights.transform(e).doubleValue() / weightSum;
			moduleMatrix.addElement(v1Cid, v2Cid, addi);
			moduleMatrix.addElement(v2Cid, v1Cid, addi);
		}
		
		return moduleMatrix;
	}

	/**
	 * 建立点与cluster的配对
	 * @param clusters
	 * @return
	 */
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
