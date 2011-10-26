package edu.zju.cs.ooobgy.db.entity;

/**
 * 按月切片团伙事实表，Vertex维度
 * @author frogcherry 周晓龙
 * @created 2011-2-4
 * @Email frogcherry@gmail.com
 */
public class ClusterVertexM {
	private Integer cvm_id;
	private String time_range;
	private String pnode;
	private String cluster_id;
	
	public Integer getCvm_id() {
		return cvm_id;
	}
	public void setCvm_id(Integer cvm_id) {
		this.cvm_id = cvm_id;
	}
	public String getTime_range() {
		return time_range;
	}
	public void setTime_range(String time_range) {
		this.time_range = time_range;
	}
	public String getPnode() {
		return pnode;
	}
	public void setPnode(String pnode) {
		this.pnode = pnode;
	}
	public String getCluster_id() {
		return cluster_id;
	}
	public void setCluster_id(String cluster_id) {
		this.cluster_id = cluster_id;
	}
	
	
}
