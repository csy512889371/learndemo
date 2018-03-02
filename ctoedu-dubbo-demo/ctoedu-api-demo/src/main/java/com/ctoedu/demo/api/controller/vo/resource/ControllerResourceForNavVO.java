package com.ctoedu.demo.api.controller.vo.resource;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;

public class ControllerResourceForNavVO implements BaseVO {
	
	private String id;
	
	private String sn;
	
	private String name;

	private String url;

	private Integer order;

//	private Short isAvailable;
	
//	private String isAvailableC;

//	private Short isLeaf;
	
	private String parentId;
	
	private String appId;
	
//	private List<MenuResourceForNavVO> children = new ArrayList<>();
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsControllerResources){
			UmsControllerResources mr = (UmsControllerResources)poObj;
			this.id = mr.getId();
			this.sn = mr.getControllerSn();
			this.name = mr.getControllerName();
			this.url = mr.getControllerUrlMapping();
			this.order = mr.getControllerOrder();
//			this.isAvailable = mr.getIsAvailable();
//			this.isAvailableC = AvailableEnum.getInfo(mr.getIsAvailable());
//			this.isLeaf = mr.getIsLeaf();
			UmsControllerResources p = mr.getParent();
			if(p != null){
				this.parentId = p.getId();
			}
			UmsApp app = mr.getApplication();
			if(app != null){
				this.appId = app.getId();
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}