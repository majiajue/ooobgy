package com.alimama.loganalyzer.tester.examples;
import java.util.*;
public class MyMultiRecParser {
			public static List<MyBean> parse(String line) {
				String[] recs=line.split("Z");
				List<MyBean> beans=new LinkedList<MyBean>();
				for (String rec:recs) {
					String[] tokens=rec.split(" ");
					beans.add( new MyBean(tokens[0],tokens[1]));
				}
				return beans;
			}
}
