package edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterFormEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;

/**
 * 判定分解事件Dissolve
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class ClusterFormJudger<V> {
	public List<ClusterEvent> judge(Map<String, IdCluster<V>> preClusters, Map<String, IdCluster<V>> nowClusters) {
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		
		Matrix interMatrix = DCD_Cache.inter_map_matrix;//从缓冲池获得交集规模矩阵
		int nowCnt = interMatrix.getRowCount();
		int preCnt = interMatrix.getColumnCount();
		boolean isForm = true;
		for (int i = 0; i < nowCnt; i++) {
			isForm = true;
			String nowId = DCD_Cache.map_row_now_cid.get(i);
			if (nowClusters.get(nowId).getVertexes().size() < 2) {//含有2个节点以下的团无合并事件
				continue;
			}
			for (int j = 0; j < preCnt; j++) {
				if (interMatrix.element(i, j) > 1) {
					isForm = false;
					break;
				}
			}
			if (isForm) {
				ClusterFormEvent cfEvent = new ClusterFormEvent(nowId);
				result.add(cfEvent);
			}
		}
		
		return result;
	}
}
