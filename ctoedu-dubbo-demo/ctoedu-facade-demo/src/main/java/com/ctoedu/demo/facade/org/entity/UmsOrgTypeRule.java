package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;

/**
 * 组织结构类型规则表，用来确定组织之间的关系
 *
 * Date:2016年11月8日 下午2:13:36
 * Version:1.0
 */
@Entity
@Table(name="UMS_ORG_TYPE_RULE")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsOrgTypeRule extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	/**
	 * 规则的父id
	 */
	private String pid;
	/**
	 * 规则的子id
	 */
	private String cid;
	/**
	 * 两者之间的数量，如果为-1表示可以有无限多个
	 */
	private Integer num;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
