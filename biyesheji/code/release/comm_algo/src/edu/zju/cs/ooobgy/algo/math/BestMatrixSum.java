package edu.zju.cs.ooobgy.algo.math;

import java.util.List;

/**
 * 计算矩阵中一些最优组合的方法，这里主要是一些最优元素组合和的方法</br>
 * 最优元素和组合：矩阵的每一行取一个元素，已经取过的列不能再取，这些取到的元素具有最大（最小）值的时候，称作这是一个
 * 最优元素和组合，这个和值被称为最优元素和</br>
 * 例如，矩阵：</br>
 * 1  2  3</br>
 * 3  3  1</br>
 * 的最优组合为m[0][2]+m[1][0]=6,或者m[0][2]+m[1][1]=6</br>
 * 当存在多个解时，返回第一个找到的解</br>
 * 该算法的各个分支在计算的时候，如果矩阵的行数大于列数，会先将其转置，再处理，保证行数小于列出，
 * 但该模块作为黑盒不对外界发生影响</br>
 * 各个算法接口返回最优元素和，可通过getCombination方法获得每行取第几列的，</br>
 * 如果行数大于列数，则该返回数组中的-1表示列数已取完，该行不取;</br>
 * 如果列数大于行数，另通过方法getUnusedCol取得未使用的列</br>
 * @author frogcherry 周晓龙
 * @created 2010-12-22
 * @Email frogcherry@gmail.com
 */
public class BestMatrixSum {
	private List<Integer> combination;
	private List<Integer> unusedCol;
	private Matrix matrix;
	
	/**
	 * 传入待处理的矩阵构造，其他构造已被禁用
	 * @param matrix
	 */
	public BestMatrixSum(Matrix matrix) {
		super();
		this.matrix = matrix;
	}

	/**
	 * 使用贪心法确定相对最优的矩阵元素和组合，这里的贪心法即每行都取最大的一个
	 */
	public double greedBestSumCombination(boolean maxIsBest){
		//TODO:
		return 0;
	}
	
	/**
	 * 求最大匹配
	 * 二部图的最大全匹配,KM算法
	 * @see "二部图的最大全匹配,KM算法"
	 * @param maxIsBest
	 * @return 最大匹配和
	 */
	public double completeBestSumCombination(boolean maxIsBest){
		//TODO:
		KMalgo kmAlgo = new KMalgo();
		return kmAlgo.km(maxIsBest);
	}

	/**
	 * 获得最优组合，每行取第几列
	 * @return
	 */
	public List<Integer> getCombination() {
		return combination;
	}

	/**
	 * 获得未使用的列，如果列数小于行数，该值无意义
	 * @return
	 */
	public List<Integer> getUnusedCol() {
		return unusedCol;
	}
	
	/**
	 * KM算法实现计算二部图最大权匹配的算法
	 * @author frogcherry 周晓龙
	 * @created 2010-12-23
	 * @Email frogcherry@gmail.com
	 */
	private class KMalgo{
		
		public double km(boolean maxIsBest) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
