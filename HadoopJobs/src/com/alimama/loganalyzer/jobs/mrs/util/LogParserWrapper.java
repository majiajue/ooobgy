package com.alimama.loganalyzer.jobs.mrs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.taobao.loganalyzer.common.util.tbacookie.AcookieUtil;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLog;
import com.taobao.loganalyzer.input.acookie.parser.AcookieLogParser;

/**
 * 淘宝的ACookie日志解析封装类，对AcookieLogParser.jar的封装而已，该库为公用库。
 * 
 * @author 明风
 *
 */
public class LogParserWrapper {
	private AcookieLog acookieLog;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
	private static final SimpleDateFormat sdfMinSecond = new SimpleDateFormat("mm:ss");
	
	
	@SuppressWarnings("unused")
	private LogParserWrapper(){
		
	}
	
	/**
	 * 默认构造器，必须传入一行Log进行解析，结果存在acookieLog中
	 * @param log
	 */
	public LogParserWrapper(String log){
		super();
		acookieLog = AcookieLogParser.parse(log);		
	}
	
	/**
	 * 返回Log所带的Cookie信息
	 * @return
	 */
	public String getCookie(){
		if (acookieLog == null){
			return null;
		}
		return acookieLog.getCookie();

	}
	
	/**
	 * 返回Log所带的时间，字符串格式
	 * @return
	 */
	public String getCookieTimeStr(){
		if (acookieLog == null){
			return null;
		}

		String time = acookieLog.getTime();
		int splitPos = time.indexOf(KQConst.TAB_SPLIT);
		if (splitPos >= 0) {
			time = time.substring(splitPos + 1);
		}
		return time;
	}
	
	
	/**
	 * 返回Log所带时间，日期格式，因为返回格式可读性差，不对外开放，需要请通过getTimeInDay，getTimeInHour，getTimeInMMSS调用
	 * 
	 * @return yyyyMMdd 例如 20100306
	 * @throws ParseException 
	 */
	private Date getCookieTime() throws ParseException{
		try {
			return sdf.parse(getCookieTimeStr());
		} catch (ParseException e) {
			throw e;
		}
	}
	
	/**
	 * cookie日志的时间，精确到天
	 * 
	 * @return 
	 * @throws ParseException 
	 */
	public String getCookieTimeInDate() throws ParseException{
		return sdfDate.format(getCookieTime());
	}
	
	
	/**
	 * cookie日志的时间，精确到小时	
	 * @return hh 如13
	 * @throws ParseException 
	 */
	public String getCookieTimeInHour() throws ParseException{
		return sdfHour.format(getCookieTime());
	}
	
	/**
	 * cookie日志的时间，精确到秒
	 * 
	 * @return mm:ss 如59:03
	 * @throws ParseException 
	 */
	public String getCookieTimeInMMSS() throws ParseException{
		return sdfMinSecond.format(getCookieTime());		
	}
	
	/**
	 * 返回Log中的Url信息
	 * @return
	 */
	public String getUrl(){
		return acookieLog.getUrl();
	}
	
	/**
	 * 返回Log中的Refer Url
	 * 
	 * @return
	 */
	
	public String getReferUrl(){
		if (acookieLog == null){
			return null;
		}

		String urlInfo = acookieLog.getUrlInfo();
		Map<String, String> urlInfo_map = AcookieUtil.parseURLInfo(urlInfo);
		if (urlInfo_map == null){
			return "";
		}
		String pre = urlInfo_map.get("pre");
		if (pre == null) {
			return "";
		}
		String refer = AcookieUtil.urlinfoReferDecode(pre);
		return refer;
	}

	/**
	 * 返回URL Info，和URL不同，从中可以提取CategoryId
	 * 
	 * @return
	 */
	public String getUrlInfo() {
		return acookieLog.getUrlInfo();
	}

	/**
	 * 返回UserId
	 * @return
	 */
	public int getUserId() {
		String strUserId = acookieLog.getCookieUid();
		int userId = 0;
		if (StringUtils.isValid(strUserId)) {
			try {
				userId = Integer.parseInt(strUserId);
			} catch (Throwable e) {
				return KQConst.UNKNOWN_INT_ID;
			}
			return userId;
		}else{
			return KQConst.UNKNOWN_INT_ID;
		}
		
	}
	



}
