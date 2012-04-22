package com.ooobgy.action.ajax.data;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetCheckinAction
 */
public class GetCheckinAction extends AjaxAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1959661945419390820L;

	/**
     * Default constructor. 
     */
    public GetCheckinAction() {
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT "
				+ "    t1.id as id,"
				+ "    t1.date as date,"
				+ "    t1.staff as staff,"
				+ "    t2.name as staff_name,"
				+ "    t1.time as time"
				+ " from checkin t1"
				+ " left outer join"
				+ "    staff t2"
				+ " on "
				+ "    t1.staff = t2.id;";
		String json = SelectJson.select(query);
		return json;
	}

}
