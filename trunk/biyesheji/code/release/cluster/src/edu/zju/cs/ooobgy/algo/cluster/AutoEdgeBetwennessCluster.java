package edu.zju.cs.ooobgy.algo.cluster;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.KeyValue;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.cluster.qualify.ClusterQualify;
import edu.zju.cs.ooobgy.algo.cluster.qualify.ModularityQualify;
import edu.zju.cs.ooobgy.algo.util.SimpleKeyValue;
import edu.zju.cs.ooobgy.graph.ClusterGraph;
import edu.zju.cs.ooobgy.graph.Graph;
import edu.zju.cs.ooobgy.graph.util.Pair;

public class AutoEdgeBetwennessCluster<V, E> implements AutoEdgeRemovalCluster<V, E> {
	private Transformer<E, ? extends Number> edge_weights;
	/** 跟踪聚类过程中的量值轨迹 */
	private List<Double> qualityTrack;
	/** 跟踪聚类过程中的切边轨迹 */
	private List<E> edgeTrack;
	/** 记录最优的聚类<迭代次数Index, quality量值> */
	private KeyValue<Integer, Double> bestCluster;
	/**
	 *  是否全切边 
	 *  true: 切边到全图所有点变成孤立点，在整个过程中跟踪最优量值(MQ算法中为最高峰值)
	 *  false: 切边到找到第一个较优值位置(MQ算法中为第一个峰值)
	 */
	private boolean clusterComplete;
	
	/**
	 * 对于有权图的构造
	 * @param edge_weights
	 */
	public AutoEdgeBetwennessCluster(
			Transformer<E, ? extends Number> edge_weights) {
		super();
		this.edge_weights = edge_weights;
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.bestCluster = new SimpleKeyValue<Integer, Double>(0, 0d);
		this.clusterComplete = true;
	}

	/**
	 * 无权图的处理
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AutoEdgeBetwennessCluster() {
		super();
		this.edge_weights = new ConstantTransformer(1);
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.bestCluster = new SimpleKeyValue<Integer, Double>(0, 0d);
		this.clusterComplete = true;
	}
	
	public AutoEdgeBetwennessCluster(
			Transformer<E, ? extends Number> edge_weights,
			boolean clusterComplete) {
		super();
		this.edge_weights = edge_weights;
		this.clusterComplete = clusterComplete;
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.bestCluster = new SimpleKeyValue<Integer, Double>(0, 0d);
	}

	@Override
	public Set<Set<V>> transform(Graph<V, E> graph1) {
		if (graph1 instanceof ClusterGraph) {
			throw new IllegalArgumentException("Trying cluster a graph could NOT cluster!");
		}
		
		ClusterGraph<V, E> graph = (ClusterGraph<V, E>)graph1;
		//必须要克隆原始边集合，否则原始边信息会在切边过程中丢失
		Map<E, Pair<V>> originEdges = new HashMap<E, Pair<V>>(graph.getEdgeMap());
		//使用传入的权值和原始边集合构造度量器ModularityQualify
		ClusterQualify<V, E> clusterQualify = new ModularityQualify<V, E>(originEdges, edge_weights);
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClusterComplete() {
		return clusterComplete;
	}

	public void setClusterComplete(boolean clusterComplete) {
		this.clusterComplete = clusterComplete;
	}
}
