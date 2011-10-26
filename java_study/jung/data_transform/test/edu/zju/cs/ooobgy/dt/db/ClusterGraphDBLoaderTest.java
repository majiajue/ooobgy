package edu.zju.cs.ooobgy.dt.db;

import org.junit.Test;

import edu.zju.cs.ooobgy.graph.Graph;

import junit.framework.TestCase;

public class ClusterGraphDBLoaderTest extends TestCase{
	private ClusterGraphDBLoader dbLoader;
	
	@Override
	protected void setUp() throws Exception {
		dbLoader = new ClusterGraphDBLoader();
		super.setUp();
	}
	
	@Test
	public void testCase1() {
		Graph<String, Integer> graph = dbLoader.load("001001");
		System.out.println(graph);
	}
}
