/**
 * 
 * @author : CHE.R.RY (周晓龙)
 * @Email  : frogcherry@gmail.com
 * @Date   : 2011-8-24
 */
package com.ooobgy.dataloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 按指定列读取CSV文件
 */
public class CsvReader {
	private List<Integer> indexes;
	private String file;

	/**
	 * @param indexes
	 * @param file
	 */
	public CsvReader(String index, String file) {
		super();
		this.indexes = new ArrayList<Integer>();
		String[] items = index.split(",");
		for (String item : items) {
			this.indexes.add(Integer.parseInt(item.trim()));
		}
		this.file = file;
	}

	public List<List<String>> readData() {
		List<List<String>> data = new LinkedList<List<String>>();

		try {
			BufferedReader reader = openFile(file);
			reader.readLine();// 第一行不要
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",");
				List<String> content = new LinkedList<String>();
				for (Integer index : indexes) {
					content.add(items[index]);
				}
				data.add(content);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * @param file2
	 * @return
	 * @throws IOException
	 */
	private BufferedReader openFile(String file2) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				new File(file)));

		return reader;
	}
}
