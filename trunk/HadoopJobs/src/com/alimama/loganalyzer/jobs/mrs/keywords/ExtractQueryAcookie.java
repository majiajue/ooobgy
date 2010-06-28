package com.alimama.loganalyzer.jobs.mrs.keywords;

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

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 
 * 抽取并转换出查询(Query)相关的Acookie详细信息，插入搜索关键词序列用户明细表（DM_FACT_QUERY_USER），用于生成UV
 * 
 * 上游： {@link GeneratedQueryFormatter}
 * 
 * 数据格式：
 * 			date|hour|query|catId|ssid|acookie|queryflag|ipv|pv
 * 数据样本：
 * 			20100107004 代 麦蒂50010388s5Rqa/ARBUcCwBAUuXqt4gN07D001010.02.0
 * 
 * 下游：DM_FACT_QUERY_USER
 * 
 * 数据格式：
 *		date|hour|query|catId|ssid|acookie|pv
 *	 			
 * 数据样本：
 * 		2010010700马丁靴0s5RdB5AtFNOUkBAa74TtpvkJaW1.0
 * 
 * @author 明风
 * @created 2010-03-08
 */
 public class ExtractQueryAcookie extends AbstractProcessor {

	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
			String line = value.toString();
			
			int dayEnd = line.indexOf(KQConst.SPLIT);
			int hourEnd = line.indexOf(KQConst.SPLIT, dayEnd + 1);
			int queryEnd = line.indexOf(KQConst.SPLIT, hourEnd + 1);
			int catEnd = line.indexOf(KQConst.SPLIT, queryEnd + 1);
			int ssidEnd = line.indexOf(KQConst.SPLIT, catEnd + 1);
			int acookieEnd = line.indexOf(KQConst.SPLIT, ssidEnd + 1);
			int flagEnd = line.indexOf(KQConst.SPLIT, acookieEnd + 1);
			
			int ipvEnd = line.indexOf(KQConst.SPLIT, flagEnd + 1);
			
			
			/**
			 * DM_FACT_QUERY_USER, 不需要Query Flag，需要AcookieId
			 */
			String outputKey = line.substring(0, acookieEnd);
			String outputValue = line.substring(ipvEnd + 1);
			
			if (dayEnd > 0 && hourEnd > 0 && acookieEnd > 0 && catEnd > 0 && ssidEnd > 0) {
				output.collect(new Text(outputKey), new Text(outputValue));
			}
		}
	}

	public static class TopReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			double pv = 0;
			while (values.hasNext()) {
				String pvStr = values.next().toString();

				double addPv = 0;
				try {
					addPv = Double.parseDouble(pvStr);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = 0;
				}

				pv += addPv;
			}
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(key.toString()).append(KQConst.SPLIT);
			outputValue.append(pv);
			output.collect(new Text(outputValue.toString()), null);
		}
	}

	public void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
	}

	public Class getMapper() {
		return TopMapper.class;
	}

	public Class getReducer() {
		return TopReducer.class;
	}
}
