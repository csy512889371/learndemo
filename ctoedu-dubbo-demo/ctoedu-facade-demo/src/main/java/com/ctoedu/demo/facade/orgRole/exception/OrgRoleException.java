package com.ctoedu.demo.facade.orgRole.exception;

import com.ctoedu.common.exception.BaseException;

public class OrgRoleException extends BaseException {

	private static final long serialVersionUID = 1L;

	public OrgRoleException(String code, Object[] args) {
        super("role", code, args, null);
    }

}
