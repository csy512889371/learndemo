package com.ctoedu.demo.facade.resource.exception;

import com.ctoedu.common.exception.BaseException;

public class ResourceException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ResourceException(String code, Object[] args) {
        super("resource", code, args, null);
    }

}
