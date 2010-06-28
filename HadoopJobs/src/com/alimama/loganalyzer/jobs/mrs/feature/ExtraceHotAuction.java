package com.alimama.loganalyzer.jobs.mrs.feature;

import java.io.IOException;
import java.util.Iterator;

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

public class ExtraceHotAuction extends AbstractProcessor {
	private static final String split = "\u0001";

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {
		public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) {
			String line = value.toString();

			int idEnd = line.indexOf(split);
			if (idEnd > 0) {
				int tradeEnd = line.indexOf(split, idEnd + 1);
				if (tradeEnd > 0) {
					String id = line.substring(0, idEnd);
					String trade = line.substring(idEnd + 1, tradeEnd);

					try {
						long tradeCount = Long.parseLong(trade);
						output.collect(new LongWritable(tradeCount), new Text(id));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text> {

		public void reduce(LongWritable key, Iterator<Text> values, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
			while (values.hasNext()) {
				output.collect(key, values.next());
			}
		}
	}

	private static class LongWritableDecreasingComparator extends LongWritable.Comparator {

		public int compare(WritableComparable a, WritableComparable b) {
			return -super.compare(a, b);
		}

		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
			return -super.compare(b1, s1, l1, b2, s2, l2);
		}
	}

	public void configJob(JobConf conf) {
		// Set Map/Reduce class
		conf.setMapOutputKeyClass(LongWritable.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(LongWritable.class);
		conf.setOutputValueClass(Text.class);

		conf.setOutputKeyComparatorClass(LongWritableDecreasingComparator.class);
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}

	public static void main(String[] args) {
		System.out.println(String.format("%05d", 3));
	}
}
