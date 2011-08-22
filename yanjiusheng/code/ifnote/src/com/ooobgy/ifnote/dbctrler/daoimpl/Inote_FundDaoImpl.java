package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_FundDao;
import com.ooobgy.ifnote.entity.Inote_Fund;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_FundDaoImpl extends TDataDaoImpl<Inote_Fund, Integer>
		implements Inote_FundDao {

	public List<Inote_Fund> findAll() {
		return findAll(Inote_Fund.class, "");
	}

	public int findCount() {
		return findCount(Inote_Fund.class);
	}

	public Inote_Fund findWithId(Integer id) {
		return findWithID(id, Inote_Fund.class);
	}

	public List<Inote_Fund> findWithCmd(String cmd) {
		return findWithCmd(Inote_Fund.class, cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_FundDao#findAllWithUid(java.lang
	 * .Integer)
	 */
	public List<Inote_Fund> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId;
		return findWithCmd(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_FundDao#findAllWithUidTime(java.
	 * lang.Integer, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Fund> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime()))
				+ "' and note_time<='"
				+ timeFormat.format(new Date(endTime.getTime())) + "'";
		return findWithCmd(cmd);
	}

}
