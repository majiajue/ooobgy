/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.daoimpl;

import java.util.List;

import com.ooobgy.data.controller.dao.FanDao;
import com.ooobgy.data.entity.Fan;

public class FanDaoImpl extends TDataDaoImpl<Fan> implements FanDao {

	public List<Fan> findAll() {
		return findAll(Fan.class, "");
	}

	public int findCount() {
		return findCount(Fan.class);
	}

	public Fan findWithId(int id) {
		return findWithID(id, Fan.class);
	}

}
