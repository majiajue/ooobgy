package com.alimama.loganalyzer.jobs.mrs.BrandModel;

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
import org.apache.hadoop.mapred.lib.HashPartitioner;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import com.alimama.loganalyzer.common.AbstractProcessor;

/*
 * 	联合 ParseBrand的输出文件 和 (MergeValueData的输出文件)
 */
public class GetModel extends AbstractProcessor {
	public static final String SPLIT = "\u0001";
	
	public static class Map extends MapReduceBase implements
	Mapper<LongWritable, Text, Text, Text> {
		
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			String line = value.toString();
			String[] sub = line.split(SPLIT);
			if( sub.length < 1 )
				return;
			if( sub[0].equals("Merge3") ) {// MergeValueData的输出文件
				if( sub.length < 9 )
					return;
				if( sub[8].equals("0") ) {
					String str_out = sub[1] + SPLIT + sub[2] + SPLIT + sub[3] + SPLIT + sub[4] + 
						SPLIT + sub[5] + SPLIT + sub[6];
					output.collect(new Text(sub[2]+SPLIT+"B"), new Text(str_out));
				}
			}
			else if( sub[0].equals("Parse") ) {//ParseBrand的输出文件
				if( sub.length < 5 )
					return;
				if( sub[4].contains("2") )
					output.collect(new Text(sub[1]+SPLIT+"A"), new Text(sub[3]));
			}	
		}
	}
	
	public static class Reduce extends MapReduceBase implements
	Reducer<Text, Text, Text, Text> {
		
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			String match_id = null;
			boolean bUse = false;
			
			while (values.hasNext()) {
				String line = values.next().toString();
				
				if( line.contains(SPLIT) == false )// MergeValueData的输出文件
				{
					bUse = true;
					match_id = line;
				}
				else if( bUse)
				{
					String[] sub = line.split(SPLIT);
					/* category_id, parent_value_id,
					 * property_id, property_name, 
					 * value_id, value_data
					 */
					String value = sub[0] + SPLIT + sub[5] + SPLIT + sub[1] + SPLIT + sub[2] + 
						SPLIT + sub[3] + SPLIT + sub[4];
					output.collect(new Text(value), null);
				}
			}
		}
	}
	
	public Class getMapper() {
		return Map.class;
	}
	
	public Class getReducer() {
		return Reduce.class;
	}
	
	public static class PKPartitioner<K2, V2> //控制分配的节点
		extends HashPartitioner<K2, V2> {
		public PKPartitioner() {
		}
		
		public int getPartition(K2 key, V2 value, int numReduceTasks) {
			return (key.toString().split(SPLIT)[0].hashCode() & Integer.MAX_VALUE) % numReduceTasks;
		}	
	}
	
	public static class PVComparator extends WritableComparator { //key排序
		public PVComparator() {
			super(Text.class, true);
		}

		private int compareStr(String a, String b) {
			String aTokens = a.split(SPLIT)[0];
			String bTokens = b.split(SPLIT)[0];
			//throw new RuntimeException("group is : " + a + "----" + b);
			return aTokens.compareTo(bTokens);
		}

		public int compare(Object a, Object b) {

			String aStr = a.toString();
			String bStr = b.toString();
			int i = compareStr(aStr, bStr);
			return i;
		}

		public int compare(WritableComparable a, WritableComparable b) {
			String aStr = a.toString();
			String bStr = b.toString();
			return compareStr(aStr, bStr);
		}

	}
	
	protected void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		conf.setPartitionerClass(PKPartitioner.class);
		conf.setOutputValueGroupingComparator(PVComparator.class);
	}
}