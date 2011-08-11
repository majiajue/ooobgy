package com.ooobgy.ifnote.dbctrler.dao;

import java.util.List;


/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public interface TDataDao<T,K> {
	public void save(T o);
	public void delete(T o);
	public void update(T o);
	
	public List<T> findAll();
	public int findCount();
	public T findWithId(K id);
	public List<T> findWithCmd(String cmd);
}
