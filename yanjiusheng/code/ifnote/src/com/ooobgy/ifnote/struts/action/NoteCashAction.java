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
import com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.Inote_CashDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Cash;
import com.ooobgy.ifnote.entity.User;
import com.ooobgy.ifnote.struts.form.NoteCashForm;

/**
 * 
 * @author 周晓龙
 * @created 2011-8-17
 */
public class NoteCashAction extends Action {

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
		NoteCashForm noteCashForm = (NoteCashForm) form;
		HttpSession session = request.getSession();
		Inote_Cash inoteCash = (Inote_Cash)session.getAttribute("inote_cash");
		//修改逻辑
		if (inoteCash == null) {
			inoteCash = new Inote_Cash();
		}
		
		User user = (User) session.getAttribute(SecretKey.USER_KEY);
		
		inoteCash.setUser_id(user.getId());
		inoteCash.setAccount(Double.valueOf(noteCashForm.getAccount()));
		inoteCash.setComment(noteCashForm.getComment());
		Calendar cal = Calendar.getInstance();
		inoteCash.setNote_time(new Timestamp(cal.getTimeInMillis()));
		
		Inote_CashDao dao = new Inote_CashDaoImpl();
		dao.save(inoteCash);
		
		return mapping.findForward("success");
	}
}