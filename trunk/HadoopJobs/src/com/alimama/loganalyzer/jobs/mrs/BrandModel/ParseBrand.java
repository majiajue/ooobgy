package com.alimama.loganalyzer.jobs.mrs.BrandModel;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.net.URI;
import java.net.URISyntaxException;

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
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import com.alimama.loganalyzer.common.AbstractProcessor;

public class ParseBrand extends AbstractProcessor {
	public static final String SPLIT = "\u0001";
	public static final String OUTTITLE = "Parse";
	
	public static class Map extends MapReduceBase implements
	Mapper<LongWritable, Text, Text, Text> {
		private java.util.Map<String, String> brandType = null;
		
		public void configure(JobConf job) {
			// BrandType.txt
			if (brandType == null) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./BrandModel.conf"), "utf-8"));
					brandType = new java.util.HashMap<String, String>();

					String line = reader.readLine();
					while (line != null && !line.isEmpty()) {
						String[] items = line.split(",");
						if( items.length >= 5 ) {
							if( !items[1].contains("其他") ) {
								String key = items[0];
								String value = String.format("%s,%s,%s,%s", items[1], items[2], items[3], items[4]);
								brandType.put(key, value);
							}
						}
						line = reader.readLine();
					}
					reader.close();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			String line = value.toString();
			if( line.startsWith("Merge3") ) {// (MergeValueData的输出文件)
				
				String[] sub = line.split(SPLIT);
				if( sub.length < 6 )
					return;
				/*
				 * category_id, property_id, property_name, value_id, value_data,
				 * parent_value_id, parent_property_id, status 
				 */
				String property_name = sub[3];
				String value_data = sub[5];
				String type = "0";
				String match_id = "0";
				
				// 看brandtype.conf文件,先筛选
				Iterator<Entry<String, String>> it = brandType.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = (Entry<String, String>)it.next();
					String key1 = entry.getKey(); 
					String value1 = entry.getValue();
					String[] sub1 = value1.split(",");
					//data, type, use, affect
					if (sub1[2].equals("0") && sub1[3].contains("1") && property_name.contains(sub1[0])) {
						type = sub1[1];
						match_id = key1;
					}
				}
				
				// 品牌的特殊处理
				if( type.equals("1") ) {
					if (property_name.contains(value_data))
						type = "2";
				}
				
				// 过滤
				it = brandType.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = (Entry<String, String>)it.next();
					String key1 = entry.getKey(); 
					String value1 = entry.getValue();
					String[] sub1 = value1.split(",");
					//data, type, use, affect
					if (sub1[2].equals("0") && sub1[3].contains("2") && property_name.contains(sub1[0])) {
						if( type.equals(sub1[1]) ) {
							type = "0";
							match_id = key1;
						}
					}
				}
				
				String value1 = property_name + SPLIT + match_id + SPLIT + type;
				output.collect(new Text(sub[2]), new Text(value1));
			}	
		}
	}
	
	public static class Reduce extends MapReduceBase implements
	Reducer<Text, Text, Text, Text> {
		
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			
			String property_id = key.toString();
			String type = "0";
			String property_name = null;
			String match_id = null;
			boolean bOut = false;
			
			while (values.hasNext()) {
				bOut = true;
				String line = values.next().toString();
				String[] sub = line.split(SPLIT);
				match_id = sub[1];
				property_name = sub[0];
				if (sub[2].equals("1") || type.equals("1"))
					type = "1";
				else if(sub[2].equals("2") && !type.equals("1"))
					type = "2";
				else if(type.equals("0"))
					type = "0";
			}
			
			if (bOut) {
				String value = OUTTITLE + SPLIT + property_id + SPLIT + property_name + SPLIT + match_id + SPLIT + type;
				output.collect(new Text(value), new Text(""));
			}
		}
	}
	
	public Class getMapper() {
		return Map.class;
	}
	
	public Class getReducer() {
		return Reduce.class;
	}
	
	protected void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		DistributedCache.createSymlink(conf);
		URI url;
		try {
			String libPath = "/group/tbads/sds/mrs/libs/";
			url = new URI(libPath + "BrandModel.conf#BrandModel.conf");
			DistributedCache.addCacheFile(url, conf);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}