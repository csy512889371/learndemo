package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 * 组织结构类型表，法院来说分为最高院、高级法院、中级法院、基层法院、海事法院、铁路法院、部门
 *
 * Date:2016年11月8日 下午2:13:19
 * Version:1.0
 */
@Entity
@Table(name="UMS_ORG_TYPE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsOrgType extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 类型sn
	 */
	private String sn;
	
	/**
	 * 类型的名称
	 */
	private String name;
	
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
	
}
