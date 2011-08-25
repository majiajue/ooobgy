
package com.frogcherry.util.fileuploadforcdeditor.searchdir;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class CKEditorBrowseFileSerlet extends HttpServlet {
	private static String baseDir;// �û��ϴ��ļ��еĸ�Ŀ¼
	private static boolean debug = false;// �Ƿ�debugģʽ
	private boolean allowDelete = false;// �Ƿ�����ɾ��
	private String realBaseDir;// �û��ļ��о��·��

	/*
	 * �ļ��������ʼ��
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// ��web.xml�ж�ȡdebugģʽ
		debug = (new Boolean(getInitParameter("debug"))).booleanValue();
		if (debug)
			System.out
					.println("\r\n---- SimpleUploaderServlet initialization started ----");
		// ��web.xml�л�ȡ��Ŀ¼���
		baseDir = getInitParameter("baseDir");
		// ��web.xml�л�ȡ��ʼɾ��Ȩ��
		allowDelete = (new Boolean(getInitParameter("allowDelete")))
				.booleanValue();
		if (baseDir == null)
			baseDir = "/UserFiles/";
		realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdirs();
		}
		if (debug) {
			System.out.println("realDir = " + realBaseDir);
			System.out
					.println("---- SimpleUploaderServlet initialization completed ----\r\n");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in!");
		PrintWriter out = resp.getWriter();
		String callback = req.getParameter("CKEditorFuncNum");
		String fileUrl = req.getParameter("imgurl");
		System.out.println("chosed:" + fileUrl);
		System.out.println("callback:" + callback);
		out.println("<script type=\"text/javascript\">");
		out.println("CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl
				+ "',''" + ")");
		// out.println("window.close();");
		out.println("</script>");
		out.flush();
		out.close();
	}

}
