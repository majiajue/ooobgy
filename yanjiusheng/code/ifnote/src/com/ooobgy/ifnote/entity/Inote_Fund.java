package com.ooobgy.ifnote.entity;

import java.sql.Timestamp;

/**
 * 用户基金交易记录
 * @author frogcherry 周晓龙
 * @created 2011-8-8
 * @Email frogcherry@gmail.com
 */
public class Inote_Fund {
	/** PK */
	private Integer id;
	/** 记录时间 */
	private Timestamp note_time;
	/** 关联用户 */
	private Integer user_id;
	/** 基金代码 */
	private Integer fund_code;
	/** 交易股数：正值表示购入，负值表示赎回 */
	private Integer count;
	/** 交易时净值 */
	private Double npv;
	/** 说明 */
	private String comment;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getNote_time() {
		return note_time;
	}
	public void setNote_time(Timestamp note_time) {
		this.note_time = note_time;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getFund_code() {
		return fund_code;
	}
	public void setFund_code(Integer fund_code) {
		this.fund_code = fund_code;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getNpv() {
		return npv;
	}
	public void setNpv(Double npv) {
		this.npv = npv;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
