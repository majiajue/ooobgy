package edu.zju.cs.ooobgy.app.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import edu.zju.cs.ooobgy.algo.dynamic_na.ClusterSliceMapper;
import edu.zju.cs.ooobgy.algo.dynamic_na.analayzer.NodeEventAnalyzer;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.NodeEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;
import edu.zju.cs.ooobgy.app.platform.TimeSliceClusterPlatform;

public class DynamicClusterActionListener implements ActionListener {
	private TimeSliceClusterPlatform rightPlatform;
	private ClusterSlice<String, Integer> preSlice;
	private ClusterSlice<String, Integer> nowSlice;

	public DynamicClusterActionListener(TimeSliceClusterPlatform leftPlatform,
			TimeSliceClusterPlatform rightPlatform) {
		super();
		this.rightPlatform = rightPlatform;
		preSlice = leftPlatform.getClusterSlice();
		nowSlice = rightPlatform.getClusterSlice();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("DA_all")) {
			allAction();
		}else if (cmd.equals("DA_node")) {
			nodeAction();
		}else if (cmd.equals("DA_cluster")) {
			clusterAction();
		}
	}

	private void clusterAction() {
		mapCluster();
		
	}

	private void nodeAction() {
		mapCluster();
		NodeEventAnalyzer<String, Integer> analyzer = new NodeEventAnalyzer<String, Integer>(preSlice);
		Set<NodeEvent<String>> nodeEvents = analyzer.analyze(nowSlice);
		System.out.println("================= Node Events =================");
		for (NodeEvent<String> nodeEvent : nodeEvents) {
			System.out.println(nodeEvent);
		}
		System.out.println();
	}

	private void allAction() {
		mapCluster();
		
	}
	
	/**
	 * 映射团伙id，可能需要对rightPlatform中的图进行重绘
	 */
	private void mapCluster(){
		if (!DCD_Cache.clusterMapped) {
			ClusterSliceMapper<String, Integer> sliceMapper = new ClusterSliceMapper<String, Integer>(preSlice);
			nowSlice = sliceMapper.transform(nowSlice);
			rightPlatform.setClusterSlice(nowSlice);
			rightPlatform.recolorSlice();
			
			DCD_Cache.clusterMapped = true;
		}
	}
}
