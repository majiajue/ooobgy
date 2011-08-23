/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.ooobgy.ifnote.struts.action;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ooobgy.ifnote.constants.SecretKey;
import com.ooobgy.ifnote.dbctrler.dao.Inote_FundDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.Inote_FundDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Fund;
import com.ooobgy.ifnote.entity.User;
import com.ooobgy.ifnote.struts.form.NoteFundForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-17-2011
 * 
 * XDoclet definition:
 * @struts.action path="/noteFund" name="noteFundForm" input="/inote/noteFund.jsp" scope="request" validate="true"
 */
public class NoteFundAction extends Action {
	/*
	 * Generated Methods
	 */

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
		NoteFundForm noteFundForm = (NoteFundForm) form;
		HttpSession session = request.getSession();
		
		Inote_Fund inote = (Inote_Fund) session.getAttribute("inote_fund");
		if (inote == null) {
			inote = new Inote_Fund();
		}
		
		User user = (User) session.getAttribute(SecretKey.USER_KEY);
		
		Calendar cal = Calendar.getInstance();
		Timestamp note_time = new Timestamp(cal.getTimeInMillis());
		inote.setUser_id(user.getId());
		inote.setNote_time(note_time);
		inote.setComment(noteFundForm.getComment());
		inote.setCount(Integer.parseInt(noteFundForm.getCount()));
		inote.setFund_code(Integer.parseInt(noteFundForm.getFund_code()));
		inote.setNpv(Double.parseDouble(noteFundForm.getNpv()));
		
		Inote_FundDao dao = new Inote_FundDaoImpl();
		if (inote.getId() == null) {
			dao.save(inote);
		} else {
			dao.update(inote);
		}
		
		session.removeAttribute("inote_fund");
		return mapping.findForward("fundList");
	}
}