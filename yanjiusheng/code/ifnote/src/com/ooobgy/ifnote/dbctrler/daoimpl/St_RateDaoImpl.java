package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.St_RateDao;
import com.ooobgy.ifnote.entity.St_Rate;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class St_RateDaoImpl extends TDataDaoImpl<St_Rate, Integer> implements St_RateDao{

	public List<St_Rate> findAll() {
		return findAll(St_Rate.class, "");
	}

	public int findCount() {
		return findCount(St_Rate.class);
	}

	public St_Rate findWithId(Integer id) {
		return findWithID(id, St_Rate.class);
	}

	public List<St_Rate> findWithCmd(String cmd) {
		return findWithCmd(St_Rate.class, cmd);
	}

}
