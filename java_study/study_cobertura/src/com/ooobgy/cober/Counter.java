package com.ooobgy.cober;

/**
 * 
 * @author frogcherry
 *
 */
public class Counter {
    
    /**
     * return x/y
     * @param x 被除数
     * @param y 除数
     * @return 商
     */
    public double div(double x, double y){
        if (y == 0) {
            throw new IllegalArgumentException("除0错误");
        }
        
        return x/y;
    }
}
