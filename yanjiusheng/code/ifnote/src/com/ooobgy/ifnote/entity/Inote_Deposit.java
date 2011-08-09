package com.ooobgy.ifnote.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户存款记录
 * @author frogcherry 周晓龙
 * @created 2011-8-8
 * @Email frogcherry@gmail.com
 */
@Entity
public class Inote_Deposit {
	/** PK */
	@Id
	@GeneratedValue
	private Integer id;
	/** 记录时间 */
	private Timestamp note_time;
	/** 关联用户 */
	private Integer user_id;
	/** 存款类型 */
	private String type;
	/** 存款金额,正值表示存入，负值表示取出 */
	private Double sum;
	/** 存款利率 */
	private Double rate;
	/** 存款时间 */
	private String dep_time;
	/** 存款银行 */
	private String bank_name;
	/** 说明 */
	private String comment;
	/** 相关联的存取款记录id */
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
	public void setNote_time(Timestamp note_time) {
		this.note_time = note_time;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
	public void setDep_time(String dep_time) {
		this.dep_time = dep_time;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
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
