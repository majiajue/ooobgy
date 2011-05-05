package edu.zju.cs.ooobgy.data.fileloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.zju.cs.ooobgy.graph.ClusterGraph;
import edu.zju.cs.ooobgy.graph.weight.EdgesWeight;

/**
 * 从文件载入数据
 * @author frogcherry 周晓龙
 * @created 2011-5-5
 * @Email frogcherry@gmail.com
 */
public class ClusterGraphFileLoader {

	public ClusterGraph<String, String> loadCase(String sliceId) throws Exception {
		ClusterGraph<String, String> graph = new ClusterGraph<String, String>();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						"data/case" + sliceId + ".csv")), "UTF-8"));
		String line = null;
		int edgeId = 0;
		EdgesWeight<String, Double> edge_weights = new EdgesWeight<String, Double>();
		while ((line = reader.readLine()) != null) {
			String[] items = line.split(",");
			String edge = Integer.toString(edgeId);
			String pnode_1 = items[0];
			String pnode_2 = items[1];
			Double weight = new Double(items[2]);
			graph.addEdge(edge, pnode_1, pnode_2);
			edge_weights.addEdgeWeight(edge, weight);

			edgeId++;
		}
		graph.setEdge_weights(edge_weights);
		
		return graph;
	}

	public Set<Set<String>> loadReal(String sliceId) throws Exception{	
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						"data/real" + sliceId + ".csv")), "UTF-8"));
		String line = null;
		Map<String, Set<String>> clMap = new HashMap<String, Set<String>>();
		while ((line = reader.readLine()) != null) {
			String[] items = line.split(",");
			String clId = items[0];
			String nodeId = items[1];
			Set<String> cl;
			if (!clMap.containsKey(clId)) {
				cl = new HashSet<String>();
			}else {
				cl = clMap.get(clId);
			}
			cl.add(nodeId);
			clMap.put(clId, cl);
		}

		Set<Set<String>> clusters = new HashSet<Set<String>>(clMap.values());
		return clusters;
	}

}
