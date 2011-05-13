package com.ooobgy.freetest;

import java.util.HashSet;
import java.util.Set;

public class FreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Integer> a = new HashSet<Integer>();
		Set<Integer> b = new HashSet<Integer>();
		
		a.add(1);
		a.add(2);
		a.add(3);
		a.add(4);
		
		b.add(1);
		b.add(12);
		b.add(3);
		b.add(43);
		
		Set<Integer> tmp = new HashSet<Integer>(a);
		tmp.retainAll(b);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(tmp);
	}

}
