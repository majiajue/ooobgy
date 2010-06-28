package com.alimama.loganalyzer.jobs.mrs.algo.query.helper;

import com.alimama.loganalyzer.jobs.mrs.algo.QueryCatPredict;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

public class QueryCatPredictMock implements QueryCatPredictor{

	public static QueryCatPredictor getInstance() {
		return new QueryCatPredictMock();
	}

	@Override
	public int predict(String query) {
		return KQConst.NOT_EXIST_INT;
	}

}
