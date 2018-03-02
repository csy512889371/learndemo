package com.ctoedu.demo.facade.user.exception;

import com.ctoedu.common.exception.BaseException;

public class UserException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
