/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.ooobgy.ifnote.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-23-2011
 * 
 * XDoclet definition:
 * @struts.form name="futuresListForm"
 */
public class FuturesListForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/** startTime property */
	private String startTime;

	/** endTime property */
	private String endTime;

	/*
	 * Generated Methods
	 */

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the startTime.
	 * @return String
	 */
	public String getStartTime() {
		return startTime;
	}

	/** 
	 * Set the startTime.
	 * @param startTime The startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/** 
	 * Returns the endTime.
	 * @return String
	 */
	public String getEndTime() {
		return endTime;
	}

	/** 
	 * Set the endTime.
	 * @param endTime The endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}