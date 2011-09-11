package com.taobao.dw.pizza.path_analysis.core.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositeTrace;

import junit.framework.TestCase;

/**
 * 测试匹配效果用例
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-9-10
 */
public class PathMatcherTest extends TestCase {
	private InvertedListReader Ilr;
	
	@Override
	protected void setUp() throws Exception {
		Ilr = new InvertedListReader();
		Ilr.readInvertedList("data/inverted_list_10.data");
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		Ilr = null;
		super.tearDown();
	}
	
	/**
	 * 测试普通路径
	 */
	@Test
	public void testNormal() {
		String[] res;
		
		res = ttestList10("1,2,3,4");//普通轨迹,实
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3)(3-4):p1,-1,1,4,1}]", "LHP:[(1,p2,5,4,2),(1,p4,20,22,2)]");
		
		res = ttestList10("31,32,6,101,102,4");//普通轨迹,虚
		assertResult(res, "CTS:[31+32{p5=(31+32)(32+6)(6-4):p5,-1,31,4,2}]", "LHP:[]");
		
		//不完全
		res = ttestList10("1,2,3");//普通轨迹,实
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3):p1,4,1,4,1}]", "LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		
		res = ttestList10("31,32");//普通轨迹,虚
		assertResult(res, "CTS:[31+32{p5=(31+32):p5,6,31,4,2}]", "LHP:[]");
	}

	/**
	 * 测试单点
	 */
	@Test
	public void testSingle() {
		String[] res;
		
		res = ttestList10("1");//首节点
		assertResult(res, "CTS:[]", "LHP:[(1,p1,2,4,-1),(1,p2,5,4,-1),(1,p4,20,22,-1),(1,p3,2,4,-1)]");

		res = ttestList10("10");//首节点
		assertResult(res, "CTS:[]", "LHP:[(10,p7,11,13,-1)]");

		res = ttestList10("6");//中间节点
		assertResult(res, "CTS:[]", "LHP:[]");

		res = ttestList10("12");//中间节点
		assertResult(res, "CTS:[]", "LHP:[]");

		res = ttestList10("4");//尾节点
		assertResult(res, "CTS:[]", "LHP:[]");

		res = ttestList10("13");//尾节点
		assertResult(res, "CTS:[]", "LHP:[]");
	}
	
	private void assertResult(String[] res, String cts, String lhp) {
//		assertEquals(res[0], cts);
//		assertEquals(res[1], lhp);
	}
	
	private String[] ttestList10(String user_path) {
		System.out.println("=====user_path: " + user_path);
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace(user_path));
//		System.out.println(ats);
		
		for(AtomTrace at:ats){
			Ilr.ips.match(at);
		}
		String[] res = new String[]{"",""};

		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		res[0] = "CTS:" + cts.toString();
		System.out.println(res[0]);
		Set<String[]> lonelyPaths = PathTraceUtils.matchLonelyHeadNode(Ilr.ips, user_path, cts);
		res[1] = "LHP:" + parseLonelyStr(lonelyPaths);
		System.out.println(res[1]);
		
		return res;
	}

	private String parseLonelyStr(Set<String[]> lonelyPaths) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String[] items : lonelyPaths) {
			sb.append("(");
			for (String item : items) {
				sb.append(item).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("),");
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		sb.append("]");
		
		return sb.toString();
	}
}
