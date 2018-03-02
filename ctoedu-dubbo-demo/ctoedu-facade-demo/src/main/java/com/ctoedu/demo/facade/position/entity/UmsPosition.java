package com.ctoedu.demo.facade.position.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Principal;

/**
 * 岗位实体类，用来确定某个人员所属的岗位，岗位隶属于组织机构
 *
 * Date:2016年11月7日 下午4:40:17
 * Version:1.0
 */
@Entity
@Table(name="UMS_POSITION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsPosition extends UUIDEntity<String> implements Principal{

	private static final long serialVersionUID = 1L;
	
	private String sn;
	/**
	 * 岗位的名称
	 */
	private String name;
	
	/**
	 * 是否可用
	 */
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	
	@Column(name="ORG_ID")
    private String orgId;
	
	@Column(name="DEPARTMENT_ID")
    private String departmentId;
	
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
