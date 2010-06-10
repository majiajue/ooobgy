/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.daoimpl;

import java.util.List;

import com.ooobgy.data.controller.dao.HomeNewsDao;

import com.ooobgy.data.entity.HomeNews;

public class HomeNewsDaoImpl  extends TDataDaoImpl<HomeNews> implements HomeNewsDao {

	public List<HomeNews> findAll() {
		return findAll(HomeNews.class, "");
	}

	public int findCount() {
		return findCount(HomeNews.class);
	}

	public List<HomeNews> findPage(int start, int pageSize) {
		return findPage(HomeNews.class, "", start, pageSize);
	}

	public HomeNews findWithId(int id) {
		return findWithID(id, HomeNews.class);
	}

}
