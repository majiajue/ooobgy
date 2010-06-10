/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Admin {
	/**
	 * 托管主键
	 */
	@Id
	@GeneratedValue
	private Integer adminId;
	/**
	 * 登录名
	 */
	private String name;
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 注册时间
	 */
	private Timestamp checkinTime;
	@OneToMany
	private Set<HomeNews> homeNewses;
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Timestamp getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Timestamp checkinTime) {
		this.checkinTime = checkinTime;
	}
	public Set<HomeNews> getHomeNewses() {
		return homeNewses;
	}
	public void setHomeNewses(Set<HomeNews> homeNewses) {
		this.homeNewses = homeNewses;
	}
	
	
}
