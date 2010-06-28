package com.alimama.loganalyzer.jobs.mrs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taobao.loganalyzer.common.util.tbacookie.AcookieUtil;
import com.taobao.loganalyzer.common.util.tbacookie.SearchListContent;
import com.taobao.loganalyzer.common.util.url.URLParser;

/**
 * URL解析类，封装了对公用库LogUtil.jar库的调用
 * 
 * @author 明风
 * 
 */

public class UrlParserWrapper {
	private static final String URL_ENCODING = "gbk";
	
	private static final String TAOBAO_SEARCH_HOME_URL = "search.taobao.com";
	
	private static final String TAOBAO_SEARCH_SHOP_URL = "shopsearch.taobao.com";
    private static final String TAOBAO_LIST_HITAO_URL = "list.hitao.taobao.com";
    private static final String TAOBAO_LIST_3C_URL = "list.3c.taobao.com";
    private static final String TAOBAO_LIST_MALL_URL = "list.mall.taobao.com";
    
	private static final String SSID_KEY = "ssid";
	
	/// SSID模式，以字母s开头，后面带数字
	private static final Pattern SSID_PATTERN = Pattern.compile("(s\\d+)");
	private static final int MAX_QUERY_LENGTH = 512;

	/**
	 * 判断URL是否为搜索型URL，格式为
	 * 1)search.taobao.com
	 * 2)shopsearch.taobao.com
	 * 3)search1.taobao.com
	 * 4)search8.taobao.com
	 * 
	 * @param url
	 *            待判断URL
	 * @return 是 返回true，否返回false
	 */
	public static boolean isSearchUrl(String url) {
		if (!StringUtils.isValid(url)) { 
		    return false;
		}
		if (url.indexOf(TAOBAO_SEARCH_HOME_URL) > 0
		    || url.indexOf(TAOBAO_SEARCH_SHOP_URL) > 0
		    || AcookieUtil.isSearch1(url)
		    || AcookieUtil.isSearch8(url) ){
			return true;
		}else{
			return false;
		}
		
	}

	
    /**
     * 判断URL是否为列表型URL，格式为
     * 1)list.hitao.taobao.com
     * 2)list.3c.taobao.com
     * 3)list.mall.taobao.com
     * 
     * @param url
     *            待判断URL
     * @return 是 返回true，否返回false
     */
    public static boolean isListUrl(String url) {
        if (!StringUtils.isValid(url)) {
            return false;
        }
        if (url.indexOf(TAOBAO_LIST_HITAO_URL) > 0 
            || url.indexOf(TAOBAO_LIST_3C_URL) > 0
            || url.indexOf(TAOBAO_LIST_MALL_URL) > 0 ){
            return true;
        }else{
            return false;
        }
        
    }
    
    
	/**
	 * 是否为宝贝详情页URl
	 * 
	 * @param url
	 *            待判断URL
	 * @return 是否为宝贝详情URL
	 */
	public static boolean isItemUrl(String url) {
		return AcookieUtil.isItem(url);
	}

	/**
	 * 提取URL中的查询词
	 * 
	 * @param url
	 *            待提取的URL
	 * 
	 * @return查询词
	 */

	public static String parseQuery(String url) {
	    String query = "";
	    Map<String, String> params = null;
	    
	    if (isSearchUrl(url) || isListUrl(url)) {
	        params = AcookieUtil.getAllURLPara(url);
	        if (params.containsKey("q")) {
	            query = params.get("q");
	        }
	    }
	      
	    if (StringUtils.isValid(query)) {  
	        try {
	            query = URLDecoder.decode(query, URL_ENCODING);
	        } catch (Throwable t) {
	            t.printStackTrace();
	            System.err.println("Query词解码失败");
	        }
	    }
	    
        query = query.trim();
	    if (isValueQuery(query)){
		    return query;
	    }else{
	    	return null;
	    }

      
	}

	/**
	 * 提取URL中的SSID
	 * 
	 * @param referUrl 搜索URL
	 * @return SSID
	 */
	public static String parseSsid(String referUrl) {
		if (!isSearchUrl(referUrl)) {
			return KQConst.UNKNOWN_STR_ID;
		}
		Hashtable<String, String> ht = URLParser.parsePara(referUrl);
		String ssid = ht.get(SSID_KEY);

		if (ssid == null) {
			return KQConst.UNKNOWN_STR_ID;
		}

		Matcher matcher = SSID_PATTERN.matcher(ssid);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return KQConst.UNKNOWN_STR_ID;
		}
	}

	/**
	 * 提取URL中的类目ID
	 * 
	 * @param url 待提取URL
	 * @return 类目ID
	 * 
	 */
	public static int parseCategory(String url) {
		Map<String, String> urlInfoMap = AcookieUtil.parseURLInfo(url);
		return parseCategory(urlInfoMap);

	}

	/**
	 * 提取宝贝详情页的类目
	 * 
	 * @param urlInfoMap
	 * @return 类目ID
	 */
	private static int parseCategory(Map<String, String> urlInfoMap) {
	    
		String category = (String) urlInfoMap.get("category");
		if (StringUtils.isValid(category)) {
			int sep = category.lastIndexOf("_");
			if (sep >= 0) {
				category = category.substring(sep + 1);
			}
			try {
				return Integer.parseInt(category);
			} catch (NumberFormatException e) {
				return KQConst.UNKNOWN_INT_ID;
			}
		} else {
			return KQConst.UNKNOWN_INT_ID;
		}
	}

	/**
	 * 提取店铺id
	 * 
	 * @param url
	 * @return
	 */
	public static String parseStoreId(String url, String urlInfo) {
	    String storeId = "";
		Map<String, String> params = AcookieUtil.getAllURLPara(url, urlInfo);
		if ( params.containsKey("shopid") ) { 
		    storeId = params.get("shopid");
		}else {
		    return KQConst.UNKNOWN_STR_ID;
		}
		return storeId;
	}


	/**
	 * 是否是有效查询词
	 * 
	 * @param query
	 * @return
	 */
	public static boolean isValueQuery(String query) {
		return StringUtils.isValid(query)
				&& query.length() < MAX_QUERY_LENGTH
				&& query.indexOf(KQConst.DEFAULT_SPLIT) < 0
				&& query.indexOf(KQConst.SECOND_SPLIT) < 0
				&& query.indexOf(KQConst.TAB_SPLIT) < 0;
		
	}

	/**
	 * 获取目标来源的类型
	 * 
	 * @param url
	 * @return
	 */
	public static byte parseUrlType(String url) {
		return KQConst.UNKNOWN_INT_ID;
	}

	   /**
     * 获取宝贝详情ID
     * 
     * @param url
     * @return
     */
    public static String parseItemId(String url) {
        return parseItemId(url, "");
    }

	/**
	 * 获取宝贝详情ID
	 * 
	 * @param url, urlInfo
	 * @return
	 */
	public static String parseItemId(String url, String urlInfo) {
        String itemId = "";
        Map<String, String> params = AcookieUtil.getAllURLPara(url, urlInfo);
        if ( params.containsKey("itemid") ) { 
            itemId = params.get("itemid");
        }else {
            return KQConst.UNKNOWN_STR_ID;
        }
        return itemId;
    }

}
