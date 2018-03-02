package com.ctoedu.demo.facade.log.enums;

/**
 * Created by Administrator on 2017/10/13.
 */
public enum OpTypeEnum {
    CREATE((short)1, "新增"), RETRIEVE((short)2, "查询"),
    UPDATE((short)3, "更新"), DELETE((short)4, "删除"),
    SEARCH((short)5, "搜索"), SCAN((short)6, "浏览"),
    EXPORT((short)7, "导出");

    private final Short value;
    private final String info;

    private OpTypeEnum(Short value, String info) {
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
    	for(OpTypeEnum opType : OpTypeEnum.values()){
    		if(opType.getValue().equals(value)){
    			return opType.getInfo();
    		}
    	}
    	return null;
    }
}
