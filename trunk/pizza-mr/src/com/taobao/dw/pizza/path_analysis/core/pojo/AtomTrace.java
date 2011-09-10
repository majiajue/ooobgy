package com.taobao.dw.pizza.path_analysis.core.pojo;

import java.util.HashMap;
import java.util.Map;

import com.taobao.dw.pizza.path_analysis.core.PizzaConst;

/**
 * 	原子轨迹，格式为：
 * 				强轨迹：nodeId1+nodeId2
 * 				弱轨迹：nodeId1-nodeId2
 * 				
 * 				
 * @author 明风
 * @modified 晓龙  2011年8月12日16:45:21
 */
public class AtomTrace {

	final public String atomTrace;
	
	final public String startNode;
	final public String endNode;
	final public String seperator;
	final public int minDepth;
	final public int maxDepth;

	public Map<String, AtomPath> atomPaths = new HashMap<String, AtomPath>();
	
	public AtomTrace(String _startNode, String _seperator, String _endNode, int minDepth, int maxDepth) {
		super();
		this.startNode = _startNode.trim();
		this.endNode = _endNode.trim();
		this.seperator = _seperator.trim();
		this.atomTrace = this.startNode + this.seperator + this.endNode;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
	}	
	
	public AtomTrace(String trace, int minDepth, int maxDepth) {
		super();
		this.startNode = trace.split(PizzaConst.NODE_SEP_PATTERN)[0];
		this.endNode =  trace.split(PizzaConst.NODE_SEP_PATTERN)[1];		
		this.seperator = (trace.indexOf("+") != -1) ? "+" : "-"; 
		this.atomTrace = trace;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
	}

	private boolean isFirstPath = false;
	
	/**
	 * 根据所有匹配的AtomPath，判断是否为第一路径，如果有任意路径为第一路径，那么该Trace就存在为第一路径的可能
	 * 
	 * 该方法有一定性能损耗，慎调用
	 * 
	 * @return
	 */
	public boolean getFirstPath(){
		for (AtomPath ap : atomPaths.values()){
			if (ap.isFirstPath) {
				isFirstPath = true;
				break;
			}
		}
		return isFirstPath;
	}

	
	public boolean startFrom(String firstNodeId) {
		return this.startNode.equalsIgnoreCase(firstNodeId);
	}
	
	public String toString(){
		return this.atomTrace + this.atomPaths;		
	}
	

}
