package edu.zju.cs.ooobgy.algo.rand;

import java.util.Random;

/**
 * 随机整数工具
 * @author frogcherry 周晓龙
 * @created 2011-5-3
 * @Email frogcherry@gmail.com
 */
public class IntRandom {
	
	/**
	 * 生成随机整数r, bottom <= r <= top
	 * @param bottom
	 * @param top
	 * @return
	 */
	public static Integer rand(int bottom, int top) {
		Random rand = new Random((new Random()).nextLong());
				
		return rand.nextInt(top - bottom + 1) + bottom;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(IntRandom.rand(1, 10));
		}
	}
}
