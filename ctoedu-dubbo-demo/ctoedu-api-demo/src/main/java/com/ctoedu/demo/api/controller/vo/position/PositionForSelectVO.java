package com.ctoedu.demo.api.controller.vo.position;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;

public class PositionForSelectVO implements BaseVO {
	
	private String id;
	
	/**
	 * 名称
	 */
	private String name;
	
	@Override
	public void convertPOToVO(Object poObj) {
		if(poObj != null){
			UmsPosition position = null;
			if(poObj instanceof UmsPosition){
				position = (UmsPosition)poObj;
			}else if(poObj instanceof UmsUserPositionRelation){
				UmsUserPositionRelation uupr = (UmsUserPositionRelation)poObj;
				position = uupr.getPosition();
			}
			if(position != null){
				this.id = position.getId();
				this.name = position.getName();
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}