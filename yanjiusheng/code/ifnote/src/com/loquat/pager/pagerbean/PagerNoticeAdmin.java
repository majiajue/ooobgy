package com.loquat.pager.pagerbean;

import com.loquat.datavo.NoticeAdmin;


/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class PagerNoticeAdmin {
	private Integer noticeId;
	private String title;
	private String editor;
	private String time;
	private String validTime;
	
	public PagerNoticeAdmin(NoticeAdmin noticeAdmin){
		this.noticeId = noticeAdmin.getNoticeId();
		this.title = noticeAdmin.getTitle();
		this.editor = noticeAdmin.getEditor();
		this.time = noticeAdmin.getTime().toString().substring(0, 19);
		this.validTime = noticeAdmin.getValidTime().toString().substring(0, 19);
	}
	/**
	 * @return the noticeId
	 */
	public Integer getNoticeId() {
		return noticeId;
	}
	/**
	 * @param noticeId the noticeId to set
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
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}
	/**
	 * @param editor the editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the validTime
	 */
	public String getValidTime() {
		return validTime;
	}
	/**
	 * @param validTime the validTime to set
	 */
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
}
