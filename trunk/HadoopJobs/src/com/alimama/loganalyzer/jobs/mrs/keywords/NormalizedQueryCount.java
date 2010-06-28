package com.alimama.loganalyzer.jobs.mrs.keywords;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsTokenization;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 
 * 归一化原始查询序列，并计算搜索次数
 * 
 * 上游： {@link ExtractAcookie}
 * 下游： {@link NormalizedQueryCount}
 *  
 * @author 明风
 * @created 2010-03-08
 */
public class NormalizedQueryCount extends AbstractProcessor {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private TwsTokenization tokenization = null;
		private Normalizer normalizer = null;

		public void close() throws IOException {
			if (tokenization != null) {
				tokenization.unitialize();
			}
		}

		public void configure(JobConf job) {
			// Step 1) tokenization
			if (tokenization == null) {
				tokenization = new TwsTokenization();
				tokenization.initialize("./tws/tws/tws.conf");
			}
			
			// Step 2) normalizer
			if (normalizer == null) {
				normalizer = new Normalizer();
				normalizer.initialize("./normalize/normalize/conf/norm.conf");
			}
		}
		
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)  throws IOException {
			// 统计出 归一化后的搜索串，原始搜索串，并计数
			// Step 1) generate all words
			String line = value.toString();
			int queryEnd = line.indexOf(KQConst.SPLIT);
			int acookieEnd = line.indexOf(KQConst.SPLIT, queryEnd + 1);
			int dayEnd = line.indexOf(KQConst.SPLIT, acookieEnd + 1);
			int hourEnd = line.indexOf(KQConst.SPLIT, dayEnd + 1);
			int ssidEnd = line.indexOf(KQConst.SPLIT, hourEnd + 1);

			if (queryEnd < 0 || acookieEnd < 0 || dayEnd < 0 || hourEnd < 0 || ssidEnd < 0) {
				System.err.println(line);
				return;
			}
			
			String query = line.substring(0, queryEnd);
			String acookie = line.substring(queryEnd + 1, acookieEnd);
			String day = line.substring(acookieEnd + 1, dayEnd);
			String hour = line.substring(dayEnd + 1, hourEnd);
			String ssid = line.substring(hourEnd+1, ssidEnd);
			
			String queryNormalized = normalizer.normalize(query, 24);
			tokenization.segment(queryNormalized);
			List<String> words = tokenization.getKeyWords();
			if (words.isEmpty()) {
				return;
			}
			
			List<String> wordsNormalized = normalizeList(words, 380);
			if (wordsNormalized.isEmpty()) {
				return;
			}
			
			Collections.sort(wordsNormalized);
			StringBuilder wordsSorted = new StringBuilder();
			Iterator<String> itWordNormalized = wordsNormalized.iterator();
			while (itWordNormalized.hasNext()) {
				wordsSorted.append(itWordNormalized.next());
				
				if (itWordNormalized.hasNext()) {
					wordsSorted.append(" ");
				}
			}
			String querySorted = wordsSorted.toString(); // 找到归一化后的搜索串
			
			String[] behaviors = line.substring(ssidEnd + 2).split(KQConst.SPLIT);
			if (behaviors.length % 3 != 0 ) {
				return;
			}
			
			int searchCount = 0;
			int pos = 0;
			while (pos < behaviors.length) { // 统计搜索的次数
				if (behaviors[pos+1].equalsIgnoreCase(KQConst.SEARCH_ITEM)) {
					searchCount ++;
				}
				pos += 3;
			}
			
			
			if (searchCount > 0) {
				StringBuilder mapKey = new StringBuilder();
				mapKey.append(day).append(KQConst.SPLIT);
				mapKey.append(querySorted).append(KQConst.SPLIT);
				mapKey.append(queryNormalized);

				String mapValue = Integer.toString(searchCount);
				output.collect(new Text(mapKey.toString()), new Text(mapValue));
				
			}
		}
	
		public List<String> normalizeList(List<String> words, int option) {
			if (normalizer != null) {
				List<String> wordsNormalized = new LinkedList<String>();
				for (String word : words) {
					String wordNormalized = normalizer.normalize(word, option);
					if (!wordNormalized.isEmpty() && !wordsNormalized.contains(wordNormalized)) {
						wordsNormalized.add(wordNormalized);	
					}
				}
				return wordsNormalized;
			} else {
				return words;
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			int totalSearchCount = 0;
			
			// 对搜索次数进行加和
			while (values.hasNext()) {
				String searchCount = values.next().toString();
				
				int addSearchCount = 0;
				try {
					addSearchCount = Integer.parseInt(searchCount);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addSearchCount = 0;
				}

				totalSearchCount += addSearchCount;
			}
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(key.toString()).append(KQConst.SPLIT);
			outputValue.append(Integer.toString(totalSearchCount));
			
			output.collect(new Text(outputValue.toString()), null);
		}
	}

	public void configJob(JobConf conf) {
		// Set Map/Reduce class
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		if (conf.get("mrs_max_xmx") != null){
			conf.set("mapred.child.java.opts", conf.get("mrs_max_xmx"));
		}
		// Create Local file
		DistributedCache.createSymlink(conf);
		URI url;
		try {
			String libPath = conf.get("lib_path") + "/";
			url = new URI(libPath + "libTwsTokenization.so#libTwsTokenization.so");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "tws.zip#tws");
			DistributedCache.addCacheArchive(url, conf);
			
			url = new URI(libPath + "libNormalizer.so#libNormalizer.so");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "libiconv.so.2#libiconv.so.2");
			DistributedCache.addCacheFile(url, conf);
			
			url = new URI(libPath + "normalize.zip#normalize");
			DistributedCache.addCacheArchive(url, conf);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}
}
