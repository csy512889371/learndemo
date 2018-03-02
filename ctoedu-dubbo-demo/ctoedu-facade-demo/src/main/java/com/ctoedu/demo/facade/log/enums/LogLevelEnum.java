package com.ctoedu.demo.facade.log.enums;

/**
 * Created by Administrator on 2017/10/13.
 */
public enum LogLevelEnum {

    MINOR((short)1, "轻微"), NORMAL((short)2, "一般"),
    MAJOR((short)3, "严重"), BLOCKER((short)4, "致命");

    private final Short value;
    private final String info;

    private LogLevelEnum(Short value, String info) {
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
    	for(LogLevelEnum level : LogLevelEnum.values()){
    		if(level.getValue().equals(value)){
    			return level.getInfo();
    		}
    	}
    	return null;
    }
}
