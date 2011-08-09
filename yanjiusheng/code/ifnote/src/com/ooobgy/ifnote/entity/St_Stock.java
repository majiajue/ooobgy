package com.ooobgy.ifnote.entity;

/**
 * 每日股票信息
 * @author frogcherry 周晓龙
 * @created 2011-8-8
 * @Email frogcherry@gmail.com
 */
public class St_Stock {
	/** PK */
	private Integer id;
	/** 记录时间戳 */
	private String timestamp;
	/** 股票代码 */
	private String code;
	/** 股票名称 */
	private String name;
	/** 股票当前市值 */
	private Double smv;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSmv() {
		return smv;
	}
	public void setSmv(Double smv) {
		this.smv = smv;
	}
}
