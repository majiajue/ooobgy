package com.taobao.dw.pizza.path_analysis.core.pojo;

import com.taobao.dw.pizza.path_analysis.core.PizzaConst;


/**
 * 原子路径
 * 
 * @author 明风
 *
 */
public class AtomPath implements Cloneable{
	public String pathId;
	public String path;
	public String nextNodeId;
	public String headNodeId;
	public String tailNodeId;
	public String tableId;
	public boolean isFirstPath;
	public int  depth = 0;
	
	public AtomPath(String _path, String pathExp) {
		super();
		path = _path;
		String[] values = pathExp.split(PizzaConst.COMMA_SPLIT);
		pathId = values[0];
		nextNodeId = values[1];
		headNodeId = values[2];
		tailNodeId = values[3];
		tableId = values[4];
		isFirstPath = path.split(PizzaConst.NODE_SEP_PATTERN)[0].equals(headNodeId);
	}
	
	public AtomPath() {
		super();
	}
	
	public String getPathAttr() {
		return this.pathId + "," + this.nextNodeId + "," + this.headNodeId + "," + this.tailNodeId + "," + this.tableId;
	}
	
	public String getPathKey() {
		return this.path;
	}
	
	public String toString(){
		return "(" + getPathKey() + "):" + getPathAttr(); 
	}
	

	/**
	 * 当传入的原子路径，和当前路径的路径Id一致时，将当前节点的下一节点id，替换为传入路径的下一节点id
	 * 
	 * @param ap
	 */
	public void updateNextNodeId(AtomPath _ap){
		if (_ap != null && (this.pathId.equals(_ap.pathId))){
			this.nextNodeId = _ap.nextNodeId;			
			this.path = this.path + "(" + _ap.path + ")";
			this.depth++;
		}
	}
	
	public Object clone() {  
		AtomPath o = null;  
        try {  
            o = (AtomPath) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  
}
