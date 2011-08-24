/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-24
 */
package com.ooobgy.dataloader;

public class LoadData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		FundLoader fundLoader = new FundLoader();
//		fundLoader.loadData("data/fund0823.csv");
		StockLoader stockLoader = new StockLoader();
		stockLoader.loadData("data/stock0823.csv", "20110823");
	}

}
