package com.ctoedu.common.entity.enums;

/**
 *
 * Date:2016年11月2日 下午1:24:08
 * Version:1.0
 */
public enum AvailableEnum {
    TRUE((short)1, "可用"), FALSE((short)2, "不可用");

    private final Short value;
    private final String info;

    private AvailableEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }
    
    public static String getInfo(Short value){
    	for(AvailableEnum ae : AvailableEnum.values()){
    		if(ae.getValue().equals(value)){
    			return ae.getInfo();
    		}
    	}
    	return null;
    }

    public String getInfo() {
        return info;
    }

    public Short getValue() {
        return value;
    }
}
