package com.ctoedu.demo.api.controller.vo.resource;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

public class MenuResourceForSelectVO implements BaseVO {
	
	private String id;
	
	private String name;

	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsMenuResources){
			UmsMenuResources mr = (UmsMenuResources)poObj;
			this.id = mr.getId();
			this.name = mr.getMenuName();
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