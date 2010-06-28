package com.alimama.loganalyzer.tester.examples.tests;

import com.alimama.loganalyzer.tester.ParserTester;
import com.alimama.loganalyzer.tester.examples.MyBean;
import com.alimama.loganalyzer.tester.examples.MyMultiRecParser;

import junit.framework.*;

public class TestMyMultiRecParser extends  TestCase{

	public void testCase1() throws Exception{
		String[] keysArr={"field1","field2"};
		Object[][] valuesArr={{"abc","def"},{"aaa","bbb"}};
		new ParserTester<MyBean>().testList(MyMultiRecParser.class,"parse", "abc defZaaa bbb", keysArr, valuesArr);
	}
	
	
}
