package matrix;

/**
 * 公共的矩阵接叄1�7
 * @author frogcherry 周晓龄1�7
 * @created 2010-12-24
 * @Email frogcherry@gmail.com
 */
public interface AbstractMatrix<T> {

	public int getRowCount();

	public int getColumnCount();

	/**
	 * 取一个元約1�7
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