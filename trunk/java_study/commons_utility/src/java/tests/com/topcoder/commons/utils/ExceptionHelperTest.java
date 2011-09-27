package com.topcoder.commons.utils;

import org.junit.Test;

import com.topcoder.commons.utils.testutil.UtilException;

import junit.framework.TestCase;

public class ExceptionHelperTest extends TestCase {

    @Test
    public void testConstructException1() throws Exception {
        String case01Msg = "case01";
        Throwable e = ExceptionHelper.constructException(UtilException.class, case01Msg);
        assertTrue(e instanceof UtilException);
        assertEquals(e.getMessage(), case01Msg);
    }
    
    @Test
    public void testConstructException2() throws Exception {
        String case01Msg = "case02";
        UtilException cause = new UtilException(new Throwable());
        Throwable e = ExceptionHelper.constructException(UtilException.class, case01Msg, cause);
        assertTrue(e instanceof UtilException);
        assertEquals(e.getMessage(), case01Msg);
    }
}
