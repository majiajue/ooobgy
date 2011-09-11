package com.taobao.dw.pizza.path_analysis.core.pojo;

import java.util.ArrayList;
import java.util.List;

import com.taobao.dw.pizza.path_analysis.core.PizzaConst;

/**
 * 组合路径
 * 
 * @author 明风
 * @modified: 周晓龙
 */
public class CompositePath {
	public String pathId;
	public List<String> paths = new ArrayList<String>();
	public String nextNodeId;
	public String headNodeId;
	public String tailNodeId;
	public String tableId;
	public int depth = 1;//记录当前深度
	
	/**
	 * 不考虑匹配深度时使用的构造，为兼容暂时保留
	 * @param ap
	 */
	public CompositePath(AtomPath ap) {
		this.pathId = ap.pathId;
		this.paths.add(ap.path);
		this.nextNodeId = ap.nextNodeId;
		this.headNodeId = ap.headNodeId;
		this.tailNodeId = ap.tailNodeId;
		this.tableId = ap.tableId;
	}

	/**
	 * 考虑匹配深度时使用的构造
	 * @param ap
	 * @param maxDepth
	 */
	public CompositePath(AtomPath ap, int maxDepth) {
		this.pathId = ap.pathId;
		this.paths.add(ap.path);
		this.nextNodeId = ap.nextNodeId;
		this.headNodeId = ap.headNodeId;
		this.tailNodeId = ap.tailNodeId;
		this.tableId = ap.tableId;
		this.depth = maxDepth;
	}

	public void extend(AtomPath atomPath) {	
		//判断这个原子路径，和当前的组合路径，是否对口
		if (this.nextStep(atomPath)){			
			this.paths.add(atomPath.path);
			this.nextNodeId = atomPath.nextNodeId;
		}
	}

	public boolean nextStep(AtomTrace atomTrace) {
		return this.paths.get(this.paths.size()-1).split(PizzaConst.NODE_SEP_PATTERN)[1].equals(atomTrace.startNode);
	}	
	
	public boolean nextStep(AtomPath atomPath){
		if (!atomPath.pathId.equals(this.pathId)){
			return false;
		}else{
			return this.paths.get(this.paths.size()-1).split(PizzaConst.NODE_SEP_PATTERN)[1].equals(atomPath.path.split(PizzaConst.NODE_SEP_PATTERN)[0]);
		}
		
	
	}
	
	public String getPathAttr() {
		return this.pathId + "," + this.nextNodeId + "," + this.headNodeId + "," + this.tailNodeId + "," + this.tableId;
	}
	
	public String getPathKey() {
		String key = "";
		for (String path:this.paths){
			key += "(" + path + ")";
		}
		return key;
	}
	
	public String toString(){
		return  getPathKey() + ":" + getPathAttr(); 
	}

	public void extend(AtomTrace at) {
//		System.out.println("at=" + at);
//		System.out.println("at min=" + at.minDepth + ",max=" + at.maxDepth);
//		System.out.println("now dep=" + depth);
		
		if (at.minDepth == depth) {
			extend(at.atomPaths.get(pathId));
			depth = at.maxDepth;
		}
	}
}
