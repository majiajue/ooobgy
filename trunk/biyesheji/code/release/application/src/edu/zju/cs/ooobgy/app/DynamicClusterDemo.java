package edu.zju.cs.ooobgy.app;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.zju.cs.ooobgy.app.platform.TimeSliceClusterPlatform;

@SuppressWarnings("serial")
public class DynamicClusterDemo extends JApplet implements Runnable{

	@Override
	public void run() {
		TimeSliceClusterPlatform platformLeft = new TimeSliceClusterPlatform();
		TimeSliceClusterPlatform platformRight = new TimeSliceClusterPlatform();
		try {
			JPanel slice1 = platformLeft.setUpView("001101");
			JPanel slice2 = platformRight.setUpView("001102");
			Container content = getContentPane();
			content.setLayout(new BorderLayout());
            content.add(slice1,BorderLayout.WEST);
            content.add(slice2,BorderLayout.EAST);
            
            JFrame jf = new JFrame();
            jf.setSize(1024, 768);
    		jf.getContentPane().add(this);
    		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		jf.pack();
    		jf.setVisible(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DynamicClusterDemo demo = new DynamicClusterDemo();
		demo.run();
	}
	
}
