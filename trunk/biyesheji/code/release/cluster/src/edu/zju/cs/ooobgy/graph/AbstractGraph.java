package edu.zju.cs.ooobgy.graph;

import edu.zju.cs.ooobgy.graph.util.Pair;

/**
 * 提取公共方法的实现到抽象类中
 * @author frogcherry 周晓龙
 * @created 2010-12-1
 * @Email frogcherry@gmail.com
 */
public abstract class AbstractGraph<V, E> implements Graph<V, E> {
	@Override
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}

	@Override
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}
	
	@Override
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v2).contains(v1);
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v2).contains(v1);
	}
	@Override
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}
	
    @Override
    public String toString() {
    	StringBuffer sb = new StringBuffer("Vertices:");
    	for(V v : getVertices()) {
    		sb.append(v+",");
    	}
    	sb.setLength(sb.length()-1);
    	sb.append("\nEdges:");
    	for(E e : getEdges()) {
    		Pair<V> ep = getEndpoints(e);
    		sb.append(e+"["+ep.getFirst()+","+ep.getSecond()+"] ");
    	}
        return sb.toString();
    }
}
