/**
 * 
 */
package com.iflytek.ise.result.entity;

import java.util.HashMap;

/**
 * <p>Title: Phone</p>
 * <p>Description: 音素，对应于xml结果中的Phone标签</p>
 * <p>Company: www.iflytek.com</p>
 * @author iflytek
 */
public class Phone {
	/**
	 * 讯飞音标-标准音标映射表（en）
	 */
	public static HashMap<String, String> phone_map = new HashMap<String, String>();
	
	static {
		phone_map.put("aa", "ɑ:");
		phone_map.put("oo", "?");
		phone_map.put("ae", "?");
		phone_map.put("ah", "?");
		phone_map.put("ao", "?:");
		phone_map.put("aw", "a?");
		phone_map.put("ax", "?");
		phone_map.put("ay", "a?");
		phone_map.put("eh", "e");
		phone_map.put("er", "?:");
		phone_map.put("ey", "e?");
		phone_map.put("ih", "?");
		phone_map.put("iy", "i:");
		phone_map.put("ow", "??");
		phone_map.put("oy", "??");
		phone_map.put("uh", "?");
		phone_map.put("uw", "?:");
		phone_map.put("ch", "t?");
		phone_map.put("dh", "?");
		phone_map.put("hh", "h");
		phone_map.put("jh", "d?");
		phone_map.put("ng", "?");
		phone_map.put("sh", "?");
		phone_map.put("th", "θ");
		phone_map.put("zh", "?");
		phone_map.put("y", "j");
		phone_map.put("d", "d");
		phone_map.put("k", "k");
		phone_map.put("l", "l");
		phone_map.put("m", "m");
		phone_map.put("n", "n");
		phone_map.put("b", "b");
		phone_map.put("f", "f");
		phone_map.put("g", "g");
		phone_map.put("p", "p");
		phone_map.put("r", "r");
		phone_map.put("s", "s");
		phone_map.put("t", "t");
		phone_map.put("v", "v");
		phone_map.put("w", "w");
		phone_map.put("z", "z");
		phone_map.put("ar", "e?");
		phone_map.put("ir", "i?");
		phone_map.put("ur", "??");
		phone_map.put("tr", "tr");
		phone_map.put("dr", "dr");
		phone_map.put("ts", "ts");
		phone_map.put("dz", "dz");
	}
	
	/**
	 * 开始帧位置，每帧相当于10ms
	 */
	public int beg_pos;
	/**
	 * 结束帧位置
	 */
	public int end_pos;
	/**
	 * 音素内容
	 */
	public String content;
	/**
	 * 增漏读信息：0（正确），16（漏读），32（增读），64（回读），128（替换）
	 */
	public int dp_message;
	/**
	 * 时长（单位：帧，每帧相当于10ms）（cn）
	 */
	public int time_len;
	
	/**
	 * 得到content对应的标准音标（en）
	 */
	public String getStdSymbol() {
		return getStdSymbol(content);
	}
	
	public static String getStdSymbol(String content) {
		String std = phone_map.get(content);
		return (null == std)? content: std;
	}
	
}
