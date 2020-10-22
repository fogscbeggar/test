package com.xiaoshuo.common;

/**
 * 正则，md5加密，首字母大小写，object数组转string数组，integer数组转string数组
 */

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;

import com.ndktools.javamd5.Mademd5;

public class StringHandle {

	public static boolean isEmpty(String value) {
		return "".equals(value) ? true : false;
	}

	public static boolean isNull(Object object) {
		return object == null ? true : false;
	}

	/**
	 * 将字符串重复n次
	 * 
	 * @param repeatStr 要重复的字符串
	 * @param repeatNum 重复次数
	 * @return 字符串
	 */
	public static String repeatString(String repeatStr, int repeatNum, String joinString) {
		return String.join(joinString, Collections.nCopies(repeatNum, repeatStr));
	}

	/**
	 * 正则表达式验证
	 * 
	 * @param regex  正则表达式
	 * @param valStr 需要验证的字符串
	 * @return 匹配返回true，否则返回false
	 */
	public static boolean regexMacher(String regex, String valStr) {
		Pattern pattern = Pattern.compile(regex); // 编译正则表达式
		Matcher matcher = pattern.matcher(valStr); // 设置验证字符串
		return matcher.matches();
	}

	// 简化正则表达式的使用
	public static Matcher regex(String htmltext, String regex) {
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(htmltext);
		return matcher;
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterToUpper(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}

		if (str.length() == 1) {
			return str.toUpperCase();
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterToLower(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}

		if (str.length() == 1) {
			return str.toLowerCase();
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1).toUpperCase();
	}

	/**
	 * 将object类型数组 转为string数组
	 * 
	 * @param objectArray
	 * @return
	 */
	public static String[] objectArrayToStringArray(Object[] objectArray) {
		return Arrays.copyOf(objectArray, objectArray.length, String[].class);
	}

	/**
	 * 将integer类型数组 转为string数组
	 * 
	 * @param intArray
	 * @return
	 */
	public static String[] integerArrayToStringArray(Integer[] intArray) {
		List<String> li = new LinkedList<String>();
		for (Integer v : intArray) {
			li.add(v.toString());
		}
		return (String[]) li.toArray(new String[0]);
	}

	/**
	 * 将string类型数组 转为integer数组
	 * 
	 * @param stringArray
	 * @return
	 */
	public static Integer[] stringArrayToIntegerArray(String[] stringArray) {
		Integer[] intArr = new Integer[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			intArr[i] = Integer.parseInt(stringArray[i]);
		}
		return intArr;
	}

	/**
	 * MD5加密工具类
	 * 
	 * @author pibigstar
	 *
	 */
	// 盐，用于混交md5
	// private static final String slat = "&%5123***&&%%$$#@";

	public static String getMd5(String dataStr) {
		try {
			// dataStr = dataStr+ slat;
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(dataStr.getBytes("UTF8"));
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String md5(String data) {
		return new Mademd5().toMd5(data);

	}
}
