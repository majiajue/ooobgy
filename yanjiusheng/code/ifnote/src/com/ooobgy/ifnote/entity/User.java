package com.ooobgy.ifnote.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User Bean: 表示普通用户的entity，含有基本用户信息
 * @author frogcherry 周晓龙
 * @created 2011-8-4
 * @Email frogcherry@gmail.com
 */
@Entity
public class User {
	/** PK */
	@Id
	@GeneratedValue
	private Integer id;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String password;
	/** email */
	private String email;
	/** 电话号码 */
	private String phoneNum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
