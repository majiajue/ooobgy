package com.ooobgy.cober;

import org.junit.Test;

import junit.framework.TestCase;

public class CounterTest extends TestCase{
    
    @Test
    public void testDiv() throws Exception {
//      assertTrue(true);//1.do nothing
        
        Counter counter = new Counter();        
        //2.simple
        double x = 3.0, y = 1.2, z = 2.5;
        double xDivY = counter.div(x, y);
        assertEquals(xDivY, z);
        
        //3.all
        assertEquals(3.0/1.2, counter.div(3.0, 1.2));
        try {
            
            counter.div(3.0, 0.0);
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), "除0错误");
        }
    }
}
