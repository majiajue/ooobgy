package com.alimama.loganalyzer.jobs.mrs.feature;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;

public class FeatureAnalysis extends AbstractProcessor {
	private static final String split = "\u0001";
	private static final String allProperties = "10003 10002 10001 10000 20571 20573 20574 20710 20930 20879"
			+ " 1626173 21514 32852 33885 1627099 30606 31161 33759 1630696 33879 33877"
			+ " 33862 1626848 1626846 1626847 1626844 1627207 2315114 2599964 2600024 2600066 2600074 2600088 2726595 2726646";

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private Set<String> statProperties = null;

		public void configure(JobConf job) {
			if (statProperties == null) {
				statProperties = new HashSet<String>();
				String propertyIds[] = allProperties.split(" ");
				for (String property : propertyIds) {
					statProperties.add(property);
				}
			}
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			try {
				String line = value.toString();

				int idEnd = line.indexOf(split);
				int tradeEnd = line.indexOf(split, idEnd + 1);
				int propertyEnd = line.indexOf(split, tradeEnd + 1);
				String trade = line.substring(idEnd + 1, tradeEnd);
				String allProperty = line.substring(tradeEnd + 1, propertyEnd);

				String[] properties = allProperty.split(";");
				Arrays.sort(properties, 0, properties.length);

				List<String> curStateProperties = new LinkedList<String>();
				for (String property : properties) {
					String id = property.substring(0, property.indexOf(":"));
					if (statProperties.contains(id)) {
						curStateProperties.add(property);
					}
				}

				for (String property : curStateProperties) {
					output.collect(new Text(property), new Text(trade));
				}

				for (int i = 0; i < curStateProperties.size(); i++)
					for (int j = i + 1; j < curStateProperties.size(); j++) {
						output.collect(new Text(curStateProperties.get(i) + ";" + curStateProperties.get(j)), new Text(trade));
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			long bahaviorCount = 0;
			long itemCount = 0;
			long hotCount = 0;
			long itemTradeCount = 0;
			long hotTradeCount = 0;

			while (values.hasNext()) {
				bahaviorCount++;

				String value = values.next().toString();
				long tradeCount = Long.parseLong(value);

				if (tradeCount > 0) {
					itemCount++;
					itemTradeCount += tradeCount;
				}

				if (tradeCount > 2) {
					hotCount++;
					hotTradeCount += tradeCount;
				}
			}

			String counts = String.format("%d_%d_%d_%d_%d", bahaviorCount, itemCount, hotCount, itemTradeCount, hotTradeCount);
			output.collect(new Text(key.toString() + split + counts), null);
		}
	}

	public void configJob(JobConf conf) {
		// Set Map/Reduce class
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}
}
