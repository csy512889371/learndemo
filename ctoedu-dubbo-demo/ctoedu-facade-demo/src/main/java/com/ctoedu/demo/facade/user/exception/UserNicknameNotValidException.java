package com.ctoedu.demo.facade.user.exception;

public class UserNicknameNotValidException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserNicknameNotValidException() {
        super("user.nickname.not.valid", null);
    }
}
