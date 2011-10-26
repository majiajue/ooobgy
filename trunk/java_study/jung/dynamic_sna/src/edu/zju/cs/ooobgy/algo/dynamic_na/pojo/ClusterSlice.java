package edu.zju.cs.ooobgy.algo.dynamic_na.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
	private Map<String, IdCluster<V>> clusters;//团伙情况，Key是团伙id，value是当前切片的团伙vertex组成
	private List<E> removedEdges;//切去的边

	public Set<Set<V>> getClusterSet(){
		Set<Set<V>> cls = new HashSet<Set<V>>();
		for (IdCluster<V> idC : clusters.values()) {
			cls.add(idC.getVertexes());
		}
		
		return cls;
	}
	
	/**
	 * 清空已有的团伙
	 */
	public void clearClusters(){
		clusters.clear();
	}
	
	public String getVertexClusterId(V v){
		for (IdCluster<V> cluster : clusters.values()) {
			if (cluster.containsVertex(v)) {
				return cluster.getId();
			}
		}
		
		return "NULL";
	}
	
	public void setSliceId(String sliceId) {
		this.sliceId = sliceId;
	}


	public void setGraph(ClusterGraph<V, E> graph) {
		this.graph = graph;
	}


	public void setRemovedEdges(List<E> removedEdges) {
		this.removedEdges = removedEdges;
	}


	/**
	 * 全构造
	 * @param sliceId
	 * @param graph
	 * @param clusters
	 * @param removedEdges
	 */
	public ClusterSlice(String sliceId, ClusterGraph<V, E> graph,
			Map<String, IdCluster<V>> clusters, List<E> removedEdges) {
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
	public ClusterSlice(String sliceId, ClusterGraph<V, E> graph) {
		super();
		this.sliceId = sliceId;
		this.graph = graph;
		this.removedEdges = new LinkedList<E>();
		this.clusters = new HashMap<String, IdCluster<V>>();
	}
	
	/**
	 * 更新(存入)一个团伙分团信息
	 */
	public void addCluster(String clusterId, IdCluster<V> cluster){
		clusters.put(clusterId, cluster);
	}


	public Map<String, IdCluster<V>> getClusters() {
		return clusters;
	}


	public void setClusters(Map<String, IdCluster<V>> clusters) {
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

	/**
	 * 根据数据库中的cache重新指派clusterId，如果已经cache了的就不赋新id了
	 */
	public void refineClusterId() {
		//TODO
	}
	
	
}