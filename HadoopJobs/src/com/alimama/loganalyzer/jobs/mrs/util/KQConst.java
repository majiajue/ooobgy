package com.alimama.loganalyzer.jobs.mrs.util;

import java.util.Map;

import com.alimama.loganalyzer.jobs.mrs.algo.query.po.Model;

/**
 * 查询词和关键字常量
 * 
 * @author 明风
 */
public class KQConst {
	public final static String SPLIT = "\u0001";
	/*
	 * 默认^A分隔符
	 */	
	public final static String DEFAULT_SPLIT = "\u0001";
	
	/*
	 * 第二种常用分隔符^B
	 */
	public final static String SECOND_SPLIT = "\u0002";

	/*
	 * 空格分隔符
	 */
	public final static String SPACE_SPLIT = " ";
	
	/*
	 * 斜杠分隔符
	 */
	public final static String SLASH_SPLIT = "/";
	
	/*
	 * TAB分隔符
	 */
	public final static String TAB_SPLIT = "\t";
	/*
	 * UTF-8编码
	 */
	public static final String UTF_8_ENCODING = "utf-8";
	
	public static final String QUERY_FIELD = "query"; 
	public static final String CLICK_FIELD = "click"; 
	public static final String CAT_FIELD = "cat";
	
	public static final int UNKNOWN_INT_ID = -99;
	public static final int NOT_EXIST_INT = -99;
	public static final String UNKNOWN_STR_ID = "-99";
	
	public static final String SEARCH_ITEM = "1"; 
	public static final String DETAIL_ITEM = "2";

}
