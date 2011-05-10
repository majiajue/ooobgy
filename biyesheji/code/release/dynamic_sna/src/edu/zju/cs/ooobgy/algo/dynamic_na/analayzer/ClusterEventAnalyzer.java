package edu.zju.cs.ooobgy.algo.dynamic_na.analayzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;

/**
 * 挖掘节点事件
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class ClusterEventAnalyzer<V, E> implements
				Transformer<ClusterSlice<V, E>, List<ClusterEvent>> {
	private ClusterSlice<V, E> preSlice;
	private Collection<IdCluster<V>> preClusters;

	public ClusterEventAnalyzer(ClusterSlice<V, E> preSlice) {
		super();
		this.preSlice = preSlice;
		preClusters = preSlice.getClusters().values();
	}
	
	public List<ClusterEvent> analyze(ClusterSlice<V, E> nowSlice) {
		Collection<IdCluster<V>> nowClusters = nowSlice.getClusters().values();
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
			
		return result;
	}


	/**
	 * 检测分解事件
	 * @param preClusterId
	 * @param nowClusterId
	 * @return
	 */
	private ClusterEvent judgeDissolve(V v, String preClusterId, String nowClusterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClusterEvent> transform(ClusterSlice<V, E> nowSlice) {		
		return analyze(nowSlice);
	}
}
