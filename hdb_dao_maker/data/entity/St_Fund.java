package com.ooobgy.ifnote.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 每日基金动态，各支基金的净值、状态等信息
 * @author frogcherry 周晓龙
 * @created 2011-8-8
 * @Email frogcherry@gmail.com
 */
@Entity
public class St_Fund {
	/** PK */
	@Id
	@GeneratedValue
	private Integer id;
	/** 基金代码 */
	private Integer code;
	/** 基金名称 */
	private String name;
	/** 净值发布日，格式yyyyMMdd */
	private String timestamp;
	/** 单位净值 */
	private Double npv;
	/** 累计净值 */
	private Double acc_npv;
	/** 增长值 */
	private Double inc_npv;
	/** 增长率 */
	private Double inc_rate;
	/** 可购买 */
	private boolean buyable;
	/** 可赎回 */
	private boolean sellable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Double getNpv() {
		return npv;
	}
	public void setNpv(Double npv) {
		this.npv = npv;
	}
	public Double getAcc_npv() {
		return acc_npv;
	}
	public void setAcc_npv(Double acc_npv) {
		this.acc_npv = acc_npv;
	}
	public Double getInc_npv() {
		return inc_npv;
	}
	public void setInc_npv(Double inc_npv) {
		this.inc_npv = inc_npv;
	}
	public Double getInc_rate() {
		return inc_rate;
	}
	public void setInc_rate(Double inc_rate) {
		this.inc_rate = inc_rate;
	}
	public boolean isBuyable() {
		return buyable;
	}
	public void setBuyable(boolean buyable) {
		this.buyable = buyable;
	}
	public boolean isSellable() {
		return sellable;
	}
	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}
}
