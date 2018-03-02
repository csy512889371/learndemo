package com.ctoedu.demo.api.controller.vo.dict;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryItem;
import com.ctoedu.demo.facade.dict.entity.UmsDictionaryType;

public class DictItemVO implements BaseVO {
	
	private String id;
	
	private Integer serial;
	
	private Integer code;
	
	private String value;
	
	private Short isAvailable;

	private String typeId;
	
	private String typeName;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			UmsDictionaryItem dictItem = null;
			if(poObj instanceof UmsDictionaryItem){
				dictItem = (UmsDictionaryItem)poObj;
			}
			if(dictItem != null){
				this.id = dictItem.getId();
				this.serial = dictItem.getSerial();
				this.code = dictItem.getCode();
				this.value = dictItem.getValue();
				this.isAvailable = dictItem.getIsAvailable();
				UmsDictionaryType type = dictItem.getType();
				if(type!=null){
					this.typeId = type.getId();
					this.typeName = type.getValue();
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}