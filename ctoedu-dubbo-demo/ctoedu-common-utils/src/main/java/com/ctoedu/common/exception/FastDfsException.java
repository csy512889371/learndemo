package com.ctoedu.common.exception;

/**
 *  2017/4/26.
 * Version:1.0
 */
public class FastDfsException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5808676116404906970L;

	public FastDfsException(String code) {
        super("fastdfs", code, null, null);
    }

    public FastDfsException(String code, Object[] args) {
        super("fastdfs", code, args, null);
    }

}
