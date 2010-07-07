package com.taobao.dw.common.udf;

import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.taobao.dw.comm.tws.TwsTokenization;

/**
 * µ¥´¿·Ö´ÊSegmentµÄUDF
 * @Author: zhouxiaolong.pt
 * @created: 2010-7-7
 **/

public class SegmentUDF extends UDF {
	private static final String HADOOP_TWS_CONF = "./tws/tws/tws.conf";
	
	private static TwsTokenization tokenization;
	
	public String evaluate(String query) {
		return segment(query);
	}
	
	private String segment(String query){	
		getTokenization().segment(query);
		List<String> words = getTokenization().getKeyWords();
		
		StringBuilder keywords = new StringBuilder();
		for (String word : words) {
			keywords.append(word).append(" ");
		}
		
		return keywords.toString().trim();
	}
	
	private TwsTokenization getTokenization() {
		if (tokenization == null) {
			tokenization = TwsTokenization.getInstance();
			tokenization.initialize(HADOOP_TWS_CONF);
		}
		
		return tokenization;
	}
}
