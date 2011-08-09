package com.ooobgy.ifnote.entity;

import java.sql.Timestamp;

/**
 * 用户期货交易记录
 * @author frogcherry 周晓龙
 * @created 2011-8-8
 * @Email frogcherry@gmail.com
 */
public class Inote_Futures {
	/** PK */
	private Integer id;
	/** 记录时间 */
	private Timestamp note_time;
	/** 关联用户 */
	private Integer user_id;
	/** 期货名称 */
	private String name;
	/** 单价 */
	private Double price;
	/** 交易数量：正值表示买入，负值表示卖出 */
	private Double sum;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
