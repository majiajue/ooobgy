package com.ooobgy.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class StringEnvReplacerTest extends TestCase{
	private StringEnvReplacer replacer;
	private Map<String, String> envs;
	@Override
	protected void setUp() throws Exception {
		envs = new HashMap<String, String>();
		envs.put("key1", "value1");
		envs.put("key2", "value2");
		replacer = new StringEnvReplacer(envs);
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		replacer = null;
		envs = null;
		
		super.tearDown();
	}
	
	@Test
	public void testFun(){
		String src = "test is ${key1} and ${key2} and ${key1}";
		System.out.println("src = " + src);
		System.out.println("dis = " + replacer.replace(src));
	}
}
