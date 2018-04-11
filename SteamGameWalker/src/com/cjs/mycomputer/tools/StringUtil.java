package com.cjs.mycomputer.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com 创建于:2017年12月1日下午2:11:40
 */
public class StringUtil {

	/**
	 * 下划线转驼峰命名
	 * 
	 * @param camel
	 * @return
	 */
	public static String _ToCamel(String _str) {
		String str = _str;
		if (!isEmptyStr(str)) {
			Pattern linePattern = Pattern.compile("_(\\w)");
			str = str.toLowerCase();
			Matcher matcher = linePattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
			}
			matcher.appendTail(sb);
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 驼峰命名转下划线
	 * 
	 * @param camel
	 * @return
	 */
	public static String CamelTo_(String camel) {
		String str = camel;
		if (!isEmptyStr(str)) {
			Pattern camelPattern = Pattern.compile("[A-Z]");
			Matcher matcher = camelPattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
			}
			matcher.appendTail(sb);
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 是否是空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
	 * @param str
	 *            待转换字符串
	 * @return 转换后字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiConvert1(String str) throws UnsupportedEncodingException {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean isEmoji=false;
		while (matcher.find()) {
			isEmoji=true;
			try {
				matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
			} catch (UnsupportedEncodingException e) {
				Log.e("emojiRecovery error", e.getMessage());
				throw e;
			}
		}
		matcher.appendTail(sb);
		if(isEmoji){
			Log.d("emojiConvert " , str + " to " + sb.toString() + ", len：" + sb.length());
		}
		return sb.toString();
	}

	/**
	 * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
	 * @param str
	 *            转换后的字符串
	 * @return 转换前的字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiRecovery2(String str) throws UnsupportedEncodingException {
		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Log.e("emojiRecovery error", e.getMessage());
				throw e;
			}
		}
		matcher.appendTail(sb);
		Log.d("emojiRecovery " ,str + " to " + sb.toString());
		return sb.toString();
	}
}
