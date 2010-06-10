/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.data.controller.dao;

public interface TDataDao<T> {
	public void save(T o);

	public void delete(T o);

	public void update(T o);
}
