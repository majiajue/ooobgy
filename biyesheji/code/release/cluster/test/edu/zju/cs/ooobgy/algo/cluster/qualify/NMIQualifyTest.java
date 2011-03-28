package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;

public class NMIQualifyTest extends TestCase{
	
	@Test
	public void testNMI(){
		int i = 0;
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2},{3,4}};
			Integer[][] cbm = {{1,2},{3,4}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2,},{3,4,5},{6}};
			Integer[][] cbm = {{1,2},{3,4,5},{6}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2,},{3,4,5},{6}};
			Integer[][] cbm = {{1,2},{3,4,5,6}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2},{3,4}};
			Integer[][] cbm = {{1,3},{2,4}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2,3},{4,5}};
			Integer[][] cbm = {{1,2},{3,4,5}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2,3,4,5}};
			Integer[][] cbm = {{1,2,3,4,5}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1,2,3}};
			Integer[][] cbm = {{1,2,3}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1}};
			Integer[][] cbm = {{1}};
			nmiCase(cam, cbm);
		}
		
		System.out.println("========= case " + i + " =========");
		i++;
		{
			Integer[][] cam = {{1}};
			Integer[][] cbm = {{1,2}};
			nmiCase(cam, cbm);
		}
	}

	private void nmiCase(Integer[][] cam, Integer[][] cbm) {
		Set<Set<Integer>> ca = makeSet(cam); 
		Set<Set<Integer>> cb = makeSet(cbm);
		
		System.out.println("ca = " + ca);
		System.out.println("cb = " + cb);
		
		@SuppressWarnings("rawtypes")
		NMIQualify qualify = new NMIQualify<Integer>();
		@SuppressWarnings("unchecked")
		double nmi = qualify.qualifyNMI(ca, cb);
		
		System.out.println("NMI(ca,cb) = " + nmi);
	}

	private Set<Set<Integer>> makeSet(Integer[][] cm) {
		Set<Set<Integer>> cs = new HashSet<Set<Integer>>();
		for (Integer[] c : cm) {
			Set<Integer> cc = new HashSet<Integer>();
			for (Integer v : c) {
				cc.add(v);
			}
			cs.add(cc);
		}
		
		return cs;
	}
}
