package com.ctoedu.demo.facade.role.exception;

import com.ctoedu.common.exception.BaseException;

public class RoleException extends BaseException {

	private static final long serialVersionUID = 1L;

	public RoleException(String code, Object[] args) {
        super("role", code, args, null);
    }

}
