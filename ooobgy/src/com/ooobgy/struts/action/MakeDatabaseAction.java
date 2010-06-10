/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package com.ooobgy.struts.action;

import init.database.InitDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.ooobgy.struts.form.MakeDatabaseForm;

/** 
 * MyEclipse Struts
 * Creation date: 06-10-2010
 * 
 * XDoclet definition:
 * @struts.action path="/makeDatabase" name="makeDatabaseForm" input="/superAdmin/makeDatabase.jsp" scope="request" validate="true"
 */
public class MakeDatabaseAction extends Action {

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MakeDatabaseForm makeDatabaseForm = (MakeDatabaseForm) form;
		
		InitDatabase initDatabase = new InitDatabase();
		initDatabase.createSchema();
		
		return null;
	}
}