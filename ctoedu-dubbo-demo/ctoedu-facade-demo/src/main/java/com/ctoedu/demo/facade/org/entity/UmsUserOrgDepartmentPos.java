package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 *
 * Date:2016年11月8日 下午2:21:50
 * Version:1.0
 */
@Entity
@Table(name="UMS_USER_ORG_DEPARTMENT_POS")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUserOrgDepartmentPos extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	/**
	 * 人员id
	 */
	private String userId;
	/**
	 * 机构的id
	 */
	private String orgId;
	/**
	 * 部门的id
	 */
	private String departmentId;
	/**
	 * 岗位的id
	 */
	private String posId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	
}
