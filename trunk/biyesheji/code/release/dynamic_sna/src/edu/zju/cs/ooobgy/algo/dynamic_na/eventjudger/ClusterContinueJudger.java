package edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterContinueEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;

/**
 * 判定延续事件Continue
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class ClusterContinueJudger<V> {
	
	public List<ClusterEvent> judge(Map<String, IdCluster<V>> preClusters, Map<String, IdCluster<V>> nowClusters) {
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		
		for (String cid : preClusters.keySet()) {
			IdCluster<V> preCl = preClusters.get(cid);
			IdCluster<V> nowCl = nowClusters.get(cid);
			if (preCl.equals(nowCl)) {
				ClusterContinueEvent continueEvent = new ClusterContinueEvent(cid);
				result.add(continueEvent);
			}
		}
		
		return result;
	}
}
