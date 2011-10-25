package edu.zju.cs.ooobgy.caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.zju.cs.ooobgy.algo.rand.IntRandom;
import edu.zju.cs.ooobgy.db.controller.dao.UndirectedRalationDao;
import edu.zju.cs.ooobgy.db.controller.daoimpl.UndirectedRalationDaoImpl;
import edu.zju.cs.ooobgy.db.entity.UndirectedRalation;

public class SimpUDR_CaseLoader {
	public static void main(String[] args) throws Throwable {
		String case_id = args[0];
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						"data/udr/s_udr" + case_id + ".csv")), "UTF-8"));
		String line = null;
		
		UndirectedRalationDao udrDao = new UndirectedRalationDaoImpl();
		
		String old_range = null;
		while ((line = reader.readLine()) != null) {
			String[] items = line.split(",");
			String time_range = items[0];
			if (old_range == null || !old_range.equals(time_range)) {
				udrDao.removeSlice(time_range);
				old_range = time_range;
			}
			String pnode_1 = items[1];
			String pnode_2 = items[2];
			Integer udr_weight = Integer.parseInt(items[3]);
			Integer call_count = IntRandom.rand(udr_weight/3 + 1, udr_weight/2 + 1);
			Integer call_time = IntRandom.rand(udr_weight*3/2 + 1, udr_weight*2 + 1);
			
			UndirectedRalation udr = new UndirectedRalation();
			udr.setTime_range(time_range);
			udr.setPnode_1(pnode_1);
			udr.setPnode_2(pnode_2);
			udr.setCall_count(call_count);
			udr.setCall_time(call_time);
			udr.setUdr_weight(udr_weight);
			udrDao.save(udr);
		}
	}
}
