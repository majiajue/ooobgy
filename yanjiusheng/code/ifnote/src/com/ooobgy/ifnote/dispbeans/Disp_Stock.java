/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-23
 */
package com.ooobgy.ifnote.dispbeans;

import com.ooobgy.ifnote.entity.Inote_Stock;

public class Disp_Stock extends Inote_Stock {

	/**
	 * 
	 */
	public Disp_Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	public Disp_Stock(Inote_Stock inote_Stock) {
		super();
		super.setId(inote_Stock.getId());
	}
}
