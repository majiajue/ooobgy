package edu.zju.cs.ooobgy.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.zju.cs.ooobgy.app.action.DynamicClusterAction;
import edu.zju.cs.ooobgy.app.platform.TimeSliceClusterPlatform;

@SuppressWarnings("serial")
public class DynamicClusterDemo extends JApplet implements Runnable{
	private TimeSliceClusterPlatform leftPlatform;
	private TimeSliceClusterPlatform rightPlatform;
	
	public void setUpView(){
		leftPlatform = new TimeSliceClusterPlatform();
		rightPlatform = new TimeSliceClusterPlatform();
		try {
			JPanel slice1 = leftPlatform.setUpView("001101");
			JPanel slice2 = rightPlatform.setUpView("001102");
			Container content = getContentPane();
			content.setLayout(new BorderLayout());
            content.add(slice1,BorderLayout.WEST);
            content.add(slice2,BorderLayout.EAST);
            
            JFrame jf = new JFrame();
            jf.setSize(1024, 768);
    		jf.getContentPane().add(this);
            //jf.setJMenuBar(setUpSwingMenuBar());
    		jf.setMenuBar(setUpAwtMenuBar());
    		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		jf.pack();
    		jf.setVisible(true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * AWT菜单，解决视图覆盖问题
	 * @return
	 */
	public MenuBar setUpAwtMenuBar(){
		MenuBar menuBar = new MenuBar();
		DynamicClusterAction actionListener = new DynamicClusterAction(leftPlatform,rightPlatform);
		
		Menu fenxiMenu;
		fenxiMenu = new Menu("Dynamic Analysis");
		menuBar.add(fenxiMenu);
		MenuItem zongtiItem = new MenuItem("DA_all");
		zongtiItem.setActionCommand("DA_all");//DA:dynamic analysis
		zongtiItem.addActionListener(actionListener);
		fenxiMenu.add(zongtiItem);
		MenuItem nodeItem = new MenuItem("DA_node");
		nodeItem.setActionCommand("DA_node");
		nodeItem.addActionListener(actionListener);
		fenxiMenu.add(nodeItem);
		MenuItem clusterItem = new MenuItem("DA_cluster");
		clusterItem.setActionCommand("DA_cluster");
		clusterItem.addActionListener(actionListener);
		fenxiMenu.add(clusterItem);
		
		return menuBar;
	}
	
	/**
	 * Swing菜单
	 * @return
	 */
	public JMenuBar setUpSwingMenuBar() {
		DynamicClusterAction actionListener = new DynamicClusterAction(leftPlatform,rightPlatform);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fenxiMenu = new JMenu("演化分析");
		menuBar.add(fenxiMenu);
		JMenuItem allItem = new JMenuItem("总体分析");
		allItem.addActionListener(actionListener);
		fenxiMenu.add(allItem);
		JMenuItem nodeItem = new JMenuItem("节点行为");
		nodeItem.addActionListener(actionListener);
		fenxiMenu.add(nodeItem);
		JMenuItem clusterItem = new JMenuItem("团伙行为");
		clusterItem.addActionListener(actionListener);
		fenxiMenu.add(clusterItem);
		
		
		return menuBar;
	}

	@Override
	public void run() {
		setUpView();
	}
	
	public static void main(String[] args) {
		DynamicClusterDemo demo = new DynamicClusterDemo();
		demo.run();
	}
	
}
