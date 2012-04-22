package com.ooobgy.action.ajax.meta;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetDomainL3Action
 */
public class GetDomainL3Action extends AjaxAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5548937202924240719L;

	/**
     * Default constructor. 
     */
    public GetDomainL3Action() {
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
					+ " from (select * from domainl3 where pid=" + pid + ") t1"
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
					+ " from (select * from domainl3) t1"
					+ " left outer join"
					+ "    staff t2"
					+ " on "
					+ "    t1.leader = t2.id;";
		}
		String json = SelectJson.select(query);
		return json;
	}

}
