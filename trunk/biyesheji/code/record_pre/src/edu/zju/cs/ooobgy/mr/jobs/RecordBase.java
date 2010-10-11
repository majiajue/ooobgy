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

/**
 * 处理原始通话记录数据，生成统一格式的基础记录数据
 * 
 * @author 周晓龙
 * @created 2010-10-9
 */
public class RecordBase implements HadoopJob {

	public static class TopMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {

		@Override
		public void configure(JobConf job) {
			super.configure(job);
		}

		@Override
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String[] items = line.split(KQConst.ORIGINAL_SPLIT);
			String caller = items[0];// 主叫号码代号
			String reciever = items[2];// 被叫号码代号
			String timeStamp = items[1];// 通话开始时间
			String switcher = items[3];// 交换机编号
			String baseStation = items[4];// 基站编号
			String callTime = items[5];// 通话时长(秒)

			String[] dayHour = timeStamp.split(KQConst.SPACE_SPLIT);
			String day = dayHour[0];
			String hour = dayHour[1];
			
			StringBuilder keyBuilder = new StringBuilder();
			keyBuilder.append(day).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(hour).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(caller).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(reciever).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(callTime).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(switcher).append(KQConst.COMMON_SPLIT);
			keyBuilder.append(baseStation);

			output.collect(new Text(keyBuilder.toString()), null);
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
