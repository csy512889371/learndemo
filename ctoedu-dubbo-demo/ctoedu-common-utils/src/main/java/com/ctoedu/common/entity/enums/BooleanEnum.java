package com.ctoedu.common.entity.enums;

/**
 *
 * Date:2016年11月2日 下午1:24:08
 * Version:1.0
 */
public enum BooleanEnum {
    TRUE((short)1, "是"), FALSE((short)2, "否");

    private final Short value;
    private final String info;

    private BooleanEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Short getValue() {
        return value;
    }
}
