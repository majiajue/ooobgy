package com.alimama.loganalyzer.jobs.mrs.BrandModel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
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
 * 联合category_property_values和 (category_properties, base_property)的结果表
 */
public class MergeCatPV extends AbstractProcessor {
	public static final String SPLIT = "\u0001";
	public static final String OUTTITLE = "Merge2";
	
	public static class Map extends MapReduceBase implements
	Mapper<LongWritable, Text, Text, Text> {
		
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			String line = value.toString();
			if( line.startsWith("Merge1") ) {// (category_properties, base_property)的结果表
				String[] sub = line.split(SPLIT);
				if( sub.length >= 6 ) {
					// category_id, property_id
					String key1 = sub[1] +SPLIT+ sub[2];
					// property_name, parent_value_id, parent_property_id
					String value1 = sub[3] +SPLIT+ sub[4] +SPLIT+ sub[5];
					output.collect(new Text(key1+SPLIT+"A"), new Text(value1));
				}
			}
			else {// category_proprety_values
				String line1 = new String(value.getBytes(),0,value.getLength(),"GBK");
				String[] sub2 = line1.split(SPLIT);
				if( sub2.length >= 9 ) {
					// category_id, property_id
					String key1 = sub2[0] +SPLIT+ sub2[1];
					// value_id, status
					String value1 = sub2[2] +SPLIT+ sub2[8];
					output.collect(new Text(key1+SPLIT+"B"), new Text(value1));
				}
			}
		}
	}
	
	public static class Reduce extends MapReduceBase implements
	Reducer<Text, Text, Text, Text> {
		
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException 
		{
			String category_id = null, property_id = null;
			String property_name = null, parent_value_id = null, parent_property_id = null;
			boolean bUse = false;
			
			String key1 = key.toString();
			String[] sub = key1.split(SPLIT);
			if( sub.length == 3 )
				category_id = sub[0]; property_id = sub[1];
			
			while (values.hasNext()) {
				String line = values.next().toString();
				String[] sub1 = line.split(SPLIT);
				
				if( sub1.length == 3 )// MergeValueData的输出文件
				{
					bUse = true;
					property_name = sub1[0];
					parent_value_id = sub1[1];
					parent_property_id = sub1[2];
				}
				else if (bUse)
				{
					/*
					 * category_id, property_id, property_name, value_id, 
					 * parent_value_id, parent_property_id, status 
					 */
					String value = OUTTITLE + SPLIT + category_id + SPLIT + property_id + SPLIT + property_name + SPLIT + 
						sub1[0] + SPLIT + parent_value_id + SPLIT +	parent_property_id + SPLIT + sub1[1];
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
			int lasttab = key.toString().lastIndexOf(SPLIT);
			String relkey = key.toString().substring(0,lasttab);
			return (relkey.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
		}	
	}
	
	public static class PVComparator extends WritableComparator { //key排序
		public PVComparator() {
			super(Text.class, true);
		}

		private int compareStr(String a, String b) {
			int lasttab = a.lastIndexOf(SPLIT);
			String aTokens=a.substring(0,lasttab);
			lasttab = b.lastIndexOf(SPLIT);
			String bTokens=b.substring(0,lasttab);
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