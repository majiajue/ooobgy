package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Futures;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_FuturesDao extends TDataDao<Inote_Futures, Integer> {
	public List<Inote_Futures> findAllWithUid(Integer userId);
	public List<Inote_Futures> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
