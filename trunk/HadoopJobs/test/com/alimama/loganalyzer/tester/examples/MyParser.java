package com.alimama.loganalyzer.tester.examples;

public class MyParser {
		public static MyBean parse(String line) {
			String[] tokens=line.split(" ");
			return new MyBean(tokens[0],tokens[1]);
		}
}
