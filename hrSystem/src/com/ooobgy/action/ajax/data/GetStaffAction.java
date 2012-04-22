package com.ooobgy.action.ajax.data;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetStaffAction
 */
public class GetStaffAction extends AjaxAction {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetStaffAction() {
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT * from staff_detail;";
		String json = SelectJson.select(query);
		return json;
	}

}
