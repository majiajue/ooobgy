package com.taobao.dw.comm.tws;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @Author: zhouxiaolong.pt
 * @created: 2010-7-7
 **/

public class Normalizer_Test extends TestCase{
	private Normalizer normalizer;

	@Override
	protected void setUp() throws Exception {
		this.normalizer = Normalizer.getInstance();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		this.normalizer = null;
		super.tearDown();
	}
	
	@Test
	public void testSimpleCase() {
		String testWord = "‘œ…˝ƒæ÷∆∞À“Ù∫–";
		normalizer.initialize("/home/a/share/phoenix/normalize/conf/norm.conf");

		String normalized1 = normalizer.normalize(testWord);
		String normalized24 = normalizer.normalize(testWord, 24);
		String normalized380 = normalizer.normalize(testWord, 380);

		System.out.println("1:" + normalized1);
		System.out.println("2:" + normalized24);
		System.out.println("3:" + normalized380);
	}
}
