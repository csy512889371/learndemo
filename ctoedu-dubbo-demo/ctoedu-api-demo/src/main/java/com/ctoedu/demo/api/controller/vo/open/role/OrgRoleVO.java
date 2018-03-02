package com.ctoedu.demo.api.controller.vo.open.role;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;

public class OrgRoleVO implements BaseVO {
	
	private String id;
	
	private String name;
	
	private String sn;
	
	private Short isAvailable;
	
	@Override
	public void convertPOToVO(Object poObj) {
		if(poObj != null){
			UmsOrgRole role = null;
			if(poObj instanceof UmsOrgRole){
				role = (UmsOrgRole)poObj;
			}else if(poObj instanceof UmsUserOrgRoleRelation){
				UmsUserOrgRoleRelation uuorr = (UmsUserOrgRoleRelation)poObj;
				role = uuorr.getRole();
			}
			if(role != null){
				this.id = role.getId();
				this.name = role.getName();
				this.sn = role.getSn();
				this.isAvailable = role.getIsAvailable();
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
}