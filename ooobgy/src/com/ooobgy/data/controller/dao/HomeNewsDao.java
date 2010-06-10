/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.dao;

import java.util.List;

import com.ooobgy.data.entity.HomeNews;


public interface HomeNewsDao extends TDataDao<HomeNews> {
	public List<HomeNews> findAll();

	public int findCount();

	public HomeNews findWithId(int id);

	public List<HomeNews> findPage(int start, int pageSize);
}
