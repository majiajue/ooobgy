package edu.zju.cs.ooobgy.algo.math.matrix;

/**
 * 字符型矩阵
 * @author frogcherry 周晓龙
 * @created 2010-12-24
 * @Email frogcherry@gmail.com
 */
public class MatrixStr implements AbstractMatrix<String>{
	private String[][] matrix;
	private int rowCount;
	private int columnCount;
	
	public MatrixStr(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.matrix = new String[rowCount][columnCount];
	}

	public MatrixStr(Matrix matrixd) {
		this.rowCount = matrixd.getRowCount();
		this.columnCount = matrixd.getColumnCount();
		this.matrix = new String[rowCount][columnCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				updateElement(i, j, matrixd.element(i, j).toString());
			}
		}
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public String element(int rowIndex, int columnIndex) {
		return matrix[rowIndex][columnIndex];
	}

	@Override
	public String updateElement(int rowIndex, int columnIndex, String newElement) {
		return matrix[rowIndex][columnIndex] = newElement;
	}

	@Override
	public AbstractMatrix<String> transpose() {
		MatrixStr matrixT = new MatrixStr(columnCount, rowCount);

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				matrixT.updateElement(j, i, matrix[i][j]);
			}
		}

		return matrixT;
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
