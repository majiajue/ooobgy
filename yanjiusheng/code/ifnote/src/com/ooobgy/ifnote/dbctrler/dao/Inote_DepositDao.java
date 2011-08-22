package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Deposit;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_DepositDao extends TDataDao<Inote_Deposit, Integer> {
	public List<Inote_Deposit> findAllWithUid(Integer userId);
	public List<Inote_Deposit> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
