package edu.zju.cs.ooobgy.algo.cluster;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Buffer;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.buffer.UnboundedFifoBuffer;
import edu.zju.cs.ooobgy.graph.Graph;


/**
 * 找出图中的弱连通分量WeakComponent
 * @author frogcherry 周晓龙
 * @created 2010-11-30
 */
public class WeakComponentClusterer<V,E> implements Transformer<Graph<V,E>, Set<Set<V>>> 
{
	
	/**
	 * 遍历Graph，将各Weak Component以点集形式返回
	 */
    public Set<Set<V>> transform(Graph<V,E> graph) {

        Set<Set<V>> clusterSet = new HashSet<Set<V>>();

        HashSet<V> unvisitedVertices = new HashSet<V>(graph.getVertices());

        while (!unvisitedVertices.isEmpty()) {
        	Set<V> cluster = new HashSet<V>();
            V root = unvisitedVertices.iterator().next();
            unvisitedVertices.remove(root);
            cluster.add(root);

            Buffer<V> queue = new UnboundedFifoBuffer<V>();
            queue.add(root);

            while (!queue.isEmpty()) {
                V currentVertex = queue.remove();
                Collection<V> neighbors = graph.getNeighbors(currentVertex);

                for(V neighbor : neighbors) {
                    if (unvisitedVertices.contains(neighbor)) {
                        queue.add(neighbor);
                        unvisitedVertices.remove(neighbor);
                        cluster.add(neighbor);
                    }
                }
            }
            clusterSet.add(cluster);
        }
        return clusterSet;
    }
}
