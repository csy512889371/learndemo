package com.ctoedu.common.utils.string;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.ctoedu.common.utils.validate.ValidateUtils;


public final class StringTools {

	private static Logger logger = LoggerFactory.getLogger(StringTools.class);

	/**
	 * 判断某个字符串是否包含在某个数组中。如果数组为null则返回false
	 * 
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean isContainsString(String str, String[] array) {
		if (array == null) {
			return false;
		}
		for (String s : array) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}

	private StringTools() {
	}

	private static final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final Format numformat = new DecimalFormat("#.##");
	private static final String zeros = "00000000000000000000";

	/**
	 * 需要进行过滤并替换的sql字符
	 */
	private static final String[][] sqlhandles = { { "'", "''" }, { "\\\\", "\\\\\\\\" } };

	public static String listingString(Object data) {
		return listingString(data, true);
	}

	public static String listingString(Object data, boolean snapped) {
		StringBuilder sb = new StringBuilder(100);
		sb.append(data.getClass().getSimpleName()).append("[");
		try {
			boolean flag = false;
			boolean isstring = true;
			Object obj = null;
			String str = "";
			for (java.lang.reflect.Method m : data.getClass().getDeclaredMethods()) {
				if ((m.getName().startsWith("get") || m.getName().startsWith("is")) && m.getParameterTypes().length == 0) {
					int l = m.getName().startsWith("get") ? 3 : 2;
					obj = m.invoke(data);
					if (snapped && obj == null)
						continue;
					isstring = obj instanceof String;
					if (!isstring && snapped) {
						if (obj instanceof Number && ((Number) obj).intValue() == 0)
							continue;
						if (obj instanceof Boolean && ((Boolean) obj) == false)
							continue;
					}
					str = isstring ? ("\"" + obj + "\"") : String.valueOf(obj);
					if (flag)
						sb.append(", ");
					sb.append(m.getName().substring(l).toLowerCase()).append("=").append(str);
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		sb.append("]");
		return sb.toString();
	}

	public static String subString(String t, int size) {
		if (t == null)
			return null;
		int hansize = size * 3 / 2;
		int len = hansize;
		if (t.length() > size) {
			int p = 0;
			for (int i = 0; i < hansize && i < t.length(); i++) {
				if (t.charAt(i) > 127)
					p++;
			}
			len -= p * 2 / 3;
			if (len < size)
				len = size;
			if (t.length() <= len)
				return t;
			return t.substring(0, len) + "...";
		}
		return t;
	}

	/**
	 * 返回全局唯一序列号，模拟Sql Server的newid()函数功能
	 */
	public static String createSequence() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 转换成用B,KB,MB,GB,TB单位来表示的大小
	 */
	public static String formatFileLength(long sizes) {
		if (sizes < 0)
			sizes = 0;
		String str = "";
		if (sizes < 1024) { // 小于1KB
			str += "" + sizes + "B";
		} else if (sizes < 1024 * 1024) { // 小于1MB
			str += "" + numformat.format(sizes / 1024.0) + "K";
		} else if (sizes < 1024 * 1024 * 1024) { // 小于1GB
			str += "" + numformat.format(sizes / (1024 * 1024.0)) + "M";
		} else if (sizes < 1024 * 1024 * 1024 * 1024L) { // 小于1TB
			str += "" + numformat.format(sizes / (1024 * 1024 * 1024.0)) + "G";
		} else { // 大于1TB
			str += "" + numformat.format(sizes / (1024 * 1024 * 1024 * 1024.0)) + "T";
		}
		for (int i = 0; i < 8 - str.length(); i++) {
			str = " " + str;
		}
		return str;
	}

	/**
	 * 把指定byte数组转换成16进制的字符串
	 */
	public static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(hex[((b >> 4) & 0xF)]).append(hex[((b >> 0) & 0xF)]);
		}
		return sb.toString();
	}

	/**
	 * 把指定16进制的字符串转换成byte数组
	 */
	public static byte[] hexStringToBytes(String inString) {
		int fromLen = inString.length();
		int toLen = (fromLen + 1) / 2;
		final byte[] b = new byte[toLen];
		for (int i = 0; i < toLen; i++) {
			b[i] = (byte) hexPairToInt(inString.substring(i * 2, (i + 1) * 2));
		}
		return b;
	}

	/**
	 * 将数组进行排序然后再组成字符串
	 * 
	 * @param totalStringList
	 * @return
	 */
	public static String ArrayToSortString(List<String> totalStringList) {
		StringBuffer str = new StringBuffer("");

		if (totalStringList != null && totalStringList.size() > 0) {
			String[] strs = totalStringList.toArray(new String[totalStringList.size()]);
			Arrays.sort(strs);
			for (String s : strs) {
				str.append(s);
			}
		}
		return str.toString();
	}

	/**
	 * 把指定cid字符串转换成byte数组
	 */
	public static byte[] convertStringCid2Bytes(String sCid) {
		byte[] cid = new byte[20];
		for (int i = 0; i < cid.length; i++) {
			cid[i] = (byte) Integer.parseInt(sCid.substring(i * 2, i * 2 + 2), 16);
		}
		return cid;
	}

	/**
	 * 在指定字符串数组里查找指定字符串，找到则返回索引号，找不到返回-1
	 */
	public static int search(String no, String[] noes) {
		for (int i = 0; i < noes.length; i++) {
			if (no.equals(noes[i]))
				return i;
		}
		return -1;
	}

	private static int hexPairToInt(String inString) {
		String digits = "0123456789abcdef";
		String s = inString.toLowerCase();
		int n = 0;
		int thisDigit = 0;
		int sLen = s.length();
		if (sLen > 2)
			sLen = 2;
		for (int i = 0; i < sLen; i++) {
			thisDigit = digits.indexOf(s.substring(i, i + 1));
			if (thisDigit < 0)
				throw new NumberFormatException();
			if (i == 0)
				thisDigit *= 0x10;
			n += thisDigit;
		}
		return n;
	}

	public static String read(InputStream in, String charset) throws IOException {
		int pos = -1;
		byte[] buf = new byte[1024 * 8];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((pos = in.read(buf)) != -1) {
			out.write(buf, 0, pos);
		}
		return (charset == null) ? new String(out.toByteArray()) : new String(out.toByteArray(), charset);
	}

	public static String read(InputStream in) throws IOException {
		return read(in, null);
	}

	/**
	 * 转换成js代码
	 */
	public static final String escapeJs(String unicode) {
		return org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(unicode);
	}

	/**
	 * 对字符进行URL编码。客户端使用js的decodeURIComponent进行解码
	 * 
	 * @param str
	 *            字符串源码
	 * @return URL编码后的字符串
	 */
	public static String encodeURL(String str) {
		try {
			return java.net.URLEncoder.encode(str, "utf-8").replaceAll("\\+", "%20");
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 对url进行解码
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeURL(String str) {
		try {
			return java.net.URLDecoder.decode(str, "utf-8");
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 转换成html代码
	 */
	public static final String escapeHtml(String unicode) {
		return org.apache.commons.lang.StringEscapeUtils.escapeHtml(unicode);
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * 判断字符串是否为非空
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}

	/**
	 * 将字符串中可能包含有非法的sql字符进行过滤，例如过滤'。
	 * 
	 * @param str
	 *            需要进行过滤的字符串
	 * @return 过滤后的安全字符串
	 */
	public static final String escapeSql(String str) {
		if (str == null) {
			return "";
		}
		for (String[] ss : sqlhandles) {
			str = str.replaceAll(ss[0], ss[1]);
		}
		return str;
	}

	/**
	 * 将数值转换成特定长度的字符串
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static String toLenString(long value, int length) {
		String val = value + "";
		if (val.length() > length) {
			try {
				throw new Exception("定义的长度小于数值的长度。");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		if (val.length() < length) {
			return zeros.substring(0, length - val.length()) + val;
		} else {
			return val;
		}
	}

	/**
	 * 将字符串中可能包含有非法的sql字符进行过滤，例如过滤'。
	 * 
	 * @param obj
	 *            过滤对象
	 * @return 过滤后的安全字符串
	 */
	public static final String escapeSql(Object obj) {
		if (obj == null) {
			return "";
		}
		return escapeSql(obj.toString());
	}

	/**
	 * 将对象安全转换成int类型，失败时返回0
	 * 
	 * @param o
	 *            目标对象
	 * @return int数字
	 */
	public static int safeToInt(Object o) {
		int rs = 0;
		try {
			rs = Integer.parseInt(o.toString());
		} catch (Exception ex) {
			rs = 0;
		}
		return rs;
	}

	/**
	 * 将对象安全转换成short类型
	 * 
	 * @param o
	 *            目标对象
	 * @return short数字
	 */
	public static int safeToShort(Object o) {
		short rs = 0;
		try {
			rs = Short.parseShort(o.toString());
		} catch (Exception ex) {
			rs = 0;
		}
		return rs;
	}

	/**
	 * 将对象安全转换成long类型
	 * 
	 * @param o
	 *            目标对象
	 * @return long数字
	 */
	public static long safeToLong(Object o) {
		long rs = 0;
		try {
			rs = Long.parseLong(o.toString());
		} catch (Exception ex) {
			rs = 0;
		}
		return rs;
	}

	/**
	 * 将对象安全转换成double类型
	 * 
	 * @param o
	 *            目标对象
	 * @return double数字
	 */
	public static double safeToDouble(Object o) {
		double rs = 0;
		try {
			rs = Double.parseDouble(o.toString());
		} catch (Exception ex) {
			rs = 0;
		}
		return rs;
	}

	/**
	 * 得到系统的时间戳
	 */
	public static String getTradeSn() {
		return "" + new java.util.Date().getTime();
	}

	/**
	 * 尝试将对象转换成double类型，如果失败时也不抛出异常而返回0
	 * 
	 * @param fieldValue
	 * @return
	 */
	public static double tryParseDouble(Object fieldValue) {
		try {
			double rs = (Double) fieldValue;
			return rs;
		} catch (Exception ex) {
			try {
				return Double.parseDouble(fieldValue.toString());
			} catch (Exception exx) {
				return 0;
			}
		}
	}

	/**
	 * 用md5算法对字符串进行加密
	 * 
	 * @param source
	 * @param key
	 * @return
	 */
	public static String encodeByMD5(String source, String key) {
		if (source == null)
			return null;
		try {
			return DigestUtils.md5DigestAsHex((source + key).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 将手机号码中的中间四位转换成*
	 * 
	 * @param src
	 * @return
	 */
	public static String phoneChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		StringBuffer tempStr = new StringBuffer();
		int srcLength = src.length();
		for (int i = 0; i < srcLength; i++) {
			if (i > 2 && i < 7) {
				tempStr.append("*");
			} else {
				tempStr.append(src.charAt(i));
			}
		}
		return tempStr.toString();
	}

	/**
	 * 将银行卡号限前4后3中间用****填充
	 * 
	 * 
	 * @param src
	 * @return
	 */
	public static String bankNoChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return src.substring(0, 4) + "****" + src.substring(src.length() - 3, src.length());
	}

	/**
	 * 将真实姓名限前**后1个名字
	 * 
	 * 
	 * @param src
	 * @return
	 */
	public static String realNameChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return "**" + src.charAt(src.length() - 1);
	}

	/**
	 * 将收款人按长度前面用**显示
	 * 
	 * @param src
	 * @return
	 */
	public static String PayeeNameChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		StringBuffer mark = new StringBuffer("");
		if (src.trim().length() > 7) {
			for (int i = 0; i < src.trim().length() - 4; i++) {
				mark.append("*");
			}
			return src.substring(0, 4) + mark;
		}
		if (src.trim().length() > 3) {
			for (int i = 0; i < src.trim().length() - 2; i++) {
				mark.append("*");
			}
			return src.substring(0, 2) + mark;
		}
		if (src.trim().length() > 1) {
			for (int i = 0; i < src.trim().length() - 1; i++) {
				mark.append("*");
			}
			return src.substring(0, 1) + mark;
		}
		return src.substring(0, 1) + "*";
	}

	/**
	 * 将身份证号限前4后4中间用****填充
	 * 
	 * 
	 * @param src
	 * @return
	 */
	public static String idCardChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return src.substring(0, 4) + "****" + src.substring(src.length() - 4, src.length());
	}

	/**
	 * 将email限前4后4中间用****填充
	 * 
	 * 
	 * @param src
	 * @return
	 */
	public static String emailChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return src.substring(0, 4) + "****" + src.substring(src.length() - 4, src.length());
	}

	/**
	 * 去空格
	 * 
	 * @param param
	 * @return
	 */
	public static String stringToTrim(String param) {
		return ValidateUtils.isEmpty(param) ? "" : param.trim();

	}

}
