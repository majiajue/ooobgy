/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.entity.Inote_Futures;
import com.ooobgy.util.NumberTool;

public class Disp_Futures extends Inote_Futures {
	private Double asset;
	private Double profit;

	public Disp_Futures(Inote_Futures inote) {
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setName(inote.getName());
		super.setPrice(NumberTool.roundDouble2(inote.getPrice()));
		super.setSum(NumberTool.roundDouble2(inote.getSum()));
		super.setComment(inote.getComment());
		super.setNow_price(NumberTool.roundDouble2(inote.getNow_price()));
	}

	public void init() {
		this.asset = NumberTool.roundDouble2(getSum() * getNow_price());
		this.profit = NumberTool.roundDouble2(getSum()
				* (getNow_price() - getPrice()));
	}

	/**
	 * @return the asset
	 */
	public Double getAsset() {
		return asset;
	}

	/**
	 * @param asset
	 *            the asset to set
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
	 * @param profit
	 *            the profit to set
	 */
	public void setProfit(Double profit) {
		this.profit = profit;
	}

}
