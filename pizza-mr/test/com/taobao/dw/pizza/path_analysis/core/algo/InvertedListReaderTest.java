package com.taobao.dw.pizza.path_analysis.core.algo;

import com.taobao.dw.pizza.path_analysis.core.pojo.AtomPath;
import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;

import junit.framework.TestCase;

public class InvertedListReaderTest extends TestCase {
	public void testRead(){
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/path.20110729");
		llr.ips.rebuildHeadNodeIndex();
		
		System.out.println(llr.ips);
		AtomTrace at = new AtomTrace("13", "+", "141",0,1);
		llr.ips.match(at);
		System.out.println(at);
		
		for (AtomTrace at1: llr.ips.getHeadPath("86")){
			for (AtomPath ap:at1.atomPaths.values()){
				if (ap.isFirstPath){
					System.out.println(ap);
				}
			}
		}
	}
	
	public void testRead1(){
		InvertedListReader llr = new InvertedListReader();
		llr.readInvertedList("data/inverted_list_10.data");
		
		llr.ips.rebuildHeadNodeIndex();
//		System.out.println(llr.ips.headPathIndex);
		System.out.println(llr.ips.toString());
//		System.out.println(llr.ips.getHeadPath("3"));
//		System.out.println(llr.ips.isHeadNode("3"));
//		System.out.println(llr.ips.isHeadNode("4"));
//		System.out.println(llr.ips);
//		AtomTrace at = new AtomTrace("13", "+", "141",0,1);
//		llr.ips.match(at);
//		System.out.println(at);
//		
//		for (AtomTrace at1: llr.ips.getHeadPath("86")){
//			for (AtomPath ap:at1.atomPaths.values()){
//				if (ap.isFirstPath){
//					System.out.println(ap);
//				}
//			}
//		}
	}
		
}
