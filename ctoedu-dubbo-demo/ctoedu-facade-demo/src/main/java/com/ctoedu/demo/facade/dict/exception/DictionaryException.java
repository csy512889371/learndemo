package com.ctoedu.demo.facade.dict.exception;

import com.ctoedu.common.exception.BaseException;

public class DictionaryException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DictionaryException(String code, Object[] args) {
        super("dict", code, args, null);
    }

}
