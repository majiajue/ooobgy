
package com.loquat.datavo;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
@Entity
public class NoticeAdmin {
	@Id
	@GeneratedValue
	private Integer noticeId;
	private String title;
	private String content;
	private String editor;
	@ManyToOne
	private SimpleAdmin admin;
	private Timestamp time;
	private Timestamp validTime;
	

	/**
	 * @return the validTime
	 */
	public Timestamp getValidTime() {
		return validTime;
	}

	/**
	 * @param validTime the validTime to set
	 */
	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	/**
	 * @return the noticeId
	 */
	public Integer getNoticeId() {
		return noticeId;
	}

	/**
	 * @param noticeId
	 *            the noticeId to set
	 */
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * @param editor
	 *            the editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}

	/**
	 * @return the admin
	 */
	public SimpleAdmin getAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(SimpleAdmin admin) {
		this.admin = admin;
	}

	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

	/**
	 * override equalsʹ��hibernate���������������
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof NoticeAdmin))
			return false;

		NoticeAdmin other = (NoticeAdmin) o;

		if (other.getNoticeId() == null)
			return false;
		return noticeId.equals(other.getNoticeId());
	}

	/**
	 * override hashCodeʹ��hibernate���������������
	 */
	public int hashCode() {
		if (noticeId != null) {
			return noticeId.hashCode();
		} else {
			return super.hashCode();
		}
	}
}
