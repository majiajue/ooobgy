/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-9-7
 */

package edu.zju.cs.ooobgy.db.entity;

import java.sql.Timestamp;

public class CallRecord {
	private Integer cid;
	private String callerNum;
	private String receiverNum;
	private Timestamp startTime;
	private String stationId;
	private String stationCode;
	private Double callDuration;
	public String getCallerNum() {
		return callerNum;
	}
	public void setCallerNum(String callerNum) {
		this.callerNum = callerNum;
	}
	public String getReceiverNum() {
		return receiverNum;
	}
	public void setReceiverNum(String receiverNum) {
		this.receiverNum = receiverNum;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
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
	public Double getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(Double callDuration) {
		this.callDuration = callDuration;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	
}
