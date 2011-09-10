package com.taobao.dw.pizza.path_analysis.core.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositePath;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositeTrace;

import junit.framework.TestCase;

public class PathTraceUtilsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSplitPathToAtomPaths(){
		//System.out.println(Arrays.asList(PathTraceUtils.splitPathToAtomPaths("(86+90)(90-91)(91+13)(13+141)(141+16)")));
		System.out.println(Arrays.asList(PathTraceUtils.splitPathToAtomPaths("(86+87)(86+88)(86+90)(87-91)(88-91)(90-91)(91+13)(91+141)(13+141)(141+16)")));
	}
	
	public void testSplitPathToNodes(){
		System.out.println(Arrays.asList(PathTraceUtils.splitPathToNodes("(86+90)(90-91)(91+13)(13+141)(141+16)")));
	}
	
	public void testSplitTrace(){
		List<AtomTrace>results = PathTraceUtils.splitTrace("66,33,88,55");
		assertEquals("[66+33{}, 66-33{}, 66-88{}, 66-55{}, 33+88{}, 33-88{}, 33-55{}, 88+55{}, 88-55{}]", results.toString());
	}

	public void testMergeTrace1(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("66,33,88,55"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_1.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		assertEquals(cts.toString(), "[66+33{p2=(66+33):p2,77,66,77,2, p1=(66+33)(33+88)(88+55):p1,-1,66,55,1}]");
	}

	public void testMergeTrace2(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("66,33,88,55,99"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_2.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		assertEquals(cts.toString(), "[66+33{p2=(66+33)(33-55)(55+99):p2,-1,66,99,2, p1=(66+33)(33+88)(88+55)(55+99):p1,-1,66,99,1}]");
	}	
	
	public void testMergeTrace3(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("1,86,90,91,141"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_3.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		for (CompositeTrace ct :cts){
			if (ct.trace.equals("1+86")){
				CompositePath cp = ct.cps.get("191993227");
				assertEquals("(1+86)",cp.getPathKey());
			}
		}
	}
	
	/**
	 * 测试loop路径
	 * added by xiaolong.zxl
	 */
	public void testMergeTrace4(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("1,2,4,2,3"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_4.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		assertEquals(cts.toString(), "[1+2{p1=(1+2):p1,3,1,3,1}]");
	}
	
	/**
	 * 测试混淆路径
	 * added by xiaolong.zxl
	 */
	public void testMergeTrac5(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("2,3,4"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_5.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		assertEquals(cts.toString(), "[2+3{p2=(2+3):p2,5,2,5,2}]");
	}
	
	/**
	 * 测试loop路径
	 * added by xiaolong.zxl
	 */
	public void testMergeTrace6(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("1,2,3,4"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_6.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
		assertEquals(cts.toString(), "[2+3{p1=(2+3)(3+4):p1,5,2,5,1}]");
	}
	
	/**
	 * 测试客栈1825+1823-1824路径
	 * added by xiaolong.zxl
	 */
	public void testMergeTrace7(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("1825,18123,1,182"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_7.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
//		assertEquals(cts.toString(), "[2+3{p1=(2+3)(3+4):p1,5,2,5,1}]");
	}
	
	/**
	 * 当前线上的List
	 * added by xiaolong.zxl
	 */
	public void testMergeTrace8(){
//		ttestReal("129,1,64,64,40");
//		ttestReal("74,40,21");
//		ttestReal("3,91");
		ttestReal("1825,1823,21,1824");
//		assertEquals(cts.toString(), "[2+3{p1=(2+3)(3+4):p1,5,2,5,1}]");
	}

	private void ttestReal(String user_path) {
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace(user_path));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_8.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
	}
	
	public void testMergeTrace9(){
		List<AtomTrace> ats = new ArrayList<AtomTrace>();
		ats.addAll(PathTraceUtils.splitTrace("2,-1,3"));
		System.out.println(ats);
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_9.data");
		for(AtomTrace at:ats){
			llr.ips.match(at);
		}
		System.out.println("ATS:" + ats);
		List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);
		System.out.println("CTS:" + cts);
//		assertEquals(cts.toString(), "[2+3{p1=(2+3)(3+4):p1,5,2,5,1}]");
	}
	
	public void testSplitNodes(){
		String[] ss = PathTraceUtils.splitPathToAtomPaths("(86+87)((87-91)(88-91)(90-91)(91+13)(91+141)(13+141)(141+16)");
		for (String s:ss)
			System.out.println(s);
	}
	
	public void testLateralView(){
		List<String[]> originalPaths = new ArrayList<String[]>();
		originalPaths.add(new String[]{"2", "5"});
		originalPaths.add(new String[]{"1", "2", "3", "4", "5"});
		originalPaths.add(new String[]{"7", "8", "9"});

		String [] result = PathTraceUtils.lateralView(originalPaths, 0);
		System.out.println(Arrays.asList(result));
		assertEquals(result.length, 30);		
	}
	
	public void testExtractTraces(){
		Set<String> traces = new LinkedHashSet<String>();
		traces.add("2, 5");
		traces.add("1, 4, 6");
		traces.add("7, 8");
		String [] result = PathTraceUtils.extractTraces(traces);
		System.out.println(Arrays.asList(result));
		assertEquals("[2,1,7, 2,1,8, 2,4,7, 2,4,8, 2,6,7, 2,6,8, 5,1,7, 5,1,8, 5,4,7, 5,4,8, 5,6,7, 5,6,8]", Arrays.asList(result).toString());
	}
	
	public void testGetAtsWithHeadNode(){
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list.data");
		
		System.out.println(PathTraceUtils.findAtsWithHeadNode("86", llr.ips));
	}
}
