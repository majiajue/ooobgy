
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
 * 抽取并转换出查询(Keyword)相关的PV，IPV，插入搜索关键词序列明细表（DM_FACT_QUERY_DETAIL）
 * 
 * 上游： {@link GeneratedQueryFormatter} 
 * 数据格式：
 * 			date|hour|query|catId|ssid|acookie|queryflag|ipv|pv
 * 数据样本：
 * 			20100107004 代 麦蒂50010388s5Rqa/ARBUcCwBAUuXqt4gN07D001010.02.0
 * 
 * 下游：DM_FACT_QUERY_DETAIL
 * 数据格式：
 * 			date|hour|query|catId|ssid|ipv|pv|queryflag
 * 数据样本：
 * 			2010010700金属链条 黑色0na0.01.000110
 *   
 * @author 明风
 * @created 2010-03-08
 *
 */
public class ExtractQuerySearch extends AbstractProcessor {
	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			String line = value.toString();

			int dayEnd = line.indexOf(KQConst.SPLIT);
			int hourEnd = line.indexOf(KQConst.SPLIT, dayEnd + 1);
			int queryEnd = line.indexOf(KQConst.SPLIT, hourEnd + 1);
			int catEnd = line.indexOf(KQConst.SPLIT, queryEnd + 1);
			int ssidEnd = line.indexOf(KQConst.SPLIT, catEnd + 1);
			int acookieEnd = line.indexOf(KQConst.SPLIT, ssidEnd + 1);
			int flagEnd = line.indexOf(KQConst.SPLIT, acookieEnd + 1);

			//DM_FACT_QUERY_DETAIL，不需要ACookieId，需要QueryFlag
			if (dayEnd > 0 && hourEnd > 0 && acookieEnd > 0 && catEnd > 0 && ssidEnd > 0) {
				String outputKey = line.substring(0, ssidEnd+1) + line.substring(acookieEnd+1, flagEnd);
				String outputValue = line.substring(flagEnd + 1);
		
				output.collect(new Text(outputKey), new Text(outputValue));
			}
		}
	}

	public static class TopReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			double pv = 0;
			double ipv = 0;
			while (values.hasNext()) {
				String[] counts = values.next().toString().split(KQConst.SPLIT);
				double addPv = 0;
				double addIpv = 0;

				try {
					addIpv = Double.parseDouble(counts[0]);
					addPv = Double.parseDouble(counts[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = addIpv = 0;
				}

				pv += addPv;
				ipv += addIpv;
			}

			String line = key.toString();
			
			int flagEnd = line.lastIndexOf(KQConst.SPLIT);

			String beforQueryFlag = line.substring(0, flagEnd);
			String queryFlag = line.substring(flagEnd + 1);
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(beforQueryFlag).append(KQConst.SPLIT);
			outputValue.append(Double.toString(ipv)).append(KQConst.SPLIT);
			outputValue.append(Double.toString(pv)).append(KQConst.SPLIT);;
			outputValue.append(queryFlag);
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
