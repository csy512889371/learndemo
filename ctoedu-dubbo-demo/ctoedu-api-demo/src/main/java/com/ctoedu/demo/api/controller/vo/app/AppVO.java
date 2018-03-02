package com.ctoedu.demo.api.controller.vo.app;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.app.entity.UmsApp;

public class AppVO implements BaseVO {
	
	private String id;
	
	/**
	 * 系统名称
	 */
	private String name;
	
	/**
	 * 系统标识符
	 */
	private String sn;
	
	/**
	 * 系统Url
	 */
	private String url;
	
	/**
	 * 系统描述
	 */
	private String description;
	
	/**
	 * 系统单点登录标志
	 */
	private Short ssoFlag;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null && poObj instanceof UmsApp){
			UmsApp app = (UmsApp)poObj;
			this.id = app.getId();
			this.name = app.getName();
			this.sn = app.getSn();
			this.url = app.getUrl();
			this.description = app.getDescription();
			this.ssoFlag = app.getSsoFlag();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getSsoFlag() {
		return ssoFlag;
	}

	public void setSsoFlag(Short ssoFlag) {
		this.ssoFlag = ssoFlag;
	}
}