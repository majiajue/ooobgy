package edu.zju.cs.ooobgy.graph;

import java.util.Set;

import edu.zju.cs.ooobgy.algo.cluster.WeakComponentClusterer;

/**
 * 实现了保存弱连通分量WeakComponent的Graph描述
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 * @see Graph
 */
public abstract class WeakComponentGraph<V, E> extends AbstractGraph<V, E>{
	private boolean isWeakCompClustered = false;
	private Set<Set<V>> components;
	
	/**
	 * 获得弱连通分量WeakComponent
	 * @return
	 */
	public Set<Set<V>> getComponents(){
		if (!isWeakCompClustered) {
			WeakComponentClusterer<V, E> wcSeacher = new WeakComponentClusterer<V, E>();
			components = wcSeacher.transform(this);
			isWeakCompClustered = true;
		}
		
		return components;
	}
	
	/**
	 * 必须实现在wc_graph中的增加顶点操作
	 * @param vertex
	 * @return
	 */
	protected abstract boolean addVertex2WC(V vertex);
	/**
	 * 必须实现在wc_graph中的增加边操作
	 * @param Edge,Vertex1,Vertex2
	 * @return
	 */
	protected abstract boolean addEdge2WC(E e, V v1, V v2);
	/**
	 * 必须实现在wc_graph中的删除顶点操作
	 * @param vertex
	 * @return
	 */
	protected abstract boolean removeVertex2WC(V vertex);
	/**
	 * 必须实现在wc_graph中的删除边操作
	 * @param Edge
	 * @return
	 */
	protected abstract boolean removeEdge2WC(E edge);

	@Override
	public final boolean addVertex(V vertex) {
		disableComponent();
		return addVertex2WC(vertex);
	}

	@Override
	public final boolean addEdge(E e, V v1, V v2) {
		disableComponent();
		return addEdge(e, v1, v2);
	}

	@Override
	public final boolean removeVertex(V vertex) {
		disableComponent();
		return removeVertex2WC(vertex);
	}

	@Override
	public final boolean removeEdge(E edge) {
		disableComponent();
		return removeEdge2WC(edge);
	}
	
	protected final void disableComponent(){
		this.isWeakCompClustered = false;
	}
}
