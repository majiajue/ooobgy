package com.ooobgy.action.ajax;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;

public class HelloAction extends AjaxAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4369174840460600990L;

	@Override
	protected String wirteJson(HttpServletRequest req) {
		return "{type:'ok', data : '[12,13]'}";
	}

}
