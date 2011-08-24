/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.entity.Inote_Futures;

public class Disp_Futures extends Inote_Futures {
	private Double asset;
	private Double profit;

	public Disp_Futures(Inote_Futures inote) {
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setName(inote.getName());
		super.setPrice(inote.getPrice());
		super.setSum(inote.getSum());
		super.setComment(inote.getComment());
		super.setNow_price(inote.getNow_price());
	}

	public void init(){
		this.asset = getSum() * getNow_price();
		this.profit = getSum() * (getNow_price() - getPrice());
	}
	
	/**
	 * @return the asset
	 */
	public Double getAsset() {
		return asset;
	}

	/**
	 * @param asset the asset to set
	 */
	public void setAsset(Double asset) {
		this.asset = asset;
	}

	/**
	 * @return the profit
	 */
	public Double getProfit() {
		return profit;
	}

	/**
	 * @param profit the profit to set
	 */
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
}
