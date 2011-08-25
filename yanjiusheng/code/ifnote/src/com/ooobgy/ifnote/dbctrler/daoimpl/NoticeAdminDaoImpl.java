
package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.loquat.datavo.NoticeAdmin;
import com.ooobgy.ifnote.dbctrler.dao.NoticeAdminDao;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class NoticeAdminDaoImpl extends TDataDaoImpl<NoticeAdmin, Integer> implements
		NoticeAdminDao {

	/**
	 * @see com.loquat.datacontroller.dao.NoticeAdminDao#findAll()
	 */
	public List<NoticeAdmin> findAll() {
		return findAll(NoticeAdmin.class, " as p order by p.time desc");
	}

	/**
	 * @see com.loquat.datacontroller.dao.NoticeAdminDao#findCount()
	 */
	public int findCount() {
		return findCount(NoticeAdmin.class);
	}

	/**
	 * @see com.loquat.datacontroller.dao.NoticeAdminDao#findPage(int, int)
	 */
	public List<NoticeAdmin> findPage(int start, int pageSize) {
		return findPage(NoticeAdmin.class, "", start, pageSize);
	}

	/**
	 * @see com.loquat.datacontroller.dao.NoticeAdminDao#findWithId(int)
	 */
	public NoticeAdmin findWithId(int noticeAdminId) {
		return findWithID(noticeAdminId, NoticeAdmin.class);
	}

	/* (non-Javadoc)
	 * @see com.ooobgy.ifnote.dbctrler.dao.TDataDao#findWithCmd(java.lang.String)
	 */
	public List<NoticeAdmin> findWithCmd(String cmd) {
		return findWithCmd(NoticeAdmin.class, cmd);
	}

	/* (non-Javadoc)
	 * @see com.ooobgy.ifnote.dbctrler.dao.TDataDao#findWithId(java.lang.Object)
	 */
	public NoticeAdmin findWithId(Integer id) {
		return findWithID(id, NoticeAdmin.class);
	}

}
