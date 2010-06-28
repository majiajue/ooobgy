package com.alimama.loganalyzer.jobs.mrs.keywords;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.alimama.loganalyzer.jobs.mrs.util.UrlParserWrapper;
import com.taobao.loganalyzer.common.util.tbacookie.AcookieUtil;
import com.taobao.loganalyzer.common.util.tbacookie.SearchListContent;
import com.taobao.loganalyzer.common.util.url.URLParser;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLog;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLogParser;

/**
 * 
 * 抽取日志中的Acookie相关详细信息，解析出Log中URL相关信息，生成基础临时数据，供后续步骤的处理
 * 
 * pre_keywords.sh中调用，为系统的第一步，入口类
 * 
 * 下游： {@link GenerateKeywordsBaseTable}
 * 		 {@link GenerateQueryBaseTable}
 * 
 * @author 明风
 * @created 2010-03-08
 * 
 */
public class ParseAcookie extends AbstractProcessor {
	private static final String SPLIT = "\u0001";
	private static final String SPLIT_TOKEN = "\u0002";
	private static final String TAB = "\t";
	public final static Pattern SSID_PATTERN = Pattern.compile("(s\\d+)");
	

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		
		private static final String SSID_KEY = "ssid";
		private static final String EMPTY_SSID = "na";
		private static final String TAOBAO_SEARCH_URL = "http://search.taobao.com/";
		private static final String TAOBAO_SEARCH_URL_1 = "http://search1.taobao.com";
		private static final String TAOBAO_SEARCH_URL_2 = "http://s.taobao.com/";
		
		private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		private static final SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
		private static final SimpleDateFormat sdfMinSecond = new SimpleDateFormat("mm:ss");

		private String testDate = "";

		public void configure(JobConf job) {
			super.configure(job);
			testDate = job.get("hadoop_test_date");
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			try {
				String line = value.toString();
				AcookieLog acookieLog = AcookieLogParser.parse(line);
				if (acookieLog == null) {
					return;
				}

				String acookie = acookieLog.getCookie();
				if (acookie == null) {
					return;
				}

				String time = acookieLog.getTime();
				int splitPos = time.indexOf(TAB);
				if (splitPos >= 0) {
					time = time.substring(splitPos + 1);
				}
				String url = acookieLog.getUrl();
				String query = null;
				String category = null;
				String ssid = "";
				int urlType = 0;

				try {
					if (isSearchUrl(url)) {
						// Case 1) search url, we get the query
						urlType = 1;
						query = getSearchQuery(url);
						ssid = getSsid(url);
					} else if (AcookieUtil.isItem(url)) {
						// Case 2) item detail url
						urlType = 2;

						String urlInfo = acookieLog.getUrlInfo();
						java.util.Map<String, String> urlInfo_map = AcookieUtil.parseURLInfo(urlInfo);
						if (urlInfo_map == null)
							return;

						String pre = urlInfo_map.get("pre");
						if (pre == null) {
							return;
						}						

						String refer = urlInfoReferDecode(pre);

						if (isSearchUrl(refer)) {
							query = getSearchQuery(refer);
							ssid = getSsid(refer);

							category = urlInfo_map.get("category");
							if (category != null && !category.equals("")) {
								int sep = category.lastIndexOf("_");
								if (sep >= 0) {
									category = category.substring(sep + 1);
								}

								try {
									Integer.parseInt(category);
								} catch (Exception e) {
									category = "";
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					urlType = 0;
				}

				if (urlType > 0 && query != null) {
					if (UrlParserWrapper.isValueQuery(query)) {
						query = query.trim();
						
						if (category == null || category.equals("")) {
							category = "0";
						}

						Date cookieTime =sdf.parse(time);
						String hour = sdfHour.format(cookieTime);
						String seconds = sdfMinSecond.format(cookieTime);

						StringBuilder mapKey = new StringBuilder();
						mapKey.append(query).append(SPLIT);
						mapKey.append(acookie).append(SPLIT);
						mapKey.append(testDate).append(SPLIT);
						mapKey.append(hour).append(SPLIT);
						mapKey.append(ssid).append(SPLIT);
						

						StringBuilder mapValue = new StringBuilder();
						mapValue.append(seconds).append(SPLIT);
						mapValue.append(urlType).append(SPLIT);
						mapValue.append(category);
					
						output.collect(new Text(mapKey.toString()), new Text(mapValue.toString()));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 从一个Search的URL中提取ssid的原始值，并进行匹配。如果为空，返回na
		 * 
		 * 匹配规则：s+数字
		 * 
		 * @param searchUrl 
		 * @return url中包含的ssid值
		 */
		private String getSsid(String searchUrl) {
			Hashtable<String, String> ht = URLParser.parsePara(searchUrl);
			String ssid = ht.get(SSID_KEY);
			 
			if (ssid == null){
				return EMPTY_SSID;
			}else{
				Matcher matcher = SSID_PATTERN.matcher(ssid); 
				if (matcher.find()){
					return matcher.group();
				}else{
					return EMPTY_SSID;
				}
			}
		}

		/**
		 * 判断URL是否为搜索URL
		 * 
		 * @param url 
		 * @return 是否搜索类型URL
		 */
		private static boolean isSearchUrl(String url) {
			url = url.toLowerCase();
			if (url.startsWith(TAOBAO_SEARCH_URL) || url.startsWith(TAOBAO_SEARCH_URL_1) || url.startsWith(TAOBAO_SEARCH_URL_2)) {
				return true;
			}
			return false;
		}

		public static String getSearchQuery(String url) {
			String query = null;
			if (url.indexOf(TAOBAO_SEARCH_URL) != -1) {
				Hashtable<String, String> ht = URLParser.parsePara(url);
				if (ht.containsKey("q")) {
					String query_encoded = ht.get("q");
					if (query_encoded != null && !query_encoded.equals("")) {
						try {
							query = URLDecoder.decode(query_encoded, "gbk");
						} catch (UnsupportedEncodingException e) {
							query = null;
						}
					}
				}
			} else {
				SearchListContent search = AcookieUtil.parseSearchList(url);
				if (search != null) {
					query = search.getQ();
				}
			}

			if (query != null) {
				query = query.trim();
			}
			return query;
		}
		
		

		public static String urlInfoReferDecode(String src) {
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length());
			int lastPos = 0, pos = 0;
			char ch;
			while (lastPos < src.length()) {
				pos = src.indexOf("%", lastPos);
				try {
					if (pos == lastPos) {
						if (src.charAt(pos + 1) == 'u') {
							ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
							tmp.append(ch);
							lastPos = pos + 6;
						} else {
							ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
							tmp.append(ch);
							lastPos = pos + 3;
						}
					} else {
						if (pos == -1) {
							tmp.append(src.substring(lastPos));
							lastPos = src.length();
						} else {
							tmp.append(src.substring(lastPos, pos));
							lastPos = pos;
						}
					}
				} catch (Exception e) {
					return src;
				}
			}
			return tmp.toString();
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			// Step 1) sort the values by time
			List<String> behaviors = new LinkedList<String>();
			while (values.hasNext()) {
				while (values.hasNext() && behaviors.size() < 10000) {
					behaviors.add(values.next().toString());
				}
				Collections.sort(behaviors);

				// Step 2) output behavior
				StringBuilder outValue = new StringBuilder();
				Iterator<String> it = behaviors.iterator();
				while (it.hasNext()) {
					outValue.append(it.next()).append(SPLIT);
				}
				output.collect(key, new Text(outValue.toString()));
				behaviors.clear();
			}
		}
	}

	public void configJob(JobConf conf) {
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);
 
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		if (conf.get("mrs_max_xmx") != null){
			conf.set("mapred.child.java.opts", conf.get("mrs_max_xmx"));
		}
	}

	public Class getMapper() {
		return Map.class;
	}

	public Class getReducer() {
		return Reduce.class;
	}

}
