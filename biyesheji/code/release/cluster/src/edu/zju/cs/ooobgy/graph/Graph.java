package edu.zju.cs.ooobgy.graph;

import java.util.Collection;

import edu.zju.cs.ooobgy.graph.util.EdgeType;
import edu.zju.cs.ooobgy.graph.util.Pair;


/**
 * 基本的graph定义的接口，规定实现了表达graph结构的类必须实现的方法
 * @author frogcherry 周晓龙
 * @created 2010-11-29
 */
public interface Graph<V,E>
{
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
     * Returns the number of predecessors that <code>vertex</code> has in this graph.
     * Equivalent to <code>vertex.getPredecessors().size()</code>.
     * @param vertex the vertex whose predecessor count is to be returned
     * @return  the number of predecessors that <code>vertex</code> has in this graph
     */
	public int getPredecessorCount(V vertex);
    
    /**
     * Returns the number of successors that <code>vertex</code> has in this graph.
     * Equivalent to <code>vertex.getSuccessors().size()</code>.
     * @param vertex the vertex whose successor count is to be returned
     * @return  the number of successors that <code>vertex</code> has in this graph
     */
	public int getSuccessorCount(V vertex);
    
    /**
     * If <code>directed_edge</code> is a directed edge in this graph, returns the source; 
     * otherwise returns <code>null</code>. 
     * The source of a directed edge <code>d</code> is defined to be the vertex for which  
     * <code>d</code> is an outgoing edge.
     * <code>directed_edge</code> is guaranteed to be a directed edge if 
     * its <code>EdgeType</code> is <code>DIRECTED</code>. 
     * @param directed_edge
     * @return  the source of <code>directed_edge</code> if it is a directed edge in this graph, or <code>null</code> otherwise
     */
	public V getSource(E directed_edge);

    /**
     * If <code>directed_edge</code> is a directed edge in this graph, returns the destination; 
     * otherwise returns <code>null</code>. 
     * The destination of a directed edge <code>d</code> is defined to be the vertex 
     * incident to <code>d</code> for which  
     * <code>d</code> is an incoming edge.
     * <code>directed_edge</code> is guaranteed to be a directed edge if 
     * its <code>EdgeType</code> is <code>DIRECTED</code>. 
     * @param directed_edge
     * @return  the destination of <code>directed_edge</code> if it is a directed edge in this graph, or <code>null</code> otherwise
     */
    public V getDest(E directed_edge);
    
    /**
     * Returns <code>true</code> if <code>vertex</code> is the source of <code>edge</code>.
     * Equivalent to <code>getSource(edge).equals(vertex)</code>.
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return <code>true</code> iff <code>vertex</code> is the source of <code>edge</code>
     */
    public boolean isSource(V vertex, E edge);
    
    /**
     * Returns <code>true</code> if <code>vertex</code> is the destination of <code>edge</code>.
     * Equivalent to <code>getDest(edge).equals(vertex)</code>.
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return <code>true</code> iff <code>vertex</code> is the destination of <code>edge</code>
     */
    public boolean isDest(V vertex, E edge);

    /**
     * Adds edge <code>e</code> to this graph such that it connects 
     * vertex <code>v1</code> to <code>v2</code>.
     * Equivalent to <code>addEdge(e, new Pair<V>(v1, v2))</code>.
     * If this graph does not contain <code>v1</code>, <code>v2</code>, 
     * or both, implementations may choose to either silently add 
     * the vertices to the graph or throw an <code>IllegalArgumentException</code>.
     * If this graph assigns edge types to its edges, the edge type of
     * <code>e</code> will be the default for this graph.
     * See <code>Hypergraph.addEdge()</code> for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @return <code>true</code> if the add is successful, <code>false</code> otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object, EdgeType)
     */
    public boolean addEdge(E e, V v1, V v2);
    
    /**
     * Adds edge <code>e</code> to this graph such that it connects 
     * vertex <code>v1</code> to <code>v2</code>.
     * Equivalent to <code>addEdge(e, new Pair<V>(v1, v2))</code>.
     * If this graph does not contain <code>v1</code>, <code>v2</code>, 
     * or both, implementations may choose to either silently add 
     * the vertices to the graph or throw an <code>IllegalArgumentException</code>.
     * If <code>edgeType</code> is not legal for this graph, this method will
     * throw <code>IllegalArgumentException</code>.
     * See <code>Hypergraph.addEdge()</code> for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @param edgeType the type to be assigned to the edge
     * @return <code>true</code> if the add is successful, <code>false</code> otherwise
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object)
     */
    public boolean addEdge(E e, V v1, V v2, EdgeType edgeType);

    /**
     * Returns the endpoints of <code>edge</code> as a <code>Pair<V></code>.
     * @param edge the edge whose endpoints are to be returned
     * @return the endpoints (incident vertices) of <code>edge</code>
     */
    public Pair<V> getEndpoints(E edge);
    
    /**
     * Returns the vertex at the other end of <code>edge</code> from <code>vertex</code>.
     * (That is, returns the vertex incident to <code>edge</code> which is not <code>vertex</code>.)
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return the vertex at the other end of <code>edge</code> from <code>vertex</code>
     */
    public V getOpposite(V vertex, E edge); 
}
