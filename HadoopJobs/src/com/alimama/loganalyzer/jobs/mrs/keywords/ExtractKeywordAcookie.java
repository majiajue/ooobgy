package com.alimama.loganalyzer.jobs.mrs.keywords;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 
 * 抽取并转换出关键词(Keyword)相关的Acookie详细信息，插入搜索关键词用户明细表（DM_FACT_KEYWORD_USER）用于生成UV
 * 
 * 上游： {@link GeneratedKeywordFormatter}
 * 
 * @author 明风
 * @created 2010-03-08
 * 
 */

public class ExtractKeywordAcookie extends AbstractProcessor {

	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private Map<String, String> cats = new ConcurrentHashMap<String, String>();
		String dataPath = "./synonyms_cat";
		
		public void configure(JobConf jobIn) {
			super.configure(jobIn);
			BufferedReader bufread = null;
			String line = null;
			try {
				bufread = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath), KQConst.UTF_8_ENCODING));
				while ((line = bufread.readLine()) != null) {
					String[] items = line.split(KQConst.SPLIT);
					if (items.length < 3) {
						continue;
					}
					String name = items[1];
					name = name.replace(" ", "");
					String cat = items[0];
					String truename = items[2];
					cats.put(cat + "\"" + name, truename);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufread != null) bufread.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)  throws IOException {
			String line = value.toString();
			
			int dayEnd = line.indexOf(KQConst.SPLIT);
			int hourEnd = line.indexOf(KQConst.SPLIT, dayEnd + 1);
			int wordEnd = line.indexOf(KQConst.SPLIT, hourEnd + 1);
			int wordTypeEnd = line.indexOf(KQConst.SPLIT, wordEnd + 1);
			int typeEnd = line.indexOf(KQConst.SPLIT, wordTypeEnd + 1);
			int brandEnd = line.indexOf(KQConst.SPLIT, typeEnd + 1);
			int catEnd = line.indexOf(KQConst.SPLIT, brandEnd + 1);
			int acookieEnd = line.indexOf(KQConst.SPLIT, catEnd + 1);
			//int ssidEnd = line.indexOf(KQConst.split, acookieEnd + 1);			
			int ipvEnd = line.indexOf(KQConst.SPLIT, acookieEnd + 1);
			int pvEnd = line.indexOf(KQConst.SPLIT, ipvEnd + 1);
			int ipvBrandEnd = line.indexOf(KQConst.SPLIT, pvEnd + 1);
			int pvBrandEnd = line.indexOf(KQConst.SPLIT, ipvBrandEnd + 1);
			
			String day = line.substring(0, dayEnd);
			String hour = line.substring(dayEnd + 1, hourEnd);
			String word = line.substring(hourEnd + 1, wordEnd);
			String wordtype = line.substring(wordEnd + 1, wordTypeEnd);
			String type = line.substring(wordTypeEnd + 1, typeEnd);
			String brand = line.substring(typeEnd + 1, brandEnd);
			String cat = line.substring(brandEnd + 1, catEnd);
			String acookie = line.substring(catEnd + 1, acookieEnd);
			
			if (dayEnd > 0 && hourEnd > 0 && acookieEnd > 0 && wordEnd > 0 && typeEnd > 0 && catEnd > 0 && wordTypeEnd > 0
					&& brandEnd > 0 && ipvEnd > 0 && pvEnd > 0 && ipvBrandEnd > 0 && pvBrandEnd > 0) {
				String rword = word.replace(" ", "");
				String tmp = cat + "\"" + rword;
				String tmp1 = cats.get(tmp);
									
				if(tmp1 != null)
				   word = tmp1;
				
				StringBuilder mapKey = new StringBuilder();
				mapKey.append(day).append(KQConst.SPLIT);
				mapKey.append(hour).append(KQConst.SPLIT);
				mapKey.append(word).append(KQConst.SPLIT);
				mapKey.append(wordtype).append(KQConst.SPLIT);
				mapKey.append(type).append(KQConst.SPLIT);
				mapKey.append(brand).append(KQConst.SPLIT);
				mapKey.append(cat).append(KQConst.SPLIT);
				mapKey.append(acookie);
				//.append(KQConst.split);
				//mapKey.append(ssid);
				
				StringBuilder mapValue = new StringBuilder();
				mapValue.append(line.substring(ipvEnd + 1, pvEnd)).append(KQConst.SPLIT);
				mapValue.append(line.substring(ipvBrandEnd + 1, pvBrandEnd));
				
				output.collect(new Text(mapKey.toString()), new Text(mapValue.toString()));
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			double pv = 0;
			double pv_brand = 0;
			
			while (values.hasNext()) {
				String[] counts = values.next().toString().split(KQConst.SPLIT);
				double addPv = 0;
				double addPv_brand = 0;

				try {
					addPv = Double.parseDouble(counts[0]);
					addPv_brand = Double.parseDouble(counts[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = addPv_brand  = 0;
				}

				pv += addPv;
				pv_brand += addPv_brand;
			}
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(key.toString()).append(KQConst.SPLIT);
			outputValue.append(Double.toString(pv)).append(KQConst.SPLIT);
			outputValue.append(Double.toString(pv_brand)).append(KQConst.SPLIT);
			output.collect(new Text(outputValue.toString()), null);
		}
	}

	public void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		DistributedCache.createSymlink(conf);
		URI url;
		try {
			String libPath = conf.get("lib_path") + "/";
			
			url = new URI(libPath + "cats1.csv#synonyms_cat");
			DistributedCache.addCacheArchive(url, conf);
						
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
	}

	public Class getMapper() {
		return TopMapper.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}
}
