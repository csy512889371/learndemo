package com.ctoedu.demo.api.controller.vo.resource;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;

public class ControllerResourceForSelectVO implements BaseVO {
	
	private String id;
	
	private String name;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsControllerResources){
			UmsControllerResources cr = (UmsControllerResources)poObj;
			this.id = cr.getId();
			this.name = cr.getControllerName();
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