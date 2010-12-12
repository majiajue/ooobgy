package edu.zju.cs.ooobgy.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.cluster.WeakComponentClusterer;
import edu.zju.cs.ooobgy.graph.util.Pair;
import edu.zju.cs.ooobgy.graph.weight.EdgeWeight;

/**
 * 在聚类算法中常用的一个Graph描述的实现，
 * 这个实现包含图的基本操作，默认地，这个图是：无向的、有权的.</br>
 * <li>无向是已经实现的特性，无法继续扩充为有向</li>
 * <li>权的实现依赖于V,E的具体实现</li>
 * 该类也实现了存储Weak Components,允许通过{@link WeakComponentGraph#getComponents()}快速得到弱连通分量
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 * @Email frogcherry@gmail.com
 * @see WeakComponentClusterer
 * @see Graph
 * @see WeakComponentGraph
 */
public class ClusterGraph<V, E> extends WeakComponentGraph<V, E> implements WeightedGraph<V, E>, UndirectedGraph<V, E>{
	private Map<V, Set<E>> vertices;//vertex为key,edges为value
	private Map<E, Pair<V>> edges;//edge为key,vertex为value
	private Transformer<E, ? extends Number> edge_weights;//边的权重

	/**
	 * 使用已知的图的表示构件进行构造，不保证依赖的完备性，请谨慎使用，使用时许哟确保传入的构件配套
	 * @param vertices
	 * @param edges
	 * @param edge_weights
	 */
	public ClusterGraph(Map<V, Set<E>> vertices, Map<E, Pair<V>> edges, 
			Transformer<E, ? extends Number> edge_weights) {
		super();
		this.vertices = vertices;
		this.edges = edges;
		this.edge_weights = edge_weights;
	}

	/**
	 * 全新空构造
	 */
	public ClusterGraph() {
		super();
		this.edges = new HashMap<E, Pair<V>>();
		this.vertices = new HashMap<V, Set<E>>();
		this.edge_weights = new EdgeWeight<E, Double>(this.getEdges());
	}

	@Override
	public Set<? extends V> getVertices() {
		return this.vertices.keySet();
	}

	@Override
	public Set<E> getEdges() {
		return this.edges.keySet();
	}

	public Set<E> getEdges(V vertex) {
		return this.vertices.get(vertex);
	}
	
	@Override
	public Set<E> getInEdges(V vertex) {
		return getEdges(vertex);
	}

	@Override
	public Set<E> getOutEdges(V vertex) {
		return getEdges(vertex);
	}

	@Override
	public Set<V> getPredecessors(V vertex) {
		return getNeighbors(vertex);
	}

	@Override
	public Set<V> getSuccessors(V vertex) {
		return getNeighbors(vertex);
	}

	/**
	 * 由于图无向，所以对于edge的source，返回第1个，谨慎使用
	 */
	@Override
	@Deprecated
	public V getSource(E directed_edge) {
		return getEndpoints(directed_edge).getFirst();
	}

	/**
	 * 由于图无向，所以对于edge的dest，返回第2个，谨慎使用
	 */
	@Override
	@Deprecated
	public V getDest(E directed_edge) {
		return getEndpoints(directed_edge).getSecond();
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		return this.edges.get(edge);
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		return getEndpoints(edge).getAnother(vertex);
	}

	@Override
	public Set<V> getNeighbors(V vertex) {
		Set<V> result = new HashSet<V>();
		Set<E> edges = getEdges(vertex);
		for (E e : edges) {
			result.add(getOpposite(vertex, e));
		}
		return result;
	}

	@Override
	protected boolean addVertex2WC(V vertex) {
		if (containsVertex(vertex)) {
			return false;
		}else{
			this.vertices.put(vertex, new HashSet<E>());
			return true;
		}
	}

	@Override
	protected boolean addEdge2WC(E e, V v1, V v2) {
		if (containsEdge(e)) {
			return false;
		} else {
			Pair<V> endPoints = new Pair<V>(v1, v2);
			addVertex2WC(v1);
			addVertex2WC(v2);
			this.edges.put(e, endPoints);
			this.vertices.get(v1).add(e);
			this.vertices.get(v2).add(e);
			return true;
		}
	}

	@Override
	protected boolean removeVertex2WC(V vertex) {
		if (containsVertex(vertex)) {
			Set<E> v_edges = getEdges(vertex);
			for (E e : v_edges) {
				removeEdge2WC(e);
			}
			this.vertices.remove(vertex);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean removeEdge2WC(E edge) {
		if (containsEdge(edge)) {
			Pair<V> endPoints = getEndpoints(edge);
			for (V v : endPoints) {
				this.vertices.get(v).remove(edge);
			}
			this.edges.remove(edge);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Transformer<E, ? extends Number> getEdgeWeights() {
		return getEdge_weights();
	}

	public void setEdge_weights(Transformer<E, ? extends Number> edge_weights) {
		this.edge_weights = edge_weights;
	}

	public Transformer<E, ? extends Number> getEdge_weights() {
		return edge_weights;
	}
}
