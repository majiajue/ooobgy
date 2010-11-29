package edu.zju.cs.ooobgy.div1;

public class AdpDevisive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		DataAdapter adapter = new DataAdapter();
//		adapter.Csv2DB("data/karate.csv");
		String shareFile = "data/katate.net";
//		adapter.EdgeDB2JungFile(shareFile);
		ClusterPlatform platform = new ClusterPlatform();
		platform.runSharedPlatForm(shareFile);
	}

}
