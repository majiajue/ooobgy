package edu.zju.cs.ooobgy.algo.math;

import junit.framework.TestCase;

public class MatrixTest extends TestCase{
	public void testMatrix(){
		Matrix matrix = new Matrix(3, 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(matrix.element(i, j) + "\t");
			}
			System.out.println();
		}
	}
}
