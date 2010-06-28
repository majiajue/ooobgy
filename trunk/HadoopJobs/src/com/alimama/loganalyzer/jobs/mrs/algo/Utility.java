package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;

// 工具类(基本类)
public class Utility {
	private static final String split = "\u0001";
	private static final String QUERY = "1";
	private static final String BROWSER = "2";
	
	public static class BehaviorData {
		public String cat;
		public double pv;
		public double ipv;
		
		public BehaviorData(String _cat, double _pv, double _ipv) {
			cat = _cat;
			pv = _pv;
			ipv = _ipv;
		}
	}
	
	public static String simulateCat(List<BehaviorData> behaviorDatas) {
		String simulateCat = "0";
		
		double random = Math.random();
		for(BehaviorData behavior : behaviorDatas) {
			random -= behavior.pv;
			if (random < 1e-6) {
				simulateCat = behavior.cat;
				break;
			}
		}
		
		return simulateCat;
	}
	
	public static BrandStylePredict.BrandStyle simulateBrandStyle(List<BrandStylePredict.BrandStyle> brandStyles) {
		BrandStylePredict.BrandStyle simulateBrandStyle = null;
		
		double random = Math.random();
		for(BrandStylePredict.BrandStyle brandStyle : brandStyles) {
			random -= brandStyle.weight;
			if (random < 1e-6) {
				simulateBrandStyle = brandStyle;
				break;
			}
		}
		
		return simulateBrandStyle;
	}
	
	public static List<Utility.BehaviorData> analyzeBehaviorData(String query, String behaviorString, TwsTokenization tokenization,
			Normalizer normalizer, TwsAnalyzer twsAnalyzer, IndexSearcher indexSearcher) {
		String[] behaviors = behaviorString.split(split);
		if (behaviors.length % 3 != 0 ) {
			return null;
		}
		
		// Step 1) 对每次搜索生成其访问的类目, 并对每次的pv按照推测的概率模拟到相应的类目中
		List<Utility.BehaviorData> behaviorDatas = new LinkedList<Utility.BehaviorData>();
		List<Utility.BehaviorData> simulatedBehaviorDatas = new LinkedList<Utility.BehaviorData>();
		int pos = 0;
		while (pos < behaviors.length && behaviors[pos+1].equalsIgnoreCase(BROWSER)){
			pos += 3;
		}
		
		if (pos < behaviors.length) {
			while(pos < behaviors.length) {

				// find next search
				int curSearch = pos;
				pos = curSearch + 3;
				while (pos < behaviors.length && behaviors[pos+1].equalsIgnoreCase(BROWSER)){
					pos += 3;
				}
				
				if (pos == curSearch + 3) {
					// no ipv
					//behaviorDatas.add(new BehaviorData(behaviors[curSearch+2], 1, 0));
					behaviorDatas.add(new Utility.BehaviorData("0", 1, 0));
					adjustCategory(behaviorDatas, query, indexSearcher, normalizer, twsAnalyzer);
				} else {
					int nextSearch = pos;
					for (int temp = curSearch + 3; temp < nextSearch; temp += 3) {
						behaviorDatas.add(new Utility.BehaviorData(behaviors[temp+2], 3.0 / (pos - curSearch - 3), 1));
					}
				}
				
				String simulateCat = simulateCat(behaviorDatas);
				behaviorDatas.clear();
				simulatedBehaviorDatas.add(new Utility.BehaviorData(simulateCat, 1, (pos - curSearch - 3) / 3));
			}
		}
		
		return simulatedBehaviorDatas;
	}

	private static void adjustCategory(List<Utility.BehaviorData> behaviorDatas, String keywords, IndexSearcher indexSearcher, Normalizer normalizer, TwsAnalyzer twsAnalyzer) {
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
			if (keywords.indexOf("******") < 0) {
				try {
					QueryParser parser = new QueryParser("query", twsAnalyzer);
					String keywordsNormalized = normalizer.normalize(keywords);
					Query query = parser.parse(keywordsNormalized);
					int weights[] = { 5, 3, 1 };
					indexSearcher.rewrite(query);
					Hits hits = indexSearcher.search(query);
					for (int j = 0; j < hits.length() && j < 3; j++) {
						String[] catDataIndexed = hits.doc(j).get("cat").split(split);

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
	
	public static List<String> normalizeList(List<String> words, Normalizer normalizer) {
		return normalizeList(words, 380, normalizer);
	}

	public static List<String> normalizeList(List<String> words, int option, Normalizer normalizer) {
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
	
	public static String generateUniformQuery(String query, String cat, List<String> words, List<String> brands, Normalizer normalizer, BrandStylePredict brandStylePredict) {
		// Step 1) generate all brands, types
		List<String> wordsNormalized = Utility.normalizeList(words, 380, normalizer); // 对所有词汇进行归一化（同义词替换，停用词过滤，大写变小写）
		if (wordsNormalized.isEmpty()) {
			return null;
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
		
		String querySorted = wordsSorted.toString();  // 排序，对不同顺序的词汇调整为同一个，例如 "三星 索尼"，调整为 "索尼 三星”
		return querySorted;
	}
}
