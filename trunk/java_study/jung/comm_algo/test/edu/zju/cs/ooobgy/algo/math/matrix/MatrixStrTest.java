package edu.zju.cs.ooobgy.algo.math.matrix;

import junit.framework.TestCase;

public class MatrixStrTest extends TestCase{
private Matrix matrix;
	
	@Override
	protected void setUp() throws Exception {
		double[][] old = {{1,2,3,4},{11,22,33,44},{111,222,333,444}};
		matrix = new Matrix(old, 3, 4);
		super.setUp();
	}
	
	public void testStr(){
		System.out.println(matrix);
		System.out.println("--------------------");
		System.out.println(matrix.toMatrixStr());
	}
}
