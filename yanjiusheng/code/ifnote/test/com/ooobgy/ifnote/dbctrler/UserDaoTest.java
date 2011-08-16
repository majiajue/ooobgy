package com.ooobgy.ifnote.dbctrler;

import com.ooobgy.ifnote.dbctrler.dao.UserDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.UserDaoImpl;
import com.ooobgy.ifnote.entity.User;

import junit.framework.TestCase;

public class UserDaoTest extends TestCase{
	public void testUserDao(){
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findWithUserName("ooobgy");
		userDao.findWithUserName("ooodabgy");
	}
}
