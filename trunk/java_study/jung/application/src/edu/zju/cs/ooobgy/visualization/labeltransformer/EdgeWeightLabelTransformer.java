package edu.zju.cs.ooobgy.visualization.labeltransformer;

import org.apache.commons.collections15.Transformer;

/**
 * 返回边的权重作为标签，需要使用边的权重Transformer<E, ? extends Number>来构造
 * @author frogcherry 周晓龙
 * @created 2011-5-5
 * @Email frogcherry@gmail.com
 */
public class EdgeWeightLabelTransformer<E> implements Transformer<E,String>{
	private Transformer<E, ? extends Number> edgeWeights;
	
	public EdgeWeightLabelTransformer(Transformer<E, ? extends Number> edgeWeights) {
		super();
		this.edgeWeights = edgeWeights;
	}

	@Override
	public String transform(E e) {
		return edgeWeights.transform(e).toString();
	}

}
