package com.alimama.loganalyzer.jobs.mrs.util;


public class GlobalInfo {
	static boolean debugOn = false;
	
	public static boolean isDebugOn(){
		return debugOn;
	}
	
	public static void setDebugOn(boolean b){
		debugOn  = b;
	}
	
	public static void debugMessage(String s){
		if (debugOn) System.out.println(s);
	}
}
