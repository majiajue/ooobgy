package com.alimama.loganalyzer.jobs.mrs.algo;

import java.util.LinkedList;
import java.util.List;

// Tws所对应的JAVA包装类
public class TwsTokenization {
	private String split = "\u0002";
	
	List<String> brands;
	List<String> keyWords;
	List<String> styles;
	List<String> catWords;
	List<String> descWords;

	static {
		System.out.println("TwsTokenization is initing...");
		System.loadLibrary("TwsTokenization");
		System.out.println("TwsTokenization inited successfully!");
	}

	public native String tokenization(String words);

	public native void initialize(String confPath);

	public native void unitialize();

	public TwsTokenization() {
		brands = new LinkedList<String>();
		keyWords = new LinkedList<String>();
		styles = new LinkedList<String>();
		catWords = new LinkedList<String>();
		descWords = new LinkedList<String>();
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
			if (subWords.length %2 == 0) {
				for (int i = 0; i < subWords.length; i += 2) {
					String word = subWords[i];
					String type = subWords[i + 1];
					
					word = word.trim();
					if (word.isEmpty()) {
						continue;
					}
					
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
		TwsTokenization tokenization = new TwsTokenization();
		System.out.println(tokenization.tokenization("Baidu"));
	}
}
