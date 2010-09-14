package edu.zju.cs.ooobgy.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import edu.zju.cs.ooobgy.db.controller.MakeDatabase;
import edu.zju.cs.ooobgy.db.controller.dao.PersonNodeDao;
import edu.zju.cs.ooobgy.db.controller.dao.RelationWeightedDao;
import edu.zju.cs.ooobgy.db.controller.daoimpl.PersonNodeDaoImpl;
import edu.zju.cs.ooobgy.db.controller.daoimpl.RelationWeightedDaoImpl;
import edu.zju.cs.ooobgy.db.entity.PersonNode;
import edu.zju.cs.ooobgy.db.entity.RelationWeighted;

/**
 * 
 * @author 周晓龙
 * @created 2010-9-14
 */
public class DataAdapter {
	public void Csv2DB(String filePath) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(filePath)), "utf-8"));
			PersonNodeDao personDao = new PersonNodeDaoImpl();
			RelationWeightedDao relationDao = new RelationWeightedDaoImpl();
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",");
				if (items.length != 4) {
					continue;
				}
				Integer edgeId = Integer.parseInt(items[0]);
				Integer callerId = Integer.parseInt(items[1]);
				Integer receiverId = Integer.parseInt(items[2]);
				Integer weight = Integer.parseInt(items[3]);

				savePersonById(personDao, callerId);
				savePersonById(personDao, receiverId);
				saveEdgeById(relationDao, edgeId, callerId, receiverId, weight);
			}
			reader.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void saveEdgeById(RelationWeightedDao relationDao, Integer edgeId,
			Integer callerId, Integer receiverId, Integer weight) {
		RelationWeighted edge = relationDao.findWithId(edgeId);
		if (edge == null) {
			edge = new RelationWeighted();
			edge.setCallerId(callerId);
			edge.setReceiverId(receiverId);
			edge.setWeight(weight);
		} else {
			edge.setWeight(weight + edge.getWeight());
		}
		relationDao.save(edge);
	}

	private void savePersonById(PersonNodeDao personDao, Integer callerId) {
		if (personDao.findWithId(callerId) != null) {
			return;
		}
		PersonNode caller = new PersonNode();
		caller.setName("\"" + callerId.toString() + "\"");
		caller.setPid(callerId);
		personDao.save(caller);
	}

	public void EdgeDB2JungFile(String filePath) {
		try {
			PrintStream out = new PrintStream(new File(filePath));
			PersonNodeDao personDao = new PersonNodeDaoImpl();
			RelationWeightedDao edgeDao = new RelationWeightedDaoImpl();
			List<PersonNode> persons = personDao.findAll();
			List<RelationWeighted> edges = edgeDao.findAll();
			
			out.println("*Vertices " + persons.size());
			for (PersonNode person : persons) {
				String line = person.getPid() + " " + person.getName();
				out.println(line);
			}
			
			out.println("*Edges");
			for (RelationWeighted edge : edges) {
				String line = edge.getCallerId() + " " + edge.getReceiverId()
						+ " " + edge.getWeight();
				out.println(line);
			}

			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void clearDB() {
		MakeDatabase.main(null);
	}
}
