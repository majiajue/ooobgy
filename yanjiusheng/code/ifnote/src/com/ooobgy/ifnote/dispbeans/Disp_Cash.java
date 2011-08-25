/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.entity.Inote_Cash;
import com.ooobgy.util.NumberTool;

public class Disp_Cash extends Inote_Cash {
	/**
	 * 
	 */
	public Disp_Cash(Inote_Cash inote) {
		super.setId(inote.getId());
		super.setNote_time(inote.getNote_time());
		super.setUser_id(inote.getUser_id());
		super.setAccount(inote.getAccount());
		super.setComment(inote.getComment());
	}
	
	public void init(){
		randDouble();
	}

	/**
	 * 
	 */
	private void randDouble() {
		setAccount(NumberTool.roundDouble2(getAccount()));
		
	}
}
