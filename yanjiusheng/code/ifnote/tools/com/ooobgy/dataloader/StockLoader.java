/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-24
 */
package com.ooobgy.dataloader;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.St_StockDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.St_StockDaoImpl;
import com.ooobgy.ifnote.entity.St_Stock;

public class StockLoader {
	public void loadData(String file, String timeStamp){
		/**
		 * code,name,smv
		 */
		CsvReader reader = new CsvReader("0,1,3", file);
		List<List<String>> data = reader.readData();
		
		St_StockDao dao = new St_StockDaoImpl();
		for (List<String> line : data) {
			try {
				St_Stock st = new St_Stock();
				st.setCode(line.get(0));
				st.setName(line.get(1));
				st.setSmv(Double.parseDouble(line.get(2)));
				st.setTimestamp(timeStamp);
				dao.save(st);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
