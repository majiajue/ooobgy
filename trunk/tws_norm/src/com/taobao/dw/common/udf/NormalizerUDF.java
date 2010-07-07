package com.taobao.dw.common.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.taobao.dw.comm.tws.Normalizer;

/**
 * µ¥´¿Normalize UDF
 * 
 * @Author: zhouxiaolong.pt
 * @created: 2010-7-7
 **/

public class NormalizerUDF extends UDF {
	private static final String HADOOP_NORM_CONF = "./normalize/normalize/conf/norm.conf";

	
	private static Normalizer normalizer;
	
	public String evaluate(String query){
		return getNormalizer().normalize(query);		
	}
	
	private Normalizer getNormalizer(){
		if (normalizer == null) {
			normalizer = Normalizer.getInstance();
			normalizer.initialize(HADOOP_NORM_CONF);
		}
		
		return normalizer;
	}
}
