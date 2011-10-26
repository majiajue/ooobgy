package edu.zju.cs.ooobgy.algo.math.matrix;

/**
 * 公共的矩阵接口
 * @author frogcherry 周晓龙
 * @created 2010-12-24
 * @Email frogcherry@gmail.com
 */
public interface AbstractMatrix<T> {

	public int getRowCount();

	public int getColumnCount();

	/**
	 * 取一个元素
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public T element(int rowIndex, int columnIndex);

	/**
	 * 更新矩阵的元素，返回旧的元素
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param newElement
	 * @return
	 */
	public T updateElement(int rowIndex, int columnIndex,
			T newElement);

	/**
	 * 输出转置后的矩阵，原矩阵不变
	 * 
	 * @return
	 */
	public AbstractMatrix<T> transpose();

}