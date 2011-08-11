package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_FundDao;
import com.ooobgy.ifnote.entity.Inote_Fund;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_FundDaoImpl extends TDataDaoImpl<Inote_Fund, Integer> implements Inote_FundDao{

	public List<Inote_Fund> findAll() {
		return findAll(Inote_Fund.class, "");
	}

	public int findCount() {
		return findCount(Inote_Fund.class);
	}

	public Inote_Fund findWithId(Integer id) {
		return findWithID(id, Inote_Fund.class);
	}

	public List<Inote_Fund> findWithCmd(String cmd) {
		return findWithCmd(Inote_Fund.class, cmd);
	}

}
