package com.loquat.pager.pagerdaoimpl;

import java.util.ArrayList;
import java.util.List;

import com.frogcherry.util.pager.PagerDao;
import com.loquat.datavo.NoticeAdmin;
import com.loquat.pager.pagerbean.PagerNoticeAdmin;
import com.ooobgy.ifnote.dbctrler.dao.NoticeAdminDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.NoticeAdminDaoImpl;

/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class NoticeAdminPagerDaoImpl implements PagerDao<PagerNoticeAdmin> {
	private NoticeAdminDao noticeAdminDao = new NoticeAdminDaoImpl();

	public List<PagerNoticeAdmin> findAll() {
		List<NoticeAdmin> noticeAdmins = noticeAdminDao.findAll();
		List<PagerNoticeAdmin> result = new ArrayList<PagerNoticeAdmin>();
		for (NoticeAdmin noticeAdmin : noticeAdmins) {
			PagerNoticeAdmin pagerNoticeAdmin = new PagerNoticeAdmin(noticeAdmin);
			result.add(pagerNoticeAdmin);
		}
		
		return result;
	}

	public List<PagerNoticeAdmin> findPage(int pageId, int pageSize) {
		int start = (pageId-1) * pageSize;
		List<NoticeAdmin> noticeAdmins = noticeAdminDao.findPage(start, pageSize);
		List<PagerNoticeAdmin> result = new ArrayList<PagerNoticeAdmin>();
		for (NoticeAdmin noticeAdmin : noticeAdmins) {
			PagerNoticeAdmin pagerNoticeAdmin = new PagerNoticeAdmin(noticeAdmin);
			result.add(pagerNoticeAdmin);
		}

		return result;
	}

	public int getTotalNumber() {
		return noticeAdminDao.findCount();
	}

}
