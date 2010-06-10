/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fan {
	/**
	 * 托管主键
	 */
	@Id
	@GeneratedValue
	private Integer fanId;
	/**
	 * 登录名
	 */
	private String name;
	/**
	 * 昵称
	 */
	private String nickname;
	private String password;
	/**
	 * 注册时间
	 */
	private Timestamp checkinTime;
	/**
	 * 权限
	 */
	private Integer authority;
	
	
	public Integer getFanId() {
		return fanId;
	}
	public void setFanId(Integer fanId) {
		this.fanId = fanId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Timestamp checkinTime) {
		this.checkinTime = checkinTime;
	}
	public Integer getAuthority() {
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	/**
	 * override equals使用hibernate二级缓存提高速率
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Fan))
			return false;

		Fan other = (Fan) o;

		if (other.getFanId() == null)
			return false;
		return fanId.equals(other.getFanId());
	}

	/**
	 * override hashCode使用hibernate二级缓存提高速率
	 */
	public int hashCode() {
		if (fanId != null) {
			return fanId.hashCode();
		} else {
			return super.hashCode();
		}
	}
}
