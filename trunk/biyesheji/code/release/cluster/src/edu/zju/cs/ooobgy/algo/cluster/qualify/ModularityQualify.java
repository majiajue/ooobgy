package edu.zju.cs.ooobgy.algo.cluster.qualify;

import edu.zju.cs.ooobgy.graph.WeakComponentGraph;

/**
 * Edge Betweenness切边算法的质量度量</br>
 * M. E. J. Newman and M. Girvan的度量算法(2004)的Java实现
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @see "M. E. J. Newman and M. Girvan: Finding and evaluating community structure in networks (2004)"
 */
public class ModularityQualify<V, E> implements ClusterQualify<V, E>{

	@Override
	public double qualify(WeakComponentGraph<V, E> graph) {
		// TODO Auto-generated method stub
		return 0;
	}

}
