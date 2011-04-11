package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.algo.util.Pair;
import edu.zju.cs.ooobgy.graph.ClusterGraph;
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
	private ClusterGraph<V, E> originGraph;

	/**
	 * 传入原始图结构的构造可以用于Ulrik的推论公式
	 */
	public ModularityQualify(ClusterGraph<V, E> oGraph) {
		this.originGraph = oGraph;
		this.edge_weights = oGraph.getEdgeWeights();
		this.originEdges = oGraph.getEdgeMap();
	}
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
	@SuppressWarnings({  "unchecked" })
	public ModularityQualify(Map<E, Pair<V>> originEdges) {
		super();
		this.originEdges = originEdges;
		this.edge_weights = new ConstantTransformer(1);
	}
	
	/**
	 * 使用Ulrik的推论公式来计算,我们的公式扩展为考虑边的权重
	 * @param clusters
	 * @return
	 */
	public double ulrikWeightedQualify(Set<Set<V>> clusters){
		double m = 0.0;//m是边的权重和
		int k = clusters.size();//k是团伙的总个数
		List<Double> M = new ArrayList<Double>(k);//各团内部边数和(权重)
		List<Double> D = new ArrayList<Double>(k);//各团内部度数和(度数边权重)
		
		Set<E> edges = originGraph.getEdges();
		for (E e : edges) {
			m += edge_weights.transform(e).doubleValue();
		}
		
		int i = 0; 
		for (Set<V> cluster : clusters) {
			Double mi = 0.0;//边
			Double di = 0.0;//度数
			for (V v : cluster) {
				Set<E> ioEdges = originGraph.getEdges(v);
				for (E e : ioEdges) {
					double eWeight = edge_weights.transform(e).doubleValue();
					di += eWeight;
					if (cluster.contains(originGraph.getOpposite(v, e))) {
						mi += eWeight;
					}
				}
			}
			M.set(i, mi / 2);
			D.set(i, di);
		}
		
		double MQ = 0.0;
		for (i = 0; i < k; i++) {
			double dMQ = M.get(i) / m - D.get(i) / (4 * m * m);
			MQ += dMQ;
		}
		
		return MQ;
	}

	@Override
	public double qualify(WeakComponentGraph<V, E> graph) {
		return qualify(graph.getComponents());
	}

	/**
	 * 现在选用ulrik的推论公式来计算
	 */
	@Override
	public double qualify(Set<Set<V>> clusters) {
		return ulrikWeightedQualify(clusters);
	}
	
	public double matrixQualify(Set<Set<V>> clusters){
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
