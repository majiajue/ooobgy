package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_StockDao;
import com.ooobgy.ifnote.entity.Inote_Stock;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_StockDaoImpl extends TDataDaoImpl<Inote_Stock, Integer>
		implements Inote_StockDao {

	public List<Inote_Stock> findAll() {
		return findAll(Inote_Stock.class, "");
	}

	public int findCount() {
		return findCount(Inote_Stock.class);
	}

	public Inote_Stock findWithId(Integer id) {
		return findWithID(id, Inote_Stock.class);
	}

	public List<Inote_Stock> findWithCmd(String cmd) {
		return findWithCmd(Inote_Stock.class, cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_StockDao#findAllWithUid(java.lang
	 * .Integer)
	 */
	public List<Inote_Stock> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId + " order by note_time desc";
		return findWithCmd(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_StockDao#findAllWithUidTime(java
	 * .lang.Integer, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Stock> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime()))
				+ "' and note_time<='"
				+ timeFormat.format(new Date(endTime.getTime())) + "'" + " order by note_time desc";
		return findWithCmd(cmd);
	}

}
