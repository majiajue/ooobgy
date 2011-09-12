package com.taobao.dw.pizza.path_analysis.core.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositeTrace;

import junit.framework.TestCase;

/**
 * 测试匹配效果用例,基于路径数据集data/inverted_list_10.data
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
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3)(3-4):p1,-1,1,4,1}]", "LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		
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
		assertResult(res, "CTS:[]", "LHP:[(1,p4,20,22,-1),(1,p3,2,4,-1),(1,p2,5,4,-1),(1,p1,2,4,-1)]");

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
	
	/**
	 * 测试溢出轨迹
	 * 所谓溢出：是指轨迹未严格按照定义路径进行，例如对于定义路径1+2+3-4，用户轨迹7+1+2属于上溢出
	 */
	@Test
	public void testOverflow() {
		String[] res;
		
		res = ttestList10("101,102,1,2,3,4");//上溢出完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3)(3-4):p1,-1,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("101,102,1,2,3");//上溢出不完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3):p1,4,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("101,102,1");//上溢出单点
		assertResult(res, "CTS:[]", "LHP:[(1,p4,20,22,-1),(1,p3,2,4,-1),(1,p2,5,4,-1),(1,p1,2,4,-1)]");
		
		res = ttestList10("1,2,3,4,101,102");//下溢出完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3)(3-4):p1,-1,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("1,2,3,101,102");//下溢出不完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3):p1,4,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("1,101,102");//下溢出单点
		assertResult(res, "CTS:[]", "LHP:[(1,p4,20,22,101),(1,p3,2,4,101),(1,p2,5,4,101),(1,p1,2,4,101)]");

		
		res = ttestList10("111,112,1,2,3,4,101,102");//全溢出完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3)(3-4):p1,-1,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("111,112,1,2,3,101,102");//全溢出不完全
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2)(2+3):p1,4,1,4,1}]", 
				"LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("111,112,1,101,102");//全溢出单点
		assertResult(res, "CTS:[]", "LHP:[(1,p4,20,22,101),(1,p3,2,4,101),(1,p2,5,4,101),(1,p1,2,4,101)]");
	}
	
	/**
	 * 测试完全不匹配路径
	 */
	@Test
	public void testNonePath(){
		String[] res;
		
		res = ttestList10("404,505");//完全不存在
		assertResult(res, "CTS:[]", "LHP:[]");
		
		res = ttestList10("404,505,2,3");//中间挖一段
		assertResult(res, "CTS:[]", "LHP:[]");
		res = ttestList10("404,505,2,3,707");//中间挖一段
		assertResult(res, "CTS:[]", "LHP:[]");
		res = ttestList10("2,3");//中间挖一段
		assertResult(res, "CTS:[]", "LHP:[]");
		res = ttestList10("2,3,404,505");//中间挖一段
		assertResult(res, "CTS:[]", "LHP:[]");
		
		res = ttestList10("3,8,12,17");//随意抓几个
		assertResult(res, "CTS:[]", "LHP:[]");
		res = ttestList10("404,3,8,12,17");//随意抓几个
		assertResult(res, "CTS:[]", "LHP:[]");
	}
	
	/**
	 * 测试循环路径
	 */
	@Test
	public void testLoopPath(){
		String[] res;
		
		res = ttestList10("1,2,101,102,2,3,4");//外循环
		assertResult(res, "CTS:[1+2{p3=(1+2):p3,7,1,4,2, p1=(1+2):p1,3,1,4,1}]", "LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("1,2,7,8,2,7,8,4");//内循环
		assertResult(res, "CTS:[1+2{p3=(1+2)(2+7)(7+8):p3,4,1,4,2, p1=(1+2):p1,3,1,4,1}]", "LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
		res = ttestList10("1,2,7,8,404,505,2,7,8,4");//混合循环
		assertResult(res, "CTS:[1+2{p3=(1+2)(2+7)(7+8):p3,4,1,4,2, p1=(1+2):p1,3,1,4,1}]", "LHP:[(1,p4,20,22,2),(1,p2,5,4,2)]");
	}
	
	private void assertResult(String[] res, String cts, String lhp) {
		assertEquals(res[0], cts);
		assertEquals(res[1], lhp);
	}
	
	/**
	 * 
	 * @param user_path
	 * @return
	 */
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
		res[1] = "LHP:" + toLonelyHeadPathString(lonelyPaths);
		System.out.println(res[1]);
		
		return res;
	}

	private String toLonelyHeadPathString(Set<String[]> lonelyPaths) {
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
