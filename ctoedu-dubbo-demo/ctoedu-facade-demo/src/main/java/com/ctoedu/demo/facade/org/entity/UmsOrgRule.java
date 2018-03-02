package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 *
 * Date:2016年11月8日 下午2:13:08
 * Version:1.0
 */
@Entity
@Table(name="UMS_ORG_RULE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsOrgRule extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	/**
	 * 组织id
	 */
	@Column(name="ORG_ID")
	private String orgId;
	/**
	 * 具体的管理组织,当管理类型为DEFINE_TYPE时，就从该字段中找到所有的可以管理的组织，这里存储的是管理组织的根组织
	 */
	@Column(name="MANAGER_ORG")
	private String managerOrg;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getManagerOrg() {
		return managerOrg;
	}
	public void setManagerOrg(String managerOrg) {
		this.managerOrg = managerOrg;
	}
	
	
}
