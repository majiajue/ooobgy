package edu.zju.cs.ooobgy.visualization.labeltransformer;

import org.apache.commons.collections15.Transformer;

/**
 * 把Vertex toString之后作为Vertex的名称.
 * 如果使用不可预料的Vertex类型，那么请实现toString方法
 * @author frogcherry 周晓龙
 * @created 2011-5-5
 * @Email frogcherry@gmail.com
 */
public class VertexContextLabelTransformer<V> implements Transformer<V,String>{

	@Override
	public String transform(V v) {
		return v.toString();
	}

}
