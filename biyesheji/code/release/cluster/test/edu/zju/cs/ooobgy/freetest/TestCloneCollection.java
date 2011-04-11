package edu.zju.cs.ooobgy.freetest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class TestCloneCollection extends TestCase{
	
	@Test
	public void testClone(){
		Map<Integer,Integer> old = new HashMap<Integer, Integer>();
		old.put(1, 1);
		old.put(2, 2);
		
		Map<Integer,Integer> newm = new HashMap<Integer, Integer>(old);
		newm.put(1, 3);
		
		System.out.println(old);
		System.out.println("-------------");
		System.out.println(newm);
	}
}
