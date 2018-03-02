package com.ctoedu.demo.facade.user.exception;
public class UserPasswordNotMatchException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
