package edu.zju.cs.ooobgy.app.platform;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.zju.cs.ooobgy.algo.cluster.EdgeBetweennessClusterer;
import edu.zju.cs.ooobgy.algo.cluster.auto.AutoEdgeBetwennessCluster;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.ClusterSlice;
import edu.zju.cs.ooobgy.algo.dynamic_na.pojo.IdCluster;
import edu.zju.cs.ooobgy.app.cache.DCD_Cache;
import edu.zju.cs.ooobgy.dt.db.ClusterGraphDBLoader;
import edu.zju.cs.ooobgy.graph.ClusterGraph;
import edu.zju.cs.ooobgy.graph.Graph;
import edu.zju.cs.ooobgy.visualization.GraphZoomScrollPane;
import edu.zju.cs.ooobgy.visualization.VisualizationViewer;
import edu.zju.cs.ooobgy.visualization.control.DefaultModalGraphMouse;
import edu.zju.cs.ooobgy.visualization.layout.AggregateLayout;
import edu.zju.cs.ooobgy.visualization.layout.CircleLayout;
import edu.zju.cs.ooobgy.visualization.layout.FRLayout;
import edu.zju.cs.ooobgy.visualization.layout.Layout;


/**
 * 
 * @author frogcherry 周晓龙
 * @created 2011-2-24
 * @Email frogcherry@gmail.com
 */
@SuppressWarnings({ "serial", "unchecked" })
public class TimeSliceClusterPlatform extends JApplet {
	VisualizationViewer<String, Integer> vv;
	
//	Factory<Graph<String, Integer>> graphFactory;
	
	Map<String,Paint> vertexPaints = 
		LazyMap.<String,Paint>decorate(new HashMap<String,Paint>(),
				new ConstantTransformer(Color.white));
	Map<Integer,Paint> edgePaints =
	LazyMap.<Integer,Paint>decorate(new HashMap<Integer,Paint>(),
			new ConstantTransformer(Color.blue));

	
	
	private ClusterSlice<String, Integer> clusterSlice;
	
	public static void main(String[] args) throws IOException {
		
		TimeSliceClusterPlatform cd = new TimeSliceClusterPlatform();
		cd.start();
		// Add a restart button so the graph can be redrawn to fit the size of the frame
		JFrame jf = new JFrame();
		jf.getContentPane().add(cd);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
	}
	
	public void start() {
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream("datasets/zachary.net");
		try
        {        
            JPanel slice1 = setUpView("001001");
            Container content = getContentPane();
            content.add(slice1);
        }
        catch (IOException e)
        {
            System.out.println("Error in loading graph");
            e.printStackTrace();
        }
	}

	public JPanel setUpView(String time_range) throws IOException {
		
    	Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };

        ClusterGraphDBLoader dbLoader = new ClusterGraphDBLoader();
		ClusterGraph<String, Integer> graph = dbLoader.load(time_range);
		this.clusterSlice = new ClusterSlice<String, Integer>(time_range, graph);
		//Create a simple layout frame
        //specify the Fruchterman-Rheingold layout algorithm
        final AggregateLayout<String, Integer> layout = 
        	new AggregateLayout<String, Integer>(new FRLayout<String, Integer>(graph));

		vv = new VisualizationViewer<String, Integer>(layout);
		vv.setBackground( Color.white );
		//Tell the renderer to use our own customized color rendering
		vv.getRenderContext().setVertexFillPaintTransformer(MapTransformer.<String,Paint>getInstance(vertexPaints));
		vv.getRenderContext().setVertexDrawPaintTransformer(new Transformer<String,Paint>() {
			public Paint transform(String v) {
				if(vv.getPickedVertexState().isPicked(v)) {
					return Color.cyan;
				} else {
					return Color.BLACK;
				}
			}
		});

		vv.getRenderContext().setEdgeDrawPaintTransformer(MapTransformer.<Integer,Paint>getInstance(edgePaints));

		vv.getRenderContext().setEdgeStrokeTransformer(new Transformer<Integer,Stroke>() {
                protected final Stroke THIN = new BasicStroke(1);
                protected final Stroke THICK= new BasicStroke(2);
                public Stroke transform(Integer e)
                {
                    Paint c = edgePaints.get(e);
                    if (c == Color.LIGHT_GRAY)
                        return THIN;
                    else 
                        return THICK;
                }
            });

		//add restart button
		JButton scramble = new JButton("Restart");
		scramble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Layout layout = vv.getGraphLayout();
				layout.initialize();
				Relaxer relaxer = vv.getModel().getRelaxer();
				if(relaxer != null) {
					relaxer.stop();
					relaxer.prerelax();
					relaxer.relax();
				}
			}

		});
		
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		vv.setGraphMouse(gm);
		
		final JToggleButton groupVertices = new JToggleButton("Group Clusters");

		//Create slider to adjust the number of edges to remove when clustering
		final JSlider edgeBetweennessSlider = new JSlider(JSlider.HORIZONTAL);
        edgeBetweennessSlider.setBackground(Color.WHITE);
		edgeBetweennessSlider.setPreferredSize(new Dimension(210, 50));
		edgeBetweennessSlider.setPaintTicks(true);
		edgeBetweennessSlider.setMaximum(graph.getEdgeCount());
		edgeBetweennessSlider.setMinimum(0);
		edgeBetweennessSlider.setValue(0);
		edgeBetweennessSlider.setMajorTickSpacing(10);
		edgeBetweennessSlider.setPaintLabels(true);
		edgeBetweennessSlider.setPaintTicks(true);

//		edgeBetweennessSlider.setBorder(BorderFactory.createLineBorder(Color.black));
		//TO DO: edgeBetweennessSlider.add(new JLabel("Node Size (PageRank With Priors):"));
		//I also want the slider value to appear
		final JPanel eastControls = new JPanel();
		eastControls.setOpaque(true);
		eastControls.setLayout(new BoxLayout(eastControls, BoxLayout.Y_AXIS));
		eastControls.add(Box.createVerticalGlue());
		eastControls.add(edgeBetweennessSlider);

		final String COMMANDSTRING = "Edges removed for clusters: ";
		final String eastSize = COMMANDSTRING + edgeBetweennessSlider.getValue();
		
		final TitledBorder sliderBorder = BorderFactory.createTitledBorder(eastSize);
		eastControls.setBorder(sliderBorder);
		//eastControls.add(eastSize);
		eastControls.add(Box.createVerticalGlue());
		
		groupVertices.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
					clusterAndRecolor(layout, edgeBetweennessSlider.getValue(), 
							DCD_Cache.similarColors, e.getStateChange() == ItemEvent.SELECTED);
					vv.repaint();
			}});


		clusterAndRecolor(layout, 0, DCD_Cache.similarColors, groupVertices.isSelected());

		edgeBetweennessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int numEdgesToRemove = source.getValue();
					clusterAndRecolor(layout, numEdgesToRemove, DCD_Cache.similarColors,
							groupVertices.isSelected());
					sliderBorder.setTitle(
						COMMANDSTRING + edgeBetweennessSlider.getValue());
					eastControls.repaint();
					vv.validate();
					vv.repaint();
				}
			}
		});

		JPanel slice = new JPanel(new BorderLayout());
		slice.add(new GraphZoomScrollPane(vv),BorderLayout.NORTH);
		JPanel south = new JPanel();
		JPanel grid = new JPanel(new GridLayout(2,1));
		grid.add(scramble);
		grid.add(groupVertices);
		south.add(grid);
		south.add(eastControls);
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
		p.add(gm.getModeComboBox());
		south.add(p);
		slice.add(south,BorderLayout.SOUTH);
		int removeEdgecount = autoClusterAndRecolor(layout, DCD_Cache.similarColors, groupVertices.isSelected());
		edgeBetweennessSlider.setValue(removeEdgecount);
//		if (time_range.equals("201002")) {
//			clusterAndRecolor(layout, 7, similarColors, groupVertices.isSelected());
//			edgeBetweennessSlider.setValue(7);
//		} else if (time_range.equals("201003")) {
//			clusterAndRecolor(layout, 6, similarColors, groupVertices.isSelected());
//			edgeBetweennessSlider.setValue(6);
//		}
		return slice;
	}

	public void clusterAndRecolor(AggregateLayout<String, Integer> layout,
		int numEdgesToRemove,
		Color[] colors, boolean groupClusters) {
		//Now cluster the vertices by removing the top 50 edges with highest betweenness
		//		if (numEdgesToRemove == 0) {
		//			colorCluster( g.getVertices(), colors[0] );
		//		} else {
		
		Graph<String, Integer> g = layout.getGraph();
        layout.removeAll();

		EdgeBetweennessClusterer<String, Integer> clusterer =
			new EdgeBetweennessClusterer<String, Integer>(numEdgesToRemove);
		Set<Set<String>> clusterSet = clusterer.transform(g);
		List<Integer> edges = clusterer.getEdgesRemoved();
		//恢复图
		clusterer.recoverGraph();
		
		clusterSlice.setRemovedEdges(edges);
		clusterSlice.clearClusters();
		
		int i = 0;
		//Set the colors of each node so that each cluster's vertices have the same color
		for (Iterator<Set<String>> cIt = clusterSet.iterator(); cIt.hasNext();) {

			Set<String> vertices = cIt.next();
			Color c = colors[i % colors.length];

			colorCluster(vertices, c);
			if(groupClusters == true) {
				groupCluster(layout, vertices);
			}
			i++;
			
			IdCluster<String> thisCluster = new IdCluster<String>(vertices, c);
			clusterSlice.addCluster(thisCluster.getId(), thisCluster);
		}
		for (Integer e : g.getEdges()) {

			if (edges.contains(e)) {
				edgePaints.put(e, Color.lightGray);
			} else {
				edgePaints.put(e, Color.black);
			}
		}

	}
	
	public int autoClusterAndRecolor(AggregateLayout<String, Integer> layout,
			Color[] colors, boolean groupClusters) {
			//Now cluster the vertices by removing the top 50 edges with highest betweenness
			//		if (numEdgesToRemove == 0) {
			//			colorCluster( g.getVertices(), colors[0] );
			//		} else {
			
			ClusterGraph<String, Integer> g = (ClusterGraph<String, Integer>)layout.getGraph();
	        layout.removeAll();

	        AutoEdgeBetwennessCluster<String, Integer> autoCluster = new AutoEdgeBetwennessCluster<String, Integer>(
					g.getEdgeWeights());
			Set<Set<String>> clusterSet = autoCluster.transform(g);
			List<Integer> removedEdges = autoCluster.getRemovedEdges();
			
			clusterSlice.setRemovedEdges(removedEdges);
			clusterSlice.clearClusters();
			
			int i = 0;
			//Set the colors of each node so that each cluster's vertices have the same color
			for (Iterator<Set<String>> cIt = clusterSet.iterator(); cIt.hasNext();) {

				Set<String> vertices = cIt.next();
				Color c = colors[i % colors.length];

				colorCluster(vertices, c);
				if(groupClusters == true) {
					groupCluster(layout, vertices);
				}
				i++;
				IdCluster<String> thisCluster = new IdCluster<String>(vertices, c);
				clusterSlice.addCluster(thisCluster.getId(), thisCluster);
			}
			for (Integer e : g.getEdges()) {

				if (removedEdges.contains(e)) {
					edgePaints.put(e, Color.lightGray);
				} else {
					edgePaints.put(e, Color.black);
				}
			}
			
			return removedEdges.size();
		}

	private void colorCluster(Set<String> vertices, Color c) {
		for (String v : vertices) {
			vertexPaints.put(v, c);
		}
	}
	
	private void groupCluster(AggregateLayout<String, Integer> layout, Set<String> vertices) {
		if(vertices.size() < layout.getGraph().getVertexCount()) {
			Point2D center = layout.transform(vertices.iterator().next());
			Graph<String, Integer> subGraph = new ClusterGraph<String, Integer>();
			for(String v : vertices) {
				subGraph.addVertex(v);
			}
			Layout<String, Integer> subLayout = 
				new CircleLayout<String, Integer>(subGraph);
			subLayout.setInitializer(vv.getGraphLayout());
			subLayout.setSize(new Dimension(40,40));

			layout.put(subLayout,center);
			vv.repaint();
		}
	}

	public ClusterSlice<String, Integer> getClusterSlice() {
		return clusterSlice;
	}

	public void setClusterSlice(ClusterSlice<String, Integer> clusterSlice) {
		this.clusterSlice = clusterSlice;
	}

	public void repaintSlice() {
		Collection<IdCluster<String>> cls = clusterSlice.getClusters().values();
		for (IdCluster<String> idCluster : cls) {
			colorCluster(idCluster.getVertexes(), idCluster.getColor());
		}
	}
}
