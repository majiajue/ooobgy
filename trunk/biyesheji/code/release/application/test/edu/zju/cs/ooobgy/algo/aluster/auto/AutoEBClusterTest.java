package edu.zju.cs.ooobgy.algo.aluster.auto;

import java.util.Set;

import org.junit.Test;

import edu.zju.cs.ooobgy.algo.cluster.auto.AutoEdgeBetwennessCluster;
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
		System.out.println("==========after cluster==========");
		AutoEdgeBetwennessCluster<String, Integer> autoCluster = new AutoEdgeBetwennessCluster<String, Integer>(
				graph.getEdge_weights());
		Set<Set<String>> groups = autoCluster.autoCluster(graph);
		System.out.println(graph);
		System.out.println("----------groups--------");
		System.out.println(groups);
	}
}
