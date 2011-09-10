package com.taobao.dw.pizza.path_analysis.core.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.taobao.dw.pizza.path_analysis.core.PizzaConst;


/**
 * 路径倒排表
 * 
 * @author 明风
 * @created 2011-07-28
 */
public class InvertedList {
	/*
	 * 		Key：原子路径
	 * 		Value：对应的原子路径集
	 */
	private Map<String, List<AtomPath>> invertedPath = new LinkedHashMap<String, List<AtomPath>>();
	/*
	 * 		Key:			首节点
	 * 		Values：	原子路径集合
	 */
	public Map<String, Set<String>> headPathIndex = new LinkedHashMap<String, Set<String>>();

	/**
	 * 各路径首节点的对照倒排表，方便后续查询
	 * 空间用量为O(n)，n是路径条数
	 * 		Key:		首节点id
	 * 		Values:		对应的路径id集合
	 */
	private Map<String, Set<String>> invertedHeadNodeIndex = new HashMap<String, Set<String>>();
	
	/**
	 * 路径详细信息，选用方法{@link #putHeadNodePathIndex(String, String)}会记录进路径的第二节点、尾节点
	 * 若选用方法 {@link #putHeadNodePathIndex(String)}将为空，后续的查询处理中自动返回-1
	 * 空间用量为O(2n)，n是路径条数
	 * Key:   路径id
	 * Values:    第二节点id、尾节点id
	 */
	private Map<String, String[]> pathProfile = new HashMap<String, String[]>();
	
	/**
	 * 在倒排表中，根据路径id对，寻找对于的路径节点对，并按指定格式填充，方便后续检索
	 * @param at
	 */
	public void match(AtomTrace at) {
		List<AtomPath> aps = invertedPath.get(at.atomTrace);
		if ( aps != null){
			for (AtomPath ap : aps){
				at.atomPaths.put(ap.pathId, ap);
			}
		}
	}
	
	/**
	 * 返回一个nodeId是否为首节点
	 * @param nodeId
	 * @return
	 */
	public boolean isHeadNode(String nodeId){
		return (this.headPathIndex.get(nodeId) != null);
	}
	

	/**
	 *  将路径加入倒排表
	 * 
	 * @param atomPath
	 * @param aps
	 */
	public void put(String atomPath, List<AtomPath> aps) {
		this.invertedPath.put(atomPath, aps);
	}
	
	/**
	 * 更新首节点路径索引
	 * @param atomPath
	 */
	private void updateHeadPathIndex(String atomPath) {
		String startNode = atomPath.split(PizzaConst.NODE_SEP_PATTERN)[0];
		if (this.headPathIndex.get(startNode) == null){
			this.headPathIndex.put(startNode, new LinkedHashSet<String>());			
		}
		this.headPathIndex.get(startNode).add(atomPath);
	}
	
	/**
	 * 返回首节点对应的所有首轨迹和首路径
	 * 
	 * @param headNodeId
	 * @return
	 */
	public List<AtomTrace> getHeadPath(String headNodeId){
		Set<String> headTraces = this.headPathIndex.get(headNodeId);		
		List<AtomTrace> results = new ArrayList<AtomTrace>();
		if (headTraces == null || headTraces.size() ==0) return results;
		for(String headTrace: headTraces){
				AtomTrace at = new AtomTrace(headTrace,0,1);//对于1-2的情况，maxD不一定能是1
				this.match(at);
				results.add(at);
		}
		return results;		
	}

	public String toString(){
		return this.invertedPath.toString() + "\nHead Paths:" + this.headPathIndex.toString() +
				"\nheadNode-pathId:" + this.invertedHeadNodeIndex;
	}

	public void clean() {
		this.invertedPath.clear();		
	}

	public void addAll(InvertedList _readInvertedList) {
		this.invertedPath.putAll(_readInvertedList.invertedPath);
	}

	/**
	 * 重修构造首路径索引
	 */
	public void rebuildHeadNodeIndex() {
		for (List<AtomPath> aps : this.invertedPath.values()){
			for (AtomPath ap:aps){
				this.updateHeadPathIndex(ap.path);
			}
		}			
	}
	
	public Set<String> findPathsWithHeadNode(String headNodeId) {
		return this.invertedHeadNodeIndex.get(headNodeId);
	}
	
	public void putHeadNodePath(String headNodeId, String pathId) {
		if (this.invertedHeadNodeIndex.get(headNodeId) == null) {
			this.invertedHeadNodeIndex.put(headNodeId, new HashSet<String>());
		}
		
		this.invertedHeadNodeIndex.get(headNodeId).add(pathId);
	}
	
	/**
	 * 消耗较低的方法，不记录路径的第二节点。
	 * 这种情况下，在下游表r_pizza_path_node_split中，对于只到达过路径首节点的用户轨迹
	 * next_node_id将被记录为-1，last_node_id也记录为-1
	 * @param pathExp
	 * @see #putHeadNodePathIndex(String, String)
	 */
	public void putHeadNodePathIndex(String pathExp){
		String[] items = pathExp.split(PizzaConst.COMMA_SPLIT);
		this.putHeadNodePath(items[2], items[0]);
	}

	/**
	 * 完全的方法，存入每个路径的第二节点及尾节点信息
	 * 这种情况下，在下游表r_pizza_path_node_split中，对于只到达过路径首节点的用户轨迹
	 * next_node_id,last_node_id将记录为真实情况
	 * @param atomPath
	 * @param pathExp
	 * @see #putHeadNodePathIndex(String)
	 */
	public void putHeadNodePathIndex(String atomPath, String pathExp) {
		String[] values = pathExp.split(PizzaConst.COMMA_SPLIT);
		String[] atomNodes = atomPath.split(PizzaConst.NODE_SEP_PATTERN);
		if (atomNodes[0].equals(values[2])) {
			String[] profile = new String[]{atomNodes[1], values[3]};
			this.pathProfile.put(values[0], profile);
		}
		this.putHeadNodePath(values[2], values[0]);
	}
	
	/**
	 * 路径明细
	 * @param pathId
	 * @return  [第二节点id,尾节点id]
	 */
	public String[] getPathFrofile(String pathId){
		return this.pathProfile.get(pathId);
	}
}
