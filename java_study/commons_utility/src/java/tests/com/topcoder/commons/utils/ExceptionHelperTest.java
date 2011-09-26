package com.topcoder.commons.utils;

import org.junit.Test;

import com.topcoder.commons.utils.testutil.UtilException;

import junit.framework.TestCase;

public class ExceptionHelperTest extends TestCase {

    @Test
    public void testConstructException() throws Exception {
        String case01Msg = "case01";
        Throwable e = ExceptionHelper.constructException(UtilException.class, case01Msg);
        assertTrue(e instanceof UtilException);
        assertEquals(e.getMessage(), case01Msg);
    }
}
