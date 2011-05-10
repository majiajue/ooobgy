package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 个体行为
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class VertexEvent<V> {
	private V vertexId;
	private String eventType;
	private String preClusterId;
	private String nowClusterId;

	public VertexEvent(V nodeId, String eventType, String preClusterId,
			String nowClusterId) {
		super();
		this.vertexId = nodeId;
		this.eventType = eventType;
		this.preClusterId = preClusterId;
		this.nowClusterId = nowClusterId;
	}



	public V getNodeId() {
		return vertexId;
	}
	public void setNodeId(V nodeId) {
		this.vertexId = nodeId;
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
	
	/**
	 * 用长id表示cluster
	 * @return
	 */
	public String toLongString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vertexId);
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		sb.append(preClusterId);
		sb.append("  ====>  ");
		sb.append(nowClusterId);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vertexId);
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		sb.append(preClusterId.substring(28));
		sb.append("  ====>  ");
		sb.append(nowClusterId.substring(28));
		
		return sb.toString();
	}
}
