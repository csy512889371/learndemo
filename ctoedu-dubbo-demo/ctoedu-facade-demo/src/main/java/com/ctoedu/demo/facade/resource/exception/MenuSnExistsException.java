package com.ctoedu.demo.facade.resource.exception;


public class MenuSnExistsException extends ResourceException {

	private static final long serialVersionUID = 1L;
	
	public MenuSnExistsException() {
        super("resource.menu.sn.exists", null);
    }
}
