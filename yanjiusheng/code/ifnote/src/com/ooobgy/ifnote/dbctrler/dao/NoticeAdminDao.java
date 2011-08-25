
package com.ooobgy.ifnote.dbctrler.dao;
import java.util.List;

import com.loquat.datavo.NoticeAdmin;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public interface NoticeAdminDao extends TDataDao<NoticeAdmin,Integer> {
	public List<NoticeAdmin> findAll();

	public int findCount();

	public NoticeAdmin findWithId(int noticeAdminId);

	public List<NoticeAdmin> findPage(int start, int pageSize);
}
