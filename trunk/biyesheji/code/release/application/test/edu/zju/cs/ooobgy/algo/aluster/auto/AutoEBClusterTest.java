package edu.zju.cs.ooobgy.algo.aluster.auto;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.zju.cs.ooobgy.algo.cluster.auto.AutoEdgeBetwennessCluster;
import edu.zju.cs.ooobgy.algo.cluster.qualify.NMIQualify;
import edu.zju.cs.ooobgy.dt.db.ClusterGraphDBLoader;
import edu.zju.cs.ooobgy.graph.ClusterGraph;

import junit.framework.TestCase;

public class AutoEBClusterTest extends TestCase {

	@Test
	public void testCase1() {
		ClusterGraphDBLoader dbLoader = new ClusterGraphDBLoader();
		ClusterGraph<String, Integer> graph = dbLoader.load("001001");
		System.out.println("==========before cluster==========");
		System.out.println(graph);
		System.out.println("==========clustering==========");
		AutoEdgeBetwennessCluster<String, Integer> autoCluster = new AutoEdgeBetwennessCluster<String, Integer>(
				graph.getEdgeWeights());
		Set<Set<String>> groups = autoCluster.autoCluster(graph);
		System.out.println("==========after cluster==========");
		System.out.println(graph);
		System.out.println("----------groups--------");
		System.out.println(groups);
		System.out.println("----------NMI--------");
		Set<Set<String>> realClsters = makeSet(new String[][]{{"1","2","3"},{"4","5","6","7"}});
		System.out.println("real cluster:" + realClsters);
		NMIQualify qualify = new NMIQualify<String>();
		double NMI = qualify.qualifyNMI(realClsters, groups);
		System.out.println("NMI = " + NMI);
	}
	
	@Test
	public void testCase2() {
		ClusterGraphDBLoader dbLoader = new ClusterGraphDBLoader();
		ClusterGraph<String, Integer> graph = dbLoader.load("201002");
		System.out.println("==========before cluster==========");
		System.out.println(graph);
		System.out.println("==========clustering==========");
		AutoEdgeBetwennessCluster<String, Integer> autoCluster = new AutoEdgeBetwennessCluster<String, Integer>(
				graph.getEdgeWeights());
		Set<Set<String>> groups = autoCluster.autoCluster(graph);
		System.out.println("==========after cluster==========");
		System.out.println(graph);
		System.out.println("----------groups--------");
		System.out.println(groups);
		System.out.println("----------NMI--------");
		Set<Set<String>> realClsters = makeSet(new String[][]{{"1","2","3"},{"4","5","6","7"}});
		System.out.println("real cluster:" + realClsters);
		NMIQualify qualify = new NMIQualify<String>();
		double NMI = qualify.qualifyNMI(realClsters, groups);
		System.out.println("NMI = " + NMI);
	}
	
	private Set<Set<String>> makeSet(String[][] cm) {
		Set<Set<String>> cs = new HashSet<Set<String>>();
		for (String[] c : cm) {
			Set<String> cc = new HashSet<String>();
			for (String v : c) {
				cc.add(v);
			}
			cs.add(cc);
		}
		
		return cs;
	}
}
