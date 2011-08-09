package com.ooobgy.util;

import java.util.Map;

/**
 * 按照传入的一个map中存储的环境变量值<key,value>，替换诸如sample${key}中的环境变量${key}
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2011-8-9
 */
public class StringEnvReplacer {
	private Map<String, String> envs;

	public StringEnvReplacer(Map<String, String> envs) {
		super();
		this.envs = envs;
	}
	
	public String replace(String source){
		StringBuilder result = new StringBuilder();
		StringBuilder key = new StringBuilder();
		char[] src = source.toCharArray();
		for (int i = 0; i < src.length; i++) {
			if (src[i] == '$' && src[i+1] == '{') {
				key.delete(0, key.length());
				i += 2;
				for (int j = i; src[j] != '}'; j++,i++) {
					key.append(src[j]);
				}
				result.append(envs.get(key.toString()));
			} else {
				result.append(src[i]);
			}
		}
		
		return result.toString();
	}
}
