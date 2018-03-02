package com.ctoedu.common.model.search.exception;

import org.springframework.core.NestedRuntimeException;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class SearchException extends NestedRuntimeException {

	private static final long serialVersionUID = 1L;

	public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
