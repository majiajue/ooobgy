package edu.zju.cs.ooobgy.algo.scorer;


/**
 * 基本的score接口，度量顶点
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @param <V> the vertex type
 * @param <S> the score type
 */
public interface VertexScorer<V, S>
{
    /**
     * Returns the algorithm's score for this vertex.
     * @return the algorithm's score for this vertex
     */
    public S getVertexScore(V v);
}
