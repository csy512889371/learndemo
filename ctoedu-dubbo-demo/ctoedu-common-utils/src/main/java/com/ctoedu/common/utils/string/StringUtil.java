package com.ctoedu.common.utils.string;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * 字符串工具类
 * 
 * @author Hill
 * 
 */
public class StringUtil extends StringUtilParent {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		if (obj != null && obj.toString() != null && !"".equals(obj.toString().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为空(自动截取首尾空白)
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return isEmpty(str, true);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            源字符串
	 * @param trim
	 *            是否截取首尾空白
	 * @return
	 */
	public static boolean isEmpty(String str, boolean trim) {
		return str == null ? true : "".equals(str.trim());
	}

	/**
	 * @param str
	 *            the string need to be parsed
	 * @param delim
	 *            the delimiter to seperate created by zqf at 6/1/2013
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] parseToArray(String str, String delim) {
		ArrayList arr = new ArrayList();
		StringTokenizer st = new StringTokenizer(str, delim);
		while (st.hasMoreTokens()) {
			arr.add(st.nextToken());
		}
		String[] ret = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			ret[i] = (String) arr.get(i);
		}
		return ret;
	}

	/**
	 * replace a old substring with rep in str
	 * 
	 * @param str
	 *            the string need to be replaced
	 * @param old
	 *            the string need to be removed
	 * @param rep
	 *            the string to be inserted
	 * @return string replaced
	 */
	public static String replace(String str, String old, String rep) {
		if ((str == null) || (old == null) || (rep == null)) {// if one is null
																// return ""
			return "";
		}
		int index = str.indexOf(old);
		if ((index < 0) || "".equals(old)) { // if no old string found or
												// nothing to replace,return the
												// origin
			return str;
		}
		StringBuffer strBuf = new StringBuffer(str);
		while (index >= 0) { // found old part
			strBuf.delete(index, index + old.length());
			strBuf.insert(index, rep);
			index = strBuf.toString().indexOf(old);
		}
		return strBuf.toString();
	}

	/**
	 * 带逗号分隔的数字转换为NUMBER类型
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Number stringToNumber(String str) throws ParseException {
		if (str == null || "".equals(str)) {
			return null;
		}
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator(',');
		dfs.setMonetaryDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###,###,###,###.##", dfs);
		return df.parse(str);
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 用于字符串替换
	 * 
	 * @param target
	 *            目标对象 需要替换的字符串
	 * @param replacement
	 *            要替换的字符串
	 * @param value
	 *            替换的值
	 * @return
	 */
	public static String replacement(String target, String replacement, String value) {
		if (target != null)
			return target.replace(replacement, value);
		return null;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算指定时间与当前时间的差
	 * 
	 * @param date
	 * @return
	 */
	public static String convDateToString(Date date) {
		Long time = new Date().getTime() - date.getTime();
		Long min = time / 1000 / 60;
		if (min < 5) {
			return "刚刚";
		} else if (min >= 5 && min < 60) {
			return min + "分钟之前";
		} else if (min >= 60 && min < 1440) {
			return min / 60 + "小时之前";
		} else if (min >= 1440 && min < 10080) {
			return min / 60 / 24 + "天之前";
		} else if (min >= 10080 && min < 40320) {
			return min / 60 / 24 / 7 + "周之前";
		} else if (min >= 40320 && min < 525600) {
			return min / 60 / 24 / 7 / 4 + "月之前";
		} else if (min >= 525600) {
			return min / 60 / 24 / 365 + "年之前";
		}
		return null;
	}

	/**
	 * @description 获取当前服务器日期
	 * @return
	 */
	public static String getCurrdate(String formatStr) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 将Object值转换成Double类型
	 * 
	 * @param value
	 * @return
	 */
	public static double getDoubleByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Double.valueOf(String.valueOf(value));
	}

	/**
	 * 将Object值转换成Float类型
	 * 
	 * @param value
	 * @return
	 */
	public static float getFloatByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Float.valueOf(String.valueOf(value));
	}

	/**
	 * 将Object值转换成Integer类型
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getIntegerByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(value));
	}

	/**
	 * 解析字符串 ---> 去掉字符串中回车、换行、空格
	 * 
	 * @param str
	 *            被解析字符串
	 * @return String 解析后的字符串
	 */
	public static String parse(String str) {
		return str.replaceAll("\n", "").replaceAll("chr(13)", "").replaceAll(" ", "");
	}

	public static Integer[] Str2Integers(String value) {
		if (null == value || !org.springframework.util.StringUtils.hasText(value)) {
			return null;
		}
		String[] values = value.split(",");
		Integer[] v = new Integer[values.length];
		for (int i = 0; i < values.length; i++) {
			v[i] = Integer.parseInt(values[i]);
		}
		return v;
	}

	public static String[] Str2Strings(String value) {
		if (null == value || !org.springframework.util.StringUtils.hasText(value)) {
			return null;
		}
		String[] values = value.split(",");
		String[] v = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			v[i] = values[i];
		}
		return v;
	}

	public static int strFormateInt(Object obj) {
		if (isNotNull(obj)) {
			return "是".equals(obj) ? 1 : 0;
		} else {
			return 0;
		}
	}

	/**
	 * 获取UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID() {

		return (UUID.randomUUID() + "").replaceAll("-", "");
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 
	 * @param initCode
	 *            初始化编码
	 * @param length
	 *            需要生成编码长度
	 * @param ind
	 *            地增量
	 * @return 递增后的编码
	 */
	public static String getNextCode(String initCode, int length, int ind) {
		Integer temp = Integer.parseInt(initCode);
		temp = temp + ind;
		String tempCode = temp.toString();
		int tempLen = 0;
		if (tempCode.length() < length) {
			tempLen = length - tempCode.length();
		}
		for (int i = 0; i < tempLen; i++) {
			tempCode = "0" + tempCode;
		}
		return tempCode;
	}

	public static int switchNumber(String str) {
		char c = str.charAt(0);
		int temp = 0;
		switch (c) {
		// 数值
		case '〇':
		case '零':
			temp = 0;
			break;
		case '一':
			temp = 1;
			break;
		case '二':
			temp = 2;
			break;
		case '三':
			temp = 3;
			break;
		case '四':
			temp = 4;
			break;
		case '五':
			temp = 5;
			break;
		case '六':
			temp = 6;
			break;
		case '七':
			temp = 7;
			break;
		case '八':
			temp = 8;
			break;
		case '九':
			temp = 9;
			break;
		// 单位，前缀是单数字
		case '十':
			temp = 10;
			break;
		}
		return temp;
	}

	/**
	 * 中文数字转换为阿拉伯数
	 * 
	 * @param String
	 *            s
	 */
	public static int cnNumToInt(String s) {
		int result = 0;
		int yi = 1;// 记录高级单位
		int wan = 1;// 记录高级单位
		int ge = 1;// 记录单位
		char c = s.charAt(0);
		int temp = 0;// 记录数值
		switch (c) {
		// 数值
		case '〇':
		case '零':
			temp = 0;
			break;
		case '一':
			temp = 1 * ge * wan * yi;
			ge = 1;
			break;
		case '二':
			temp = 2 * ge * wan * yi;
			ge = 1;
			break;
		case '三':
			temp = 3 * ge * wan * yi;
			ge = 1;
			break;
		case '四':
			temp = 4 * ge * wan * yi;
			ge = 1;
			break;
		case '五':
			temp = 5 * ge * wan * yi;
			ge = 1;
			break;
		case '六':
			temp = 6 * ge * wan * yi;
			ge = 1;
			break;
		case '七':
			temp = 7 * ge * wan * yi;
			ge = 1;
			break;
		case '八':
			temp = 8 * ge * wan * yi;
			ge = 1;
			break;
		case '九':
			temp = 9 * ge * wan * yi;
			ge = 1;
			break;
		// 单位，前缀是单数字
		case '十':
			ge = 10;
			break;
		case '百':
			ge = 100;
			break;
		case '千':
			ge = 1000;
			break;
		// 高级单位，前缀可以是多个数字
		case '万':
			wan = 10000;
			ge = 1;
			break;
		case '亿':
			yi = 100000000;
			wan = 1;
			ge = 1;
			break;
		default:
			return -1;
		}
		result += temp;
		if (ge > 1) {
			result += 1 * ge * wan * yi;
		}
		return result;
	}

	public static String geneStrAry(String str, String splits) {
		if (StringUtil.isEmpty(str))
			return "";
		String[] ary = str.split(splits);
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < ary.length; i++) {
			sb.append("'");
			sb.append(ary[i]);
			sb.append("'");
			if (i < ary.length - 1)
				sb.append(",");
		}
		return sb.toString();
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? false : str2 == null ? true : str1.equals(str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? false : str2 == null ? true : str1.equalsIgnoreCase(str2);
	}

	/**
	 * 
	 * @param obj
	 *            传数值类型的obj
	 * @param format
	 * @return
	 */
	public static String decimalFormat(Object obj) {
		if (null == obj)
			return "";
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(obj);
	}

	/**
	 * 
	 * @param obj
	 *            传数值类型的obj
	 * @param format
	 * @return
	 */
	public static String decimalFormat(Object obj, String format) {
		if (null == obj)
			return "";
		DecimalFormat df = new DecimalFormat(format);
		return df.format(obj);
	}

}
