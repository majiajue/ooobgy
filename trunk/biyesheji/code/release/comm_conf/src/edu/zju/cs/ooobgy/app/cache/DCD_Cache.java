package edu.zju.cs.ooobgy.app.cache;

import java.awt.Color;
import java.util.List;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
/**
 * 公共数据区
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class DCD_Cache {
	//1.状态配置
	public static Boolean clusterMapped = false;
	//2.持久数据
	public static final Color[] similarColors =
	{
		new Color(216, 134, 134),
		new Color(135, 137, 211),
		new Color(134, 206, 189),
		new Color(206, 176, 134),
		new Color(194, 204, 134),
		new Color(145, 214, 134),
		new Color(133, 178, 209),
		new Color(103, 148, 255),
		new Color(60, 220, 220),
		new Color(30, 250, 100)
	};
	//3.数据cache
	public static List<String> map_row_now_cid = null;//矩阵行号序列，对应now切片团id
	public static List<String> map_col_pre_cid = null;//矩阵列号序列，对应pre切片团id
	public static Matrix jaccard_map_matrix = null;//jaccard相似度矩阵
	public static Matrix inter_map_matrix = null;//交集规模矩阵
	
}
