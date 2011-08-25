
package com.frogcherry.util.pager;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class PagerServer {
	/**
	 * ΪPagerDaoװ���ϲ�������ItemDao
	 */
	private PagerDao pagerDao;
	/**
	 * д���ҳ��Ϣ
	 */
	private PagerInfo pagerInfo = new PagerInfo();

	/**
	 * ��ʼ����ҳ���ߣ���ҳ��loadʱ����
	 * 
	 * @return
	 */
	public List initPager() {
		List result = new ArrayList();// ���Ӧ�ð����������
		pagerInfo.setPageSize(10);// ��ʼ��ҳ���СΪ10
		pagerInfo.setInfoCount(pagerDao.getTotalNumber());
		pagerInfo.setNowPage(1);// page��1��
		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(1, pagerInfo.getPageSize()));

		return result;
	}

	/**
	 * ��һҳ
	 * 
	 * @return
	 */
	public List nextPage(PagerInfo nowPagerInfo) {
		List result = new ArrayList();// ���Ӧ�ð����������
		setPagerInfo(nowPagerInfo);
		pagerInfo.setNowPage(pagerInfo.getNowPage() + 1);
		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(pagerInfo.getNowPage(), pagerInfo
				.getPageSize()));

		return result;
	}

	/**
	 * ��һҳ
	 * 
	 * @return
	 */
	public List prevPage(PagerInfo nowPageInfo) {
		List result = new ArrayList();// ���Ӧ�ð����������
		setPagerInfo(nowPageInfo);
		pagerInfo.setNowPage(pagerInfo.getNowPage() - 1);

		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(pagerInfo.getNowPage(), pagerInfo
				.getPageSize()));

		return result;
	}

	/**
	 * ��ת��ĳһҳ
	 * 
	 * @param pageid
	 * @return
	 */
	public List gotoPage(int pageid, PagerInfo nowPageInfo) {
		List result = new ArrayList();// ���Ӧ�ð����������
		setPagerInfo(nowPageInfo);
		pagerInfo.setNowPage(pageid);

		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(pagerInfo.getNowPage(), pagerInfo
				.getPageSize()));

		return result;
	}

	/**
	 * ����һҳ
	 * 
	 * @return
	 */
	public List firstPage(PagerInfo nowPageInfo) {
		return gotoPage(1, nowPageInfo);
	}

	/**
	 * �����һҳ
	 * 
	 * @return
	 */
	public List lastPage(PagerInfo nowPageInfo) {
		List result = new ArrayList();// ���Ӧ�ð����������
		setPagerInfo(nowPageInfo);
		pagerInfo.setNowPage(pagerInfo.getPageCount());

		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(pagerInfo.getNowPage(), pagerInfo
				.getPageSize()));

		return result;
	}

	/**
	 * ��ķ�ҳ��С
	 * 
	 * @param nowPageInfo
	 * @return
	 */
	public List changePageSize(int newPageSize, PagerInfo nowPageInfo) {
		List result = new ArrayList();// ���Ӧ�ð����������
		setPagerInfo(nowPageInfo);
		pagerInfo.setPageSize(newPageSize);
		int pageCount = (int) (pagerInfo.getInfoCount() / newPageSize);
		if (pageCount * newPageSize == pagerInfo.getInfoCount()) {
			pagerInfo.setPageCount(pageCount);
		} else {
			pagerInfo.setPageCount(pageCount + 1);
		}
		pagerInfo.setNowPage(1);// �ص���һҳ

		result.add(0, pagerInfo);
		result.add(1, pagerDao.findPage(1, pagerInfo.getPageSize()));
		return result;
	}

	/**
	 * @return the pagerInfo
	 */
	public PagerInfo getPagerInfo() {
		return pagerInfo;
	}

	/**
	 * @param pagerInfo
	 *            the pagerInfo to set
	 */
	protected void setPagerInfo(PagerInfo pagerInfo) {
		this.pagerInfo = pagerInfo;
	}

	/**
	 * @param pagerDao
	 *            the pagerDao to set
	 */
	protected void setPagerDao(PagerDao pagerDao) {
		this.pagerDao = pagerDao;
	}

}
