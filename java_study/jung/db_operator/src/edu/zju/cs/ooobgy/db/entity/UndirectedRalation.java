package edu.zju.cs.ooobgy.db.entity;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2010-12-21
 * @Email frogcherry@gmail.com
 */
public class UndirectedRalation {
	private Integer udr_id;
	private String time_range;
	private String pnode_1;
	private String pnode_2;
	private Integer call_count;
	private Integer call_time;
	private Integer udr_weight;
	public Integer getUdr_id() {
		return udr_id;
	}
	public void setUdr_id(Integer udr_id) {
		this.udr_id = udr_id;
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
	public Integer getCall_count() {
		return call_count;
	}
	public void setCall_count(Integer call_count) {
		this.call_count = call_count;
	}
	public Integer getCall_time() {
		return call_time;
	}
	public void setCall_time(Integer call_time) {
		this.call_time = call_time;
	}
	public Integer getUdr_weight() {
		return udr_weight;
	}
	public void setUdr_weight(Integer udr_weight) {
		this.udr_weight = udr_weight;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("udrBean:[ ");
		str.append(this.udr_id).append(", ");
		str.append(this.time_range).append(", ");
		str.append(this.pnode_1).append(", ");
		str.append(this.pnode_2).append(", ");
		str.append(this.call_count).append(", ");
		str.append(this.call_time).append(", ");
		str.append(this.udr_weight);
		str.append("]");
		return str.toString();
	}
}
