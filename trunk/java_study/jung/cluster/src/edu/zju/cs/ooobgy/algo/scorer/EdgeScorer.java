/*
 * Created on Jul 6, 2007
 *
 * Copyright (c) 2007, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package edu.zju.cs.ooobgy.algo.scorer;


/**
 * 基本的score接口，度量边
 * @author frogcherry 周晓龙
 * @created 2010-12-9
 * @Email frogcherry@gmail.com
 * @param <V> the vertex type
 * @param <S> the score type
 */
public interface EdgeScorer<E, S>
{
    /**
     * Returns the algorithm's score for this edge.
     * @return the algorithm's score for this edge
     */
    public S getEdgeScore(E e);
}
