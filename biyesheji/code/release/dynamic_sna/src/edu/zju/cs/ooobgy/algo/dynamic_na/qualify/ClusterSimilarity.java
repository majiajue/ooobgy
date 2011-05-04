package edu.zju.cs.ooobgy.algo.dynamic_na.qualify;

import java.util.Set;

import org.apache.commons.collections15.Transformer;

/**
 * Jaccard's coefficient and Adamic/Adar
 * 计算两个团伙C1,C2的相似度 = |C1 交 C@| / |C1 并 C2| </br>
 * 先已知C1,然后传入C2进行计算
 * @author frogcherry 周晓龙
 * @created 2010-12-18
 * @Email frogcherry@gmail.com
 * @see "David Liben-Nowell: The Link Prediction Problem for Social Networks"
 */
public class ClusterSimilarity<V> implements Transformer<Set<V>, Double>{
	private Set<V> c1;
	
	public ClusterSimilarity(Set<V> c1) {
		super();
		this.c1 = c1;
	}

	/**
	 * 计算两个团伙C1,C2的相似度 = |C1 交 C@| / |C1 并 C2| </br>
	 * 先已知C1,然后传入C2进行计算
	 */
	@Override
	public Double transform(Set<V> c2) {
		int intersectionSize = 0;//交集的大小
		int unionSize = 0;//并集的大小
		
		for (V vertex : c1) {
			if (c2.contains(vertex)) {
				intersectionSize ++;
			}
		}
		unionSize = c1.size() + c2.size() - intersectionSize;
		
		return new Double(intersectionSize) / new Double(unionSize);
	}

}
