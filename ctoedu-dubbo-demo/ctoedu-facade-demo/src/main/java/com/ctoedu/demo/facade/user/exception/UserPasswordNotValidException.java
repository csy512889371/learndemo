package com.ctoedu.demo.facade.user.exception;

public class UserPasswordNotValidException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserPasswordNotValidException() {
        super("user.password.not.valid", null);
    }
}
