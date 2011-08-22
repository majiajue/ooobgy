/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-22
 */
package com.ooobgy.ifnote.dbctrler;

import java.sql.Timestamp;
import java.util.List;

import com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.Inote_CashDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Cash;

import junit.framework.TestCase;

public class CashTest extends TestCase {
	public void testFindTime(){
		Inote_CashDao dao = new Inote_CashDaoImpl();
		Timestamp startTime = Timestamp.valueOf("2011-08-12 19:11:40");
		Timestamp endTime  = Timestamp.valueOf("2012-03-12 19:11:40");
		List<Inote_Cash> list = dao.findAllWithUidTime(1, startTime, endTime);
		for (Inote_Cash inoteCash : list) {
			System.out.println(inoteCash.getNote_time());
		}
	}
}
