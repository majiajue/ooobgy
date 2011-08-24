/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-24
 */
package com.ooobgy.dataloader;

import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.St_FundDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.St_FundDaoImpl;
import com.ooobgy.ifnote.entity.St_Fund;

public class FundLoader {
	private String file;

	/**
	 * @param file
	 */
	public FundLoader() {
		super();
	}
	
	public void loadData(String file1){
		this.file = file1;
		/**
		 * time,code,name,npv,acc_npv,inc_npv,inc_rate
		 */
		CsvReader reader = new CsvReader("1,2,3,4,5,6,7", file);
		List<List<String>> data = reader.readData();
		
		St_FundDao dao = new St_FundDaoImpl();
		for (List<String> line : data) {
			try {
				St_Fund st = new St_Fund();
				st.setTimestamp(line.get(0));
				st.setCode(Integer.parseInt(line.get(1)));
				st.setName(line.get(2));
				st.setNpv(Double.parseDouble(line.get(3)));
				st.setAcc_npv(Double.parseDouble(line.get(4)));
				st.setInc_npv(Double.parseDouble(line.get(5)));
				st.setInc_rate(Double.parseDouble(line.get(6).replaceAll("%",
						"").trim()));
				st.setBuyable(true);
				st.setSellable(true);
				dao.save(st);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
