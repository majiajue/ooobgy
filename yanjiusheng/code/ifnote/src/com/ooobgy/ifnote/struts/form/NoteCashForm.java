package com.ooobgy.ifnote.struts.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.ooobgy.ifnote.constants.SecretKey;
import com.ooobgy.ifnote.dbctrler.dao.Inote_CashDao;
import com.ooobgy.ifnote.dbctrler.daoimpl.Inote_CashDaoImpl;
import com.ooobgy.ifnote.entity.Inote_Cash;
import com.ooobgy.ifnote.entity.User;

/**
 * 现金记账
 * 
 * @author 周晓龙
 * @created 2011-8-17
 */
public class NoteCashForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173324267296803186L;

	/** account property */
	private String account;

	/** comment property */
	private String comment;

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
		try {
			Double.parseDouble(account);
		} catch (Throwable e) {
			ActionMessage actionMessage = new ActionMessage("error.account");
			errors.add("account", actionMessage);
			return errors;
		}

		return errors;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		String idStr = (String) request.getAttribute("cid");
		if (idStr != null && idStr.length() > 0) {
			try {
				Integer id = Integer.parseInt(idStr);
				Inote_Cash inote = initInote(id);
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute(SecretKey.USER_KEY);
				if (user != null && inote != null) {
					if (user.getId() == inote.getUser_id()) {
						this.account = inote.getAccount().toString();
						this.comment = inote.getComment();
						session.setAttribute("inote_cash", inote);
						return;
					} else {
						initBlankInote();
					}
				}
				initBlankInote();
				
				return;
			} catch (Throwable e) {
				//Do nothing,转入初始化空白Bean的操作
			}
		}
		
		initBlankInote();
	}

	private void initBlankInote() {
		this.account = "";
		this.comment = "";
	}

	private Inote_Cash initInote(Integer id) {
		Inote_CashDao inoteCashDao = new Inote_CashDaoImpl();
		Inote_Cash inote = inoteCashDao.findWithId(id);
		
		if (inote==null || inote.getId() == null) {
			return null;
		} else {
			return inote;
		}
	}

	/**
	 * Returns the account.
	 * 
	 * @return String
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * Set the account.
	 * 
	 * @param account
	 *            The account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * Returns the comment.
	 * 
	 * @return String
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set the comment.
	 * 
	 * @param comment
	 *            The comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}