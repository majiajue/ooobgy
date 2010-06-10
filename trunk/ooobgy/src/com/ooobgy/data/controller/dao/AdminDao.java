/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.dao;

import java.util.List;

import com.ooobgy.data.entity.Admin;

public interface AdminDao extends TDataDao<Admin> {
	public Admin findWithId(int id);
	public List<Admin> findAll();
	public int findCount();
}
