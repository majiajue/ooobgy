/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.entity.Inote_Deposit;
import com.ooobgy.util.NumberTool;

public class Disp_Deposit extends Inote_Deposit {
	private Double asset;
	private Double profit;
	
	public Disp_Deposit(Inote_Deposit inote){
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setType(inote.getType());
		super.setSum(NumberTool.roundDouble2(inote.getSum()));
		super.setRate(NumberTool.roundDouble2(inote.getRate()));
		super.setDep_time(inote.getDep_time());
		super.setBank_name(inote.getBank_name());
		super.setComment(inote.getComment());
		super.setLink_id(inote.getLink_id());
	}
	
	public void init(){
		this.profit = NumberTool.roundDouble2(getSum() * getRate() / 100);
		this.asset = NumberTool.roundDouble2(getSum() + this.profit);
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
