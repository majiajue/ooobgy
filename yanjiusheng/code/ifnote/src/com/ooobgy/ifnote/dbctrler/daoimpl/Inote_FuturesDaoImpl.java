package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_FuturesDao;
import com.ooobgy.ifnote.entity.Inote_Futures;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_FuturesDaoImpl extends TDataDaoImpl<Inote_Futures, Integer>
		implements Inote_FuturesDao {

	public List<Inote_Futures> findAll() {
		return findAll(Inote_Futures.class, "");
	}

	public int findCount() {
		return findCount(Inote_Futures.class);
	}

	public Inote_Futures findWithId(Integer id) {
		return findWithID(id, Inote_Futures.class);
	}

	public List<Inote_Futures> findWithCmd(String cmd) {
		return findWithCmd(Inote_Futures.class, cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_FuturesDao#findAllWithUid(java.lang
	 * .Integer)
	 */
	public List<Inote_Futures> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId + " order by note_time desc";
		return findWithCmd(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_FuturesDao#findAllWithUidTime(java
	 * .lang.Integer, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Futures> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime()))
				+ "' and note_time<='"
				+ timeFormat.format(new Date(endTime.getTime())) + "'" + " order by note_time desc";
		return findWithCmd(cmd);
	}

}
