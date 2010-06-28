package com.alimama.loganalyzer.jobs.mrs.keywords.bak;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import com.alimama.loganalyzer.common.AbstractProcessor;
import com.taobao.loganalyzer.common.util.tbacookie.AcookieUtil;
import com.taobao.loganalyzer.common.util.tbacookie.SearchListContent;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLog;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLogParser;

// 该类的目的是从Acookie日志中抽取某一Acookie在某一小时内的与某个query相关的所有行为
public class ExtractAcookieQuery_remove_at_ssid extends AbstractProcessor {
	private static final String split = "\u0001";
	private static final String split_token = "\u0002";

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		private String testDate = "";
		
		public void configure(JobConf job) {
			super.configure(job);
			testDate = job.get("hadoop_test_date");
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) {
			try {
				String line = value.toString();
				AcookieLog acookielog = AcookieLogParser.parse(line);
				if (acookielog == null) {
					return;
				}

				String acookie = acookielog.getCookie();
				if (acookie == null) {
					return;
				}

				String time = acookielog.getTime();
				int splitPos = time.indexOf("\t");
				if (splitPos >= 0) {
					time = time.substring(splitPos + 1);
				}
				String url = acookielog.getUrl();
				String query = null;
				String category = null;
				int urlType = 0;

				try {
					if (Map.isSearchUrl(url)) {
						// Case 1) search url, we get the query
						urlType = 1;
						
						SearchListContent search = AcookieUtil.parseSearchList(url);
						if (search == null) return;
						query = search.getQ();
					} else if (AcookieUtil.isItem(url)) {
						// Case 2) item detail url
						urlType = 2;

						String urlInfo = acookielog.getUrlInfo();
						java.util.Map<String, String> urlInfo_map = AcookieUtil.parseURLInfo(urlInfo);
						if (urlInfo_map == null)
							return;
	
						String pre = urlInfo_map.get("pre");
						if (pre == null) {
							return;
						}
						
						String at_ssid = urlInfo_map.get("at_ssid");
						if (at_ssid != null) {
							return;
						}
						
						String refer = pre;
						if (!pre.equals("")) {
							try {
								refer = URLDecoder.decode(pre, "utf-8");
							} catch (Exception e) {
								refer = pre;
							}
						}
	
						if (Map.isSearchUrl(refer)) {
							SearchListContent search = AcookieUtil.parseSearchList(refer);
							if (search == null) return;
							query = search.getQ();
							category = urlInfo_map.get("category");
							if (category != null && !category.isEmpty()) {
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
					query.trim();
					if (!query.isEmpty() && query.length() < 512 
							&& query.indexOf(split) < 0 && query.indexOf(split_token) < 0) {
						if (category == null || category.isEmpty()) {
							category = "0";
						}
						StringBuilder mapKey = new StringBuilder();
						mapKey.append(query).append(split);
						mapKey.append(acookie).append(split);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date;
						try {
							date = sdf.parse(time);
						} catch (ParseException e) {
							e.printStackTrace();
							return;
						}

						mapKey.append(testDate).append(split);

						SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
						mapKey.append(sdfHour.format(date)).append(split);

						SimpleDateFormat sdfValue = new SimpleDateFormat("mm:ss");
						String seconds = sdfValue.format(date);

						StringBuilder mapValue = new StringBuilder();
						mapValue.append(seconds).append(split);
						mapValue.append(Integer.toString(urlType)).append(split);
						mapValue.append(category);

						// Step 3) output result
						output.collect(new Text(mapKey.toString()), new Text(mapValue.toString()));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static boolean isSearchUrl(String url) {
			url = url.toLowerCase();
			if (url.startsWith("http://search1.taobao.com")) {
				return true;
			}
			return false;
		}
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
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
					outValue.append(it.next()).append(split);
				}
				output.collect(key, new Text(outValue.toString()));
				behaviors.clear();
			}
		}
	}

	public void configJob(JobConf conf) {
		// Step 1) Set Map/Reduce class
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		// Step 2) Set max virtual memory
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

	public static void main(String[] args) throws ParseException {
		String line = "2000-02-29 12:10:15^A123.145.175.236^AIh1tAXGEzRgBATVcMHTa/WBP^AIE6http://search1.taobao.com/browse/0/n-g,ywxnpmban5xgy6i----------------40--commend-0-all-0.htm?ssid=e-s5&at_topsearch=1^A^Aacookie_load_id=93320928685091289532&title=%u8FDE%u8863%u88D9%u5B63%u5019%u98CE%20%u8FDE%u8863%u88D9-%u4EF7%u683C%u5973%u88C5/%u5973%u58EB%u7CBE%u54C1%20-%20%u6DD8%u5B9D%u7F51&pre=http%3A//search1.taobao.com/browse/16-50010850/n-1--1----------------------4-----------------------g%2Cxs7lv4vx44qmdlgsylels-------2-------b--40-grid-commend-200-all-50010850.htm%3Fssid%3Dp11-s1&category=search_50010850&userid=45819429&channel=112&at_ssid=commend-0-1-11-7219-0-50010850&at_bucketid=sbucket_3&ad_id=^AQG97RcuMqu0cb2B6_1252166392_47^A45819429";
		Map map = new Map();
		map.map(null, new Text(line), null, null);
	}
}
