package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_StockDao;
import com.ooobgy.ifnote.entity.Inote_Stock;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_StockDaoImpl extends TDataDaoImpl<Inote_Stock, Integer> implements Inote_StockDao{

	public List<Inote_Stock> findAll() {
		return findAll(Inote_Stock.class, "");
	}

	public int findCount() {
		return findCount(Inote_Stock.class);
	}

	public Inote_Stock findWithId(Integer id) {
		return findWithID(id, Inote_Stock.class);
	}

	public List<Inote_Stock> findWithCmd(String cmd) {
		return findWithCmd(Inote_Stock.class, cmd);
	}

}
