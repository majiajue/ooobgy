package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.UserDao;
import com.ooobgy.ifnote.entity.User;

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class UserDaoImpl extends TDataDaoImpl<User, Integer> implements UserDao{

	public List<User> findAll() {
		return findAll(User.class, "");
	}

	public int findCount() {
		return findCount(User.class);
	}

	public User findWithId(Integer id) {
		return findWithID(id, User.class);
	}

	public List<User> findWithCmd(String cmd) {
		return findWithCmd(User.class, cmd);
	}

}
