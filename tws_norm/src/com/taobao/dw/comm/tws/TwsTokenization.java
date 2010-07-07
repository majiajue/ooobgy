package com.taobao.dw.comm.tws;

import java.util.LinkedList;
import java.util.List;

/**
 * TwsTokenization淘宝分词器</p> singleton版本
 * 
 * @author zhouxiaolong.pt
 * 
 */
public class TwsTokenization {
	private static final String split = "\u0002";

	private List<String> brands;
	private List<String> keyWords;
	private List<String> styles;
	private List<String> catWords;
	private List<String> descWords;

	static {
		System.loadLibrary("TwsTokenization");
	}

	public native String tokenization(String words);

	public native void initialize(String confPath);

	public native void unitialize();

	private volatile static TwsTokenization INSTANCE;

	/**
	 * 禁用构造保证singleton
	 */
	private TwsTokenization() {
		brands = new LinkedList<String>();
		keyWords = new LinkedList<String>();
		styles = new LinkedList<String>();
		catWords = new LinkedList<String>();
		descWords = new LinkedList<String>();
	}

	/**
	 * 获得TwsTokenization</p> 最简单的实现，线程安全交由Hadoop管理</p>
	 * 
	 * @return
	 */
	public static TwsTokenization getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TwsTokenization();
		}

		return INSTANCE;
	}

	public boolean segment(String words) {
		brands.clear();
		keyWords.clear();
		styles.clear();
		catWords.clear();
		descWords.clear();

		String tokenResult = tokenization(words);
		if (tokenResult != null && !tokenResult.isEmpty()) {
			String[] subWords = tokenResult.split(split);
			for (int i = 0; i < subWords.length; i += 2) {
				String word = subWords[i];
				String type = subWords[i + 1];
				if (type.indexOf('B') != -1) {
					brands.add(word);
				}
				if (type.indexOf('S') != -1) {
					styles.add(word);
				}
				if (type.indexOf('C') != -1) {
					catWords.add(word);
				}
				if (type.indexOf('D') != -1) {
					descWords.add(word);
				}

				keyWords.add(word);
			}
		}

		return true;
	}

	public List<String> getBrands() {
		return brands;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public List<String> getStyles() {
		return styles;
	}

	public List<String> getCatWords() {
		return catWords;
	}

	public List<String> getDescWords() {
		return descWords;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: TwsTokenization <Query>");
			return;
		}
		TwsTokenization tokenization = new TwsTokenization();
		tokenization.initialize("/usr/local/libdata/tws/tws.conf");
		tokenization.segment(args[0]);
		List<String> words = tokenization.getKeyWords();
		System.out.println("Tws分词结果：" + words);

		List<String> descWords = tokenization.getDescWords();
		System.out.println("修饰词：" + descWords);

		tokenization.unitialize();
	}
}
