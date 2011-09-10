package com.taobao.dw.pizza.path_analysis.biz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		String refer = "=1024x768&category=&userid=273422663&channel=112&at_alitrackid=www.taobao.com&ad_id=";
		String url = "http://s.taobao.com/search?q=1";
		System.out.println(getReferParam(refer, url));
	}
	
	private static boolean test(String url) {
		Pattern pattern = Pattern.compile("^http://(s|search|search1)\\.taobao\\.com.*");
		Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				return true;
			}
		return false;
	}
	
	public static String getReferParam(String refer, String url) {
		if (refer.indexOf("&scr=") == -1) return "";
		String param = "scr=" + refer.split("&scr=", -1)[1];
		if(url.indexOf("?") != -1) return "&" + param;
		return "?" + param;
	}
}
