package com.ooobgy.action.ajax.meta;

import javax.servlet.http.HttpServletRequest;

import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetDomainL1Action
 */
public class GetDomainL1Action extends AjaxAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7467428770831548L;

	/**
     * Default constructor. 
     */
    public GetDomainL1Action() {
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT "
				+ "    t1.id as id,"
				+ "    t1.name as name,"
				+ "    t1.leader as leader,"
				+ "    t2.name as leader_name"
				+ " from domainl1 t1"
				+ " left outer join"
				+ "    staff t2"
				+ " on "
				+ "    t1.leader = t2.id;";
		String json = SelectJson.select(query);
		return json;
	}

}
