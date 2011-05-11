package edu.zju.cs.ooobgy.algo.dynamic_na.event;

/**
 * 团伙行为
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class ClusterEvent {
	protected String clusterId;
	protected String eventType;
	
	public ClusterEvent(String clusterId, String eventType) {
		super();
		this.clusterId = clusterId;
		this.eventType = eventType;
	}
	
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * 用长的cluster id
	 * @return
	 */
	public String toLongString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clusterId);
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		
		return sb.toString( );
	}
	
	/**
	 * 用短的cluster id
	 * @return
	 */
	public String toShortString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clusterId.substring(28));
		sb.append("\t: <");
		sb.append(eventType);
		sb.append(">    ");
		
		return sb.toString( );
	}
	
	@Override
	public String toString() {
		return toShortString();
	}
}
