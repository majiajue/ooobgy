/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package edu.zju.cs.ooobgy.visualization.control;

import java.awt.geom.Point2D;

import edu.zju.cs.ooobgy.visualization.VisualizationServer;


public interface ScalingControl {

    /**
     * zoom the display in or out
     * @param vv the VisualizationViewer
     * @param amount how much to adjust scale by
     * @param at where to adjust scale from
     */
    void scale(VisualizationServer<?,?> vv, float amount, Point2D at);

}