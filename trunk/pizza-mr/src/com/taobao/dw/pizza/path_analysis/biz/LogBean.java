package com.taobao.dw.pizza.path_analysis.biz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogBean {
	
	public LogBean(){};
	
	public LogBean(String mid, String isUser,String nodeFeature,String url,String isRefer,String logTime,String urlWithParam){
		this.mid = mid;
		this.isUser = isUser;
		this.nodeFeature = nodeFeature; 
		this.url = url;
		this.isRefer = isRefer;
		this.logTime = logTime;
		this.urlWithParam = urlWithParam;
	}
	
	private String uid;
	
	private String mid;
	
	//1为会员,0为访客
	private String isUser;
	
	private String nodeFeature;
	
	private String url;
	
	private String logTime;
	
	//url本身加上httpheader中参数合成的url
	private String urlWithParam;
	
	public String getUrlWithParam() {
		return urlWithParam;
	}

	public void setUrlWithParam(String urlWithParam) {
		this.urlWithParam = urlWithParam;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	//1为refer,0为url
	private String isRefer;
	
	public String getIsRefer() {
		return isRefer;
	}

	public void setIsRefer(String isRefer) {
		this.isRefer = isRefer;
	}

	//子节点(存在多个)
	private List<LogBean> nextPaths = new ArrayList<LogBean>();
	
	//父节点(有一个或没有，如果没有，则该节点为该路径的起始节点)
	private LogBean previous;

	public LogBean getPrevious() {
		return previous;
	}

	public void setPrevious(LogBean previous) {
		this.previous = previous;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getIsUser() {
		return isUser;
	}

	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}

	public String getNodeFeature() {
		return nodeFeature;
	}

	public void setNodeFeature(String nodeFeature) {
		this.nodeFeature = nodeFeature;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	//重写hashCode,这是为了顺序匹配节点而复写方法
	@Override
	public int hashCode(){
		return this.url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false ;
		if(!(obj instanceof LogBean)) return false ;
		LogBean newlb =  (LogBean) obj;
		return (this.getUrl().equals(newlb.getUrl()));
	}
	
	private String getPreviousNodeFeture(){
		if(previous == null) return "-2";
		if(previous.getNodeFeature() == null) return "-2";
		return previous.getNodeFeature();
	}
	
	//获取页面停留时间，如果该节点没有后续子节点则不统计，返回-1
	//具体逻辑为：
	private long getStayTime(){
		try {
			if(nextPaths == null || nextPaths.size() == 0) return -1;
			LogBean next = nextPaths.get(0);
			long staytime = Long.parseLong(next.getLogTime()) - Long.parseLong(this.getLogTime());
			return staytime > 180 ? 180 : staytime;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private String getNextNodeFeture(){
		String nextNodeFeture = "";
		Set<String> set = new HashSet<String>();
		//用set来去重
		for (LogBean lb : nextPaths) {
			if(lb.getNodeFeature() == null) set.add("-2");
			else set.add(lb.getNodeFeature());
		}
		for(String o : set) nextNodeFeture += "," + o;
		if(nextNodeFeture.startsWith(",")) nextNodeFeture = nextNodeFeture.substring(1);
		if("".equals(nextNodeFeture)) nextNodeFeture = "-2";
		return nextNodeFeture;
	}
	
	//-1表示匹配不上节点,-2表示节点为空
	private String getNodeContext(){
		return getNodeFeature() + "_" + getPreviousNodeFeture() + "_" + getNextNodeFeture(); 
	}

	@Override
	public String toString() {
		return "" 
		+ "\"url\":\"" +  url + "\","
		+ "\"nodeFeature\":\"" + nodeFeature + "\","
		+ "\"isUser\":\"" + isUser + "\","
		+ "\"mid\":\"" + mid + "\","
		+ "\"isRefer\":\"" + isRefer + "\","
		+ "\"logTime\":\"" + logTime + "\","
		+ "\"nodeContext\":\"" + getNodeContext() + "\","
		+ "\"stayTime\":\"" + getStayTime() + "\","
		+ "\"urlWithParam\":\"" + getUrlWithParam() + "\""
		+ "]";
	}

	public List<LogBean> getNextPaths() {
		return nextPaths;
	}

	public void setNextPaths(List<LogBean> nextPaths) {
		this.nextPaths = nextPaths;
	}

}
