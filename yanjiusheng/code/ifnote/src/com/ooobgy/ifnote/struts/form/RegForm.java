package com.ooobgy.ifnote.struts.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ooobgy.ifnote.dbctrler.dao.UserDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.UserDaoImpl;
import com.ooobgy.ifnote.entity.User;

/**
 * 
 * @author 周晓龙
 * @created 2011-8-16
 */
public class RegForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2986689438440716241L;

	/** phone property */
	private String phone = "";

	/** username property */
	private String username = "";

	/** email property */
	private String email = "";

	/** psw property */
	private String psw = "";

	/** psw2 property */
	private String psw2 = "";

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

		if (username == null || username.length() < 4) {
			ActionMessage actionMessage = new ActionMessage("reg.username");
			errors.add("username", actionMessage);
		}
		if (phone == null || phone.length() != 11 || !checkphone(phone)) {
			ActionMessage actionMessage = new ActionMessage("reg.phone");
			errors.add("phone", actionMessage);
		}
		if (email == null || email.length() < 1 || !checkEmail(email)) {
			ActionMessage actionMessage = new ActionMessage("reg.email");
			errors.add("email", actionMessage);
		}
		if (psw == null || psw.length() < 5) {
			ActionMessage actionMessage = new ActionMessage("reg.psw");
			errors.add("psw", actionMessage);
			if (psw2 == null || !psw2.equals(psw)) {
				ActionMessage actionMessage2 = new ActionMessage("reg.psw2");
				errors.add("psw2", actionMessage2);
			}
		}
		

		User user = checkUser();
		if (user != null) {
			ActionMessage actionMessage = new ActionMessage("reg.exist");
			errors.add("exist", actionMessage);
		}
		return errors;
	}

	private boolean checkphone(String phone2) {
		String regex = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone2);
		return m.find();
	}

	private boolean checkEmail(String mail) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mail);
		return m.find();
	}

	private User checkUser() {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.findWithUserName(username);

		return user;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		psw = "";
		psw2 = "";
		super.reset(mapping, request);
	}

	/**
	 * Returns the phone.
	 * 
	 * @return String
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set the phone.
	 * 
	 * @param phone
	 *            The phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the username.
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username.
	 * 
	 * @param username
	 *            The username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the email.
	 * 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email.
	 * 
	 * @param email
	 *            The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the psw.
	 * 
	 * @return String
	 */
	public String getPsw() {
		return psw;
	}

	/**
	 * Set the psw.
	 * 
	 * @param psw
	 *            The psw to set
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}

	/**
	 * Returns the psw2.
	 * 
	 * @return String
	 */
	public String getPsw2() {
		return psw2;
	}

	/**
	 * Set the psw2.
	 * 
	 * @param psw2
	 *            The psw2 to set
	 */
	public void setPsw2(String psw2) {
		this.psw2 = psw2;
	}
}