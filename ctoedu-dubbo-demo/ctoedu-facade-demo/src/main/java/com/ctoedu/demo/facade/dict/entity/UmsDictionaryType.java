package com.ctoedu.demo.facade.dict.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;

/**
 * Created by Administrator on 2017/10/13.
 */
@Entity
@Table(name="UMS_DICTIONARY_TYPE")
public class UmsDictionaryType extends UUIDEntity<String> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 54114571025976530L;
	/**
     * 数据字典类型编码
     */
    private String code;
    /**
     * 数据字典类型名称
     */
    private String value;
    /**
     * 是否可用
     */
    @Column(name="IS_AVAILABLE")
    private Short isAvailable = AvailableEnum.TRUE.getValue();
    /**
     * 数据字典类型描述
     */
    @Column(name = "DESCRIPTION")
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
