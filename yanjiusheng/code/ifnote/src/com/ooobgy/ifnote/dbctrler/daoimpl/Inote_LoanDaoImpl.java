package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_LoanDao;
import com.ooobgy.ifnote.entity.Inote_Loan;

/**
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_LoanDaoImpl extends TDataDaoImpl<Inote_Loan, Integer>
		implements Inote_LoanDao {

	public List<Inote_Loan> findAll() {
		return findAll(Inote_Loan.class, "");
	}

	public int findCount() {
		return findCount(Inote_Loan.class);
	}

	public Inote_Loan findWithId(Integer id) {
		return findWithID(id, Inote_Loan.class);
	}

	public List<Inote_Loan> findWithCmd(String cmd) {
		return findWithCmd(Inote_Loan.class, cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_LoanDao#findAllWithUid(java.lang
	 * .Integer)
	 */
	public List<Inote_Loan> findAllWithUid(Integer userId) {
		String cmd = " p where p.user_id=" + userId + " order by note_time desc";
		return findWithCmd(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ooobgy.ifnote.dbctrler.dao.Inote_LoanDao#findAllWithUidTime(java.
	 * lang.Integer, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public List<Inote_Loan> findAllWithUidTime(Integer userId,
			Timestamp startTime, Timestamp endTime) {
		String cmd = " p where p.user_id=" + userId + " and note_time>='"
				+ timeFormat.format(new Date(startTime.getTime()))
				+ "' and note_time<='"
				+ timeFormat.format(new Date(endTime.getTime())) + "'" + " order by note_time desc";
		return findWithCmd(cmd);
	}

}
