package com.ctoedu.demo.cache;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import com.ctoedu.common.utils.security.Md5Utils;

/**
 * 缓存键自定义
 *
 */
public class CacheKeyGenerator extends SimpleKeyGenerator {
	 

	private static final Logger LOG = LoggerFactory.getLogger(CacheKeyGenerator.class);

	public Object generate(Object target, Method method, Object... params) {
		if (Void.class == method.getReturnType()) {
			LOG.error("无返回值的方法不可缓存 {}", method.getName());
			return null;
		}
		Object result = null;
		StringBuilder sb = new StringBuilder();
		sb.append(method.getDeclaringClass().getSimpleName());
		sb.append("/").append(method.getName()).append("/");
		if (params.length > 0) {
			for (Object param : params) {
				if (param == null) {
					sb.append(SimpleKey.EMPTY).append("/");
					continue;
				}
				if (String.class.isAssignableFrom(param.getClass())
						|| Number.class.isAssignableFrom(param.getClass())) {
					sb.append(param).append("/");
					continue;
				}
				if (Date.class.isAssignableFrom(param.getClass())) {
					Date date = (Date) param;
					sb.append(date.getTime()).append("/");
					continue;
				}
				//TODO List参数类型处理
				if(List.class.isAssignableFrom(param.getClass())){
					@SuppressWarnings("unchecked")
					List<Object> objs = (List<Object>) param;
					 for (Object object : objs) {
						if (object instanceof ListKeyParam) {
							sb.append(((ListKeyParam)object).getKey());
						}
					}
					continue;
				}
				LOG.warn("缓存数据时存在不可识别的参数类型 {},method={}", param.getClass(), method);
				sb.append(param).append("/");
			}
		}
		//TODO md5-->number 可将key缩短节省内存空间
		result = super.generate(target, method,Md5Utils.hash(sb.toString()));
		LOG.debug("Cache key = {},method={}", result, method);
		return result;
	}
}
