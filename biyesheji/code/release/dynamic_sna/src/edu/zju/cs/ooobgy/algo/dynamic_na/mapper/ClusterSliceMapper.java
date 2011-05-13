package edu.zju.cs.ooobgy.algo.dynamic_na.mapper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.collections15.Transformer;

import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.algo.dynamic_na.qualify.ClusterSimilarity;
import edu.zju.cs.ooobgy.algo.math.BestMatrixSum;
import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;

/**
 * 对2个切片进行团伙映射，以前一时间片的切片为参数构造映射器
 * 调用map或者transform对传入的切片进行映射处理.注意，此处理默认破坏传入的slice;preSlice保持
 * 
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class ClusterSliceMapper<V, E> implements
		Transformer<ClusterSlice<V, E>, ClusterSlice<V, E>> {
	private ClusterSlice<V, E> preSlice;

	public ClusterSliceMapper(ClusterSlice<V, E> preSlice) {
		super();
		this.preSlice = preSlice;
	}

	@Override
	public ClusterSlice<V, E> transform(ClusterSlice<V, E> nowSlice) {
		return map(nowSlice);
	}

	public ClusterSlice<V, E> map(ClusterSlice<V, E> nowSlice) {
		// 1.生成相似度矩阵
		int preC_cnt = preSlice.getClusters().size();
		//System.err.println(preSlice.getClusters());//debug
		int nowC_cnt = nowSlice.getClusters().size();
		
		List<IdCluster<V>> preClusters = new ArrayList<IdCluster<V>>();
		List<String> pre_col_cid = new ArrayList<String>();
		List<String> now_row_cid = new ArrayList<String>();
		List<IdCluster<V>> nowClusters = new ArrayList<IdCluster<V>>();
		//制作行号序列:矩阵行号序列，对应now切片团id
		Map<String, IdCluster<V>> nowClusterMap = nowSlice.getClusters();
		for (Entry<String, IdCluster<V>> id_cluster : nowClusterMap.entrySet()) {
			now_row_cid.add(id_cluster.getKey());
			nowClusters.add(id_cluster.getValue());
		}
		//制作列号序列:矩阵列号序列，对应pre切片团id
		Map<String, IdCluster<V>> preClusterMap = preSlice.getClusters();
		for (Entry<String, IdCluster<V>> id_cluster : preClusterMap.entrySet()) {
			pre_col_cid.add(id_cluster.getKey());
			preClusters.add(id_cluster.getValue());
		}
		
		Matrix jaccard_mapMatrix = new Matrix(nowC_cnt, preC_cnt);
		Matrix inter_mapMatrix = new Matrix(nowC_cnt, preC_cnt);
		for (int i = 0; i < preC_cnt; i++) {
			ClusterSimilarity<V> clusterSimilarity = new ClusterSimilarity<V>(
					preClusters.get(i).getVertexes());
			for (int j = 0; j < nowC_cnt; j++) {
				Double jaccard = clusterSimilarity.transform(
						nowClusters.get(j).getVertexes());
				Double inter = clusterSimilarity.getIntersection();
				jaccard_mapMatrix.setElement(j, i, jaccard);
				inter_mapMatrix.setElement(j, i, inter);
			}
		}
		//System.err.println(mapMatrix);//debug
		// 2.相似度映射
		BestMatrixSum kmMapper = new BestMatrixSum(jaccard_mapMatrix);
		kmMapper.completeBestSumCombination(true);//默认jac值越大相关性越高
		Map<Integer, Integer> bestMap = kmMapper.getCombination();//映射结果
		//TODO 3.反射写回映射结果，后期需要写回到数据库中
		nowSlice.clearClusters();//清空旧的信息
		for (Entry<Integer, Integer> now_pre : bestMap.entrySet()) {
			int now_i = now_pre.getKey();
			int pre_i = now_pre.getValue();
			if (now_i >= nowC_cnt) {//nowkey越界说明在now_slice不存在该团
				continue;
			}
			
			Color color = Color.black;
			IdCluster<V> nowCluster = nowClusters.get(now_i);
			String id = nowCluster.getId();
			if (pre_i >= preC_cnt) {//匹配越界说明在pre_slice里面不匹配该now_slice团
				//TODO 颜色的指定算法还需要调整,现在是倒排新色
				color = DCD_Cache.similarColors[DCD_Cache.similarColors.length - 1 - now_i % DCD_Cache.similarColors.length];
			} else {//有匹配的情况
				id = preClusters.get(pre_i).getId();
				now_row_cid.set(now_i, id);
				color = preClusters.get(pre_i).getColor();
			}
			nowCluster.setColor(color);
			nowCluster.setId(id);
			nowSlice.addCluster(id, nowCluster);
		}
		
		//写回cache
		DCD_Cache.map_col_pre_cid = pre_col_cid;
		DCD_Cache.map_row_now_cid = now_row_cid;
		DCD_Cache.jaccard_map_matrix = jaccard_mapMatrix;
		DCD_Cache.inter_map_matrix = inter_mapMatrix;

		return nowSlice;
	}
}
