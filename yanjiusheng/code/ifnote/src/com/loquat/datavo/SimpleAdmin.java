
package com.loquat.datavo;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
@Entity
public class SimpleAdmin {
	/** �й�id */
	@Id
	@GeneratedValue
	private Integer adminId;
	/** ���� */
	private String userId;
	private String name;
	private boolean gender;
	private int age;
	private String password;
	@OneToMany
	private Set<NoticeAdmin> notices;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the notices
	 */
	public Set<NoticeAdmin> getNotices() {
		return notices;
	}

	/**
	 * @param notices
	 *            the notices to set
	 */
	public void setNotices(Set<NoticeAdmin> notices) {
		this.notices = notices;
	}

	/**
	 * @return the adminId
	 */
	public Integer getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId
	 *            the adminId to set
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public boolean isGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * override equalsʹ��hibernate���������������
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof SimpleAdmin))
			return false;

		SimpleAdmin other = (SimpleAdmin) o;

		if (other.getAdminId() == null)
			return false;
		return adminId.equals(other.getAdminId());
	}

	/**
	 * override hashCodeʹ��hibernate���������������
	 */
	public int hashCode() {
		if (adminId != null) {
			return adminId.hashCode();
		} else {
			return super.hashCode();
		}
	}

}
