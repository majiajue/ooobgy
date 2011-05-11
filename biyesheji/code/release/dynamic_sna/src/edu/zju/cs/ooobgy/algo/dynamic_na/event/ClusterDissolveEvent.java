package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 团伙分解事件Dissolve
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class ClusterDissolveEvent extends ClusterEvent {
	
	
	public ClusterDissolveEvent(String clusterId, String eventType) {
		super(clusterId, "dissolve");
	}
	
	@Override
	public String toString() {
		return toShortString();
	}
}
