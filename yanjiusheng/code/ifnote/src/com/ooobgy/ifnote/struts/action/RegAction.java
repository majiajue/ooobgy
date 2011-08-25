package com.ooobgy.ifnote.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ooobgy.ifnote.constants.SecretKey;
import com.ooobgy.ifnote.dbctrler.dao.UserDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.UserDaoImpl;
import com.ooobgy.ifnote.entity.User;
import com.ooobgy.ifnote.struts.form.RegForm;

/**
 * 注册action
 * @author 周晓龙
 * @created 2011-8-16
 */
public class RegAction extends Action {


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
		RegForm regForm = (RegForm) form;
		
		UserDao userDao = new UserDaoImpl();
		User user = new User();
		user.setUserName(regForm.getUsername());
		user.setEmail(regForm.getEmail());
		user.setPhoneNum(regForm.getPhone());
		user.setPassword(regForm.getPsw());
		userDao.save(user);
		
		HttpSession session = request.getSession();
		session.setAttribute(SecretKey.USER_KEY, user);
		
		return mapping.findForward("userinfo");
	}
}