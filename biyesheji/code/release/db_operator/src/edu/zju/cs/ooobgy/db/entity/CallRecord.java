package edu.zju.cs.ooobgy.db.entity;

import java.sql.Timestamp;

/**
 * 基础通话数据pojo
 * @author frogcherry 周晓龙
 * @created 2010-12-21
 * @Email frogcherry@gmail.com
 */
public class CallRecord {
	private Integer callId;
	private String caller;
	private String receiver;
	private Timestamp stime;
	private Integer duringTime;
	private String stationId;
	private String stationCode;
	
	public Integer getCallId() {
		return callId;
	}
	public void setCallId(Integer callId) {
		this.callId = callId;
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
	public Timestamp getStime() {
		return stime;
	}
	public void setStime(Timestamp stime) {
		this.stime = stime;
	}
	public Integer getDuringTime() {
		return duringTime;
	}
	public void setDuringTime(Integer duringTime) {
		this.duringTime = duringTime;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
}
