/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.dbctrler.dao.St_FundDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.St_FundDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Fund;
import com.ooobgy.ifnote.entity.St_Fund;
import com.ooobgy.util.NumberTool;

public class Disp_Fund extends Inote_Fund {
	private static final String DEF_NAME = "未定义";
	
	private String name;
	private Double now_npv;
	private Double asset;
	private Double profit;
	
	public Disp_Fund(Inote_Fund inote){
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setFund_code(inote.getFund_code());
		super.setCount(inote.getCount());
		super.setNpv(NumberTool.roundDouble2(inote.getNpv()));
		super.setComment(inote.getComment());
	}

	public void init(){
		St_FundDao dao = new St_FundDaoImpl();
		St_Fund stFund = dao.findWithCode(getFund_code());
		if (stFund == null) {//未定义内容
			this.name = DEF_NAME;
			this.now_npv = NumberTool.roundDouble2(this.getNpv());
		} else {
			this.name = stFund.getName();
			this.now_npv = NumberTool.roundDouble2(stFund.getNpv());
		}
		
		countAsset();
	}
	
	/**
	 * 
	 */
	private void countAsset() {
		this.profit = NumberTool.roundDouble2(getCount() * (getNow_npv() - getNpv()));
		this.asset = NumberTool.roundDouble2(getCount() * getNow_npv());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the now_npv
	 */
	public Double getNow_npv() {
		return now_npv;
	}

	/**
	 * @param nowNpv the now_npv to set
	 */
	public void setNow_npv(Double nowNpv) {
		now_npv = nowNpv;
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
