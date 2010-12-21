package edu.zju.cs.ooobgy.db.entity;

/**
 * 有向的社会关系建模pojo
 * @author frogcherry 周晓龙
 * @created 2010-12-21
 * @Email frogcherry@gmail.com
 */
public class DirectedRalation {
	private Integer dr_id;
	private String time_range;
	private String caller;
	private String receiver;
	private Integer call_count;
	private Integer call_time;
	private Integer dr_weight;
	
	public Integer getDr_id() {
		return dr_id;
	}
	public void setDr_id(Integer dr_id) {
		this.dr_id = dr_id;
	}
	public String getTime_range() {
		return time_range;
	}
	public void setTime_range(String time_range) {
		this.time_range = time_range;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
	public Integer getDr_weight() {
		return dr_weight;
	}
	public void setDr_weight(Integer dr_weight) {
		this.dr_weight = dr_weight;
	}

}
