package com.ctoedu.demo.api.controller.vo.role;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;

public class RoleVO implements BaseVO {
	
	private String id;
	
	private String name;
	
	private String sn;
	
	private Short isAvailable;
	
	private String appId;
	
	private String appName;
	
	private boolean sysDefault = false;
	
	@Override
	public void convertPOToVO(Object poObj) {
		if(poObj != null){
			UmsRole role = null;
			if(poObj instanceof UmsRole){
				role = (UmsRole)poObj;
			}else if(poObj instanceof UmsUserRoleRelation){
				UmsUserRoleRelation uurr = (UmsUserRoleRelation)poObj;
				role = uurr.getRole();
			}
			if(role != null){
				this.id = role.getId();
				this.name = role.getName();
				String sn = role.getSn();
				this.sn = sn;
				for(String d : FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX){
					if(sn.startsWith(d)){
						this.sysDefault = true;
						break;
					}
				}
				this.isAvailable = role.getIsAvailable();
				this.appId = role.getAppId();
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

	public boolean isSysDefault() {
		return sysDefault;
	}

	public void setSysDefault(boolean sysDefault) {
		this.sysDefault = sysDefault;
	}
}