package com.ctoedu.demo.facade.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 *
 * Date:2016年11月11日 上午11:31:44
 * Version:1.0
 */
@Entity
@Table(name="UMS_APPLICATION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsApp extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	/**
	 * 系统标识符
	 */
	private String sn;
	/**
	 * 系统名称
	 */
	private String name;
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
