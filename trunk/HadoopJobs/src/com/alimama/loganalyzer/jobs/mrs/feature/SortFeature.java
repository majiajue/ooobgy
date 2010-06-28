package com.alimama.loganalyzer.jobs.mrs.feature;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;

public class SortFeature extends AbstractProcessor {
	private static final String split = "\u0001";

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private Set<String> statProperties = null;

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			try {
				output.collect(new Text(value.toString()), new Text(""));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			
			output.collect(key, null);
		}
	}

	private static class HotDecreasingComparator extends Text.Comparator {

		public int compare(WritableComparable a, WritableComparable b) {
			String keyA = a.toString();
			String keyB = b.toString();
			System.err.println(keyA);
			System.err.println(keyB);

			int tradeEndA = keyA.indexOf(split);
			int hotEndA = keyA.indexOf(split, tradeEndA + 1);
			int perEndA = keyA.indexOf(split, hotEndA + 1);
			double percentageA = Double.parseDouble(keyA.substring(hotEndA + 1, perEndA));

			int tradeEndB = keyB.indexOf(split);
			int hotEndB = keyB.indexOf(split, tradeEndB + 1);
			int perEndB = keyB.indexOf(split, hotEndB + 1);
			double percentageB = Double.parseDouble(keyB.substring(hotEndB + 1, perEndB));

			if (percentageA > percentageB)
				return -1;
			else
				return 1;
		}
	}

	public void configJob(JobConf conf) {
		// Set Map/Reduce class
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setOutputKeyComparatorClass(HotDecreasingComparator.class);
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}

	public static void main(String[] args) {
	}
}
