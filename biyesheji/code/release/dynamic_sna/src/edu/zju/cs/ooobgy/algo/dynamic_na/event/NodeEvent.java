package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 个体行为
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class NodeEvent<V> {
	private V nodeId;
	private String eventType;
	private String preClusterId;
	private String nowClusterId;
	
	public NodeEvent() {
		
	}

	public NodeEvent(V nodeId, String eventType, String preClusterId,
			String nowClusterId) {
		super();
		this.nodeId = nodeId;
		this.eventType = eventType;
		this.preClusterId = preClusterId;
		this.nowClusterId = nowClusterId;
	}



	public V getNodeId() {
		return nodeId;
	}
	public void setNodeId(V nodeId) {
		this.nodeId = nodeId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getPreClusterId() {
		return preClusterId;
	}
	public void setPreClusterId(String preClusterId) {
		this.preClusterId = preClusterId;
	}
	public String getNowClusterId() {
		return nowClusterId;
	}
	public void setNowClusterId(String nowClusterId) {
		this.nowClusterId = nowClusterId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nodeId);
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		sb.append(preClusterId);
		sb.append("  ====>  ");
		sb.append(nowClusterId);
		
		return sb.toString();
	}
}
