/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.zju.cs.ooobgy.db.controller.HibernateSessionFactory;
import edu.zju.cs.ooobgy.db.controller.dao.TDataDao;

public class TDataDaoImpl<T> implements TDataDao<T> {

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
	protected <K> T findWithID(K id, Class impclass) {
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
	protected <K> List<T> findAll(Class impclass, String order) {
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
	protected <K> List<T> findPage(Class impclass, String order, int start,
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
	protected <K> List<T> findOnePage(Class impclass, String order, int start,
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

	@SuppressWarnings("unchecked")
	protected int findCount(Class impclass) {
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

	@SuppressWarnings("unchecked")
	protected int findCountByOrder(Class impclass, String order) {
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

}
