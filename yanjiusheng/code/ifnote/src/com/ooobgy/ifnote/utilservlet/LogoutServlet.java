package com.ooobgy.ifnote.utilservlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ooobgy.ifnote.constants.SecretKey;

public class LogoutServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2392965820018246694L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> keys = new ArrayList<String>();
		keys.add(SecretKey.USER_KEY);
		for (String key : keys) {
			if (session.getAttribute(key) != null) {
				session.removeAttribute(key);
			}
		}
		
		String inf = request.getParameter("inf");
		
		String forward = "/home/login.jsp";
		if (inf!=null && inf.equals("in")) {
			forward = "/home/home.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
		dispatcher.forward(request, response);
	}
}
