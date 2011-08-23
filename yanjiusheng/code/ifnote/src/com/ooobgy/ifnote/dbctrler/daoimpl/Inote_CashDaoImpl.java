package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao;
import com.ooobgy.ifnote.entity.Inote_Cash;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_CashDaoImpl extends TDataDaoImpl<Inote_Cash, Integer>
		implements Inote_CashDao {

	public List<Inote_Cash> findAll() {
		return findAll(Inote_Cash.class, "");
	}

	public int findCount() {
		return findCount(Inote_Cash.class);
	}

	public Inote_Cash findWithId(Integer id) {
		return findWithID(id, Inote_Cash.class);
	}

	public List<Inote_Cash> findWithCmd(String cmd) {
		return findWithCmd(Inote_Cash.class, cmd);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao#findAllWithUid(java.lang.Integer)
	 */
	public List<Inote_Cash> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId + " order by note_time desc";
		return findWithCmd(cmd);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao#findAllWithUidTime(java.lang.Integer,
	 *      java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Cash> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime())) + "' and note_time<='" + 
						timeFormat.format(new Date(endTime.getTime()))+"'" + " order by note_time desc";
		return findWithCmd(cmd);
	}

}
