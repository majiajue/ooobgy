package com.taobao.dw.pizza.path_analysis.common;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

import com.taobao.dw.pizza.path_analysis.biz.LogBean;


public class CommonFunction {
	
	public static String CTRL_FIELD = "\001";

	/**
	 * 判断一个字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}

	public static String getDeCodeURL(String url) {
		if (url != null) {
			try {
				url = url.trim();
				if (HASURLDecoder.isUtf8Url(url)) {
					url = URLDecoder.decode(url, "UTF-8");
				} else {
					url = URLDecoder.decode(url, "GBK");
				}
			} catch (Exception e) {
				return url;
			}
		}
		return url;
	}

	/**
	 * 从refer中得到实际的referer
	 * @param refer
	 * @return
	 */
	public static String getRealReferFromRefer(String refer) {
		if (refer.indexOf("&pre=") != -1)
			refer = refer.split("&pre=", -1)[1];
		if (refer.indexOf("&scr=") != -1)
			refer = refer.split("&scr=", -1)[0];
		return refer;
	}
	
	/**
	 * 
	 * @param refer
	 * @param url
	 * @return 
	 */
	public static String getReferParam(String refer, String url) {
		if (refer.indexOf("&scr=") == -1) return "";
		String param = "scr=" + refer.split("&scr=", -1)[1];
		if(url.indexOf("?") != -1) return "&" + param;
		return "?" + param;
	}
	
	/**
	 * 判断mid是否为正常的19位数字，如果不是，则此行日志可抛弃
	 * @param mid
	 * @return
	 */
	public static boolean isCorrectMid(String mid) {
		Pattern pattern = Pattern.compile("^[\\d]{19}$");
		Matcher matcher = pattern.matcher(mid);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前是否为登录用户
	 * @param uid
	 * @return
	 */
	public static boolean isUser(String uid) {
		Pattern pattern = Pattern.compile("^[\\d]{2,}$");
		Matcher matcher = pattern.matcher(uid);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 递归获取队列
	 * @param logBean
	 * @param isInit isInit=0表示为叶子借点，-1表示为非叶子节点
	 * @return
	 */
	public static String getRecursionResult(LogBean logBean,int isInit){
		String recursionResult = isInit == 0 ? logBean.toString() + "}" : "";
		if(logBean.getPrevious() != null) {
			recursionResult += (logBean.getPrevious() + "}" + getRecursionResult(logBean.getPrevious(), -1));
		}
		return recursionResult;
	}
	
	/**
	 * 由于递归排序采取的为倒序，这里对递归排序的结果进行正向封装
	 * @param logBean
	 * @param isInit
	 * @return
	 */
	public static String reverseString(LogBean logBean,int isInit){
		String result = "";
		String[] array = getRecursionResult(logBean, isInit).split("]}");
		//对数组进行倒转排序并输出
		for(int i = array.length - 1; i >= 0; i--){
			result += ",{" + array[i] + "}";
		}
		if(result.startsWith(",")) result = result.substring(1);
		result = "\"entitys\":[" + result + "]";
		return result;
	}


	public static class FirstPartitioner implements Partitioner<Text, Text> {

		@Override
		public void configure(JobConf conf) {
		}

		@Override
		public int getPartition(Text key, Text value, int numPartitions) {
			String[] t = key.toString().split("\"");
			int hashCode = WritableComparator.hashBytes(key.getBytes(), t[0]
					.length());
			return (hashCode & Integer.MAX_VALUE) % numPartitions;
		}

	}

	public static class FirstGroupingComparator implements RawComparator<Text> {

		@Override
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
			return WritableComparator.compareBytes(b1, s1, 0, b2, s2, 0);
		}

		@Override
		public int compare(Text o1, Text o2) {
			String[] t1 = o1.toString().split("\"");
			String[] t2 = o2.toString().split("\"");
			return WritableComparator.compareBytes(o1.getBytes(), 0, t1[0]
					.length(), o2.getBytes(), 0, t2[0].length());
		}
	}
}
