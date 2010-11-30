package edu.zju.cs.ooobgy.graph;

import java.util.Collection;

import edu.zju.cs.ooobgy.graph.util.Pair;


/**
 * 基本的graph定义的接口，规定实现了表达graph结构的类必须实现的方法
 * @author frogcherry 周晓龙
 * @created 2010-11-29
 */
public interface Graph<V,E>
{
	/**
	 * 返回图中所有顶点
	 * @return
	 */
	public Collection<? extends V> getVertices(); 
	
	/**
	 * 返回图中所有边
	 * @return
	 */
    public Collection<E> getEdges();
    
    /**
     * 给一个<code>vertex</code>，返回对于该<code>vertex</code>的InEdges
     * @param vertex
     * @return
     */
	public Collection<E> getInEdges(V vertex);
    
    /**
     * 给一个<code>vertex</code>，返回对于该<code>vertex</code>的OutEdges
     * @param vertex
     * @return
     */
	public Collection<E> getOutEdges(V vertex);

    /**
     * 返回一个<code>vertex</code>的先导<code>vertex</code>，
     * 即该<code>vertex</code>的入边所连接的另一个<code>vertex</code>
     * @param vertex
     * @return
     */
	public Collection<V> getPredecessors(V vertex);
    
    /**
     * 返回一个<code>vertex</code>的后续<code>vertex</code>，
     * 即该<code>vertex</code>的出边所连接的另一个<code>vertex</code>
     * @param vertex
     * @return
     */
	public Collection<V> getSuccessors(V vertex);
    
    /**
     * 返回一个<code>vertex</code>的入度inDegree，
     * 即该<code>vertex</code>的入边条数
     * @param vertex
     * @return
     */
	public int inDegree(V vertex);
    
   
	/**
	 * 返回一个<code>vertex</code>的出度outDegree，
     * 即该<code>vertex</code>的出边条数
	 * @param vertex
	 * @return
	 */
	public int outDegree(V vertex);
    
    /**
     * 如果v1是v2的先导Vertex则返回true
     * @param v1
     * @param v2
     * @return
     */
	public boolean isPredecessor(V v1, V v2);
    
    /**
     * 如果v1是v2的后续Vertex则返回true
     * @param v1
     * @param v2
     * @return
     */
	public boolean isSuccessor(V v1, V v2);
    
    /**
     * 返回一个<code>Edge</code>的源source
     * @param directed_edge
     * @return
     */
	public V getSource(E directed_edge);

    /**
     * 返回一个<code>Edge</code>的目的地destination
     * @param directed_edge
     * @return
     */
    public V getDest(E directed_edge);
    
    /**
     * 如果vertex是edge的source则返回true
     * @param vertex
     * @param edge
     * @return
     */
    public boolean isSource(V vertex, E edge);
    
    /**
     * 如果vertex是edge的目的地destination则返回true
     * @param vertex
     * @param edge
     * @return
     */
    public boolean isDest(V vertex, E edge);
    
    /**
     * 增加一个vertex
     * @param vertex
     * @return
     */
    public boolean addVertex(V vertex);

    /**
     * 增加一条边e,v1指向v2的边，v1,v2中的任何一个不存在会自动add进该vertex
     * @param e
     * @param v1
     * @param v2
     * @return
     */
    public boolean addEdge(E e, V v1, V v2);
    
    /**
     * 去掉一个顶点
     * @param vertex
     * @return
     */
    public boolean removeVertex(V vertex);

    /**
     * 去掉一条边
     * @param edge
     * @return
     */
    public boolean removeEdge(E edge);
    
    /**
     * 返回一个边的两个端点，以<code>Pair</code>方式返回[source,dest]
     * @param edge
     * @return
     */
    public Pair<V> getEndpoints(E edge);
    
    /**
     * 返回对于一条边edge,vertex另一端的顶点
     * @param vertex
     * @param edge
     * @return
     */
    public V getOpposite(V vertex, E edge);

    /**
     * 返回一个顶点的所有相邻顶点
     * @param vertex
     * @return
     */
    public Collection<V> getNeighbors(V vertex);
}
