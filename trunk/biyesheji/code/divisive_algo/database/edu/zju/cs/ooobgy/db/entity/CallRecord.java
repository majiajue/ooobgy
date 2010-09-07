/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-9-7
 */

package edu.zju.cs.ooobgy.db.entity;

import java.sql.Timestamp;

//create table HD_DEMO
//(
//  CALLER_NUM    VARCHAR2(50),
//  RECEIVER_NUM  VARCHAR2(50),
//  START_TIME    DATE,
//  SWITCH_ID     VARCHAR2(50),
//  STATION_CODE  VARCHAR2(50),
//  CALL_DURATION NUMBER
//);
public class CallRecord {
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
	
	
}
