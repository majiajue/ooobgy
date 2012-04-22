package com.ooobgy.action.ajax.meta;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import com.ooobgy.action.AjaxAction;
import com.ooobgy.db.SelectJson;

/**
 * Servlet implementation class GetLevelAction
 */
public class GetLevelAction extends AjaxAction {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 2042744943149307074L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public GetLevelAction() {
        super();
    }

	@Override
	protected String wirteJson(HttpServletRequest req) {
		String query = "SELECT "
				+ "    t1.id as id,"
				+ "    t1.name as name"
				+ " from level t1";
		
		return SelectJson.select(query);
	}

}
