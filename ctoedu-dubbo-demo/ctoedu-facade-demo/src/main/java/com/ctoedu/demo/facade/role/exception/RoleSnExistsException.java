package com.ctoedu.demo.facade.role.exception;


public class RoleSnExistsException extends RoleException {

	private static final long serialVersionUID = 1L;

	public RoleSnExistsException() {
        super("role.sn.exists", null);
    }
}
