package com.ctoedu.demo.facade.org.exception;


public class OrgTypeSnExistsException extends OrgException {

	private static final long serialVersionUID = 1L;

	public OrgTypeSnExistsException() {
        super("orgtype.sn.exists", null);
    }
}
