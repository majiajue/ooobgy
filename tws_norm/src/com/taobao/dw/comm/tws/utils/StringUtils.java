package com.taobao.dw.comm.tws.utils;

/**
 * 字符串操作函数
 * 
 * @author 明风
 *
 */
public class StringUtils {

	public StringUtils() {
        super();
    }
	
    /**
     * 是否有效字符串
     * @param s
     * @return 有效
     */
	public static boolean isValid(String s){
		return !(s==null || s.trim().equals(""));
	}


}
