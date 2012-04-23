package com.ooobgy.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class AjaxAction extends HttpServlet {
	/**
	 * must override by subject, return response Jsone String
	 * @param req
	 */
	protected abstract String wirteJson(HttpServletRequest req);

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/x-json");
		resp.setCharacterEncoding("UTF-8");
		String jsonStr = this.wirteJson(req);
		PrintWriter out = resp.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
	}

}
