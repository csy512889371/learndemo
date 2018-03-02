package com.ctoedu.demo.facade.dict.exception;


public class DictionaryTypeCodeExistsException extends DictionaryException {

	private static final long serialVersionUID = 1L;

	public DictionaryTypeCodeExistsException() {
        super("dict.type.code.exists", null);
    }
}
