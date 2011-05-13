package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 团伙分解事件Dissolve
 * @author frogcherry 周晓龙
 * @created 2011-5-13
 * @Email frogcherry@gmail.com
 */
public class ClusterFormEvent extends ClusterEvent {
	
	
	public ClusterFormEvent(String clusterId) {
		super(clusterId, "form");
	}
	
	@Override
	public String toString() {
		return toShortString();
	}
}
