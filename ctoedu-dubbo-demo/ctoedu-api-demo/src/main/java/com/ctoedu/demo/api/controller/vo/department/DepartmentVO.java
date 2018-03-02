package com.ctoedu.demo.api.controller.vo.department;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;

public class DepartmentVO implements BaseVO {
	
	private String id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String description;
	
	private Short isAvailable;
	
	private String orgId;
	
	private String orgName;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			UmsDepartment department = null;
			if(poObj instanceof UmsDepartment){
				department = (UmsDepartment)poObj;
			}else if(poObj instanceof UmsUserDepartmentRelation){
				UmsUserDepartmentRelation uugr = (UmsUserDepartmentRelation)poObj;
				department = uugr.getDepartment();
			}
			if(department != null){
				this.id = department.getId();
				this.name = department.getName();
				this.description = department.getDescription();
				this.isAvailable = department.getIsAvailable();
				this.orgId = department.getOrgId();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
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
}