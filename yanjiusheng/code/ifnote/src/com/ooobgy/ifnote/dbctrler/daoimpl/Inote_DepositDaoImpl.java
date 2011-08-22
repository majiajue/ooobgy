package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_DepositDao;
import com.ooobgy.ifnote.entity.Inote_Deposit;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_DepositDaoImpl extends TDataDaoImpl<Inote_Deposit, Integer>
		implements Inote_DepositDao {

	public List<Inote_Deposit> findAll() {
		return findAll(Inote_Deposit.class, "");
	}

	public int findCount() {
		return findCount(Inote_Deposit.class);
	}

	public Inote_Deposit findWithId(Integer id) {
		return findWithID(id, Inote_Deposit.class);
	}

	public List<Inote_Deposit> findWithCmd(String cmd) {
		return findWithCmd(Inote_Deposit.class, cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_DepositDao#findAllWithUid(java.lang
	 * .Integer)
	 */
	public List<Inote_Deposit> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId;
		return findWithCmd(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_DepositDao#findAllWithUidTime(java
	 * .lang.Integer, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Deposit> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime()))
				+ "' and note_time<='"
				+ timeFormat.format(new Date(endTime.getTime())) + "'";
		return findWithCmd(cmd);
	}

}
