package edu.zju.cs.ooobgy.mr.jobs;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
 * 归一化单个PersonNode的信息 
 * 数据format：
 * PersonId^A主叫次数^A主叫时间^A主叫人数^A被叫次数^A被叫时间^A被叫人数^A双向联系人数^A综合权值 
 * 权值公式 weight = (主叫权值/10+主叫人数*50)*1 + (被叫权值/10+被叫人数*50)*0.8 
 * 向下取整
 * 
 * @author 周晓龙
 * @created 2010-10-11
 */
public class PersonRecord implements HadoopJob {
	/**
	 * 权值公式 weight = (主叫权值/10+主叫人数*50)*1.0 + (被叫权值/10+被叫人数*50)*0.8 
	 * 向下取整
	 * 
	 * @return weight
	 */
	public static int weightEvaluate(int callPcnt, int callWeight, int receivePcount,
			int receiveWeight) {
		double weight;

		weight = (callWeight / 10.0 + callPcnt * 50) * 1.0
				+ (receiveWeight / 10.0 + receivePcount * 50) * 0.8;

		return (int) weight;
	}

	public static class TopMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {

		@Override
		public void configure(JobConf job) {
			super.configure(job);
		}

		/**
		 * flag = 1：主叫记录
		 * flag = 0：被叫记录
		 */
		@Override
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String[] items = line.split(KQConst.COMMON_SPLIT);
			String caller = items[0];
			String receiver = items[1];
			String cnt = items[2];
			String time = items[3];
			String weight = items[4];
			
			StringBuilder calRecBuilder = new StringBuilder();
			calRecBuilder.append("1").append(KQConst.COMMON_SPLIT);
			calRecBuilder.append(receiver).append(KQConst.COMMON_SPLIT);
			calRecBuilder.append(cnt).append(KQConst.COMMON_SPLIT);
			calRecBuilder.append(time).append(KQConst.COMMON_SPLIT);
			calRecBuilder.append(weight);
			
			StringBuilder recRecBuilder = new StringBuilder();
			recRecBuilder.append("0").append(KQConst.COMMON_SPLIT);
			recRecBuilder.append(caller).append(KQConst.COMMON_SPLIT);
			recRecBuilder.append(cnt).append(KQConst.COMMON_SPLIT);
			recRecBuilder.append(time).append(KQConst.COMMON_SPLIT);
			recRecBuilder.append(weight);
			
			output.collect(new Text(caller), new Text(calRecBuilder.toString()));
			output.collect(new Text(receiver), new Text(recRecBuilder.toString()));	
		}
	}

	public static class TopReducer extends MapReduceBase implements
	Reducer<Text, Text, Text, Text> {

		@Override
		public void configure(JobConf job) {
			super.configure(job);
		}

		@Override
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			Set<String> pubBuff = new HashSet<String>();//求交双向联系的集合buff
			
			int callCount = 0;
			int callTime = 0;
			int callPcnt = 0;
			int callWeight = 0;
			
			int receiveCount = 0;
			int receiveTime = 0;
			int receivePcnt = 0;
			int receiveWeight = 0;
			
			int twoWayCnt = 0;//双向通信人数
			
			String line;
			while (values.hasNext()) {
				line = values.next().toString();
				String[] items = line.split(KQConst.COMMON_SPLIT);
				String flag = items[0];
				String target = items[1];
				String count = items[2];
				String time = items[3];
				String weight = items[4];
				if (flag.equals("1")) {//主叫
					callCount += Integer.parseInt(count);
					callPcnt ++;
					callTime += Integer.parseInt(time);
					callWeight += Integer.parseInt(weight);
					if (pubBuff.contains(target)) {
						twoWayCnt ++;
						pubBuff.remove(target);
					} else {
						pubBuff.add(target);
					}
				}else if (flag.equals("0")) {//被叫
					receiveCount += Integer.parseInt(count);
					receivePcnt ++;
					receiveTime += Integer.parseInt(time);
					receiveWeight += Integer.parseInt(weight);
					if (pubBuff.contains(target)) {
						twoWayCnt ++;
						pubBuff.remove(target);
					} else {
						pubBuff.add(target);
					}
				}
			}
			
			int totalWeight = weightEvaluate(callPcnt, callWeight, receivePcnt, receiveWeight);
			
			StringBuilder outLine = new StringBuilder();
			outLine.append(key.toString()).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(receiveCount)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(receiveTime)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(receivePcnt)).append(KQConst.COMMON_SPLIT);
			
			outLine.append(Integer.toString(callCount)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(callTime)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(callPcnt)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(twoWayCnt)).append(KQConst.COMMON_SPLIT);
			outLine.append(Integer.toString(totalWeight));
			output.collect(new Text(outLine.toString()), null);
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
