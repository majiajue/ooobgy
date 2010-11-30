package edu.zju.cs.ooobgy.graph;

import java.util.Collection;

public interface GroupedGraph<V, E> extends Graph<V, E>{
	public Collection<E> getGroups();
}
