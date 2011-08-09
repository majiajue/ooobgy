package com.ooobgy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 读ini配置
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class IniConfReader {
	public Map<String, String> readIni(String file) throws IOException {
		Map<String, String> conf = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		String line = null;
		while((line = reader.readLine()) != null){
			String[] items = line.split("=");
			if(items.length == 2)
				conf.put(items[0].trim(), items[1].trim());
		}
		
		return conf;
	}
}
