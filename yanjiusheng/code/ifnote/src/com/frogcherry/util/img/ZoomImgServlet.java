
package com.frogcherry.util.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class ZoomImgServlet extends HttpServlet {

	// /* (non-Javadoc)
	// * @see
	// javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse)
	// */
	// @Override
	// protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	// throws ServletException, IOException {
	// // TODO Auto-generated method stub
	// service(req, resp);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int targetW;
		int targetH;
		String width = req.getParameter("targetW");
		String hight = req.getParameter("targetH");
		if (width != null) {
			targetW = Integer.parseInt(width);
		} else {
			targetW = 150;
		}
		if (hight != null) {
			targetH = Integer.parseInt(hight);
		} else {
			targetH = 150;
		}
		String url = req.getParameter("url");
		if (null == url || "".equals(url)) {
			throw new ServletException("ͼ��������");
		}
		// System.out.println("�յ���urlΪ:" + url);
		// ͼ����ʵ·��
		String srcImgFileName = getServletContext().getRealPath(url);
		resp.setContentType("image/jpeg");
		ServletOutputStream imgout = resp.getOutputStream();
		// ʹ�����Ź��߶�ͼ����д���
		BufferedImage srcImage = ImageIO.read(new File(srcImgFileName));
		BufferedImage zoomedImage = ZoomImg.zoom(srcImage, targetW, targetH);
		// ����JPEGͼ������������ڱ����ڴ��е�ͼ����ݵ�JPEG����������
		JPEGImageEncoder jpgEncoder = JPEGCodec.createJPEGEncoder(imgout);
		// ����BufferedImage���󵽡�JPEG���������
		jpgEncoder.encode(zoomedImage);
		imgout.close();
	}

}
