package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao;
import com.ooobgy.ifnote.entity.Inote_Cash;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_CashDaoImpl extends TDataDaoImpl<Inote_Cash, Integer> implements Inote_CashDao{

	public List<Inote_Cash> findAll() {
		return findAll(Inote_Cash.class, "");
	}

	public int findCount() {
		return findCount(Inote_Cash.class);
	}

	public Inote_Cash findWithId(Integer id) {
		return findWithID(id, Inote_Cash.class);
	}

	public List<Inote_Cash> findWithCmd(String cmd) {
		return findWithCmd(Inote_Cash.class, cmd);
	}

}
