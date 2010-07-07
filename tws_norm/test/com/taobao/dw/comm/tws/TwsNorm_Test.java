package com.taobao.dw.comm.tws;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * @Author: zhouxiaolong.pt
 * @created: 2010-7-7
 **/

public class TwsNorm_Test extends TestCase {
	private TwsNorm twsNorm;

	@Override
	protected void setUp() throws Exception {
		this.twsNorm = TwsNorm.getInstance();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {		
		this.twsNorm = null;
		super.tearDown();
	}
	
	@Test
	public void testSimpleCase() {
		String query = "iPhone4 美行";
		twsNorm.initOnLocal();
		System.out.println("归一化前：" + query);
		System.out.println("归一化后: " + twsNorm.uniformQuery(query));
		twsNorm.uninit();
	}
}
