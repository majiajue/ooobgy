package com.ooobgy.ifnote.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户的股票交易记录
 * @author frogcherry 周晓龙
 * @created 2011-8-9
 * @Email frogcherry@gmail.com
 */
@Entity
public class Inote_Stock {
	/** PK */
	@Id
	@GeneratedValue
	private Integer id;
	/** 记录时间 */
	private Timestamp note_time;
	/** 关联用户 */
	private Integer user_id;
	/** 股票代码 */
	private String stock_code;
	/** 交易股数：正值表示购入，负值表示赎回 */
	private Integer count;
	/** 交易时股票市值*/
	private Double smv;
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
	public void setNote_time(Timestamp noteTime) {
		note_time = noteTime;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer userId) {
		user_id = userId;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stockCode) {
		stock_code = stockCode;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSmv() {
		return smv;
	}
	public void setSmv(Double smv) {
		this.smv = smv;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
