/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.daoimpl;

import java.util.List;

import com.ooobgy.data.controller.dao.AdminDao;
import com.ooobgy.data.entity.Admin;

public class AdminDaoImpl extends TDataDaoImpl<Admin> implements AdminDao {

	public List<Admin> findAll() {
		return findAll(Admin.class, "");
	}

	public int findCount() {
		return findCount(Admin.class);
	}

	public Admin findWithId(int id) {
		return findWithID(id, Admin.class);
	}

}
