package com.ctoedu.demo.facade.resource.exception;


public class ControllerResNotDeleteException extends ResourceException {

	private static final long serialVersionUID = 1L;
	
	public ControllerResNotDeleteException(String reason) {
        super("resource.controller.not.delete", new Object[]{reason});
    }
}
