package com.alimama.loganalyzer.jobs.mrs.algo;

// 工具类，归一化
public class Normalizer {
	static {
		System.out.println("Normalizer is initing...");
		System.loadLibrary("Normalizer");
		System.out.println("Normalizer inited successfully!");
	}

	public static native String normalize(String words, int options);

	public native void initialize(String confPath);

	public native void unitialize();

	public Normalizer() {
	}

	public String normalize(String words) {
		return normalize(words, 380);
	}
	
	public static void main(String[] args) {
		Normalizer norm = new Normalizer();
		norm.initialize("/home/a/share/phoenix/normalize/conf/norm.conf");
		String normalized = norm.normalize("Nokia");
		System.out.println(normalized);
	}
}
