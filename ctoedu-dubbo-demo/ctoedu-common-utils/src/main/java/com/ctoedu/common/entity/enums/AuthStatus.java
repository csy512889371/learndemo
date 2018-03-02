package com.ctoedu.common.entity.enums;

/**
 * 
 *
 *
 */
public enum AuthStatus {
	/**
	 * 无权访问
	 */
    A200((int)200, "已授权"),
	/**
	 * 错误的请求
	 */
    A400((int)400, "错误的请求"),
    /**
	 * 登录状态已过期
	 */
    A4011((int)4011, "登录状态已过期"),
    /**
	 * 未登录
	 */
    A4012((int)4012, "未登录"),
    /**
	 * 无权访问
	 */
    A403((int)403, "无权访问"),
	
	/**
	 * 无权访问
	 */
    A4031((int)4031, "用户被禁用");
	
    private final int value;
    private final String info;

    private AuthStatus(int value, String info) {
        this.value = value;
        this.info = info;
    }
    
    public static String getInfo(int value){
    	for(AuthStatus ae : AuthStatus.values()){
    		if(ae.getValue() == value){
    			return ae.getInfo();
    		}
    	}
    	return null;
    }

    public String getInfo() {
        return info;
    }

    public int getValue() {
        return value;
    }
}
