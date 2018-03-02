package com.ctoedu.demo.api.controller.vo.resource;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

public class ControllerResourceVO implements BaseVO, IFayTreeNode {
	
	private String id;
	
	private String sn;
	
	private String name;

	private String urlMapping;

	private Integer order;

	private Short isAvailable;
	
	private String parentId;
	
	private String parentName;
	
	private String appId;
	
	private String appName;
	
	private String menuId;
	
	private String menuName;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsControllerResources){
			UmsControllerResources cr = (UmsControllerResources)poObj;
			this.id = cr.getId();
			this.sn = cr.getControllerSn();
			this.name = cr.getControllerName();
			this.urlMapping = cr.getControllerUrlMapping();
			this.order = cr.getControllerOrder();
			this.isAvailable = cr.getIsAvailable();
			UmsControllerResources parent = cr.getParent();
			if(parent != null){
				this.parentId = parent.getId();
				this.parentName = parent.getControllerName();
			}
			UmsApp app = cr.getApplication();
			if(app != null){
				this.appId = app.getId();
				this.appName = app.getName();
			}
			UmsMenuResources umr = cr.getMenu();
			if(umr != null){
				this.menuId = umr.getId();
				this.menuName = umr.getMenuName();
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

	public String getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}
}