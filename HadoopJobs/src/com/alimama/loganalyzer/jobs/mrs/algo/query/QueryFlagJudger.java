package com.alimama.loganalyzer.jobs.mrs.algo.query;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alimama.loganalyzer.jobs.mrs.algo.BrandStylePredict;
import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.QueryCatPredict;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsTokenization;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.QueryCatPredictor;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.WordSeperator;
import com.alimama.loganalyzer.jobs.mrs.algo.query.helper.TwsWordSeperator;
import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.BrandJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.DataJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.CatJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.DescJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.judger.ModelJudger;
import com.alimama.loganalyzer.jobs.mrs.algo.query.loader.BrandLoader;
import com.alimama.loganalyzer.jobs.mrs.algo.query.loader.CatLoader;
import com.alimama.loganalyzer.jobs.mrs.algo.query.loader.ModelLoader;
import com.alimama.loganalyzer.jobs.mrs.util.GlobalInfo;

/**
 * Query标识判断器
 * 
 	根据一个Query，预测该Query所属类目，然后对Query进行分词，对每个词进行判断，得出Query各个词属性。
 
  	QueryFlag的返回是：
		类目词|品牌词|型号词|修饰词|普通词
		
	例如：
		10011
		00101
		
	如果一个Query的词，都是由类目词和品牌词构成，结果就会是：
	
		11000
		或者
		11100
		
	没有普通词，那么这个Query在前端就可以通过程序很轻易地剔除。
 * 
 * 
 * @author 明风
 * @date 2010-04-26
 */
public class QueryFlagJudger {

	//类目，品牌，型号初始化器，负责从文件系统读取数据
	private static CatLoader catLoader = new CatLoader();
	private static BrandLoader brandLoader = new BrandLoader();
	private static ModelLoader modelLoader = new ModelLoader();

	//类目，品牌，型号判断器，根据初始化器提供的数据，判断一个词，是否为类目词，品牌词和型号词
	private static CatJudger catJudger;
	private static BrandJudger brandJudger;
	private static ModelJudger modelJudger;
	//修饰词判断器
	private static DescJudger descJudger = new DescJudger();
	
	//Query类目预测器
	private static QueryCatPredictor queryCatPredictor;
	
	//Query分词器
	private static WordSeperator wordSeperator;
	
	//Query标识位类
	QueryFlag queryFlag = new QueryFlag();
	
	/**
	 * 构造器中，通过Loader读取数据，并把数据分发到不同的Judger中
	 * 
	 * 
	 * @param _catPath 		类目库位置
	 * @param _brandsPath	品牌库位置
	 * @param _stylesPath	型号库位置
	 */
	public QueryFlagJudger(String _catPath, String _brandPath, String _modelPath) {
		System.out.println("QueryFlagJudger start initing……");		
		
		try {
			catLoader.loadFromPath(_catPath);
			brandLoader.loadFromPath(_brandPath);
			modelLoader.loadFromPath(_modelPath);
			
			catJudger = new CatJudger(catLoader.getData());
			brandJudger = new BrandJudger(brandLoader.getData());
			modelJudger = new ModelJudger(modelLoader.getData());
			
			catJudger.setCatIdTree(catLoader.getCatIdTree());
			brandJudger.setCatIdTree(catLoader.getCatIdTree());
			modelJudger.setCatIdTree(catLoader.getCatIdTree());
			
			System.out.println("QueryFlagJudger init successfully!");
			
		} catch (Throwable t) {
			System.out.println("QueryFlagJudger init failed!");
			System.err.println(t);
			t.printStackTrace();
		}		
	}

	public void setQueryCatPredict(QueryCatPredictor _queryCatPredict){
		this.queryCatPredictor = _queryCatPredict;
	}
	
	public void setWordSperator(WordSeperator _wordSeperator){
		this.wordSeperator = _wordSeperator;
	}
	
	//禁用
	private QueryFlagJudger(){
		super();
	}
	
	
	/**
	 * 判断Query词的类型，调用该接口，表示Query词为归一化，未分词，需要处理，并且没有预测出类目
	 * 
	 * @param query 原始Query词
	 * 
	 * @return	标识字符串
	 */
	public String judge(String query) {
		if (this.queryCatPredictor == null){
			throw new RuntimeException("类目预测器为空，Query判断器不能正常工作");
		}			
		int catPredicted = queryCatPredictor.predict(query);
		return judge(query, catPredicted);
	}

	
	/**
	 * Query词未归一化，未分词，已经预测好类目
	 * 
	 * @param query			原始Query词
	 * @param catPredicted	Query词所在的类目
	 * @return
	 */
	public  String judge(String query, int catPredicted) {
		if (this.wordSeperator == null){
			throw new RuntimeException("分词器为空，Query判断器不能正常工作");
		}		
		
		List<String> words = wordSeperator.segment(query);
		List<String> descs = wordSeperator.getDescWords();
		
		GlobalInfo.debugMessage("分词结果:"+words);
		GlobalInfo.debugMessage("描述词:"+descs);
		
		return judge(words, descs, catPredicted);
	}
	
	/**
	 * Query词已经分词，已经预测好类目
	 * 
	 * @param words			分词后的结果
	 * @param descWords		修饰词
	 * @param catPredicted
	 * @return
	 */
	public String judge(List<String> words , List<String> descWords, int catPredicted){
		descJudger.init(descWords);		
		queryFlag.reset();
	
		for (String word: words){
			GlobalInfo.debugMessage("开始判断:[" + word + "]");
			int catId = catJudger.judge(word, catPredicted);
			if (catId > 0){
				queryFlag.setCat(true);
				GlobalInfo.debugMessage("1.类目词");
				continue;
			}			
			
			int brandId = brandJudger.judge(word, catPredicted);			
			if (brandId > 0){
				queryFlag.setBrand(true);
				GlobalInfo.debugMessage("2.品牌词");
				continue;
			}
			
			int modelId = modelJudger.judge(word, catPredicted);			
			if (modelId > 0){
				queryFlag.setModel(true);
				GlobalInfo.debugMessage("3.型号词");
				continue;
			}
				
			//修饰词判断
			if (descJudger.judge(word)){
				queryFlag.setDesc(true);
				GlobalInfo.debugMessage("4.修饰词");
				continue;
			}

			//如果不是任何一种，则为普通词
			queryFlag.setNormal(true);
			GlobalInfo.debugMessage("5.普通词");
		}
		
		return queryFlag.getFlag();		
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
		List<String> wordsNormalized = normalizeList(words, 380, normalizer); // 对所有词汇进行归一化（同义词替换，停用词过滤，大写变小写）
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
	
	public static void main(String[] args){
		if(args.length != 2) {
			System.out.println("usage: QueryFlagJudger <Query> <CatId>");
			return;
		}		

		GlobalInfo.setDebugOn(true);
		
		TwsTokenization tokenization = new TwsTokenization();
		tokenization.initialize("/usr/local/libdata/tws/tws.conf");
		
		Normalizer normalizer = new Normalizer();
		normalizer.initialize("/home/a/share/phoenix/normalize/conf/norm.conf");
		
		WordSeperator tws = TwsWordSeperator.getInstance();
		((TwsWordSeperator)tws).setTws(tokenization);
		
		QueryFlagJudger queryFlagJudger = new QueryFlagJudger("./data/cats", "./data/brands", "./data/types");		
		queryFlagJudger.setWordSperator(tws);

		String query = args[0];

		String queryNormalized = normalizer.normalize(query, 24);
		tokenization.segment(queryNormalized);
		List<String> words = tokenization.getKeyWords();
		List<String> descs = tokenization.getDescWords();
	

		String queryUniformed = generateUniformQuery(null, null, words, null, normalizer, null);
		
		String catId = args[1];
		
		String queryFlag = queryFlagJudger.judge(words, descs, Integer.parseInt(catId));
		
		System.out.println("分词后的结果是:" + words);
		System.out.printf("归一后的结果是[%s]\n", queryUniformed);
		System.out.printf("判断结果是[%s]\n", queryFlag);
		System.out.println();
		
		GlobalInfo.setDebugOn(false);
	}

}


