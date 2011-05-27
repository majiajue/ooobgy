package edu.zju.cs.ooobgy.algo.cluster.auto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.KeyValue;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.cluster.EdgeBetweennessClusterer;
import edu.zju.cs.ooobgy.algo.cluster.WeakComponentClusterer;
import edu.zju.cs.ooobgy.algo.cluster.qualify.ClusterQualify;
import edu.zju.cs.ooobgy.algo.cluster.qualify.ModularityQualify;
import edu.zju.cs.ooobgy.algo.util.Pair;
import edu.zju.cs.ooobgy.algo.util.SimpleKeyValue;
import edu.zju.cs.ooobgy.graph.ClusterGraph;
import edu.zju.cs.ooobgy.graph.Graph;

/**
 * 自动地使用MQ度量进行EdgeBetweenness聚类</br>
 * 可选是否进行完全的切边
 * @author frogcherry 周晓龙
 * @created 2010-12-14
 * @Email frogcherry@gmail.com
 */
public class AutoEdgeBetwennessCluster<V, E> implements AutoEdgeRemovalCluster<V, E> {
	private Transformer<E, ? extends Number> edge_weights;
	/** 跟踪聚类过程中的量值轨迹 */
	private List<Double> qualityTrack;
	/** 跟踪聚类过程中的切边轨迹 */
	private List<E> edgeTrack;
	/** 记录最优的聚类<迭代次数Index, quality量值> */
	private BestAEBCluster bestCluster;
	/**
	 *  是否全切边 
	 *  true: 切边到全图所有点变成孤立点，在整个过程中跟踪最优量值(MQ算法中为最高峰值)
	 *  false: 切边到找到第一个较优值位置(MQ算法中为第一个峰值)
	 */
	private boolean clusterComplete;
	/**
	 * 是否打印聚类过程
	 */
	private boolean printClusterProcess;
	
	public AutoEdgeBetwennessCluster(
			Transformer<E, ? extends Number> edge_weights,
			boolean clusterComplete, boolean printClusterProcess) {
		super();
		this.edge_weights = edge_weights;
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.clusterComplete = clusterComplete;
		this.printClusterProcess = printClusterProcess;
	}

	/**
	 * 对于有权图的构造
	 * @param edge_weights
	 */
	public AutoEdgeBetwennessCluster(
			Transformer<E, ? extends Number> edge_weights) {
		super();
		this.edge_weights = edge_weights;
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.clusterComplete = true;
		this.printClusterProcess = false;
	}

	/**
	 * 无权图的处理
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AutoEdgeBetwennessCluster() {
		super();
		this.edge_weights = new ConstantTransformer(1);
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.clusterComplete = true;
		this.printClusterProcess = false;
	}
	
	/**
	 * 有权图构造，可指定是否切边到底
	 * @param edge_weights
	 * @param clusterComplete
	 */
	public AutoEdgeBetwennessCluster(
			Transformer<E, ? extends Number> edge_weights,
			boolean clusterComplete) {
		super();
		this.edge_weights = edge_weights;
		this.clusterComplete = clusterComplete;
		this.qualityTrack = new LinkedList<Double>();
		this.edgeTrack = new LinkedList<E>();
		this.printClusterProcess = false;
	}

	@Override
	public Set<Set<V>> transform(Graph<V, E> graph1) {
		if (!(graph1 instanceof ClusterGraph)) {
			throw new IllegalArgumentException("Trying cluster a graph could NOT cluster!");
		}		
		ClusterGraph<V, E> graph = (ClusterGraph<V, E>)graph1;
		ClusterGraph<V, E> optGraph = graph.clone();//克隆一份用于操作的图
		
		//先获取原始的分组情况,建立最优轨迹初始化
		WeakComponentClusterer<V, E> wcSearcher = new WeakComponentClusterer<V, E>();
		Set<Set<V>> initClusterSet = wcSearcher.transform(graph);
		this.bestCluster = new BestAEBCluster(initClusterSet);
		
		////必须要克隆原始边集合，否则原始边信息会在切边过程中丢失;
		Map<E, Pair<V>> originEdges = new HashMap<E, Pair<V>>(graph.getEdgeMap());
		//使用传入的权值和原始边集合构造度量器ModularityQualify
		ClusterQualify<V, E> clusterQualify = new ModularityQualify<V, E>(graph);
		
		//自动切边，两种策略，依照@clusterComplete值
		EdgeBetweennessClusterer<V, E> ebCluster;
		boolean checkPeak = false;
		for (int i = 0; i < originEdges.size(); i++) {
			ebCluster = new EdgeBetweennessClusterer<V, E>(1, edge_weights);
			Set<Set<V>> clusters = ebCluster.transform(optGraph);//1.切边
			Double mq = new Double(clusterQualify.qualify(clusters));//2.评价
			//3.加入track
			qualityTrack.add(mq);
			E removedEdge = ebCluster.getEdgesRemoved().get(0);
			edgeTrack.add(removedEdge);
			printClusterProcess(removedEdge, mq);
			//4.更新最优记录
			if (mq > bestCluster.bestMQ()) {
				checkPeak = true;//从谷底上升后才开始检测峰值
				////System.out.println("best change!");
				bestCluster.setBestClusterSet(clusters);
				bestCluster.setBestTrack(new SimpleKeyValue<Integer, Double>(i, mq));
			}else if (!clusterComplete && checkPeak && mq < bestCluster.bestMQ()) {
				bestCluster = backup(bestCluster, graph);
				break;//如果选定不完全切边选项，则在第一个峰值就退出迭代
			}
		}
		
		//------debug
		System.out.println("----------cluster end--------");
		System.out.println("edgeTrack:" + edgeTrack);
		System.out.println("qualityTrack" + qualityTrack);
		System.out.println("bestCluster" + bestCluster.bestTrack);
		//------
		
		return bestCluster.getBestClusterSet();
	}


	private BestAEBCluster backup(BestAEBCluster bestCluster, ClusterGraph<V, E> graph) {
		ClusterGraph<V, E> optGraph = graph.clone();
		Double bestMQ = Double.NEGATIVE_INFINITY;
		int bestTrack = 0;
		for (int i = qualityTrack.size()-1; i >=0; i--) {
			if (bestMQ <= qualityTrack.get(i)) {
				bestMQ = qualityTrack.get(i);
				bestTrack = i;
			}
		}
		for (int i = 0; i <= bestTrack; i++) {
			optGraph.removeEdge(edgeTrack.get(i));
		}
		WeakComponentClusterer<V, E> wcSearch = new WeakComponentClusterer<V, E>();
		Set<Set<V>> clusterSet = wcSearch.transform(optGraph);
		
		bestCluster.setBestClusterSet(clusterSet);
		bestCluster.setBestTrack(new SimpleKeyValue<Integer, Double>(bestTrack, qualityTrack.get(bestTrack)));
		
		return bestCluster;
	}

	private void printClusterProcess(E removedEdge, Double mq) {
		if (printClusterProcess) {
			System.out.println("remove edge:\t" + removedEdge + "\tMQ = " + mq);
		}	
	}

	public boolean isClusterComplete() {
		return clusterComplete;
	}

	public void setClusterComplete(boolean clusterComplete) {
		this.clusterComplete = clusterComplete;
	}
	
	/**
	 * 最优切边效果entity
	 * @author frogcherry 周晓龙
	 * @created 2010-12-14
	 * @Email frogcherry@gmail.com
	 */
	private class BestAEBCluster{
		private KeyValue<Integer, Double> bestTrack;
		private Set<Set<V>> bestClusterSet;
		
		public BestAEBCluster(Set<Set<V>> initClusterSet) {
			super();
			this.bestTrack = new SimpleKeyValue<Integer, Double>(0, Double.NEGATIVE_INFINITY);
			this.bestClusterSet = initClusterSet;
		}

		public Set<Set<V>> getBestClusterSet() {
			return bestClusterSet;
		}
		
		public void setBestClusterSet(Set<Set<V>> bestClusterSet) {
			this.bestClusterSet = bestClusterSet;
		}
		
		@SuppressWarnings("unused")
		public KeyValue<Integer, Double> getBestTrack() {
			return bestTrack;
		}

		public void setBestTrack(KeyValue<Integer, Double> bestTrack) {
			this.bestTrack = bestTrack;
		}

		public Integer bestTrackIndex(){
			return this.bestTrack.getKey();
		}
		
		public Double bestMQ(){
			return this.bestTrack.getValue();
		}
	}

	@Override
	public Set<Set<V>> autoCluster(Graph<V, E> graph) {
		return transform(graph);
	}

	/**
	 * 获取切边信息
	 * 必须先聚类才能得到移去的边的信息，否则得到的是空集
	 */
	@Override
	public List<E> getRemovedEdges() {
		List<E> edges = new LinkedList<E>();
		for (int i = 0; i <= bestCluster.bestTrackIndex(); i++) {
			edges.add(edgeTrack.get(i));
		}
		
		return edges;
	}

	public boolean isPrintClusterProcess() {
		return printClusterProcess;
	}

	public void setPrintClusterProcess(boolean printClusterProcess) {
		this.printClusterProcess = printClusterProcess;
	}
}
