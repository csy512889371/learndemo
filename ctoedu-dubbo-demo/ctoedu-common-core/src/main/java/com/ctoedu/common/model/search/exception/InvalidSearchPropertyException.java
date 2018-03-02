package com.ctoedu.common.model.search.exception;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
@SuppressWarnings("serial")
public final class InvalidSearchPropertyException extends SearchException {

    public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
        this(searchProperty, entityProperty, null);
    }

    public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
        super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
    }


}
