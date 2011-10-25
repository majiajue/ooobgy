package edu.zju.cs.ooobgy.caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.zju.cs.ooobgy.db.controller.dao.UndirectedRalationDao;
import edu.zju.cs.ooobgy.db.controller.daoimpl.UndirectedRalationDaoImpl;
import edu.zju.cs.ooobgy.db.entity.UndirectedRalation;

public class UDR_CaseLoader {
	public static void main(String[] args) throws Throwable {
		String case_id = args[0];
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						"data/udr/udr" + case_id + ".csv")), "UTF-8"));
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
			UndirectedRalation udr = new UndirectedRalation();
			udr.setTime_range(time_range);
			udr.setPnode_1(items[1]);
			udr.setPnode_2(items[2]);
			udr.setCall_count(Integer.parseInt(items[3]));
			udr.setCall_time(Integer.parseInt(items[4]));
			udr.setUdr_weight(Integer.parseInt(items[5]));
			udrDao.save(udr);
		}
	}
}
