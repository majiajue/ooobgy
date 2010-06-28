package com.alimama.loganalyzer.jobs.mrs.keywords;

import static org.junit.Assert.fail;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtractQuerySearchTest {
	private Mapper<LongWritable, Text, Text, Text> mapper;
	private MapDriver<LongWritable, Text, Text, Text> mapDriver;

	private Reducer<Text, Text, Text, Text> reducer;
	private ReduceDriver<Text, Text, Text, Text> reduceDriver;
	
	private Long mapInputKey = 1l;	
	private String mapInputValue = "2010010700ºÚ°×50012027s18NF/pApWoLU4BAYlkwNKKTTxi000012.01.0";	
	private String mapOutputKey = "2010010700ºÚ°×50012027s1800001";	
	private String mapOutputValue = "2.01.0";
	
	@Before
	public void setUp() throws Exception {
		mapper = new ExtractQuerySearch.TopMapper();
		mapDriver = new MapDriver<LongWritable, Text, Text, Text>(mapper);
		
		reducer = new ExtractQuerySearch.TopReducer();
		reduceDriver = new ReduceDriver<Text, Text, Text, Text>(reducer);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMap() {
		mapDriver
		.withInput(new LongWritable(mapInputKey), new Text(mapInputValue))
		.withOutput(new Text(mapOutputKey), new Text(mapOutputValue))
		.runTest();	
	}


}
