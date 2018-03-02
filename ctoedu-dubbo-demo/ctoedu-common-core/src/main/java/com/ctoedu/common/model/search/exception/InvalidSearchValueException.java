package com.ctoedu.common.model.search.exception;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public final class InvalidSearchValueException extends SearchException {

	private static final long serialVersionUID = 1L;

	public InvalidSearchValueException(String searchProperty, String entityProperty, Object value) {
        this(searchProperty, entityProperty, value, null);
    }

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value, Throwable cause) {
        super("Invalid Search Value, searchProperty [" + searchProperty + "], " +
                "entityProperty [" + entityProperty + "], value [" + value + "]", cause);
    }

}
