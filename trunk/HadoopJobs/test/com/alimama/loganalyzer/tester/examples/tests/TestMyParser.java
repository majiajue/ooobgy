package com.alimama.loganalyzer.tester.examples.tests;

import com.alimama.loganalyzer.tester.ParserTester;
import com.alimama.loganalyzer.tester.examples.MyBean;
import com.alimama.loganalyzer.tester.examples.MyParser;

import junit.framework.*;

public class TestMyParser extends  TestCase{

	public void testCase1() throws Exception{
		String[] keys={"field1","field2"};
		Object[] values={"abc","def"};
		new ParserTester<MyBean>().test(MyParser.class,"parse", "abc def", keys, values);
	}
	
	public void testCase2() throws Exception{
		String[] keys={"field1","field2"};
		Object[] values={"hello123","a"};
		new ParserTester<MyBean>().test(MyParser.class,"parse", "hello123 a", keys, values);
	}	
}
