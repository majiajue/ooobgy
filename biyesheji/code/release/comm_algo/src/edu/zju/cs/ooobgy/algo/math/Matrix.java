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
	
	/**
	 * 使用二维数组和行列规模初始化一个矩阵
	 * @param matrix
	 * @param rowCount
	 * @param columnCount
	 */
	public Matrix(double[][] matrix, int rowCount, int columnCount) {
		this.matrix = matrix;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * 使用行列规模初始化一个空的矩阵
	 * @param rowCount
	 * @param columnCount
	 */
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
		checkColumn(columnIndex);
		checkRow(rowIndex);
		return matrix[rowIndex][columnIndex];
	}
	
	/**
	 * 获得某行的加和结果
	 * @return
	 */
	public double sumRow(int rowIndex){
		checkRow(rowIndex);
		
		double sum = 0;
		for (int i = 0; i < columnCount; i++) {
			sum += matrix[rowIndex][i];
		}
		
		return sum;
	}

	private void checkRow(int rowIndex) {
		if (rowIndex >= rowCount) {
			throw new IllegalArgumentException("matrix row index <" + rowIndex + "> id out of bound [" + rowCount + "]!");
		}
	}
	
	/**
	 * 获得某列的加和结果
	 * @return
	 */
	public double sumColumn(int columnIndex){
		checkColumn(columnIndex);
		
		double sum = 0;
		for (int i = 0; i < rowCount; i++) {
			sum += matrix[i][columnIndex];
		}
		
		return sum;
	}

	private void checkColumn(int columnIndex) {
		if (columnIndex >= columnCount) {
			throw new IllegalArgumentException("matrix column index <" + columnIndex + "> id out of bound [" + columnCount + "]!");
		}
	}
	
	/**
	 * 对矩阵所有元素进行加和
	 * @return
	 */
	public double sumElements() {
		double sum = 0;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				sum += matrix[i][j];
			}
		}
		
		return sum;
	}
	
	/**
	 * 元素递加一个数据
	 * @param rowIndex
	 * @param columnIndex
	 * @param addition
	 * @return
	 */
	public double addElement(int rowIndex, int columnIndex, double addition){
		checkColumn(columnIndex);
		checkRow(rowIndex);
		matrix[rowIndex][columnIndex] += addition;
		return matrix[rowIndex][columnIndex];
	}

	@Override
	public String toString() {
		StringBuilder matrixStr = new StringBuilder();
		for (int i = 0; i < rowCount; i++) {
			matrixStr.append("[");
			for (int j = 0; j < columnCount; j++) {
				matrixStr.append(matrix[i][j]);
				if (j < columnCount - 1) {
					matrixStr.append("\t");
				}
			}
			matrixStr.append("]");
			if (i < rowCount - 1) {
				matrixStr.append("\n");
			}
		}
		
		return matrixStr.toString();
	}
}
