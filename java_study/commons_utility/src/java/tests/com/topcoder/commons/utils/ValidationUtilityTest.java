package com.topcoder.commons.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.commons.utils.testutil.UtilException;

import junit.framework.TestCase;

public class ValidationUtilityTest extends TestCase {

    public void testCheckNotNull() throws Exception {
        String name = "case";
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNotNull("note", name + "note",
                    exceptionClass);
            ValidationUtility.checkNotNull(null, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
            assertEquals(e.getMessage(), name + " should not be null");
        }
    }

    public void testcheckNotEmpty() throws Exception {
        String name = "case";
        String v = null;
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNotEmpty(v, name + "note",
                    exceptionClass);
            ValidationUtility.checkNotEmpty("note", name + "note",
                    exceptionClass);
            ValidationUtility.checkNotEmpty("", name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
            assertEquals(e.getMessage(), name + " should not be empty");
        }
    }

    public void testcheckNotEmptyAfterTrimming() throws Exception {
        String name = "case";
        String v = null;
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNotEmptyAfterTrimming(v, name + "note",
                    exceptionClass);
            ValidationUtility.checkNotEmptyAfterTrimming("note", name + "note",
                    exceptionClass);
            ValidationUtility.checkNotEmptyAfterTrimming("  ", name,
                    exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
            assertEquals(e.getMessage(), name
                    + " should not be empty (trimmed)");
        }
    }

    public void testcheckNotNullNorEmpty() throws Exception {
        String name = "case";
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNotNullNorEmpty("note", name + "note",
                    exceptionClass);
            ValidationUtility.checkNotNullNorEmpty("", name,
                    exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }

    public void testcheckNotNullNorEmptyAfterTrimming() throws Exception {
        String name = "case";
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNotNullNorEmptyAfterTrimming("note", name
                    + "note", exceptionClass);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming("  ", name,
                    exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }

    public void testcheckInstance() throws Exception {
        String name = "case";
        Class<?> expectedType = String.class;
        try {
            expectedType = String.class;
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkInstance("note", expectedType,
                    name + "note", exceptionClass);
            expectedType = Integer.class;
            ValidationUtility.checkInstance("  ", expectedType, name,
                    exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
            assertEquals(e.getMessage(), name + " should be an instance of "
                    + expectedType.getName());
        }
    }
    
    public void testcheckNullOrInstance() throws Exception {
        String name = "case";
        Class<?> expectedType = String.class;
        String v = null;
        try {
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkNullOrInstance(v, expectedType,
                    name + "note", exceptionClass);
            expectedType = String.class;
            ValidationUtility.checkNullOrInstance("note", expectedType,
                    name + "note", exceptionClass);
            expectedType = Integer.class;
            ValidationUtility.checkNullOrInstance("  ", expectedType, name,
                    exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
            assertEquals(e.getMessage(), name + " should be null or an instance of "
                    + expectedType.getName());
        }
    }
    
    public void testcheckExists() throws Exception {
        String name = "case";
        File file = null;
        
        try {
            file = null;
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkExists(file, name, exceptionClass);
            file = new File("./test_files/empty");
            ValidationUtility.checkExists(file, name, exceptionClass);
            file = new File("null");
            ValidationUtility.checkExists(file, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckIsFile() throws Exception {
        String name = "case";
        File file = null;
        
        try {
            file = null;
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkIsFile(file, name, exceptionClass);
            file = new File("./test_files/empty");
            ValidationUtility.checkIsFile(file, name, exceptionClass);
            file = new File("./test_files");
            ValidationUtility.checkIsFile(file, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckIsDirectory() throws Exception {
        String name = "case";
        File file = null;
        
        try {
            file = null;
            Class<UtilException> exceptionClass = UtilException.class;
            ValidationUtility.checkIsDirectory(file, name, exceptionClass);
            file = new File("./test_files");
            ValidationUtility.checkIsDirectory(file, name, exceptionClass);
            file = new File("./test_files/empty");
            ValidationUtility.checkIsDirectory(file, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckNotEmptyCol() throws Exception {
        String name = "case";
        Collection<String> col = null;
        
        Class<UtilException> exceptionClass = UtilException.class;
        try {
            col = null;
            ValidationUtility.checkNotEmpty(col, name, exceptionClass);
            col = new ArrayList<String>();
            col.add(name);
            ValidationUtility.checkNotEmpty(col, name, exceptionClass);
            col.clear();
            ValidationUtility.checkNotEmpty(col, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckNotNullNorEmptyCol() throws Exception {
        String name = "case";
        Collection<String> col = null;
        
        Class<UtilException> exceptionClass = UtilException.class;
        try {
            col = new ArrayList<String>();
            col.add(name);
            ValidationUtility.checkNotNullNorEmpty(col, name, exceptionClass);
            col.clear();
            ValidationUtility.checkNotNullNorEmpty(col, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckNotEmptyMap() throws Exception {
        String name = "case";
        Map<String,String> map = null;
        
        Class<UtilException> exceptionClass = UtilException.class;
        try {
            map = null;
            ValidationUtility.checkNotEmpty(map, name, exceptionClass);
            map = new HashMap<String,String>();
            map.put("k1","v1");
            ValidationUtility.checkNotEmpty(map, name, exceptionClass);
            map.clear();
            ValidationUtility.checkNotEmpty(map, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
    
    public void testcheckNotNullNorEmptyMap() throws Exception {
        String name = "case";
        Map<String,String> map = null;
        
        Class<UtilException> exceptionClass = UtilException.class;
        try {
            map = new HashMap<String,String>();
            map.put("k1","v1");
            ValidationUtility.checkNotNullNorEmpty(map, name, exceptionClass);
            map.clear();
            ValidationUtility.checkNotNullNorEmpty(map, name, exceptionClass);
            fail();
        } catch (UtilException e) {
            assertTrue(e instanceof UtilException);
        }
    }
}
