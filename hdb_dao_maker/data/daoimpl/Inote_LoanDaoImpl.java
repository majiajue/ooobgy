package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_LoanDao;
import com.ooobgy.ifnote.entity.Inote_Loan;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class Inote_LoanDaoImpl extends TDataDaoImpl<Inote_Loan, Integer> implements Inote_LoanDao{

	public List<Inote_Loan> findAll() {
		return findAll(Inote_Loan.class, "");
	}

	public int findCount() {
		return findCount(Inote_Loan.class);
	}

	public Inote_Loan findWithId(Integer id) {
		return findWithID(id, Inote_Loan.class);
	}

	public List<Inote_Loan> findWithCmd(String cmd) {
		return findWithCmd(Inote_Loan.class, cmd);
	}

}
