package com.ooobgy.ifnote.entity;

/**
 * User Bean: 表示普通用户的entity，含有基本用户信息
 * @author frogcherry 周晓龙
 * @created 2011-8-4
 * @Email frogcherry@gmail.com
 */
public class User {
	private Integer id;
	private String userName;
	private String password;
	private String email;
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
