package com.ctoedu.demo.api.controller.vo.orgRole;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;

public class OrgRoleVO implements BaseVO {
	
	private String id;
	
	private String name;
	
	private String sn;
	
	private Short isAvailable;
	
	private String orgId;
	
	private String orgName;
	
	private boolean sysDefault = false;
	
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
				String sn = role.getSn();
				this.sn = sn;
				for(String d : FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX){
					if(sn.startsWith(d)){
						this.sysDefault = true;
						break;
					}
				}
				this.isAvailable = role.getIsAvailable();
				this.orgId = role.getOrgId();
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

	public boolean isSysDefault() {
		return sysDefault;
	}

	public void setSysDefault(boolean sysDefault) {
		this.sysDefault = sysDefault;
	}
}