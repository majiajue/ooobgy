package edu.zju.cs.ooobgy.algo.math;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.algo.util.array.ArrayUtil;

/**
 * 计算矩阵中一些最优组合的方法，这里主要是一些最优元素组合和的方法</br>
 * 最优元素和组合：矩阵的每一行取一个元素，已经取过的列不能再取，这些取到的元素具有最大（最小）值的时候，称作这是一个
 * 最优元素和组合，这个和值被称为最优元素和</br>
 * 例如，矩阵：</br>
 * 1  2  3</br>
 * 3  3  1</br>
 * 的最优组合为m[0][2]+m[1][0]=6,或者m[0][2]+m[1][1]=6</br>
 * 当存在多个解时，返回第一个找到的解</br>
 * 各个算法接口:贪心法greedBestSumCombination,KM完备算法completeBestSumCombination返回最优元素和</br>
 * 通过getCombination方法获得取到的是哪些元素,map<Key行下标,Value列下标>，</br>
 * <li>当行数大于列数时,一些行rowi无法取到任何元素，这时对应map.get(rowi)返回越界的列值表示该行rowi不取值</li>
 * <li>当列数大于行数时,使用map.get(rowj) rowCount<=j<colCount,可以知道那些列没被使用</li></br>
 * 另外使用List getUnusedRow和getUnusedCol可以知道那些行(列没有被使用) </br>
 * </br>
 * 警告：矩阵内的参数非负数；有负数将可能产生不可预料的结果
 * @author frogcherry 周晓龙
 * @created 2010-12-22
 * @Email frogcherry@gmail.com
 */
public class BestMatrixSum {
	private Map<Integer, Integer> combination;
	private Matrix matrix;
	
	/**
	 * 传入待处理的矩阵构造，其他构造已被禁用
	 * @param matrix
	 */
	public BestMatrixSum(Matrix matrix) {
		super();
		this.matrix = matrix;
		this.combination = new HashMap<Integer, Integer>();
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
		Matrix todoMatrix = matrix;
		
		//对于最小权的预处理
		double roof = 0;
		if (!maxIsBest) {
			roof = matrix.maxElement() + 1;
			todoMatrix = matrix.invMatrix(roof);
		}
		
		KMalgo kmAlgo = new KMalgo(todoMatrix);
		double res = kmAlgo.km();
		
		//对于最小权的数据恢复处理
		if (!maxIsBest) {
			int min = matrix.getRowCount();
			if (matrix.getRowCount() > matrix.getColumnCount()) {
				min = matrix.getColumnCount();
			}
			res = res - roof * min;
			res = -res;
		}
		
		return res;
	}

	/**
	 * 获得最优组合，每行取第几列
	 * @return
	 */
	public Map<Integer, Integer> getCombination() {
		return combination;
	}
	
	/**
	 * 取得未被使用的行
	 */
	public List<Integer> getUnusedRow(){
		List<Integer> unRows = new LinkedList<Integer>();
		int columnCount = matrix.getColumnCount();
		int rowCount = matrix.getRowCount();
		if (columnCount >= rowCount) {
			return unRows;
		}
		
		for (Entry<Integer, Integer> kv : combination.entrySet()) {
			if (kv.getValue() >= columnCount) {
				unRows.add(kv.getKey());
			}
		}
		
		return unRows;
	}
	
	/**
	 * 取得未被使用的列
	 */
	public List<Integer> getUnusedCol(){
		List<Integer> unCols = new LinkedList<Integer>();
		int colCount = matrix.getColumnCount();
		int rowCount = matrix.getRowCount();
		if (colCount <= rowCount) {
			return unCols;
		}
		
		for (int i = rowCount; i < colCount; i++) {
			unCols.add(combination.get(i));
		}
		
		return unCols;
	}
	/**
	 * KM算法实现计算二部图最大权匹配的算法
	 * 只做最大值
	 * @author frogcherry 周晓龙
	 * @created 2010-12-23
	 * @Email frogcherry@gmail.com
	 */
	private class KMalgo{	
		private Matrix wMatrix;
		private int maxn;
		private double[] lx;
		private double[] ly;
		private int[] linky;
		private boolean[] visx;
		private boolean[] visy;
		private double lack;
		
		/**
		 * 构造时进行初始化工作
		 */
		public KMalgo(Matrix matrix) {
			this.wMatrix = matrix.makeSquareMatrix(0);//填充成方阵
			this.maxn = wMatrix.getRowCount();
			this.lx = new double[maxn];
			this.ly = new double[maxn];
			this.linky = new int[maxn];
			this.visx = new boolean[maxn];
			this.visy = new boolean[maxn];
		}
		
		/**
		 * KM算法求最大全匹配
		 * @return
		 */
		public double km() {
			this.linky = ArrayUtil.formatArray(linky, Integer.MIN_VALUE, maxn);
			//1.初始化顶标
			for(int i = 0; i < maxn; i++){
		        for(int j = 0; j < maxn; j++){
		        	if (wMatrix.element(i, j) > lx[i]) {
						lx[i] = wMatrix.element(i, j);
					}
		        }
			}
			
			//2.最大全匹配
			for (int x = 0; x < maxn; x++) {
				while (true) {
					this.visx = new boolean[maxn];
					this.visy = new boolean[maxn];
					lack = Integer.MAX_VALUE;
					if(find(x)) 
						break;
					for(int i = 0; i < maxn; i++){
						if(visx[i]) 
							lx[i] -= lack;
						if(visy[i]) 
							ly[i] -= lack;
					}
				}
			}
			
			return 0;
		}

		private boolean find(int x) {
			visx[x] = true;
			for(int y = 0; y < maxn; y++){
				if(visy[y])
					continue;
				double t = lx[x] + ly[y] - wMatrix.element(x, y);
				if(t == 0){
					visy[y] = true;
					if (linky[y] == Integer.MIN_VALUE || find(linky[y])) {
						linky[y] = x;
						return true;
					}
				}else if (lack > t) {
					lack = t;
				}
			}

			return false;
		}
	}
}
