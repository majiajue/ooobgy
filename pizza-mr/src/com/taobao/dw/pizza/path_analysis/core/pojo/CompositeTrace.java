package com.taobao.dw.pizza.path_analysis.core.pojo;

import java.util.HashMap;
import java.util.Map;


/**
 * 组合轨迹
 * 
 * @author 明风
 *
 */
public class CompositeTrace {
	//起始轨迹
	public String trace;
	public Map<String, CompositePath>  cps = new HashMap<String, CompositePath>();
	
		
	/**
	 * 将原子轨迹中的轨迹，加入到组合轨迹中。将原子轨迹中的相同路径id的下一结点，更新到组合轨迹中
	 */
	public void extend(AtomTrace at) {
		for (String pathId : at.atomPaths.keySet()){
			if (this.cps.get(pathId) != null){
				this.cps.get(pathId).extend(at);
			}
		}
		
	}


	/**
	 * 判断原子轨迹是否可能为当前组合轨迹的下一步
	 * 
	 * @param atomTrace
	 * @return
	 */
	public boolean nextStep(AtomTrace atomTrace) {
		if ( atomTrace.atomPaths.size() == 0) return false;
		for (CompositePath cp : this.cps.values()){
			if(cp.nextStep(atomTrace))
				return true;
		}
		return false;
	}

	/**
	 * 初始化第一个轨迹
	 * 
	 * @param at
	 */
	public void init(AtomTrace at) {
		this.trace = at.atomTrace;
		for (String key : at.atomPaths.keySet()){
			AtomPath ap = at.atomPaths.get(key);
			if (ap.isFirstPath){
				this.cps.put(key, new CompositePath(ap, at.maxDepth));
			}
		}
	}
	
	public String toString(){
		return this.trace + this.cps;
	}


}
