package com.alimama.loganalyzer.jobs.mrs.spu;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.filecache.DistributedCache;

import com.alimama.loganalyzer.common.AbstractProcessor;

public class AnalyzeSpu extends AbstractProcessor {
	public static final String TAB = "\t";
	public static final String SPLIT = "\u0001";
	public static final String SEMICOLON = ";";
	public static final String COLON = ":";
	
	public static class Map extends MapReduceBase implements
	Mapper<LongWritable, Text, Text, Text> {
		private HashSet<String> brand = new HashSet<String>();
		private HashSet<String> model = new HashSet<String>();
		
		public void configure(JobConf job) {
			BufferedReader brand_reader = null;
			BufferedReader model_reader = null;
			try {
				String line;
				
				brand_reader = new BufferedReader(new InputStreamReader(new FileInputStream("./brand_property.txt"), "utf-8"));
				while ( (line = brand_reader.readLine()) != null ) {
					String[] items = line.split(SPLIT);
					if( items.length == 2 ) {
						brand.add(items[0]);
					}
				}
				
				model_reader = new BufferedReader(new InputStreamReader(new FileInputStream("./model_property.txt"), "utf-8"));
				while ( (line = model_reader.readLine()) != null ) {
					String[] items = line.split(SPLIT);
					if( items.length == 2 ) {
						model.add(items[0]);
					}
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if( brand_reader != null )
					try {
						brand_reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if( model_reader != null )
					try {
						model_reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			String line = value.toString();
			String[] sub = line.split(TAB);
			
			if( sub.length > 5 ) { 
				String spu_id = sub[0];
				String properties = sub[1]+SEMICOLON+sub[3];
				String category_id = sub[2];
				String status = sub[4];
				String brand_id = "0", model_id = "0";
				String brand_property_id = "0", model_property_id = "0";
				boolean bBrand = false, bModel = false;
				
				//output.collect(new Text(spu_id), new Text(properties));
				
				String[] property_pair = properties.split(SEMICOLON);
				if( property_pair.length > 0 ) {
					for( String p:property_pair ) {
						if( p.isEmpty() )
							continue;
						String[] pair = p.split(COLON);
						if( pair.length != 2 ) {
							continue;
						}
						
						String property_id = pair[0];
						String value_id = pair[1];
						
						// 如果是ISBN(1636953), ISRC(1636962)，批准文号(1989854) 不做处理
						if(property_id.equals("1636953") || property_id.equals("1636962") 
								|| property_id.equals("1989854") ) {
							return;
						}
						// 充值卡(20435) PS游戏名称(2554503), 期刊名(33391) 不做处理
						if(property_id.equals("20435") || property_id.equals("2554503") 
								|| property_id.equals("33391")) {
							return;
						}
						
						if(property_id.equals("20000")) {
							brand_id = value_id;
							brand_property_id = property_id;
							bBrand=true;
						}
						else if(brand.contains(property_id) && !bBrand) {
							brand_id = value_id;
							brand_property_id = property_id;
							bBrand = true;
						}
						if(model.contains(property_id) && !bModel) {
							model_id = value_id;
							model_property_id = property_id;
							bModel = true;
						}
					}
				}
				
				if( bBrand || bModel ) {
					String strOut = spu_id + SPLIT + category_id + SPLIT + 
						brand_property_id + SPLIT + brand_id + SPLIT +
						model_property_id + SPLIT + model_id + SPLIT + status 
						+ SPLIT + properties;
					output.collect(new Text(spu_id), new Text(strOut));
				}
			} 
		}	
	}
	
	public static class Reduce extends MapReduceBase implements
	Reducer<Text, Text, Text, Text> {
		
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
		throws IOException {
			while(values.hasNext()) {
				String line = values.next().toString();
				output.collect(new Text(line), null);
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
	
		conf.set("mapred.child.java.opts", "-Xmx1536M");
	
		DistributedCache.createSymlink(conf);
		URI uri;
		try {
			String libPath = "/group/tbads/sds/mrs/libs/";
			uri = new URI(libPath + "brand_property.txt#brand_property.txt");
			DistributedCache.addCacheFile(uri, conf);
			
			uri = new URI(libPath + "model_property.txt#model_property.txt");
			DistributedCache.addCacheFile(uri, conf);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
