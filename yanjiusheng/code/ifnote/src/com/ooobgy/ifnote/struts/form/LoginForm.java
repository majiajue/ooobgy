package com.ooobgy.ifnote.struts.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ooobgy.ifnote.constants.SecretKey;
import com.ooobgy.ifnote.dbctrler.dao.UserDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.UserDaoImpl;
import com.ooobgy.ifnote.entity.User;

/**
 * 
 * @author 周晓龙
 * @created 2011-8-16
 */
public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4270053672924048770L;

	/** username property */
	private String userId;

	/** password property */
	private String userPwd;
	/** iCode property 验证码 */
	private String iCode;

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		String confirmCode = (String) session
				.getAttribute(SecretKey.CONFIRMATION_CODE_KEY);

		/**
		 * 检测验证码
		 */
		if (!checkICode(confirmCode)) {
			ActionMessage actionMessage = new ActionMessage("login.iCodeError");
			errors.add("iCode", actionMessage);
			return errors;
		}
		/**
		 * 检测用户名是否合法
		 */
		if (userId == null || userId.length() < 1) {
			ActionMessage actionMessage = new ActionMessage("login.noUserId");
			errors.add("userId", actionMessage);
			return errors;
		}

		/**
		 * 检测密码是否合法
		 */
		if (userPwd == null || userPwd.length() < 1) {
			ActionMessage actionMessage = new ActionMessage("login.noUserPwd");
			errors.add("userPwd", actionMessage);
			return errors;
		}

		/**
		 * 到数据库中检测
		 */
		User user = checkUser();
		if (user == null) {
			ActionMessage actionMessage = new ActionMessage("login.noUser");
			errors.add("noUser", actionMessage);
		} else if (!user.getPassword().equals(this.userPwd)) {
			ActionMessage actionMessage = new ActionMessage("login.error.psw");
			errors.add("errPsw", actionMessage);
		} else {
			session.setAttribute(SecretKey.USER_KEY, user);
		}

		return errors;
	}

	private User checkUser() {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findWithUserName(userId);

		return user;
	}

	/**
	 * 检测验证码
	 * 
	 * @param iCode
	 * @return
	 */
	private boolean checkICode(String iCode) {
		if (iCode == null) {
			iCode = "";
		}

		return iCode.equals(this.iCode);
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.iCode = "";
		this.userId = "";
		this.userPwd = "";

		super.reset(mapping, request);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getiCode() {
		return iCode;
	}

	public void setiCode(String iCode) {
		this.iCode = iCode;
	}
}