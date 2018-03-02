package com.ctoedu.demo.facade.user.exception;

public class UserUsernameEmptyException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserUsernameEmptyException() {
        super("user.username.empty", null);
    }
}
