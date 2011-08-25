/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-24
 */
package com.ooobgy.ifnote.utilservlet;

import java.awt.Font;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 根据Param参数话饼状图并输出
 */
public class PieChartServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9040209313243897398L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		String dataStr = req.getParameter("data");
		dataStr = URLDecoder.decode(dataStr, "utf-8");
		String title = req.getParameter("title");
		title = URLDecoder.decode(title, "utf-8");
		System.out.println(title);
		int width = Integer.parseInt(req.getParameter("width"));
		int height = Integer.parseInt(req.getParameter("height"));
//		System.out.println("#######" + dataStr);
		String[] items = dataStr.split("-");
//		System.out.println("#items#" + items.length);
		for (String item : items) {
			String[] kv = item.split("_");
//			System.out.println("#kv#" + kv.length);
			if (kv.length == 2) {
				dataset.setValue(kv[0], Double.parseDouble(kv[1]));
			}
		}
		
		JFreeChart pieChart = ChartFactory.createPieChart3D(title, dataset, true, true, true);
		pieChart.getTitle().setFont(new Font("微软雅黑", Font.BOLD, 20));
		PiePlot piePlot = (PiePlot) pieChart.getPlot();
		piePlot.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
		pieChart.getLegend().setItemFont(new Font("微软雅黑", Font.BOLD, 12));


		//输出图片
		RenderedImage buffImg = pieChart.createBufferedImage(width, height);
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/png");
		
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "png", sos);
		sos.close();
//		ChartUtilities.saveChartAsPNG(new File("D:/yanjiusheng/code/BarChart3D.png"), pieChart, 300, 300); 
	}

	
}
