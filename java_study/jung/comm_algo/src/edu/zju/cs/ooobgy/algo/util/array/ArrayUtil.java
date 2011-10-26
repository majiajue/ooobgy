package edu.zju.cs.ooobgy.algo.util.array;

public class ArrayUtil {
	public static double[] formatArray(double[] array, double value, int size){
		for (int i = 0; i < size; i++) {
			array[i] = value;
		}
		
		return array;
	}
	
	public static int[] formatArray(int[] array, int value, int size) {
		for (int i = 0; i < size; i++) {
			array[i] = value;
		}
		
		return array;
	}
}
