
package com.frogcherry.util.fileuploadforcdeditor.searchdir;

import java.io.File;
import java.util.ArrayList;
/**
 * 
 * @Author 周晓龙 frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-25
 */
public class TravelDir {
	private ArrayList<String> filelist = new ArrayList<String>();


	public ArrayList<String> getTravelResult(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return filelist;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getTravelResult(files[i].getAbsolutePath());
			} else {
				// String strFileName =
				// files[i].getAbsolutePath().toLowerCase();
				// System.out.println(strFileName + "#" + cnt);
				// cnt++;
				filelist.add(files[i].getAbsolutePath());
			}
		}
		return filelist;
	}

	/**
	 * ��ȡ�ļ���ķ���
	 */
	public static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * ��ȡ��չ��ķ���
	 */
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public String getUrl(String fileName, String baseUrl) {
		return fileName.substring(fileName.lastIndexOf(baseUrl)).replace('\\',
				'/');
	}

	/**
	 * ����õ�URL��ַ
	 * 
	 * @param strPath
	 * @param baseUrl
	 * @return
	 */
	public ArrayList<String> getURLResult(String strPath, String baseUrl) {
		ArrayList<String> ifilelist = getTravelResult(strPath);
		ArrayList<String> urllist = new ArrayList<String>();
		for (int cnt = ifilelist.size() - 1; cnt > -1; cnt--) {
			String file = ifilelist.get(cnt);
			urllist.add(getUrl(file, baseUrl));
			// System.out.println("@#" + file + "#" +cnt);
		}

		return urllist;
	}
}
