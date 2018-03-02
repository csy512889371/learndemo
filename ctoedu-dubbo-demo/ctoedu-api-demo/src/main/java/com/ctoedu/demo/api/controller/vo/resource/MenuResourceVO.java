package com.ctoedu.demo.api.controller.vo.resource;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

public class MenuResourceVO implements BaseVO, IFayTreeNode {
	
	private String id;
	
	private String sn;
	
	private String name;

	private String url;

	private String icon;

	private Integer order;

	private Short isAvailable;
	
//	private Short isLeaf;
	
	private String parentId;
	
	private String parentName;
	
	private String appId;
	
	private String appName;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsMenuResources){
			UmsMenuResources mr = (UmsMenuResources)poObj;
			this.id = mr.getId();
			this.sn = mr.getMenuSn();
			this.name = mr.getMenuName();
			this.url = mr.getMenuUrl();
			this.icon = mr.getMenuIcon();
			this.order = mr.getMenuOrder();
			this.isAvailable = mr.getIsAvailable();
//			this.isLeaf = mr.getIsLeaf();
			UmsMenuResources parent = mr.getParent();
			if(parent != null){
				this.parentId = parent.getId();
				this.parentName = parent.getMenuName();
			}
			UmsApp app = mr.getApplication();
			if(app != null){
				this.appId = app.getId();
				this.appName = app.getName();
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}
}