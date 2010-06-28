package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

// Lucene Analyzer  π”√Tws∑÷¥ 
public class TwsAnalyzer extends Analyzer {
	String confPath = null;
	
	public TwsAnalyzer(String _confPath) {
		confPath = _confPath;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		//System.out.println("tokenStream" + fieldName);
		return new TwsLuceneTokenizer(confPath, reader);
	}

	// reuse
	public TokenStream reusableTokenStream(String fieldName, Reader reader) {
		//System.out.println("reusableTokenStream" + fieldName);
		TwsLuceneTokenizer prevTokenizer = (TwsLuceneTokenizer)getPreviousTokenStream();
		if (prevTokenizer == null) {
			TokenStream stream = tokenStream(fieldName, reader);
			this.setPreviousTokenStream(stream);
			return stream;
		} else {
			try {
				prevTokenizer.reset(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prevTokenizer;
		}
	}
}
