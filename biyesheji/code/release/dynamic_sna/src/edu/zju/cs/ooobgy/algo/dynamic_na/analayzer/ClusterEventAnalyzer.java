package edu.zju.cs.ooobgy.algo.dynamic_na.analayzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger.ClusterContinueJudger;
import edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger.ClusterDissolveJudger;
import edu.zju.cs.ooobgy.algo.dynamic_na.eventjudger.ClusterFormJudger;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;

/**
 * 挖掘团伙事件
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class ClusterEventAnalyzer<V, E> implements
				Transformer<ClusterSlice<V, E>, List<ClusterEvent>> {
	private ClusterSlice<V, E> preSlice;
	private Collection<IdCluster<V>> preClusters;
	private ClusterSlice<V, E> nowSlice;

	public ClusterEventAnalyzer(ClusterSlice<V, E> preSlice) {
		super();
		this.preSlice = preSlice;
		preClusters = preSlice.getClusters().values();
	}
	
	/**
	 * 分析所有团伙事件
	 * @param nowSlice
	 * @return
	 */
	public List<ClusterEvent> analyze(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		
		result.addAll(judgeContinueEvents(nowSlice));
		result.addAll(judgeKMergeEvents(nowSlice));
		result.addAll(judgeKSplitEvents(nowSlice));
		result.addAll(judgeFormEvents(nowSlice));
		result.addAll(judgeDissolveEvents(nowSlice));
			
		return result;
	}

	/**
	 * 检测延续事件
	 * @param nowSlice
	 * @return
	 */
	public List<ClusterEvent> judgeContinueEvents(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result;
		ClusterContinueJudger<V> judger = new ClusterContinueJudger<V>();
		result = judger.judge(preSlice.getClusters(), nowSlice.getClusters());

		return result;
	}
	
	/**
	 * 检测合并事件
	 * @param nowSlice
	 * @return
	 */
	public List<ClusterEvent> judgeKMergeEvents(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		// TODO Auto-generated method stub
		return result;
	}
	
	/**
	 * 检测分裂事件
	 * @param nowSlice
	 * @return
	 */
	public List<ClusterEvent> judgeKSplitEvents(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result = new LinkedList<ClusterEvent>();
		// TODO Auto-generated method stub
		return result;
	}
	
	/**
	 * 检测形成事件
	 * @param nowSlice
	 * @return
	 */
	public List<ClusterEvent> judgeFormEvents(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result;
		ClusterFormJudger<V> judger = new ClusterFormJudger<V>();
		result = judger.judge(preSlice.getClusters(), nowSlice.getClusters());
		return result;
	}

	/**
	 * 检测分解事件
	 * @param preClusterId
	 * @param nowClusterId
	 * @return
	 */
	public List<ClusterEvent> judgeDissolveEvents(ClusterSlice<V, E> nowSlice) {
		this.nowSlice = nowSlice;
		List<ClusterEvent> result;
		ClusterDissolveJudger<V> judger = new ClusterDissolveJudger<V>();
		result = judger.judge(preSlice.getClusters(), nowSlice.getClusters());
		
		return result;
	}

	/**
	 * 默认的transform计算所有事件类型
	 */
	@Override
	public List<ClusterEvent> transform(ClusterSlice<V, E> nowSlice) {		
		return analyze(nowSlice);
	}
}
