package com.ctoedu.demo.facade.log.enums;

/**
 * Created by Administrator on 2017/10/13.
 */
public enum OpResultEnum {
    SUCCESS((short)1, "成功"), FAIl((short)2, "失败");

    private final Short value;
    private final String info;

    private OpResultEnum(Short value, String info) {
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
    	for(OpResultEnum result : OpResultEnum.values()){
    		if(result.getValue().equals(value)){
    			return result.getInfo();
    		}
    	}
    	return null;
    }
}
