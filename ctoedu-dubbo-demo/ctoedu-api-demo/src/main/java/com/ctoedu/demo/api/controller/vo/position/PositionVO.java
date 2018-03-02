package com.ctoedu.demo.api.controller.vo.position;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;

public class PositionVO implements BaseVO {
	
	private String id;
	
	/**
	 * 标识符
	 */
	private String sn;
	/**
	 * 岗位的名称
	 */
	private String name;
	
	/**
	 * 是否可用
	 */
	private Short isAvailable;
	
	private String orgId;
	
	private String departmentId;
	
	private String orgName;
	
	private String departmentName;
	
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
				this.sn = position.getSn();
				this.name = position.getName();
				this.isAvailable = position.getIsAvailable();
				this.departmentId = position.getDepartmentId();
				this.orgId = position.getOrgId();
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

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}