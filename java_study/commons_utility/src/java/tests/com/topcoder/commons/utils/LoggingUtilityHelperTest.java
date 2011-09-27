package com.topcoder.commons.utils;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * 
 * @author frogcherry 周晓龙 frogcherry@gmail.com
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoggingUtilityHelperTest extends TestCase {

    @Test
    public void testgetMethodEntranceMessage() throws Exception {
        String sign = "case01";
        String msg = LoggingUtilityHelper.getMethodEntranceMessage(sign);
        assertEquals(msg, "Entering method [case01].");
    }
}
