package edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterDissolveEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;

/**
 * 判定分解事件Dissolve
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class ClusterDissolveJudger<V> {
	
	public List<ClusterEvent> judge(Map<String, IdCluster<V>> preClusters, Map<String, IdCluster<V>> nowClusters) {
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		
		Matrix interMatrix = DCD_Cache.inter_map_matrix;//从缓冲池获得交集规模矩阵
		int nowCnt = interMatrix.getRowCount();
		int preCnt = interMatrix.getColumnCount();
		boolean isDissolve = true;
		for (int i = 0; i < preCnt; i++) {
			isDissolve = true;
			String preId = DCD_Cache.map_col_pre_cid.get(i);
			if (preClusters.get(preId).getVertexes().size() < 2) {//含有2个节点以下的团无分解事件
				continue;
			}
			for (int j = 0; j < nowCnt; j++) {
				if (interMatrix.element(j, i) > 1) {
					isDissolve = false;
					break;
				}
			}
			if (isDissolve) {
				ClusterDissolveEvent cdEvent = new ClusterDissolveEvent(preId);
				result.add(cdEvent);
			}
		}
		
		return result;
	}
	
	
}
