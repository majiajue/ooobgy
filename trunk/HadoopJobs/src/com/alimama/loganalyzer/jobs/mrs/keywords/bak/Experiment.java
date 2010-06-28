package com.alimama.loganalyzer.jobs.mrs.keywords.bak;

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

public class Experiment extends AbstractProcessor {
	private static final String split = "\u0001";

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			String line = value.toString();
			
			int dayEnd = line.indexOf(split);
			int hourEnd = line.indexOf(split, dayEnd + 1);
			int wordEnd = line.indexOf(split, hourEnd + 1);
			int wordTypeEnd = line.indexOf(split, wordEnd + 1);
			int typeEnd = line.indexOf(split, wordTypeEnd + 1);
			int brandEnd = line.indexOf(split, typeEnd + 1);
			int catEnd = line.indexOf(split, brandEnd + 1);
			//int acookieEnd = line.indexOf(split, catEnd + 1);
			int acookieEnd = 1;
			
			if (dayEnd > 0 && hourEnd > 0 && acookieEnd > 0 && wordEnd > 0 && typeEnd > 0 && catEnd > 0 && wordTypeEnd > 0) {
				try {
					String word = line.substring(hourEnd + 1, wordEnd);
					String wordType = line.substring(wordEnd + 1, wordTypeEnd);
					String brand = line.substring(typeEnd + 1, brandEnd);
					String type = line.substring(wordTypeEnd + 1, typeEnd);
					
					if (wordType.equals("4")) {
						output.collect(value, new Text(""));
					}
					
					/*
					int brandNumber = 0;
					int typeNumber = 0;
					
					try {
						brandNumber = Integer.parseInt(brand);
						typeNumber = Integer.parseInt(type);
					} catch (NumberFormatException e) {
						brandNumber = 0;
						typeNumber = 0;
					}
					
					if (wordType.equals("2") && brandNumber == 0) {
						output.collect(value, new Text(""));
					}
					
					if (wordType.equals("1") && brandNumber == 0) {
						output.collect(value, new Text(""));
					}
					*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					output.collect(value, new Text(""));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			output.collect(key, null);
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
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
