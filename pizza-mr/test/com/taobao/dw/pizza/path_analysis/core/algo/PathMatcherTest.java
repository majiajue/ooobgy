package com.taobao.dw.pizza.path_analysis.core.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositeTrace;

import junit.framework.TestCase;

/**
 * ≤‚ ‘∆•≈‰–ßπ˚”√¿˝
 * @author Frogcherry ÷‹œ˛¡˙
 * @Email  frogcherry@gmail.com
 * @created  2011-9-10
 */
public class PathMatcherTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	public void testNormal() {
		ttestList10("1,2,3,4");
	}
	
	private void ttestList10(String user_path) {
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace(user_path));
//		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_10.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		//System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		Set<String[]> lonelyPaths = PathTraceUtils.matchLonelyHeadNode(llr.ips, user_path, cts);
		System.out.println("LHP:" + parseLonelyStr(lonelyPaths));
	}

	private String parseLonelyStr(Set<String[]> lonelyPaths) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String[] items : lonelyPaths) {
			sb.append("{");
			for (String item : items) {
				sb.append(item).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		
		return sb.toString();
	}
}
