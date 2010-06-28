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
 * 根据原始序列出现的频繁度，排序后，选择最大的一个，和归一后的关键词序列，产生搜索关键词序列频繁序列对照表（DM_FACT_QUERY_FRQT）
 * 
 * 上游： {@link NormalizedQuerySort}
 * 
 * @author 明风
 * @created 2010-03-08
 */

public class NormalizedQuerySort extends AbstractProcessor {
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			// Step 1) generate all words
			String line = value.toString();
			int dayEnd = line.indexOf(KQConst.SPLIT);
			int querySortedEnd = line.indexOf(KQConst.SPLIT, dayEnd + 1);
			if (dayEnd > 0 && querySortedEnd > 0) {
				String mapKey = line.substring(0, querySortedEnd);
				String mapValue = line.substring(querySortedEnd + 1);
				try {
					output.collect(new Text(mapKey), new Text(mapValue));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			int maxSearchCount = 0;
			String maxSearchQuery = ""; 
			
			while (values.hasNext()) {
				String items[] = values.next().toString().split(KQConst.SPLIT);
				if (items.length != 2) {
					continue;
				}
				
				int searchCount = 0;
				try {
					searchCount = Integer.parseInt(items[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					searchCount = 0;
				}

				if (searchCount > maxSearchCount) {
					maxSearchCount = searchCount;
					maxSearchQuery = items[0];
				}
			}
			
			if (maxSearchCount > 0 && !maxSearchQuery.isEmpty()) {
				StringBuilder outputValue = new StringBuilder();
				outputValue.append(key.toString()).append(KQConst.SPLIT);
				outputValue.append(maxSearchQuery);
				output.collect(new Text(outputValue.toString()), null);
			}
		}
	}

	public void configJob(JobConf conf) {
		// Set Map/Reduce class
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		if (conf.get("mrs_max_xmx") != null){
			conf.set("mapred.child.java.opts", conf.get("mrs_max_xmx"));
		}
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}
}
