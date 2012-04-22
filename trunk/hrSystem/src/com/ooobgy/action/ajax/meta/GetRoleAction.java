package com.ooobgy.action.ajax.meta;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet implementation class GetRoleAction
 */
public class GetRoleAction extends AjaxAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6833194857729106109L;

	/**
     * Default constructor. 
     */
    public GetRoleAction() {
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT "
				+ "    t1.id as id,"
				+ "    t1.name as name"
				+ " from role t1";
		
		return SelectJson.select(query);
	}


}
