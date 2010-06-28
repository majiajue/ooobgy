package com.alimama.loganalyzer.jobs.mrs.keywords;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
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
import org.apache.lucene.search.IndexSearcher;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsAnalyzer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsTokenization;
import com.alimama.loganalyzer.jobs.mrs.algo.Utility;
import com.alimama.loganalyzer.jobs.mrs.algo.query.QueryFlagJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.TwsWordSeperator;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.WordSeperator;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 产生查询词（Query）模块的临时基础表，对ACookie日志的提取结果，进行原始Query词进行归一化化，供下面的步骤进一步处理
 * 
 * 上游： {@link ExtractAcookie}
 * 数据格式：
 * 			query|AcookieId|date|hour|ssid	mmss:urltype:catId|mmss:urltype:catId|mmss:urltype:catId...			
 * 数据样本：
 * 			高跟鞋 韩国g96WA4WafFUCAfhDJHhibqLy2010010700s1	39:571040:2825001202741:3325001202759:1910
 * 
 * 
 * 下游： {@link ExtractQueryAcookie} 
 * 		 {@link ExtractQuerySearch}
 * 数据格式：
 * 			date|hour|query|catId|ssid|acookie|queryflag|ipv|pv
 * 数据样本：
 * 			20100107004 代 麦蒂50010388s5Rqa/ARBUcCwBAUuXqt4gN07D001010.02.0
 * 
 */
public class GenerateQueryBaseTable extends AbstractProcessor {
	
	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		static private TwsAnalyzer twsAnalyzer = null;
		static private IndexSearcher indexSearcher = null;
		//private BrandStylePredict brandStylePredict = null;
		static private QueryFlagJudger queryFlagJudger = null; 
		static private TwsTokenization tokenization = null;
		static private Normalizer normalizer = null;
		
		public void close() throws IOException {
			if (tokenization != null) {
				tokenization.unitialize();
			}
			
			if (normalizer != null){
				normalizer.unitialize();
			}
			
			if (twsAnalyzer != null){
				twsAnalyzer.close();
			}
		}

		public void configure(JobConf job) {
			try {
				if (tokenization == null) {
					tokenization = new TwsTokenization();
					tokenization.initialize("./tws/tws/tws.conf");
					System.out.println("tokenization initialize ok");
				}
				
				if (normalizer == null) {
					normalizer = new Normalizer();
					normalizer.initialize("./normalize/normalize/conf/norm.conf");
					System.out.println("normalizer initialize ok");
				}
				
				if (twsAnalyzer == null) {
					twsAnalyzer = new TwsAnalyzer("./tws/tws/tws.conf");
					System.out.println("twsAnalyzer initialize ok");
				}

				if (indexSearcher == null) {
					indexSearcher = new IndexSearcher("./lucene_data/lucene_data");
					System.out.println("luceneIndex initialize ok");
				}

				
				WordSeperator tws = TwsWordSeperator.getInstance();
				((TwsWordSeperator)tws).setTws(tokenization);
				
				queryFlagJudger = new QueryFlagJudger("./cats", "./brands", "./types");			
				
				queryFlagJudger.setWordSperator(tws);
				
			} catch (Throwable t) {
				t.printStackTrace();
			} 
		}
		
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
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

			// Step 2) analyze all behavior data
			List<Utility.BehaviorData> behaviorDatas = Utility.analyzeBehaviorData(query, line.substring(ssidEnd + 2), tokenization, normalizer, twsAnalyzer, indexSearcher);
			
			// Step 3) generate all brands, styles, keywords
			String queryNormalized = normalizer.normalize(query, 24);	// 繁体变为简体，全角变半角，  提高分词准确性
			tokenization.segment(queryNormalized);						// 分词
			List<String> words = tokenization.getKeyWords();			// 所有的关键词
			List<String> brands = Utility.normalizeList(tokenization.getBrands(), normalizer);  // 对所有的品牌词汇进行归一化
			List<String> styles = Utility.normalizeList(tokenization.getStyles(), normalizer);  // 对所有的型号词汇进行归一化
			List<String> descs = tokenization.getDescWords();
			
			// Step 3) construct output
			for (Utility.BehaviorData data : behaviorDatas) {
					try {
						String queryUniform = Utility.generateUniformQuery(query, data.cat, words, brands, normalizer, null);
						if (queryUniform == null){
							System.err.println("Query is empty.["+ query + "]");
							System.err.println();
							return;
						}
						String flag = queryFlagJudger.judge(queryUniform, Integer.parseInt(data.cat));
//						if (queryUniform.equals("诺基亚")){
//							System.out.printf("Flag judge result of [%s] and [%s] under cat [{%s}] is {%s}:", query, queryUniform, data.cat, flag);
//							byte[] results = queryUniform.getBytes("utf-8");
//							System.out.println("---------");
//							for(byte r:results)
//								System.out.print(r);								
//							System.out.println("---------");
//						}
						outputRecord(output, day, hour, queryUniform, data.cat, flag, ssid, acookie, data.ipv, data.pv);
					} catch (Throwable e) {
						System.err.println(e);
						return;
					}
				}
		}
		
		private void outputRecord(OutputCollector<Text, Text> output, String day, String hour, String queryNormalized, String catId, String flag, String ssid, String acookie, double ipv, double pv) throws IOException {
			StringBuilder mapKey = new StringBuilder();
			mapKey.append(day).append(KQConst.SPLIT);
			mapKey.append(hour).append(KQConst.SPLIT);
			mapKey.append(queryNormalized).append(KQConst.SPLIT);
			mapKey.append(catId).append(KQConst.SPLIT);
			mapKey.append(ssid).append(KQConst.SPLIT);
			mapKey.append(acookie).append(KQConst.SPLIT);
			mapKey.append(flag);

			StringBuilder mapValue = new StringBuilder();
			mapValue.append(Double.toString(ipv)).append(KQConst.SPLIT);
			mapValue.append(Double.toString(pv));
			
			if (output != null) {
				output.collect(new Text(mapKey.toString()), new Text(mapValue.toString()));
			}

		}
	}

	public static class TopReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			double pv = 0;
			double ipv = 0;
			
			while (values.hasNext()) {
				String[] subStrings = values.next().toString().split(KQConst.SPLIT);
				
				double addPv = 0;
				double addIpv = 0;
				try {
					addIpv = Double.parseDouble(subStrings[0]);
					addPv = Double.parseDouble(subStrings[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = addIpv = 0;
				}

				pv += addPv;
				ipv += addIpv;
			}
			
			StringBuilder outputValue = new StringBuilder();
			outputValue.append(key.toString()).append(KQConst.SPLIT);
			outputValue.append(Double.toString(ipv)).append(KQConst.SPLIT);
			outputValue.append(Double.toString(pv));
			
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
			
			url = new URI(libPath + "tws.zip#tws");
			DistributedCache.addCacheArchive(url, conf);

			url = new URI(libPath + "normalize.zip#normalize");
			DistributedCache.addCacheArchive(url, conf);
			
			url = new URI(libPath + "lucene_data.zip#lucene_data");
			DistributedCache.addCacheArchive(url, conf);
			
			url = new URI(libPath + "brands#brands");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "types#types");
			DistributedCache.addCacheFile(url, conf);
			
			url = new URI(libPath + "cats#cats");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "libTwsTokenization.so#libTwsTokenization.so");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "libNormalizer.so#libNormalizer.so");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "libiconv.so.2#libiconv.so.2");
			DistributedCache.addCacheFile(url, conf);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public Class getMapper() {
		return TopMapper.class;
	}

	public Class getReducer() {
		return TopReducer.class;
	}
	
}
