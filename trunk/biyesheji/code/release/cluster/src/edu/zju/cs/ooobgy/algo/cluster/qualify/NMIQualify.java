package edu.zju.cs.ooobgy.algo.cluster.qualify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;

/**
 * NMI度量用于度量聚类效果的质量，要求已知团伙的真实情况。然后与聚类效果进行比较
 * 公式：NMI = I(X,Y)/sqrt(H(X,Y)*H(X,Y))
 * 0 <= NMI <= 1, 越大效果越好，完全一致NMI就是1
 * @author frogcherry 周晓龙
 * @created 2011-3-28
 * @Email frogcherry@gmail.com
 * @see "Alexander Strehl & Joydeep Ghosh: 
 * Cluster Ensembles -- A Knowledge Reuse Framework for Combining Multiple Partitions"
 */
public class NMIQualify<V> {
	private Matrix nhl = null;
	private List<Double> nh = null;
	private List<Double> nl = null;
	private int n = 0;
	
	/**
	 * 计算NMI的值
	 * @param realClusters 已知的网络结构
	 * @param qClusters 待比较的聚类结构
	 * @return
	 */
	public double qualifyNMI(Collection<Collection<V>> realClusters, Collection<Collection<V>> qClusters){		
		init(realClusters, qClusters);
		
		double nmi, ixy, hx, hy;
		try {
			ixy = qualifyI(realClusters, qClusters);
			hx = qualifyH(realClusters, nh);
			hy = qualifyH(qClusters, nl);
			
			nmi = ixy / Math.sqrt(hx * hy);
		} catch (Throwable e) {
			nmi = 0;
			e.printStackTrace();
		}
		
		return nmi;
	}
	
	private void init(Collection<Collection<V>> realClusters, Collection<Collection<V>> qClusters) {
		int ka = realClusters.size();
		int kb = qClusters.size();
		this.nh = new ArrayList<Double>(ka);
		this.nl = new ArrayList<Double>(kb);
		this.nhl = new Matrix(ka, kb);
		this.n = ka + kb;
		
		int i = 0;
		int j = 0;
		for (Collection<V> ca : realClusters) {
			j = 0;
			nh.add(i, new Double(ca.size()));
			for (Collection<V> cb : qClusters) {
				if (i == 0) {
					nl.add(j, new Double(cb.size()));
				}
				
				Collection<V> intersection = new HashSet<V>(ca);
				intersection.retainAll(cb);
				nhl.setElement(i, j, new Double(intersection.size()));
				j++;
			}
			i++;
		}
		
	}

	/**
	 * 计算I的值，mutual information互信值
	 * @param realClusters
	 * @param qClusters
	 * @return
	 */
	private double qualifyI(Collection<Collection<V>> realClusters, Collection<Collection<V>> qClusters){
		double ixy = 0;
		for (int i = 0; i < nh.size(); i++) {
			for (int j = 0; j < nl.size(); j++) {
				double nij = nhl.element(i, j);
				double interValue = n* nij / (nh.get(i) * nl.get(j));
				if (interValue > 0){
					ixy = ixy + nij * Math.log(n* nij / (nh.get(i) * nl.get(j)));
				}
			}
		}
		
//		System.out.println(">>>>>>>>>>ixy = " + ixy);
		
		return ixy;
	}
	
	/**
	 * 计算熵的值。H，entropy
	 * @param clusters 考察的聚类结构
	 * @param nc 记录聚类结构中各个类的大小的list
	 * @return
	 */
	private double qualifyH(Collection<Collection<V>> clusters, List<Double> nc){
		double hc = 0;
		for (int i = 0; i < nc.size(); i++) {
			hc = hc + nc.get(i) * Math.log(nc.get(i) / n);
		}
		
//		System.out.println(">>>>>>>>>>h(" + clusters + ") = " + hc);
//		System.out.println(nc + "|||" + n + "|||");
		return hc;
	} 
}
