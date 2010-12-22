package edu.zju.cs.ooobgy.algo.dynamic_na.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.zju.cs.ooobgy.graph.ClusterGraph;

/**
 * 一个(时间切片)的图团伙情况
 * @author frogcherry 周晓龙
 * @created 2010-12-22
 * @Email frogcherry@gmail.com
 */
public class ClusterSlice<V, E> {
	private String sliceId;//切片标识id，例如201012
	private ClusterGraph<V, E> graph;//这个切片对应的图
	private Map<String, Set<V>> clusters;//团伙情况，Key是团伙id，value是当前切片的团伙vertex组成
	private List<E> removedEdges;//切去的边
	
	/**
	 * 全构造
	 * @param sliceId
	 * @param graph
	 * @param clusters
	 * @param removedEdges
	 */
	public ClusterSlice(String sliceId, ClusterGraph<V, E> graph,
			Map<String, Set<V>> clusters, List<E> removedEdges) {
		super();
		this.sliceId = sliceId;
		this.graph = graph;
		this.clusters = clusters;
		this.removedEdges = removedEdges;
	}


	/**
	 * 新建一个团伙map的构造，使用该构造后必须要使用putCluster来构造团伙结构
	 * @param sliceId
	 * @param graph
	 * @param removedEdges
	 */
	public ClusterSlice(String sliceId, ClusterGraph<V, E> graph,
			List<E> removedEdges) {
		super();
		this.sliceId = sliceId;
		this.graph = graph;
		this.removedEdges = removedEdges;
		this.clusters = new HashMap<String, Set<V>>();
	}
	
	/**
	 * 更新(存入)一个团伙分团信息
	 */
	public void putCluster(String clusterId, Set<V> cluster){
		clusters.put(clusterId, cluster);
	}


	public Map<String, Set<V>> getClusters() {
		return clusters;
	}


	public void setClusters(Map<String, Set<V>> clusters) {
		this.clusters = clusters;
	}


	public String getSliceId() {
		return sliceId;
	}


	public ClusterGraph<V, E> getGraph() {
		return graph;
	}


	public List<E> getRemovedEdges() {
		return removedEdges;
	}
	
	
}