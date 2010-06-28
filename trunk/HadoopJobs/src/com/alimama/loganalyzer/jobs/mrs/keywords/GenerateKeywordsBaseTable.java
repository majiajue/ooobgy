package com.alimama.loganalyzer.jobs.mrs.keywords;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.algo.BrandStylePredict;
import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsAnalyzer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsTokenization;
import com.alimama.loganalyzer.jobs.mrs.algo.Utility;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


/**
 * 生成关键词（Keyword）模块的基础表，对ACookie日志的提取结果，做【框架，分词，归一，过滤，映射，统计】几个常规操作，把原始文本进行格式化，供下面的步骤进一步处理
 * 
 * 上游： {@link ExtractAcookie}
 * 
 * 下游： {@link ExtractKeywordAcookie}
 * 		 {@link ExtractKeywordSearch}
 * 
 */

public class GenerateKeywordsBaseTable extends AbstractProcessor {
	private static final String QUERY = "1";
	private static final String BROWSER = "2";

	public static class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private TwsAnalyzer twsAnalyzer = null;
		private IndexSearcher indexSearcher = null;
		private BrandStylePredict brandStylePredict = null;
		private TwsTokenization tokenization = null;
		private Normalizer normalizer = null;
		
		private Map<String, String> brandsMap = new ConcurrentHashMap<String, String>();
		String dataPath = "./brands";

		public void close() throws IOException {
			if (tokenization != null) {
				tokenization.unitialize();
			}
		}

		public void configure(JobConf job) {
			super.configure(job);
			BufferedReader bufread = null;
			String line = null;

			try {
				bufread = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath), KQConst.UTF_8_ENCODING));
				while ((line = bufread.readLine()) != null) {
					String[] items = line.split(KQConst.SPLIT);
					if (items.length != 5) {
						continue;
					}

					String name = items[4];
					brandsMap.put(items[3], name);

				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufread != null)
						bufread.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (tokenization == null) {
				tokenization = new TwsTokenization();
				tokenization.initialize("./tws/tws/tws.conf");
			}

			if (normalizer == null) {
				normalizer = new Normalizer();
				normalizer.initialize("./normalize/normalize/conf/norm.conf");
			}

			if (twsAnalyzer == null) {
				twsAnalyzer = new TwsAnalyzer("./tws/tws/tws.conf");
			}
			System.out.println("twsAnalyzer initialize ok");

			if (indexSearcher == null) {
				try {
					indexSearcher = new IndexSearcher("./lucene_data/lucene_data");
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("luceneIndex initialize ok");

			// Step 4) Create BrandStylePredict
			brandStylePredict = new BrandStylePredict("./cats", "./brands",
					"./types", tokenization, normalizer);
			brandStylePredict.initialize();
			System.out.println("cats_brands_types initialize ok");
		}
		
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
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
			
			// Step 2) generate all brands
			// Step 3) generate all types
			String queryNormalized = normalizer.normalize(query, 24);// 繁体变为简体，全角变半角，  提高分词准确性
			tokenization.segment(queryNormalized);	// 分词
	
			List<String> words = normalizeList(tokenization.getKeyWords()); // 对所有的词汇进行归一化（同义词替换，停用词过滤，大写变小写）
			List<String> brands = normalizeList(tokenization.getBrands());  // 对所有的品牌词汇进行归一化
			List<String> styles = normalizeList(tokenization.getStyles());  //    ~    型号~
			//List<String> catWords = normalizeList(tokenization.getCatWords());
			List<String> descWords = normalizeList(tokenization.getDescWords());  // ~产品修饰词~
			//System.out.println(words.toString());
			//System.out.println(brands.toString());
			//System.out.println(styles.toString());
			//System.out.println(descWords.toString());
			
			// Step 4) generate all category  生成所有的可能的类目，对本次搜索按权重进行划分
			List<Utility.BehaviorData> behaviorDatas = new LinkedList<Utility.BehaviorData>();
			List<Utility.BehaviorData> simulatedBehaviorDatas = new LinkedList<Utility.BehaviorData>();
			String[] behaviors = line.substring(ssidEnd + 2).split(KQConst.SPLIT);
			if (behaviors.length % 3 != 0 ) {
				return;
			}
			int pos = 0;
			while (pos < behaviors.length && behaviors[pos+1].equalsIgnoreCase(BROWSER)){
				pos += 3;
			}
			
			if (pos < behaviors.length) {
				while(pos < behaviors.length) {
					// find next search
					int curSearch = pos;
					//boolean noCat = true;//behaviors[curSearch+2].equalsIgnoreCase("0");
					
					pos = curSearch + 3;
					while (pos < behaviors.length && behaviors[pos+1].equalsIgnoreCase(BROWSER)){
						pos += 3;
					}
					
					if (pos == curSearch + 3) {
						// no ipv
						//behaviorDatas.add(new BehaviorData(behaviors[curSearch+2], 1, 0));
						behaviorDatas.add(new Utility.BehaviorData("0", 1, 0));
						adjustCategory(behaviorDatas, query);
					} else {
						int nextSearch = pos;
						for (int temp = curSearch + 3; temp < nextSearch; temp += 3) {
							behaviorDatas.add(new Utility.BehaviorData(behaviors[temp+2], 3.0 / (pos - curSearch - 3), 1));
						}
					}
					
					String simulateCat = Utility.simulateCat(behaviorDatas);
					behaviorDatas.clear();
					simulatedBehaviorDatas.add(new Utility.BehaviorData(simulateCat, 1, (pos - curSearch - 3) / 3));
				}
			}
			
			// Step 5) Adjust category which hasn't been specified    对所有没有ipv的搜索进行类目预测并拆分
			//adjustCategory(behaviorDatas, query);
			
			// Step 6) construct output  构造输出
			for (Utility.BehaviorData data : simulatedBehaviorDatas) {
				List<String> realBrands = new LinkedList<String>();
				List<String> realStyles = new LinkedList<String>();
				// 根据品牌库，型号库，类目库来预测可能的再当前目录下的品牌，型号组合
				List<BrandStylePredict.BrandStyle> brandStyles = brandStylePredict.predict(data.cat, brands, styles, realBrands, realStyles);
				BrandStylePredict.BrandStyle brandStyle = Utility.simulateBrandStyle(brandStyles);
				//System.out.println(brandStyles);
				
				// 对每个可能的品牌型号组合，并推断关键词的类型，然后输出最终结果
				//for (BrandStylePredict.BrandStyle brandStyle : brandStyles) {
				for (String word : words) {
					int wordType = 5;
					if (realStyles.contains(word)) {
						wordType = 1;
					} else if (realBrands.contains(word)) {
						wordType = 2;
						if (brandsMap.get(brandStyle.brandId) != null)
							word = brandsMap.get(brandStyle.brandId);
					} else if (brandStylePredict.isCatWord(word, data.cat)) {
						wordType = 3;
					} else if (descWords.contains(word)) {
						wordType = 4;
					}
					double pv = data.pv;// * brandStyle.weight;
					double ipv = data.ipv;// * brandStyle.weight;
					outputRecord(output, day, hour, word, wordType, brandStyle.styleId, brandStyle.brandId, data.cat, acookie, ssid, ipv, pv, ipv / words.size(), pv/ words.size());
				}
				//}
			}
		}
		
		private void outputRecord(OutputCollector<Text, Text> output, String day, String hour, String word, int wordType, String styleId, String brandId, String catId, String acookie, String ssid, double ipv, double pv, double ipv_brand, double pv_brand) throws IOException {
			StringBuilder mapKey = new StringBuilder();
			mapKey.append(day).append(KQConst.SPLIT);
			mapKey.append(hour).append(KQConst.SPLIT);
			mapKey.append(word).append(KQConst.SPLIT);
			mapKey.append(Integer.toString(wordType)).append(KQConst.SPLIT);
			mapKey.append(styleId).append(KQConst.SPLIT);
			mapKey.append(brandId).append(KQConst.SPLIT);
			mapKey.append(catId).append(KQConst.SPLIT);
			mapKey.append(acookie).append(KQConst.SPLIT);
			//因为关键词搜索暂时不区分主动和被动，所以暂时不把ssid加入后续处理
			//mapKey.append(ssid).append(split);
			
			StringBuilder mapValue = new StringBuilder();
			mapValue.append(Double.toString(ipv)).append(KQConst.SPLIT);
			mapValue.append(Double.toString(pv)).append(KQConst.SPLIT);
			mapValue.append(Double.toString(ipv_brand)).append(KQConst.SPLIT);
			mapValue.append(Double.toString(pv_brand)).append(KQConst.SPLIT);
			

			if (output != null) {
				output.collect(new Text(mapKey.toString()), new Text(mapValue.toString()));
			}
		}
		
		private void adjustCategory(List<Utility.BehaviorData> behaviorDatas, String keywords) {
			double pv = 0;
			double ipv = 0;
			
			for (int i = behaviorDatas.size() - 1; i >= 0; i --) {
				Utility.BehaviorData behavior = behaviorDatas.get(i);
				String cat = behavior.cat;
				
				boolean transform = false;
				if (cat.isEmpty() || cat.equalsIgnoreCase("0")) {
					transform = true;
				} else {
					try {
						Integer.parseInt(cat);
					} catch (NumberFormatException e) {
						transform = true;
					}
				}
				
				if (transform) {
					behaviorDatas.remove(i);
					pv += behavior.pv;
					ipv += behavior.ipv;
				}
			}
			
			if (pv > 1e-2) {
				java.util.Map<Integer, Double> mapCats = new java.util.HashMap<Integer, Double>();
				if (keywords.indexOf("******") < 0) {   //lucene bug, 会导致长时间不能返回
					try {
						QueryParser parser = new QueryParser("query", twsAnalyzer);
						String keywordsNormalized = normalizer.normalize(keywords);
						Query query = parser.parse(keywordsNormalized);
						int weights[] = { 5, 3, 1 };
						indexSearcher.rewrite(query);
						Hits hits = indexSearcher.search(query);
						for (int j = 0; j < hits.length() && j < 3; j++) {
							String[] catDataIndexed = hits.doc(j).get("cat").split(KQConst.SPLIT);

							for (int k = 1; k < catDataIndexed.length; k += 2) {
								int inferCatNumber = Integer.parseInt(catDataIndexed[k]);
								int belief = Integer.parseInt(catDataIndexed[k + 1]);
								double addBelief = weights[j] * belief;

								if (mapCats.containsKey(inferCatNumber)) {
									double orgBelief = mapCats.get(inferCatNumber);
									mapCats.put(inferCatNumber, orgBelief + addBelief);
								} else {
									mapCats.put(inferCatNumber, addBelief);
								}
							}
						}
					} catch (IOException e) {
						// Do nothing
					} catch (ParseException e) {
						// Do nothing
					} catch (TooManyClauses e) {
						// Do nothing
					} catch (Exception e) {
						// Do nothing
					}
				}

				if (mapCats.isEmpty()) {
					behaviorDatas.add(new Utility.BehaviorData("0", pv, ipv));
				} else {
					double allWeights = 0;
					for (double d : mapCats.values()) {
						allWeights += d;
					}
					for (java.util.Map.Entry<Integer, Double> entry : mapCats.entrySet()) {
						double ratio = entry.getValue() / allWeights;
						behaviorDatas.add(new Utility.BehaviorData(Integer.toString(entry.getKey()), pv * ratio, ipv * ratio));
					}
				}
			}
		}
		public List<String> normalizeList(List<String> words) {
			return normalizeList(words, 380);
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
			// Step 1) add all data, ipv
			double pv = 0;
			double ipv = 0;
			double pv_brand = 0;
			double ipv_brand = 0;
			while (values.hasNext()) {
				String[] counts = values.next().toString().split(KQConst.SPLIT);
				double addPv = 0;
				double addIpv = 0;
				double addPv_brand = 0;
				double addIpv_brand = 0;

				try {
					addIpv = Double.parseDouble(counts[0]);
					addPv = Double.parseDouble(counts[1]);
					addIpv_brand = Double.parseDouble(counts[2]);
					addPv_brand = Double.parseDouble(counts[3]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					addPv = addIpv = addPv_brand = addIpv_brand = 0;
				}

				pv_brand += addPv_brand;
				ipv_brand += addIpv_brand;
				pv += addPv;
				ipv += addIpv;
			}
			
			StringBuilder outputValue = new StringBuilder();
			
			outputValue.append(key.toString());
			outputValue.append(ipv).append(KQConst.SPLIT);
			outputValue.append(pv).append(KQConst.SPLIT);
			outputValue.append(ipv_brand).append(KQConst.SPLIT);
			outputValue.append(pv_brand).append(KQConst.SPLIT);
			
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

		if (conf.get("mapred.task.timeout") != null){
			conf.set("mapred.task.timeout", conf.get("mapred.task.timeout"));
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
			
			url = new URI(libPath + "lucene_data.zip#lucene_data");
			DistributedCache.addCacheArchive(url, conf);

			url = new URI(libPath + "brands#brands");
			DistributedCache.addCacheFile(url, conf);

			url = new URI(libPath + "types#types");
			DistributedCache.addCacheFile(url, conf);
			
			url = new URI(libPath + "cats#cats");
			DistributedCache.addCacheFile(url, conf);
			
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
		return TopMapper.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}
	
	public static void main(String[] args) throws IOException {
		String line = "92062ZJeRAW7bXj8BAZWMitN+QhJ/2010010808e-s15	43:5010";
				//System.out.println("-----------------------------------------------");
		TopMapper map = new TopMapper();
		map.map(null, new Text(line), null, null);
	}
}
