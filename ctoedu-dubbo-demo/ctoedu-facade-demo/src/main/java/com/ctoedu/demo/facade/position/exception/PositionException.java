package com.ctoedu.demo.facade.position.exception;

import com.ctoedu.common.exception.BaseException;

public class PositionException extends BaseException {

	private static final long serialVersionUID = 1L;

	public PositionException(String code, Object[] args) {
        super("position", code, args, null);
    }

}
