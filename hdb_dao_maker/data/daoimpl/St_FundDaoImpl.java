package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.St_FundDao;
import com.ooobgy.ifnote.entity.St_Fund;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class St_FundDaoImpl extends TDataDaoImpl<St_Fund, Integer> implements St_FundDao{

	public List<St_Fund> findAll() {
		return findAll(St_Fund.class, "");
	}

	public int findCount() {
		return findCount(St_Fund.class);
	}

	public St_Fund findWithId(Integer id) {
		return findWithID(id, St_Fund.class);
	}

	public List<St_Fund> findWithCmd(String cmd) {
		return findWithCmd(St_Fund.class, cmd);
	}

}
