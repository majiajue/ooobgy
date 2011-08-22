package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Cash;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_CashDao extends TDataDao<Inote_Cash, Integer> {
	public List<Inote_Cash> findAllWithUid(Integer userId);
	public List<Inote_Cash> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
