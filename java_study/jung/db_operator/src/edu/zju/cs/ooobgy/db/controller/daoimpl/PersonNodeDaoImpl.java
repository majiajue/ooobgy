package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.PersonNodeDao;
import edu.zju.cs.ooobgy.db.entity.PersonNode;

public class PersonNodeDaoImpl extends TDataDaoImpl<PersonNode,Integer> implements PersonNodeDao{

	@Override
	public List<PersonNode> findAll() {
		return findAll(PersonNode.class, "");
	}

	@Override
	public int findCount() {
		return findCount(PersonNode.class);
	}

	@Override
	public PersonNode findWithId(Integer id) {
		return findWithID(id, PersonNode.class);
	}

}
