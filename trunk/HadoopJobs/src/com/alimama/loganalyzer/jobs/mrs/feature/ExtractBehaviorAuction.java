package com.alimama.loganalyzer.jobs.mrs.feature;

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

public class ExtractBehaviorAuction extends AbstractProcessor {
	private static final String split = "\u0001";
	private static final String behavior_split = "_";

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			try {
				String line = value.toString();

				if (line.contains(split)) {
					// auction record
					Integer pos = 0;

					int priceNumber = 5;
					int idNumber = 7;
					int categoryNumber = 9;
					int propertyNumber = 11;
					
					String price = getField(line, priceNumber, 0, 0, pos);
					String id = getField(line, idNumber, 0, 0, pos);
					String category = getField(line, categoryNumber, 0, 0, pos);
					String property = getField(line, propertyNumber, 0, 0, pos);

					if (id != null && category != null && property != null && category.equalsIgnoreCase("1512")) {
						output.collect(new Text(id), new Text("property" + split + property + split + price));
					}
				} else {
					// hehavior record
					int idBegin = line.indexOf("\t") + 1;
					String items[] = line.substring(idBegin).split(behavior_split);
					assert (items.length == 12);
					String id = items[0];
					String tradeCount = items[7];
					output.collect(new Text(id), new Text("tradeCount" + split + tradeCount));
					//output.collect(new Text(id), new Text("tradeCount" + split + tradeCount));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		static String getField(String line, int fieldNumber, int startPos, int startFieldNumber, Integer fieldStartPos) {
			int curIdNumber = startFieldNumber;

			int splitPos = startPos - 1;
			for (; curIdNumber < fieldNumber; curIdNumber++) {
				startPos = splitPos + 1;
				splitPos = line.indexOf(split, startPos);
				if (splitPos < 0) {
					break;
				}
			}

			if (curIdNumber == fieldNumber) {
				fieldStartPos = startPos;
				splitPos = line.indexOf(split, startPos);
				if (splitPos <= 0) {
					return line.substring(fieldStartPos);
				} else {
					return line.substring(fieldStartPos, splitPos);
				}
			}

			return null;
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			String property = null;
			String tradeCount = null;
			
			while (values.hasNext()) {
				String temp = values.next().toString();
				if (temp.startsWith("tradeCount")) {
					tradeCount = temp.substring(temp.indexOf(split) + 1);
				} else if (temp.startsWith("property")) {
					property = temp.substring(temp.indexOf(split) + 1);
				}
			}

			if (property != null && tradeCount != null) {
				output.collect(new Text(key.toString() + split + tradeCount + split + property), null);
			}
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

	public static void main(String[] args) {
		
		String line = "2009-07-29 20:40:182009-08-12 20:40:189999-01-01 00:00:0023d3c7e20873120530d1189b482be592258欧式铁艺家具※浴室用品/铁艺框/铁艺穿衣镜/壁挂镜框8e6c2ea47ece34c4696c4fd173a97102b5001053452631956:20407737;2631889:20406239泉州福建085899345930031438396890";
		line = "-2147483648	01b193df6069058a7022a2d786741b33_4_1_0_0_1_1_1_28.35_5.00_5_1";
		
		// hehavior record
		int idBegin = line.indexOf("\t") + 1;
		String items[] = line.substring(idBegin + 1).split(behavior_split);
		assert (items.length == 12);
		String id = items[0];
		String tradeCount = items[7];
		
		Integer pos = 0;

		int priceNumber = 5;
		int idNumber = 7;
		int categoryNumber = 9;
		int propertyNumber = 11;
		
		String price = Map.getField(line, priceNumber, 0, 0, pos);
		id = Map.getField(line, idNumber, 0, 0, pos);
		String category = Map.getField(line, categoryNumber, 0, 0, pos);
		String property = Map.getField(line, propertyNumber, 0, 0, pos);
	}
}
