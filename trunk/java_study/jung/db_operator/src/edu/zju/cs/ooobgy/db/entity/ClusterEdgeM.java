package edu.zju.cs.ooobgy.db.entity;

/**
 * 按月切片团伙事实表，Edge维度
 * @author frogcherry 周晓龙
 * @created 2011-2-6
 * @Email frogcherry@gmail.com
 */
public class ClusterEdgeM {
	private Integer cem_id;
	private String time_range;
	private String pnode_1;
	private String pnode_2;
	private String cem_weight;
	private boolean removed;
	
	public Integer getCem_id() {
		return cem_id;
	}
	public void setCem_id(Integer cem_id) {
		this.cem_id = cem_id;
	}
	public String getTime_range() {
		return time_range;
	}
	public void setTime_range(String time_range) {
		this.time_range = time_range;
	}
	public String getPnode_1() {
		return pnode_1;
	}
	public void setPnode_1(String pnode_1) {
		this.pnode_1 = pnode_1;
	}
	public String getPnode_2() {
		return pnode_2;
	}
	public void setPnode_2(String pnode_2) {
		this.pnode_2 = pnode_2;
	}
	public String getCem_weight() {
		return cem_weight;
	}
	public void setCem_weight(String cem_weight) {
		this.cem_weight = cem_weight;
	}
	public boolean isRemoved() {
		return removed;
	}
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	
}
