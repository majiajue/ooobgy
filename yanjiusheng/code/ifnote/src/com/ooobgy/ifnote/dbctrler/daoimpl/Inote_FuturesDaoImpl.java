package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_FuturesDao;
import com.ooobgy.ifnote.entity.Inote_Futures;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_FuturesDaoImpl extends TDataDaoImpl<Inote_Futures, Integer> implements Inote_FuturesDao{

	public List<Inote_Futures> findAll() {
		return findAll(Inote_Futures.class, "");
	}

	public int findCount() {
		return findCount(Inote_Futures.class);
	}

	public Inote_Futures findWithId(Integer id) {
		return findWithID(id, Inote_Futures.class);
	}

	public List<Inote_Futures> findWithCmd(String cmd) {
		return findWithCmd(Inote_Futures.class, cmd);
	}

}
