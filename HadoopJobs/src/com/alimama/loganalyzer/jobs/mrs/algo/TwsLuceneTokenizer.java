package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

// Lucene Tokenizer  π”√Tws
public class TwsLuceneTokenizer extends Tokenizer {
	private final int BUFFER_SIZE = 4096;
	char buffer[] = new char[BUFFER_SIZE];
	private static TwsTokenization tokenization = null;
	Iterator<String> curWord = null;
	int offset = 0;

	public TwsLuceneTokenizer(String _confPath, Reader in) {
		super(in);
		if (tokenization == null) {
			tokenization = new TwsTokenization();
			tokenization.initialize(_confPath);
		}
		loadReader();
	}
	
	public void close() throws IOException {
		super.close();
	}

	public void loadReader() {
		try {
			int readedChars = input.read(buffer);
			if (readedChars == -1 || readedChars > 1024) {
				// empty or too long to discard it
				curWord = null;
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append(buffer, 0, readedChars);
				tokenization.segment(builder.toString());
				curWord = tokenization.getKeyWords().iterator();
			}
			offset = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Token next() throws IOException {
		if (curWord != null && curWord.hasNext()) {
			String word = curWord.next();
			Token token = new Token(word, offset, offset + word.length());
			offset += word.length();
			return token;
		}
		return null;
	}

	public void reset(Reader input) throws IOException {
		super.reset(input);
		loadReader();
	}
}
