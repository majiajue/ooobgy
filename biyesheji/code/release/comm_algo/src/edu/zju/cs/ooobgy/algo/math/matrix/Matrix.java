package edu.zju.cs.ooobgy.algo.math.matrix;

/**
 * 描述矩阵的数据结构的封装，仅实现现在需要的功能 内容元素使用double基本类型填充，暂时不需要复杂的封装实现，
 * 如在其他场合使用，请转化为double数值矩阵
 * 
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 */
public class Matrix implements AbstractMatrix<Double> {
	private double[][] matrix;
	private int rowCount;
	private int columnCount;

	/**
	 * 使用二维数组和行列规模初始化一个矩阵
	 * 
	 * @param matrix
	 * @param rowCount
	 * @param columnCount
	 */
	public Matrix(double[][] matrix, int rowCount, int columnCount) {
		this.matrix = matrix;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}
	
	/**
	 * 使用行列规模初始化一个空的矩阵
	 * 
	 * @param rowCount
	 * @param columnCount
	 */
	public Matrix(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.matrix = new double[rowCount][columnCount];
	}
	
	/**
	 * 创建一个克隆
	 * @param tMatrix
	 */
	public Matrix(Matrix tMatrix) {
		this.rowCount = tMatrix.getRowCount();
		this.columnCount = tMatrix.getColumnCount();
		this.matrix = new double[rowCount][columnCount];
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				this.updateElement(i, j, tMatrix.element(i, j));
			}
		}
	}

	/**
	 * @see edu.zju.cs.ooobgy.algo.math.matrix.AbstractMatrix#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @see edu.zju.cs.ooobgy.algo.math.matrix.AbstractMatrix#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @see edu.zju.cs.ooobgy.algo.math.matrix.AbstractMatrix#element(int, int)
	 */
	@Override
	public Double element(int rowIndex, int columnIndex) {
		checkColumn(columnIndex);
		checkRow(rowIndex);
		return matrix[rowIndex][columnIndex];
	}

	/**
	 * 获得某行的加和结果
	 * 
	 * @return
	 */
	public double sumRow(int rowIndex) {
		checkRow(rowIndex);

		double sum = 0;
		for (int i = 0; i < columnCount; i++) {
			sum += matrix[rowIndex][i];
		}

		return sum;
	}

	/**
	 * 检查行参数，越界则抛出异常
	 * 
	 * @param rowIndex
	 */
	private void checkRow(int rowIndex) {
		if (rowIndex >= rowCount) {
			throw new IllegalArgumentException("matrix row index <" + rowIndex
					+ "> id out of bound [" + rowCount + "]!");
		}
	}

	/**
	 * 检查列行数，越界则抛出异常
	 * 
	 * @param columnIndex
	 */
	private void checkColumn(int columnIndex) {
		if (columnIndex >= columnCount) {
			throw new IllegalArgumentException("matrix column index <"
					+ columnIndex + "> id out of bound [" + columnCount + "]!");
		}
	}

	/**
	 * 获得某列的加和结果
	 * 
	 * @return
	 */
	public double sumColumn(int columnIndex) {
		checkColumn(columnIndex);

		double sum = 0;
		for (int i = 0; i < rowCount; i++) {
			sum += matrix[i][columnIndex];
		}

		return sum;
	}

	/**
	 * 对矩阵所有元素进行加和
	 * 
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
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param addition
	 * @return
	 */
	public double addElement(int rowIndex, int columnIndex, double addition) {
		checkColumn(columnIndex);
		checkRow(rowIndex);
		matrix[rowIndex][columnIndex] += addition;
		return matrix[rowIndex][columnIndex];
	}

	/**
	 * @see edu.zju.cs.ooobgy.algo.math.matrix.AbstractMatrix#updateElement(int, int, double)
	 */
	@Override
	public Double updateElement(int rowIndex, int columnIndex, Double newElement) {
		checkColumn(columnIndex);
		checkRow(rowIndex);
		double oldElement = matrix[rowIndex][columnIndex];
		matrix[rowIndex][columnIndex] = newElement;
		return oldElement;
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

	/**
	 * @see edu.zju.cs.ooobgy.algo.math.matrix.AbstractMatrix#transpose()
	 */
	@Override
	public Matrix transpose() {
		Matrix matrixT = new Matrix(columnCount, rowCount);

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				matrixT.updateElement(j, i, matrix[i][j]);
			}
		}

		return matrixT;
	}

	/**
	 * 用defaultElement填充矩阵，使其成为方阵 返回处理后的方阵，
	 * 原矩阵不变
	 * 
	 * @return
	 */
	public Matrix makeSquareMatrix(double defaultElement) {
		if (columnCount == rowCount) {//如果本来就是方阵，直接返回
			return new Matrix(this);
		}
		
		boolean rowMore = rowCount > columnCount;	
		int squareCount = rowMore ? rowCount : columnCount;
		Matrix matrixS = new Matrix(squareCount, squareCount);
		//1.填充旧矩阵
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				matrixS.updateElement(i, j, matrix[i][j]);
			}
		}
		
		//2.默认值填充其他位置
		for (int i = rowCount; i < squareCount; i++) {
			for (int j = columnCount; j < squareCount; j++){
				matrixS.updateElement(i, j, defaultElement);
			}
		}

		return matrixS;
	}
	
	/**
	 * 将矩阵的每个元素都取反，返回新矩阵，原矩阵不处理
	 * @return
	 */
	public Matrix negElements() {
		Matrix nMatrix = new Matrix(rowCount, columnCount);
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				nMatrix.updateElement(i, j, -matrix[i][j]);
			}
		}
		
		return nMatrix;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Matrix cloneMatrix = new Matrix(this);
		return cloneMatrix;
	}
	
	/**
	 * 转换成str矩阵
	 * @return
	 */
	public MatrixStr toMatrixStr() {
		return new MatrixStr(this);
	}
	
	/**
	 * 返回矩阵中的最大元素
	 * @return
	 */
	public double maxElement(){
		double max = matrix[0][0] ;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (matrix[i][j] > max) {
					max = matrix[i][j];
				}
			}
		}
		
		return max;
	}
	
	/**
	 * 将矩阵中的每一个元素对一个值complete取补
	 * 即m[i][j] = complete - m[i][j]
	 * @param complete
	 * @return
	 */
	public Matrix invMatrix(double complete){
		Matrix invMatrix = new Matrix(rowCount, columnCount);
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				invMatrix.updateElement(i, j, complete - matrix[i][j]);
			}
		}
		
		return invMatrix;
	}
}
