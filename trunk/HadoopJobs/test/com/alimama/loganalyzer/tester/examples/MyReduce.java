package com.alimama.loganalyzer.tester.examples;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MyReduce extends MapReduceBase implements
Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterator<Text> values,
	OutputCollector<Text, Text> output, Reporter reporter)
	throws IOException {
            long[] counters=null;
	int sum=0;
    while (values.hasNext()) {
		sum+=Integer.valueOf(values.next().toString());
	}
		output.collect(key, new Text(String.valueOf(sum)));
}
}