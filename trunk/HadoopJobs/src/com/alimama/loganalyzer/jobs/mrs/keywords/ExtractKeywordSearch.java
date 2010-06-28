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

/**
 * 抽取并转换出关键词(Keyword)相关的PV，IPV，SearchNum，插入搜索关键词明细SV表（DM_FACT_KEYWORD_DETAIL）
 * 
 * 上游： {@link GeneratedKeywordFormatter}
 * 
 * @author 明风
 * @created 2010-03-08
 *
 */
public class ExtractKeywordSearch extends AbstractProcessor {
	private static final String split = "\u0001";

	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
			String line = value.toString();
			
			int dayEnd = line.indexOf(split);
			int hourEnd = line.indexOf(split, dayEnd + 1);
			int wordEnd = line.indexOf(split, hourEnd + 1);
			int wordTypeEnd = line.indexOf(split, wordEnd + 1);
			int typeEnd = line.indexOf(split, wordTypeEnd + 1);
			int brandEnd = line.indexOf(split, typeEnd + 1);
			int catEnd = line.indexOf(split, brandEnd + 1);
			int acookieEnd = line.indexOf(split, catEnd + 1);
			//int ssidEnd = line.indexOf(split, acookieEnd + 1);
			
			//String ssid = line.substring(acookieEnd + 1, ssidEnd);

			if (dayEnd > 0 && hourEnd > 0 && acookieEnd > 0 && wordEnd > 0 && typeEnd > 0 && catEnd > 0 && wordTypeEnd > 0 ) {
				StringBuilder mapKey = new StringBuilder();
				mapKey.append(line.substring(0, catEnd));
				//mapKey.append(split).append(ssid);
				
				String mapValue = line.substring(acookieEnd + 1);
				output.collect(new Text(mapKey.toString()), new Text(mapValue));
			}
		}
	}

	public static class TopReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			double ipv = 0;
			double pv = 0;
			double ipv_brand = 0;
			double pv_brand = 0;
			while (values.hasNext()) {
				String[] counts = values.next().toString().split(split);
				double addPv = 0;
				double addIpv = 0;
				double addPv_brand = 0;
				double addIpv_brand = 0;

				try {
					addIpv = Double.parseDouble(counts[0]);
					addPv = Double.parseDouble(counts[1]);
					addIpv_brand = Double.parseDouble(counts[2]);
					addPv_brand = Double.parseDouble(counts[3]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = addIpv = addPv_brand = addIpv_brand = 0;
				}

				ipv += addIpv;
				pv += addPv;
				ipv_brand += addIpv_brand;
				pv_brand += addPv_brand;
			}
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(key.toString()).append(split);
			outputValue.append(Double.toString(ipv)).append(split);
			outputValue.append(Double.toString(pv)).append(split);
			outputValue.append(Double.toString(ipv_brand)).append(split);
			outputValue.append(Double.toString(pv_brand)).append(split);
			output.collect(new Text(outputValue.toString()), null);
		}
	}

	public void configJob(JobConf conf) {
		// Step 1) Set Map/Reduce class
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
