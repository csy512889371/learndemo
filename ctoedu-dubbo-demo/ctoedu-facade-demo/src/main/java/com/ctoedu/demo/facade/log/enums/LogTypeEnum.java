package com.ctoedu.demo.facade.log.enums;

/**
 * Created by Administrator on 2017/10/13.
 */
public enum LogTypeEnum {
    LOGIN((short)1, "登录"), BUSINESS((short)2, "业务"), INTERFACE((short)3, "接口"), ACTION((short)4, "行为"), LOGOUT((short)5, "登出");

    private final Short value;
    private final String info;

    private LogTypeEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Short getValue() {
        return value;
    }
    
    public static String getInfo(Short value){
    	for(LogTypeEnum type : LogTypeEnum.values()){
    		if(type.getValue().equals(value)){
    			return type.getInfo();
    		}
    	}
    	return null;
    }
}
