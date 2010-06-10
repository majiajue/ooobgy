/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.dao;

import java.util.List;

import com.ooobgy.data.entity.Fan;

public interface FanDao extends TDataDao<Fan> {
	public Fan findWithId(int id);
	public List<Fan> findAll();
	public int findCount();
}
