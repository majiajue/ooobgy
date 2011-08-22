package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Stock;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_StockDao extends TDataDao<Inote_Stock, Integer> {
	public List<Inote_Stock> findAllWithUid(Integer userId);
	public List<Inote_Stock> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
