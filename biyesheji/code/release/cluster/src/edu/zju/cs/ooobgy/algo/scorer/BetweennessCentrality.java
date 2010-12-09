package edu.zju.cs.ooobgy.algo.scorer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.zju.cs.ooobgy.algo.util.MapBinaryHeap;
import edu.zju.cs.ooobgy.graph.Graph;

/**
 * 对图中betweenness进行计算，比M. E. J. Newman and M. Girvan定义的最短路径算法稍有优化
 * 
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @see "Ulrik Brandes: A Faster Algorithm for Betweenness Centrality. Journal of Mathematical Sociology 25(2):163-177, 2001."
 */
public class BetweennessCentrality<V, E> 
	implements VertexScorer<V, Double>, EdgeScorer<E, Double> 
{
	protected Graph<V,E> graph;
	protected Map<V, Double> vertex_scores;
	protected Map<E, Double> edge_scores;
	protected Map<V, BetweennessData> vertex_data;
		
	/**
	 * Calculates betweenness scores based on the all-pairs unweighted shortest paths
	 * in the graph.
	 * @param graph the graph for which the scores are to be calculated
	 */
	@SuppressWarnings("unchecked")
	public BetweennessCentrality(Graph<V, E> graph) 
	{
		initialize(graph);
		computeBetweenness(new LinkedList<V>(), new ConstantTransformer(1));
	}

	/**
	 * Calculates betweenness scores based on the all-pairs weighted shortest paths in the
	 * graph.
	 * 
	 * <p>NOTE: This version of the algorithm may not work correctly on all graphs; we're still
	 * working out the bugs.  Use at your own risk.
	 * @param graph the graph for which the scores are to be calculated
	 * @param edge_weights the edge weights to be used in the path length calculations
	 */
	public BetweennessCentrality(Graph<V, E> graph, 
			Transformer<E, ? extends Number> edge_weights) 
	{
		// reject negative-weight edges up front
		for (E e : graph.getEdges())
		{
			double e_weight = edge_weights.transform(e).doubleValue();
        	if (e_weight < 0)
        		throw new IllegalArgumentException(String.format(
        				"Weight for edge '%s' is < 0: %d", e, e_weight)); 
		}
			
		initialize(graph);
		computeBetweenness(new MapBinaryHeap<V>(new BetweennessComparator()), 
			edge_weights);
	}

	protected void initialize(Graph<V,E> graph)
	{
		this.graph = graph;
		this.vertex_scores = new HashMap<V, Double>();
		this.edge_scores = new HashMap<E, Double>();
		this.vertex_data = new HashMap<V, BetweennessData>();
		
		for (V v : graph.getVertices())
			this.vertex_scores.put(v, 0.0);
		
		for (E e : graph.getEdges())
			this.edge_scores.put(e, 0.0);
	}
	
	protected void computeBetweenness(Queue<V> queue, 
			Transformer<E, ? extends Number> edge_weights)
	{
		for (V v : graph.getVertices())
		{
			// initialize the betweenness data for this new vertex
			for (V s : graph.getVertices()) 
				this.vertex_data.put(s, new BetweennessData());
			
            vertex_data.get(v).numSPs = 1;
            vertex_data.get(v).distance = 0;

            Stack<V> stack = new Stack<V>();
            queue.offer(v);

            while (!queue.isEmpty()) 
            {
            	V w = queue.poll();
                stack.push(w);
            	BetweennessData w_data = vertex_data.get(w);
                
                for (E e : graph.getOutEdges(w))
                {
                	V x = graph.getOpposite(w, e);
                	if (x.equals(w))
                		continue;
                	double wx_weight = edge_weights.transform(e).doubleValue(); 
                	
                	BetweennessData x_data = vertex_data.get(x);
                	double x_potential_dist = w_data.distance + wx_weight;
                	
                    if (x_data.distance < 0) 
                    {
                    	x_data.distance = x_potential_dist;
                      	queue.offer(x);
                    }
                    
                    // note:
                    // (1) this can only happen with weighted edges
                    // (2) x's SP count and incoming edges are updated below 
                    if (x_data.distance > x_potential_dist)
                    {
                    	x_data.distance = x_potential_dist;
                    	x_data.incomingEdges.clear(); 
                    	((MapBinaryHeap<V>)queue).update(x);
                    }
                }
                for (E e: graph.getOutEdges(w))
                {
                	V x = graph.getOpposite(w, e);
                	if (x.equals(w))
                		continue;
                	double e_weight = edge_weights.transform(e).doubleValue();
                	BetweennessData x_data = vertex_data.get(x);
                	double x_potential_dist = w_data.distance + e_weight;
                    if (x_data.distance == x_potential_dist) 
                    {
                        x_data.numSPs += w_data.numSPs;
                        x_data.incomingEdges.add(e);
                    }
                }
            }
    		while (!stack.isEmpty()) 
    		{
    		    V x = stack.pop();

    		    for (E e : vertex_data.get(x).incomingEdges)
    		    {
    		    	V w = graph.getOpposite(x, e);
    		        double partialDependency = 
    		        	vertex_data.get(w).numSPs / vertex_data.get(x).numSPs *
    		        	(1.0 + vertex_data.get(x).dependency);
    		        vertex_data.get(w).dependency +=  partialDependency;
    		        double e_score = edge_scores.get(e).doubleValue();
    		        edge_scores.put(e, e_score + partialDependency);
    		    }
    		    if (!x.equals(v)) 
    		    {
    		    	double x_score = vertex_scores.get(x).doubleValue();
    		    	x_score += vertex_data.get(x).dependency;
    		    	vertex_scores.put(x, x_score);
    		    }
    		}
        }


    	for (V v : graph.getVertices()) { 
    		double v_score = vertex_scores.get(v).doubleValue();
    		v_score /= 2.0;
   			vertex_scores.put(v, v_score);
   		}
    
    	for (E e : graph.getEdges()) {
    		double e_score = edge_scores.get(e).doubleValue();
    		e_score /= 2.0;
    		edge_scores.put(e, e_score);
    	}
        

        vertex_data.clear();
	}
	
	public Double getVertexScore(V v) 
	{
		return vertex_scores.get(v);
	}

	public Double getEdgeScore(E e) 
	{
		return edge_scores.get(e);
	}

    private class BetweennessData 
    {
        double distance;
        double numSPs;
        List<E> incomingEdges;
        double dependency;

        BetweennessData() 
        {
            distance = -1;
            numSPs = 0;
            incomingEdges = new ArrayList<E>();
            dependency = 0;
        }
        
        @Override
        public String toString()
        {
        	return "[d:" + distance + ", sp:" + numSPs + 
        		", p:" + incomingEdges + ", d:" + dependency + "]\n";
        }
    }
    
    private class BetweennessComparator implements Comparator<V>
    {
		public int compare(V v1, V v2) 
		{
			return vertex_data.get(v1).distance > vertex_data.get(v2).distance ? 1 : -1;
		}
    }
}
