package edu.zju.cs.ooobgy.algo.math.km;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import edu.zju.cs.ooobgy.algo.math.BestMatrixSum;
import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;
import edu.zju.cs.ooobgy.algo.math.matrix.MatrixStr;
import junit.framework.TestCase;

/**
 * KM算法测试类
 * @author frogcherry 周晓龙
 * @created 2010-12-24
 * @Email frogcherry@gmail.com
 */
public class KMTest extends TestCase{
	private Matrix matrix;
	private int caseId = 0;
	
	@Override
	protected void setUp() throws Exception {
		double[][] old = {{3,1,1},{1,1,3},{1,3,1}};
		matrix = new Matrix(old, 3, 3);
		super.setUp();
	}
	
	@Test
	public void testKM(){
		BestMatrixSum bestMatrixSum = new BestMatrixSum(matrix);
		bestMatrixSum.completeBestSumCombination(true);
		MatrixStr matrixStr = new MatrixStr(matrix.makeSquareMatrix(0));
		Map<Integer, Integer> linky = bestMatrixSum.getCombination();
		
		System.out.println("------------ case " + caseId + " ------------");
		System.out.println(linky);
		for (Entry<Integer, Integer> kv : linky.entrySet()) {
			String chosed = "<" + matrixStr.element(kv.getKey(), kv.getValue()) + ">";
			matrixStr.updateElement(kv.getKey(), kv.getValue(), chosed);
		}
		System.out.println(matrixStr);
		caseId ++;
	}
	
	@Test
	public void testOther(){
		caseId = 10;
		{double[][] old = {{1,7,1},{1,7,3},{1,8,1}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{8,2,8},{8,2,7},{8,1,8}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{8,1,8},{8,8,1},{8,8,8}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{9,1,8},{8,8,1},{8,8,8}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{9,1,8},{8,8,1},{8,8,1}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{9,1,8},{8,8,1},{1,8,1}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{9,1,8},{1,8,1},{1,8,1}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
		
		{double[][] old = {{9,1,8},{1,9,1},{1,8,1}};
		matrix = new Matrix(old, 3, 3);
		testKM();}
	}
	
	/**
	 * 测试非方阵
	 */
	public void testNosq(){
		caseId = 20;
		
		{double[][] old = {{9,1,8,10},{1,9,1,10},{1,8,1,10}};
		matrix = new Matrix(old, 3, 4);
		testKM();}

		{double[][] old = {{9,1,8,10},{1,8,1,10},{1,8,1,10}};
		matrix = new Matrix(old, 3, 4);
		testKM();}
		
		{double[][] old = {{9,1,8},{1,8,1},{1,8,1},{10,10,10}};
		matrix = new Matrix(old, 4, 3);
		testKM();}
		
		{double[][] old = {{5,1,8,1,8,10,1,8,1,10,10,10}};
		matrix = new Matrix(old, 1, 12);
		testKM();}
		
		{double[][] old = {{5},{1},{8},{1},{8},{10},{1},{8},{1},{12},{10},{10}};
		matrix = new Matrix(old, 12, 1);
		testKM();}
	}
	
	public static void main(String[] args) {
		KMTest kmTest = new KMTest();
		kmTest.testOther();
	}
}
