package com.ctoedu.demo.facade.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Principal;

/**
 *
 * Date:2016年11月8日 下午3:06:11
 * Version:1.0
 */
@Entity
@Table(name = "UMS_ROLE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsRole extends UUIDEntity<String> implements Principal{

	private static final long serialVersionUID = 1L;
	private String name;
	private String sn;
	/**
	 * 是否可用
	 */
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	@Column(name="APP_ID")
    private String appId;
	
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
}
