package com.ctoedu.demo.facade.dict.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;

/**
 * Created by Administrator on 2017/10/13.
 */
@Entity
@Table(name="UMS_DICTIONARY_ITEM")
public class UmsDictionaryItem extends UUIDEntity<String> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6003822964039923939L;
	/**
     * 数据字典条目序号
     */
    private Integer serial;
    /**
     * 数据字典条目编码
     */
    private Integer code;
    /**
     * 数据字典条目名称
     */
    private String value;
    /**
     * 是否可用
     */
    @Column(name="IS_AVAILABLE")
    private Short isAvailable = AvailableEnum.TRUE.getValue();
    @ManyToOne
    @JoinColumn(name="TYPE_ID", referencedColumnName="ID")
    private UmsDictionaryType type;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public UmsDictionaryType getType() {
        return type;
    }

    public void setType(UmsDictionaryType type) {
        this.type = type;
    }
}
