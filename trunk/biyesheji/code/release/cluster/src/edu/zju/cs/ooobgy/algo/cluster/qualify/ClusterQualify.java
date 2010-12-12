package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.Set;

import edu.zju.cs.ooobgy.graph.WeakComponentGraph;

/**
 * 聚类划分的评价类
 * @author frogcherry 周晓龙
 * @created 2010-12-12
 * @Email frogcherry@gmail.com
 */
public interface ClusterQualify<V, E> {
	/**
	 * 从一个可读划分的图实现来评定聚类的质量
	 * @param graph
	 * @return
	 */
	public double qualify(WeakComponentGraph<V, E> graph);
	/**
	 * 从指定的划分来评定聚类的质量
	 * @param clusters
	 * @return
	 */
	public double qualify(Set<Set<V>> clusters);
}
