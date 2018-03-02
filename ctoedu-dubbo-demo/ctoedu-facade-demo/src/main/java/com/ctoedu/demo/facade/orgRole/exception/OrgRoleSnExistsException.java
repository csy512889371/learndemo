package com.ctoedu.demo.facade.orgRole.exception;


public class OrgRoleSnExistsException extends OrgRoleException {

	private static final long serialVersionUID = 1L;

	public OrgRoleSnExistsException() {
        super("role.sn.exists", null);
    }
}
