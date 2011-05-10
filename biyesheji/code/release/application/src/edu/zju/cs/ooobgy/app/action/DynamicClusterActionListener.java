package edu.zju.cs.ooobgy.app.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import edu.zju.cs.ooobgy.algo.dynamic_na.ClusterSliceMapper;
import edu.zju.cs.ooobgy.algo.dynamic_na.analayzer.ClusterEventAnalyzer;
import edu.zju.cs.ooobgy.algo.dynamic_na.analayzer.VertexEventAnalyzer;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.ClusterEvent;
import edu.zju.cs.ooobgy.algo.dynamic_na.event.VertexEvent;
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
		}else if (cmd.equals("DA_vertex")) {
			vertexAction();
		}else if (cmd.equals("DA_cluster")) {
			clusterAction();
		}
	}

	private void clusterAction() {
		mapCluster();
		ClusterEventAnalyzer<String, Integer> cAnalyzer = new ClusterEventAnalyzer<String, Integer>(preSlice);
		List<ClusterEvent> clusterEvents = cAnalyzer.analyze(nowSlice);
		System.out.println("================= Cluster Events =================");
		for (ClusterEvent clusterEvent : clusterEvents) {
			System.out.println(clusterEvent);
		}
		System.out.println();
	}

	private void vertexAction() {
		mapCluster();
		VertexEventAnalyzer<String, Integer> vAnalyzer = new VertexEventAnalyzer<String, Integer>(preSlice);
		List<VertexEvent<String>> vertexEvents = vAnalyzer.analyze(nowSlice);
		System.out.println("================= Vertex Events =================");
		for (VertexEvent<String> vertexEvent : vertexEvents) {
			System.out.println(vertexEvent);
		}
		System.out.println();
	}

	private void allAction() {
		vertexAction();
		clusterAction();
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
			rightPlatform.getVv().repaint();
			
			DCD_Cache.clusterMapped = true;
		}
	}
}
