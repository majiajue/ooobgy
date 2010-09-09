package edu.zju.cs.ooobgy.db.controller.daoimpl;

import java.util.List;

import edu.zju.cs.ooobgy.db.controller.dao.CallRecordDao;
import edu.zju.cs.ooobgy.db.entity.CallRecord;

/**
 * 
 * @author 周晓龙
 * @created 2010-9-9
 */
public class CallRecordDaoImpl extends TDataDaoImpl<CallRecord,Integer> implements CallRecordDao {

	@Override
	public List<CallRecord> findAll() {
		return findAll(CallRecord.class, "");
	}

	@Override
	public int findCount() {
		return findCount(CallRecord.class);
	}

	@Override
	public CallRecord findWithId(Integer id) {
		return findWithID(id, CallRecord.class);
	}

}
