package ${impl_package};

import java.util.List;

import ${dao_package}.${entity}Dao;
import ${entity_package}.${entity};

/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate ${date}
 */
public class ${entity}DaoImpl extends TDataDaoImpl<${entity}, Integer> implements ${entity}Dao{

	public List<${entity}> findAll() {
		return findAll(${entity}.class, "");
	}

	public int findCount() {
		return findCount(${entity}.class);
	}

	public ${entity} findWithId(Integer id) {
		return findWithID(id, ${entity}.class);
	}

	public List<${entity}> findWithCmd(String cmd) {
		return findWithCmd(${entity}.class, cmd);
	}

}
