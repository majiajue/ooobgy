
package com.loquat.datavo;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
@Deprecated
@Entity
public class Notice {
	@Id
	@GeneratedValue
	private Integer noticeId;
	private String title;
	private String content;
	private byte type;
	private int editorId;
	private Timestamp time;

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
	 * @return the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * @return the editorId
	 */
	public int getEditorId() {
		return editorId;
	}

	/**
	 * @param editorId
	 *            the editorId to set
	 */
	public void setEditorId(int editorId) {
		this.editorId = editorId;
	}

	/**
	 * override equalsʹ��hibernate���������������
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Notice))
			return false;

		Notice other = (Notice) o;

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
