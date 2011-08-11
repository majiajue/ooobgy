package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_DepositDao;
import com.ooobgy.ifnote.entity.Inote_Deposit;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_DepositDaoImpl extends TDataDaoImpl<Inote_Deposit, Integer> implements Inote_DepositDao{

	public List<Inote_Deposit> findAll() {
		return findAll(Inote_Deposit.class, "");
	}

	public int findCount() {
		return findCount(Inote_Deposit.class);
	}

	public Inote_Deposit findWithId(Integer id) {
		return findWithID(id, Inote_Deposit.class);
	}

	public List<Inote_Deposit> findWithCmd(String cmd) {
		return findWithCmd(Inote_Deposit.class, cmd);
	}

}
