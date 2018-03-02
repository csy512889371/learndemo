package com.ctoedu.demo.facade.app.exception;

import com.ctoedu.common.exception.BaseException;

public class AppException extends BaseException {

	private static final long serialVersionUID = 1L;

	public AppException(String code, Object[] args) {
        super("app", code, args, null);
    }

}
