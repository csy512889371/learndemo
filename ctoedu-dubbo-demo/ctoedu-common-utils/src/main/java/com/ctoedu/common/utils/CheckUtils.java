package com.ctoedu.common.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

import com.ctoedu.common.utils.string.StringUtil;


/**
 * 通用检查
 * 
 * @author healy
 * 
 */
public class CheckUtils {

	public static final String COMMON_FIELD = "flowID,initiator,";

	/**
	 * 验证字符串是否为空串
	 * 
	 * @param str
	 * @param checkName
	 */
	public static void valueIsEmpty(String str, String checkName) {
		if (StringUtil.isEmpty(str)) {
			StringBuffer sb = new StringBuffer();
			sb.append(checkName).append(" must be specified");
			throw new IllegalArgumentException(sb.toString());
		}
	}

	/**
	 * 验证字符串是否为空串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param checkNames
	 */
	public static void valueIsEmpty(String[] strs, String... checkNames) {
		StringBuffer buffer = new StringBuffer();
		if (strs.length != checkNames.length)
			throw new IllegalArgumentException(
					"strs's length is not equlas checkNames's length");
		for (int i = 0; i < strs.length; i++) {
			if (StringUtil.isBlank(strs[i])) {
				buffer.append(checkNames[i]).append(",");
			}
		}
		if (buffer.toString().endsWith(",")) {
			buffer.append(" must be specified");
			throw new IllegalArgumentException(buffer.toString());
		}
	}

	/**
	 * 检查对象项是否为NULL
	 * 
	 * @param obj
	 *            检查对象
	 * @param checkname
	 *            对象检查为NULL项(分隔符逗号) 格式为："name,age"
	 * @throws CommonException
	 */
	public static void valueIsNull(Object obj, String checkName) {
		StringBuffer sb = new StringBuffer();
		checkValueIsNull(sb, obj, checkName);
		if (sb.toString().endsWith(",")) {
			sb.append(" must be specified");
			throw new IllegalArgumentException(sb.toString());
		}
	}

	/**
	 * 检查对象项是否为NULL
	 * 
	 * @param objs
	 *            检查对象集合
	 * @param checkNames
	 *            对象检查为NULL项(分隔符逗号) 其中每项格式为："name,age"
	 * @throws CommonException
	 */
	public static void valueIsNull(Object[] objs, String[] checkNames) {
		if (objs.length != checkNames.length) {
			throw new IllegalArgumentException(
					"objs's length is not equlas checkNames's length");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			checkValueIsNull(sb, objs[i], checkNames[i]);
		}
		if (sb.toString().endsWith(",")) {
			sb.append(" must be specified");
			throw new IllegalArgumentException(sb.toString());
		}
	}

	/**
	 * 判断对象项是否为NULL
	 * 
	 * @param sb
	 * @param obj
	 *            检查对象
	 * @param checkNames
	 *            对象检查为NULL项(分隔符逗号) 其中每项格式为："name,age"
	 */
	private static void checkValueIsNull(StringBuffer sb, Object obj,
			String checkNames) {
		if (obj == null) {
			sb.append(checkNames + ",");
			return;
		}
		if (!(isPrimitive(obj) || obj instanceof Date || obj instanceof String || obj instanceof BigDecimal)) {
			String chkname[] = checkNames.split(",");
			for (int j = 0; j < chkname.length; j++) {
				Predicate predicate = new BeanPredicate(chkname[j],
						PredicateUtils.nullPredicate());
				if (predicate.evaluate(obj))
					sb.append(chkname[j] + ",");
			}
		}
	}

	/**
	 * 判断是否是基本类型及包装类 true:是,false:否
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(Object obj) {
		if (obj.getClass().isPrimitive())
			return true;
		if (obj instanceof Long || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Double)
			return true;
		return false;
	}

	/**
	 * 验证对象是否为空
	 * 
	 * @param obj
	 *            被验证的对象
	 * @param message
	 *            异常信息
	 */
	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message + " must be specified");
		}
	}

	/**
	 * 验证对象集合是否为空
	 * 
	 * @param objs
	 *            被验证的对象
	 * @param messages
	 *            异常信息
	 */
	public static void notNull(Object[] objs, String... messages) {
		if (objs.length != messages.length) {
			throw new IllegalArgumentException(
					"objs's length is not equlas message's length");
		}
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] == null) {
				throw new IllegalArgumentException(messages[i]
						+ " must be specified");
			}
		}
	}

	/**
	 * 验证对象是否为空，或者字符串是否为空串(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 *            被验证的对象
	 * @param message
	 *            异常信息
	 */
	public static void strNotNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message + " must be specified");
		}
		if (obj instanceof String && obj.toString().length() == 0) {
			throw new IllegalArgumentException(message + " must be specified");
		}
	}

	/**
	 * 验证对象是否为NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 *            被验证的对象
	 * @param message
	 *            异常信息
	 */
	@SuppressWarnings("rawtypes")
	public static void notEmpty(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message + " must be specified");
		}
		if (obj instanceof String && obj.toString().trim().length() == 0) {
			throw new IllegalArgumentException(message + " must be specified");
		}
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
			throw new IllegalArgumentException(message + " must be specified");
		}
		if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			throw new IllegalArgumentException(message + " must be specified");
		}
		if (obj instanceof Map && ((Map) obj).isEmpty()) {
			throw new IllegalArgumentException(message + " must be specified");
		}
	}

	/**
	 * 判断参数否非空
	 * 
	 * @param obj
	 * @param message
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断参数是否非NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String && obj.toString().trim().length() == 0) {
			return true;
		}
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
			return true;
		}
		if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		}
		if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		}
		return false;
	}
}
