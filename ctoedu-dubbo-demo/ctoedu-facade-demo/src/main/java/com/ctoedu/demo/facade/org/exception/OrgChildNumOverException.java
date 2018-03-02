package com.ctoedu.demo.facade.org.exception;

public class OrgChildNumOverException extends OrgException {

	private static final long serialVersionUID = 1L;

	public OrgChildNumOverException(Object[] args) {
        super("org.num.over", args);
    }
}
