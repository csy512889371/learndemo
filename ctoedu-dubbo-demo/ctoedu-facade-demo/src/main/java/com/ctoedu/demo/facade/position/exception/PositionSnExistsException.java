package com.ctoedu.demo.facade.position.exception;


public class PositionSnExistsException extends PositionException {

	private static final long serialVersionUID = 1L;

	public PositionSnExistsException() {
        super("position.sn.exists", null);
    }
}
