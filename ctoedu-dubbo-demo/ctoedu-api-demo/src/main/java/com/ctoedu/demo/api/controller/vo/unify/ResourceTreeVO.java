package com.ctoedu.demo.api.controller.vo.unify;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.enums.UnifyType;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

public class ResourceTreeVO implements BaseVO,IFayTreeNode {
	
	private String id;
	
	/**
	 * 组织的名称
	 */
	private String name;
	/**
	 * 组织机构的排序号
	 */
	private Integer orderNum;
	
	private String parentId;
	
	private String type;
	
	private String available = "2";//'1'为可用，'2'为不可用
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsApp){
				UmsApp umr = (UmsApp)poObj;
				this.id = umr.getId();
				this.name = umr.getName();
				this.type = UnifyType.APP.toString();
			}else if(poObj instanceof UmsMenuResources){
				UmsMenuResources umr = (UmsMenuResources)poObj;
				this.id = umr.getId();
				this.name = umr.getMenuName();
				this.type = Resource.RESOURCE_MENU.toString();
				UmsMenuResources parent = umr.getParent();
				if(parent == null){
					UmsApp app = umr.getApplication();
					if(app != null) this.parentId = app.getId();
				}else{
					this.parentId = parent.getId();
				}
				
			}else if(poObj instanceof UmsControllerResources){
				UmsControllerResources ucr = (UmsControllerResources)poObj;
				this.id = ucr.getId();
				this.name = ucr.getControllerName();
				UmsMenuResources umr = ucr.getMenu();
				if(umr == null) {
					UmsApp app = ucr.getApplication();
					if(app != null) this.parentId = app.getId();
				}else{
					this.parentId = umr.getId();
				}
				this.type = Resource.RESOURCE_CONTROLLER.toString();
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

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
}