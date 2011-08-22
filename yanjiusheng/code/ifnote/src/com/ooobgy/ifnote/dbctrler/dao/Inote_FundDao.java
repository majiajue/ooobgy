package com.ooobgy.ifnote.dbctrler.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.entity.Inote_Fund;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface Inote_FundDao extends TDataDao<Inote_Fund, Integer> {
	public List<Inote_Fund> findAllWithUid(Integer userId);
	public List<Inote_Fund> findAllWithUidTime(Integer userId, Timestamp startTime, Timestamp endTime);
}
