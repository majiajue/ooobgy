package edu.zju.cs.ooobgy.mr.jobs;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import edu.zju.cs.ooobgy.common.KQConst;
import edu.zju.cs.ooobgy.mr.common.HadoopJob;

public class DateFilter implements HadoopJob {
	
	public static class TopMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		private static String date_filter = "";
		
		@Override
		public void configure(JobConf job) {
			super.configure(job);
			date_filter = job.get("date_filter");
		}

		@Override
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String[] items = line.split(KQConst.COMMON_SPLIT, 1);
			String date = items[0];
			if (date.contains(date_filter)) {//过滤掉不需要的信息,只输出需要的信息
				output.collect(value, new Text());
			}		
		}

	}

	/**
	 * Unused
	 * this job need no reducer
	 * @author 周晓龙
	 * @created 2010-10-9
	 */
	public static class TopReducer extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {
		@Override
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			while(values.hasNext()){
				output.collect(key, values.next());
			}
		}
	}
	
	@Override
	public void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Mapper> getMapper() {
		return TopMapper.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Reducer> getReducer() {
		return TopReducer.class;
	}

}
