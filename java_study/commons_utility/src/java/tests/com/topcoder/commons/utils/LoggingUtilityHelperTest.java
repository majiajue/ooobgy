package com.topcoder.commons.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class LoggingUtilityHelperTest extends TestCase {

    @Test
    public void testgetMethodEntranceMessage() throws Exception {
        String sign = "case01";
        String msg = LoggingUtilityHelper.getMethodEntranceMessage(sign);
        assertEquals(msg, "Entering method [case01].");
    }
}
