package edu.zju.cs.ooobgy.algo.dynamic_na;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;

/**
 * 对两个切片进行动态分析
 * 
 * @author frogcherry 周晓龙
 * @created 2010-12-22
 * @Email frogcherry@gmail.com
 */
public class DynamicCluster<V, E> implements
		Transformer<ClusterSlice<V, E>, ClusterSlice<V, E>> {
	private ClusterSlice<V, E> preSlice;// 已知的上一切片
	private Set<Set<V>> newSliceClusters;// 已知的待处理切片的团伙信息

	/**
	 * 使用已知的上一切片，已知的待处理切片的团伙信息进行构造; 已知条件不满足无法进行分析
	 * 
	 * @param preSlice
	 * @param newSliceClusters
	 */
	public DynamicCluster(ClusterSlice<V, E> preSlice,
			Set<Set<V>> newSliceClusters) {
		super();
		this.preSlice = preSlice;
		this.newSliceClusters = newSliceClusters;
	}

	/**
	 * 传入新的切片（包含图信息，不包含团伙对应信息）,根据已知的上一切片进行对应动态分析
	 */
	@Override
	public ClusterSlice<V, E> transform(ClusterSlice<V, E> newSlice) {
		ClusterSlice<V, E> slice = newSlice;
		List<Entry<String, IdCluster<V>>> preCluster = new ArrayList<Entry<String, IdCluster<V>>>(
				preSlice.getClusters().entrySet());//已知团伙的id对应情况，转为list方便后续分析
		List<Set<V>> newClusters = new ArrayList<Set<V>>(newSliceClusters);//新的团伙信息，待分析list
		Matrix similarityMatrix = new Matrix(preCluster.size(), newClusters.size());//相似度矩阵
		
		//1.填充相似度矩阵
		for (int i = 0; i < preCluster.size(); i++) {
			
		}
		
		// TODO Auto-generated method stub
		return slice;
	}

}
