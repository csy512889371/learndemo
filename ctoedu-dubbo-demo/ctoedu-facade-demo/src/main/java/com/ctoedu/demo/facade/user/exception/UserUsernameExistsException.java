package com.ctoedu.demo.facade.user.exception;

public class UserUsernameExistsException extends UserException {

	private static final long serialVersionUID = 1L;
	
	public UserUsernameExistsException() {
        super("user.username.exists", null);
    }
}
