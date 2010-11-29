/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package edu.zju.cs.ooobgy.db.controller.dao;

import java.util.List;

public interface TDataDao<T,K> {
	public void save(T o);
	public void delete(T o);
	public void update(T o);
	
	public List<T> findAll();
	public int findCount();
	public T findWithId(K id);
}
