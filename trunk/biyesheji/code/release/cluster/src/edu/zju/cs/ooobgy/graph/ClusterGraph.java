package edu.zju.cs.ooobgy.graph;

import java.util.Set;

import edu.zju.cs.ooobgy.algo.cluster.WeakComponentClusterer;
import edu.zju.cs.ooobgy.graph.util.Pair;

/**
 * 在聚类算法中常用的一个Graph描述的实现，
 * 这个实现包含图的基本操作，默认地，这个图是：有向的、有权的.</br>
 * 该类也实现了存储Weak Components,允许通过getComponents()快速得到弱连通分量
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 * @Email frogcherry@gmail.com
 * @see WeakComponentClusterer
 * @see Graph
 * @see WeakComponentGraph
 */
public class ClusterGraph<V, E> extends WeakComponentGraph<V, E> {

	@Override
	public Set<? extends V> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<E> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<E> getInEdges(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<E> getOutEdges(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> getPredecessors(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> getSuccessors(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int inDegree(V vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int outDegree(V vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPredecessor(V v1, V v2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V getSource(E directed_edge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V getDest(E directed_edge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSource(V vertex, E edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<V> getNeighbors(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean addVertex2WC(V vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean addEdge2WC(E e, V v1, V v2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean removeVertex2WC(V vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean removeEdge2WC(E edge) {
		// TODO Auto-generated method stub
		return false;
	}

}
