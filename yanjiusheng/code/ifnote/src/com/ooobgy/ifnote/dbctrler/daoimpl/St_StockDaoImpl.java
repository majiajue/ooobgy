package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.St_StockDao;
import com.ooobgy.ifnote.entity.St_Stock;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class St_StockDaoImpl extends TDataDaoImpl<St_Stock, Integer> implements St_StockDao{

	public List<St_Stock> findAll() {
		return findAll(St_Stock.class, "");
	}

	public int findCount() {
		return findCount(St_Stock.class);
	}

	public St_Stock findWithId(Integer id) {
		return findWithID(id, St_Stock.class);
	}

	public List<St_Stock> findWithCmd(String cmd) {
		return findWithCmd(St_Stock.class, cmd);
	}

	/**
	 * @see com.ooobgy.ifnote.dbctrler.dao.St_StockDao#findWithCode(java.lang.String)
	 */
	public St_Stock findWithCode(String code) {
		String cmd = " p where p.code=" + code + " order by timestamp desc";
		List<St_Stock> sts = findWithCmd(cmd);
		if (sts.size() < 1) {
			return null;
		} else {
			return sts.get(0);
		}
	}

}
