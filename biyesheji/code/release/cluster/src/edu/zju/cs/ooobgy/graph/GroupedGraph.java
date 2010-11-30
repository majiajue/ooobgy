package edu.zju.cs.ooobgy.graph;

import java.util.Collection;

/**
 * 包含分团操作的图接口
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 */
public interface GroupedGraph<V, E> extends Graph<V, E>{
	public Collection<E> getGroups();
}
