package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Loan;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_LoanDao extends TDataDao<Inote_Loan, Integer> {
	public List<Inote_Loan> findAllWithUid(Integer userId);
	public List<Inote_Loan> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
