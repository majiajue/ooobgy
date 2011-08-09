package com.ooobgy.ifnote.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户的贷款记录
 * @author frogcherry 周晓龙
 * @created 2011-8-9
 * @Email frogcherry@gmail.com
 */
@Entity
public class Inote_Loan {
	/** PK */
	@Id
	@GeneratedValue
	private Integer id;
	/** 记录时间 */
	private Timestamp note_time;
	/** 关联用户 */
	private Integer user_id;
	/** 贷款类型 */
	private String type;
	/** 贷款金额,正值表示贷出，负值表示还款 */
	private Double sum;
	/** 贷款利率 */
	private Double rate;
	/** 贷款时间 */
	private String dep_time;
	/** 贷款银行 */
	private String bank_name;
	/** 说明 */
	private String comment;
	/** 相关联的贷还款记录id */
	private Integer link_id;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getDep_time() {
		return dep_time;
	}
	public void setDep_time(String depTime) {
		dep_time = depTime;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getLink_id() {
		return link_id;
	}
	public void setLink_id(Integer linkId) {
		link_id = linkId;
	}
}
