package com.taobao.dw.pizza.path_analysis.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.taobao.dw.pizza.path_analysis.core.algo.PathTraceUtils;
import com.taobao.dw.pizza.path_analysis.core.algo.PizzaJSON;


import junit.framework.TestCase;

public class PizzaJSONTest extends TestCase {
	String dataPath = "./data/path_analysis.data";
	
	public void testParsePathExp() throws JSONException {
		String jsonStr = readJSON();
		Map<String, JSONObject> nodeAttrs = PizzaJSON.parseUserRoute(jsonStr);
		
		String[] rawTraces = PathTraceUtils.extractTraces(nodeAttrs.keySet());
		for (String trace : rawTraces) {
			System.out.println(trace);
		}
		
//		System.out.println(rawTraces);
//		assertEquals("[1,52,40, 1,52,88, 1,52,99, 1,85,40, 1,85,88, 1,85,99]", rawTraces.toString());		
//		System.out.println(readJSON());
	}

	private String readJSON() {
		BufferedReader reader = null;
		String line = null;
		String s = "";
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath), PizzaConst.UTF_8_ENCODING));
			while ((line = reader.readLine()) != null) {
				s = s + line;
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("Data Loader loading failed!", e);
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return s;
	}

}
