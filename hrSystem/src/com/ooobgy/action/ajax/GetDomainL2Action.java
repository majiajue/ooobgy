package com.ooobgy.action.ajax;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetDomainL2Action
 */
public class GetDomainL2Action extends AjaxAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3047624091280853657L;

	/**
     * Default constructor. 
     */
    public GetDomainL2Action() {
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String pid = req.getParameter("pid");
		String query;
		if (pid != null) {
			query = "SELECT "
					+ "    t1.id as id,"
					+ "    t1.name as name,"
					+ "    t1.leader as leader,"
					+ "    t2.name as leader_name"
					+ " from (select * from domainl2 where pid=" + pid + ") t1"
					+ " left outer join"
					+ "    staff t2"
					+ " on "
					+ "    t1.leader = t2.id;";
		} else {
			query = "SELECT "
					+ "    t1.id as id,"
					+ "    t1.name as name,"
					+ "    t1.leader as leader,"
					+ "    t2.name as leader_name"
					+ " from (select * from domainl2) t1"
					+ " left outer join"
					+ "    staff t2"
					+ " on "
					+ "    t1.leader = t2.id;";
		}
		String json = SelectJson.select(query);
		return json;
	}

}
