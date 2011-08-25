
package com.frogcherry.util.pager;

/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class PagerInfo {
	private int pageCount; // ��ҳ��
	private int nowPage; // ��ǰҳ���
	private int pageSize = 10; // ҳ���С
	private int infoCount; // �ܼ�¼��

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		if (pageCount == 0)
			pageCount = 1;
		this.pageCount = pageCount;
	}

	/**
	 * @return the nowPage
	 */
	public int getNowPage() {
		return nowPage;
	}

	/**
	 * Խ��������
	 * 
	 * @param nowPage
	 *            the nowPage to set
	 */
	public void setNowPage(int nowpage) {
		if (nowpage > getPageCount() || nowpage < 1) {// Խ����,Խ��������
			return;
		}
		this.nowPage = nowpage;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the infoCount
	 */
	public int getInfoCount() {
		return infoCount;
	}

	/**
	 * �Զ�����ҳ��
	 * 
	 * @param infoCount
	 *            the infoCount to set
	 */
	public void setInfoCount(int infoCount) {
		this.pageCount = (int) (infoCount / this.pageSize);
		if (this.pageCount * this.pageSize < infoCount) {
			setPageCount(getPageCount() + 1);
		}
		this.infoCount = infoCount;

		// �ձ��쳣����
		if (infoCount == 0) {
			this.pageCount = 1;
			this.nowPage = 1;
		}
	}

}
