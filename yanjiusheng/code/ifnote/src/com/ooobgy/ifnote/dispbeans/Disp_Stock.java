/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.dbctrler.dao.St_StockDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.St_StockDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Stock;
import com.ooobgy.ifnote.entity.St_Stock;

public class Disp_Stock extends Inote_Stock {
	private static final String DEF_NAME = "未定义";
	
	private String name;
	private Double now_smv;
	private Double asset;
	private Double profit;

	/**
	 * 
	 */
	public Disp_Stock(Inote_Stock inote) {
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setStock_code(inote.getStock_code());
		super.setCount(inote.getCount());
		super.setSmv(inote.getSmv());
		super.setComment(inote.getComment());
	}

	public void init(){
		St_StockDao dao = new St_StockDaoImpl();
		St_Stock stStock = dao.findWithCode(getStock_code());
		if (stStock == null) {//未定义内容
			this.name = DEF_NAME;
			this.now_smv = this.getSmv();
		} else {
			this.name = stStock.getName();
			this.now_smv = stStock.getSmv();
		}
		
		countAsset();
	}
	
	/**
	 * 
	 */
	private void countAsset() {
		this.profit = getCount() * (getNow_smv() - getSmv());
		this.asset = getCount() * getNow_smv();
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
	 * @return the now_smv
	 */
	public Double getNow_smv() {
		return now_smv;
	}

	/**
	 * @param nowSmv the now_smv to set
	 */
	public void setNow_smv(Double nowSmv) {
		now_smv = nowSmv;
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
