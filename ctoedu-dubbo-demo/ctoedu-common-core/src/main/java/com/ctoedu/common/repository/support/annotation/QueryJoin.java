package com.ctoedu.common.repository.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.criteria.JoinType;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryJoin {

    /**
     * 连接的名字
     * @return
     */
    String property();

    JoinType joinType();

}
