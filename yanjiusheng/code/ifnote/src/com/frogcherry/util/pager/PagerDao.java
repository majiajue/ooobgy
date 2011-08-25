
package com.frogcherry.util.pager;

import java.util.List;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public interface PagerDao<T> {
	public List<T> findAll();

	public int getTotalNumber();

	public List<T> findPage(int pageId, int pageSize);

}
