package com.ctoedu.common.model.search.exception;


import com.ctoedu.common.model.search.SearchOperator;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public final class InvlidSearchOperatorException extends SearchException {
	private static final long serialVersionUID = 1L;

	public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
