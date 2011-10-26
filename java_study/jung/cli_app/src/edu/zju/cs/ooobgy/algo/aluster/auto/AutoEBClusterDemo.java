package edu.zju.cs.ooobgy.algo.aluster.auto;

import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import edu.zju.cs.ooobgy.algo.cluster.auto.AutoEdgeBetwennessCluster;
import edu.zju.cs.ooobgy.algo.cluster.qualify.NMIQualify;
import edu.zju.cs.ooobgy.data.fileloader.ClusterGraphFileLoader;
import edu.zju.cs.ooobgy.graph.ClusterGraph;

/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-5-5
 * @Email frogcherry@gmail.com
 */
public class AutoEBClusterDemo {
	
	/**
	 * 测试一个case
	 * @param sliceId 对应文件case${sliceId}.csv和real${sliceId}.csv
	 * @param isClusterComplete 切片完全代表切完所有的边再决定取哪个为最优，否则就在MQ第一个峰值时停止
	 * @param isPrintClusterProcess true代表打印出聚类过程的信息，否则不打印
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void testCase(String sliceId, boolean isClusterComplete, boolean isPrintClusterProcess) throws Exception{
		ClusterGraphFileLoader fileLoader = new ClusterGraphFileLoader();
		System.out.println("data loading...");
		ClusterGraph<String, String> graph = fileLoader.loadCase(sliceId);
		Set<Set<String>> realClsters = fileLoader.loadReal(sliceId);
		
		System.out.println("========== graph structure ==========");
		//System.out.println(graph);
		System.out.println("(skip)");
		Date start = new Date();
		System.out.println("========== clustering ==========");
		AutoEdgeBetwennessCluster<String, String> autoCluster = new AutoEdgeBetwennessCluster<String, String>(
				graph.getEdgeWeights(), isClusterComplete, isPrintClusterProcess);
		Set<Set<String>> groups = autoCluster.autoCluster(graph);
		Date end = new Date();
		Double clusterSecs = (end.getTime()-start.getTime())*1.0/1000;
		System.out.println("---------- groups --------");
		System.out.println(groups);
		System.out.println("---------- NMI --------");
		System.out.println("real cluster:" + realClsters);
		NMIQualify qualify = new NMIQualify<String>();
		double NMI = qualify.qualifyNMI(realClsters, groups);
		System.out.println("NMI = " + NMI);
		System.out.println("---------- time cost (second) --------");
		System.out.println(clusterSecs);
	}
	
	public static void main(String[] args) throws Exception{
		String sliceId = args[0];
		boolean isComp = args[1].equalsIgnoreCase("t");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		AutoEBClusterDemo demo = new AutoEBClusterDemo();
		demo.testCase(sliceId, isComp, true);
//		demo.testCase("001", false, true);
		
//		demo.testCase("0001", false, true);
//		demo.testCase("0002", false, true);
//		demo.testCase("0003", false, true);
		
	}
}
