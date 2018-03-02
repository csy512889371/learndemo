package com.ctoedu.demo.api.controller.vo.department;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;

public class DepartmentForSelectVO implements BaseVO {
	
	private String id;
	
	/**
	 * 名称
	 */
	private String name;
	
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