/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package edu.zju.cs.ooobgy.db.controller.dao;

public interface TDataDao<T> {
	public void save(T o);

	public void delete(T o);

	public void update(T o);
}
