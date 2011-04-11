package edu.zju.cs.ooobgy.algo.aluster;

import java.util.Set;

import org.junit.Test;

import edu.zju.cs.ooobgy.algo.cluster.EdgeBetweennessClusterer;
import edu.zju.cs.ooobgy.dt.db.ClusterGraphDBLoader;
import edu.zju.cs.ooobgy.graph.ClusterGraph;
import junit.framework.TestCase;

public class EBClusterTest extends TestCase {
	@Test
	public void testCase1() {
		ClusterGraphDBLoader dbLoader = new ClusterGraphDBLoader();
		ClusterGraph<String, Integer> graph = dbLoader.load("001001");
		System.out.println("==========before cluster==========");
		System.out.println(graph);
		System.out.println("==========after cluster==========");
		EdgeBetweennessClusterer<String, Integer> clusterer = new EdgeBetweennessClusterer<String, Integer>(1,graph.getEdgeWeights());
		Set<Set<String>> groups = clusterer.transform(graph);
		System.out.println(graph);
		System.out.println("----------groups--------");
		System.out.println(groups);
		System.out.println("rm edges: " + clusterer.getEdgesRemoved());
		System.out.println("rm edges: " + clusterer.getEdges_removed());
	}
}
