package com.ooobgy.ifnote.dbctrler.daoimpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ooobgy.ifnote.dbctrler.HibernateSessionFactory;
import com.ooobgy.ifnote.dbctrler.dao.TDataDao;
/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public abstract class TDataDaoImpl<T,K> implements TDataDao<T,K> {
	protected static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void save(T object) {
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		tx.begin();
		session.saveOrUpdate(object);
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();
	}

	public void delete(T object) {
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		tx.begin();
		// session.update(object);
		session.delete(object);
		tx.commit();
		session.flush();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();
	}

	public void update(T object) {
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		tx.begin();
		session.saveOrUpdate(object);
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();
	}

	@SuppressWarnings( { "unchecked" })
	protected T findWithID(K id, Class<?> impclass) {
		T object = (T) new Object();
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		// System.out.println(impclass+"++"+id);
		object = (T) session.get(impclass, (Serializable) id);
		tx.commit();
//		 HibernateSessionFactory.closeSession();
		 HibernateSessionFactory.getSession();
		return object;
	}

	@SuppressWarnings( { "unchecked" })
	protected List<T> findAll(Class<?> impclass, String order) {
		List<T> list = new ArrayList<T>();
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "from " + impclass.getSimpleName() + order;
		System.out.println(hql);
		list = (List<T>) session.createQuery(hql).list();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();

		return list;
	}

	@SuppressWarnings( { "unchecked" })
	protected List<T> findPage(Class<?> impclass, String order, int start,
			int pageSize) {
		List<T> list = new ArrayList<T>();
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "from " + impclass.getSimpleName() + order;
		System.out.println(hql);
		Query query = session.createQuery(hql);
		query.setFirstResult(start).setMaxResults(pageSize);
		list = (List<T>) query.list();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();

		return list;
	}

	@SuppressWarnings( { "unchecked" })
	protected List<T> findOnePage(Class<?> impclass, String order, int start,
			int pageSize) {
		List<T> list = new ArrayList<T>();
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "from " + impclass.getSimpleName() + order;
		System.out.println(hql);
		Query query = session.createQuery(hql);
		query.setFirstResult(start).setMaxResults(pageSize);
		list = (List<T>) query.list();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();

		return list;
	}

	protected int findCount(Class<?> impclass) {
		int count;
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "select count(*) from " + impclass.getSimpleName();
		System.out.println(hql);
		Query query = session.createQuery(hql);
		count = ((Long) query.iterate().next()).intValue();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();

		return count;
	}

	protected int findCountByOrder(Class<?> impclass, String order) {
		int count;
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "select count(*) from " + impclass.getSimpleName() + order;
		System.out.println(hql);
		Query query = session.createQuery(hql);
		count = ((Long) query.iterate().next()).intValue();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();

		return count;
	}

	protected int delete(Class<?> impclass, String cmd){
		// 开启会话
		Session session = HibernateSessionFactory.getSession();
		// 事务处理对象
		Transaction tx = null;
		tx = session.beginTransaction();
		String hql = "delete from " + impclass.getSimpleName() + cmd;
		System.out.println(hql);
		Query query = session.createQuery(hql);
		int removeLines = query.executeUpdate();
		tx.commit();
		HibernateSessionFactory.closeSession();
		HibernateSessionFactory.getSession();
		
		return removeLines;
	}

	protected List<T> findWithCmd(Class<?> impclass, String cmd) {
		return findAll(impclass, cmd);
	}
	
	
}
