package com.ctoedu.demo.facade.org.exception;

import com.ctoedu.common.exception.BaseException;

public class OrgException extends BaseException {

	private static final long serialVersionUID = 1L;

	public OrgException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
