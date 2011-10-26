package edu.zju.cs.ooobgy.db.entity;

/**
 * 描述基站维表的pojo
 * @author frogcherry 周晓龙
 * @created 2010-12-21
 * @Email frogcherry@gmail.com
 */
public class Station {
	private Integer sid;
	private String station_id;
	private String station_code;
	
	public Integer getSid() {
		return sid;
	}
	
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	public String getStation_id() {
		return station_id;
	}
	
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	
	public String getStation_code() {
		return station_code;
	}
	
	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}
}
