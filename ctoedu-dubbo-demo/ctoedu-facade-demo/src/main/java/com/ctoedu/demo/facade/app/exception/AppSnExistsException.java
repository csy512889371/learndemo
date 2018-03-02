package com.ctoedu.demo.facade.app.exception;


public class AppSnExistsException extends AppException {

	private static final long serialVersionUID = 1L;

	public AppSnExistsException() {
        super("app.sn.exists", null);
    }
}
