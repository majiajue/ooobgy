/**
 * 
 */
package com.taobao.dw.pizza.path_analysis.mr;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;

/**
 * InvertedListBuilder≤‚ ‘”√¿˝
 * @author √˜∑Á
 *
 */
public class InvertedListBuilderTest extends TestCase {
	private Mapper<LongWritable, Text, Text, Text> mapper;
	private MapDriver<LongWritable, Text, Text, Text> mapDriver;

	private Reducer<Text, Text, Text, Text> reducer;
	private ReduceDriver<Text, Text, Text, Text> reduceDriver;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		mapper = new InvertedListBuilder.IlbMapper();
		mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		
		reducer = new InvertedListBuilder.IlbReducer();
		reduceDriver = new ReduceDriver<Text, Text, Text, Text>(reducer);	
	}
	
	public void testMap() {
		mapDriver
		.withInput(new LongWritable(1l), new Text("p1n1,n2,n7,n4,n8"))
		.withOutput(new Text("n1-n2"), new Text("p1,n7"))
		.withOutput(new Text("n2-n7"), new Text("p1,n4"))
		.withOutput(new Text("n7-n4"), new Text("p1,n8"))
		.withOutput(new Text("n4-n8"), new Text("p1,-1"))
		.runTest();	
	}
	
	public void testReduce(){
		List<Text> values = new ArrayList<Text>();
		values.add(new Text("p1,n7"));
		values.add(new Text("p2,n17"));
		values.add(new Text("p3,n27"));
		values.add(new Text("p4,n37"));
		reduceDriver.
		withInput(new Text("n1-n2"), values)
		.withOutput(new Text("n1-n2"), new Text("p1,p2,p3,p4n7,n17,n27,n37")).runTest();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
