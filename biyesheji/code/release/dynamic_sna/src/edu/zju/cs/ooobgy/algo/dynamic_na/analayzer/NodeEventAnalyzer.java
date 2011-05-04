package edu.zju.cs.ooobgy.algo.dynamic_na.analayzer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.NodeEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;

/**
 * 挖掘节点事件
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class NodeEventAnalyzer<V, E> implements
				Transformer<ClusterSlice<V, E>, Set<NodeEvent<V>>> {
	private ClusterSlice<V, E> preSlice;

	public NodeEventAnalyzer(ClusterSlice<V, E> preSlice) {
		super();
		this.preSlice = preSlice;
	}
	
	public Set<NodeEvent<V>> analyze(ClusterSlice<V, E> nowSlice) {
		Collection<IdCluster<V>> nowClusters = nowSlice.getClusters().values();
		Set<NodeEvent<V>> result = new HashSet<NodeEvent<V>>();
		
		String nowClusterId, preClusterId;
		for (IdCluster<V> idCluster : nowClusters) {
			nowClusterId = idCluster.getId();
			for (V v : idCluster.getVertexes()) {
				preClusterId = preSlice.getVertexClusterId(v);
				if (!nowClusterId.equals(preClusterId)) {
					result.add(judge(v, preClusterId, nowClusterId));
				}
			}
		}
			
		return result;
	}

	private NodeEvent<V> judge(V v, String preClusterId, String nowClusterId) {
		String eventType = "continue";
		if (preClusterId.equals("NULL")) {
			eventType = "appear";
		} else if (nowClusterId.equals("NULL")) {
			eventType = "disappear";
		} else {
			eventType = "leave_join";
		}
		
		return new NodeEvent<V>(v, eventType, preClusterId, nowClusterId);
	}

	@Override
	public Set<NodeEvent<V>> transform(ClusterSlice<V, E> nowSlice) {		
		return analyze(nowSlice);
	}
}
