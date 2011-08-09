package ${dao_package};

import java.util.List;


/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate ${date}
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
