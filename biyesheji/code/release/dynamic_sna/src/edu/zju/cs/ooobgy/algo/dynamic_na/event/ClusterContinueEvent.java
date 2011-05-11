package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 团伙Continue延续事件
 * @author frogcherry 周晓龙
 * @created 2011-5-11
 * @Email frogcherry@gmail.com
 */
public class ClusterContinueEvent extends ClusterEvent {
	
	
	public ClusterContinueEvent(String clusterId) {
		super(clusterId, "Continue");
	}
	
	@Override
	public String toString() {
		return toShortString();
	}
}
