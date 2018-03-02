package com.ctoedu.demo.facade.org.entity.enums;

/**
 *
 * Date:2016年11月2日 下午1:24:08
 * Version:1.0
 */
public enum OrgManagerTypeEnum {
	DEFAULT((short)1, "默认"), DEFINE((short)2, "自定义"), ALL((short)3, "所有部门");

    private final Short value;
    private final String info;

    private OrgManagerTypeEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }
    
    public static String getInfo(Short value){
    	for(OrgManagerTypeEnum mt : OrgManagerTypeEnum.values()){
    		if(mt.getValue().equals(value)){
    			return mt.getInfo();
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
