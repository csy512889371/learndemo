package com.ctoedu.common.repository.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启查询缓存
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    boolean value() default true;

}
