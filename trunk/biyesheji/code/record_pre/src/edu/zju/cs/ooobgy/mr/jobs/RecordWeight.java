package edu.zju.cs.ooobgy.mr.jobs;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;

import edu.zju.cs.ooobgy.mr.common.HadoopJob;

public class RecordWeight implements HadoopJob {

	@Override
	public void configJob(JobConf conf) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<? extends Mapper> getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Reducer> getReducer() {
		// TODO Auto-generated method stub
		return null;
	}

}
