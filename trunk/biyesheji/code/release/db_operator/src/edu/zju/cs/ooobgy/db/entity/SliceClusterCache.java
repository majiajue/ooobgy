package edu.zju.cs.ooobgy.db.entity;

/**
 * 记录
 * @author frogcherry 周晓龙
 * @created 2011-5-10
 * @Email frogcherry@gmail.com
 */
public class SliceClusterCache {
	private Integer scc_id;
	private String time_range;
	private String clusterId;
	private String members;
	
	public Integer getScc_id() {
		return scc_id;
	}
	public void setScc_id(Integer scc_id) {
		this.scc_id = scc_id;
	}
	public String getTime_range() {
		return time_range;
	}
	public void setTime_range(String time_range) {
		this.time_range = time_range;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
}
