package edu.zju.cs.ooobgy.algo.math;

/**
 * 描述矩阵的数据结构的封装，仅实现现在需要的功能
 * 内容元素使用double基本类型填充，暂时不需要复杂的封装实现
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 */
public class Matrix {
	private double[][] matrix;
	private int rowCount;
	private int columnCount;
	
	public Matrix(double[][] matrix, int rowCount, int columnCount) {
		this.matrix = matrix;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}

	public Matrix(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.matrix = new double[rowCount][columnCount];
	}
	
	/**
	 * 取一个元素
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public double element(int rowIndex, int columnIndex){
		//TODO Exception
		return matrix[rowIndex][columnIndex];
	}
	
	/**
	 * 获得某行的加和结果
	 * @return
	 */
	public double sumRow(int rowIndex){
		if (rowIndex >= rowCount) {
			throw new IllegalArgumentException("matrix row index <" + rowIndex + "> id out of bound [" + rowCount + "]!");
		}
		
		double sum = 0;
		for (int i = 0; i < columnCount; i++) {
			sum += matrix[rowIndex][i];
		}
		
		return sum;
	}
}
