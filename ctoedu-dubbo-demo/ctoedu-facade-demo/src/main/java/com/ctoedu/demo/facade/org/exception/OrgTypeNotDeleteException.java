package com.ctoedu.demo.facade.org.exception;


public class OrgTypeNotDeleteException extends OrgException {

	private static final long serialVersionUID = 1L;

	public OrgTypeNotDeleteException() {
        super("orgtype.not.delete", null);
    }
}
