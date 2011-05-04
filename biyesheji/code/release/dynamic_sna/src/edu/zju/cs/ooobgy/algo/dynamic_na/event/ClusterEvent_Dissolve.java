package edu.zju.cs.ooobgy.algo.dynamic_na.event;

public class ClusterEvent_Dissolve extends ClusterEvent {
	
	
	public ClusterEvent_Dissolve(String clusterId, String eventType) {
		super(clusterId, eventType);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clusterId);
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		
		return super.toString();
	}
}
