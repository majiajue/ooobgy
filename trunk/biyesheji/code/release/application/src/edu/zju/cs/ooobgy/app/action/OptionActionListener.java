package edu.zju.cs.ooobgy.app.action;

import java.awt.CheckboxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import edu.zju.cs.ooobgy.app.platform.TimeSliceClusterPlatform;

public class OptionActionListener implements ItemListener {
	private TimeSliceClusterPlatform leftPlatform;
	private TimeSliceClusterPlatform rightPlatform;
	
	
	public OptionActionListener(TimeSliceClusterPlatform leftPlatform,
			TimeSliceClusterPlatform rightPlatform) {
		super();
		this.leftPlatform = leftPlatform;
		this.rightPlatform = rightPlatform;
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		String optionCmd = e.getItem().toString();
		if (optionCmd.equals("Show Vertex Id")) {
			showVertexIdAction(((CheckboxMenuItem)e.getSource()).getState());
		} else if (optionCmd.equals("Show Edge Weight")) {
			showEdgeWeightAction(((CheckboxMenuItem)e.getSource()).getState());
		}
	}


	private void showEdgeWeightAction(boolean state) {
		leftPlatform.changeEdgeWeightVisible(state);
		rightPlatform.changeEdgeWeightVisible(state);
	}


	private void showVertexIdAction(boolean state) {
		leftPlatform.changeVertexIdVisible(state);
		rightPlatform.changeVertexIdVisible(state);
	}
}
