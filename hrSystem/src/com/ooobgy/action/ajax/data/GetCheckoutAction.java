package com.ooobgy.action.ajax.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetCheckoutAction
 */
public class GetCheckoutAction extends AjaxAction {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCheckoutAction() {
    	super();
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT "
				+ "    t1.id as id,"
				+ "    t1.date as date,"
				+ "    t1.staff as staff,"
				+ "    t2.name as staff_name,"
				+ "    t1.time as time"
				+ " from checkout t1"
				+ " left outer join"
				+ "    staff t2"
				+ " on "
				+ "    t1.staff = t2.id;";
		String json = SelectJson.select(query);
		return json;
	}

}
