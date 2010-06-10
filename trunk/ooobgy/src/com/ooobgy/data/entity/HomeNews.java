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
import javax.persistence.ManyToOne;

/**
 * 首页新闻
 * @author zhouxiaolong.pt
 *
 */
@Entity
public class HomeNews {
	@Id
	@GeneratedValue
	private Integer newsId;
	private String title;
	private String content;
	private Timestamp publishTime;
	/**
	 * 是否置顶
	 */
	private boolean top;
	/**
	 * 人气
	 */
	private Integer popularity;
	/**
	 * 编辑
	 */
	private String editor;
	/**
	 * 发表的Admin
	 */
	@ManyToOne
	private Admin admin;
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public Integer getPopularity() {
		return popularity;
	}
	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	/**
	 * override equals使用hibernate二级缓存提高速率
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof HomeNews))
			return false;

		HomeNews other = (HomeNews) o;

		if (other.getNewsId() == null)
			return false;
		return newsId.equals(other.getNewsId());
	}

	/**
	 * override hashCode使用hibernate二级缓存提高速率
	 */
	public int hashCode() {
		if (newsId != null) {
			return newsId.hashCode();
		} else {
			return super.hashCode();
		}
	}
}
