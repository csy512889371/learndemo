package com.ctoedu.demo.facade.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.exception.BaseException;

/**
 *
 * Date:2016年11月8日 下午3:02:58
 * Version:1.0
 */
@Entity
@Table(name = "UMS_ACL")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsAcl extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	private String ptype;
	private String rtype;
	private String pid;
	private String rid;
	private Integer aclState;
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public Integer getAclState() {
		return aclState;
	}
	public void setAclState(Integer aclState) {
		this.aclState = aclState;
	}
	
	/**
	 * 设置权限，在某个位置设置访问或者无法访问
	 * @param index
	 * @param permit
	 */
	public void setPermission(Integer index,boolean permit) {
		if(index<0||index>31) {
			throw new BaseException("权限的位置只能在0-31之间");
		}
		this.aclState = setBit(this.aclState,index,permit);
	}
	/**
	 * 具体进行设置
	 * @param state
	 * @param index
	 * @param permit
	 */
	public Integer setBit(Integer state,Integer index,boolean permit) {
		Integer tmp = 1;
		tmp = tmp<<index;
		if(permit) {
			state = state|tmp;
		} else {
			tmp = ~tmp;
			state = state&tmp;
		}
		return state;
	}
	
	/**
	 * 检查在某个位置是否可以访问
	 * @param index
	 * @return
	 */
	public boolean checkPermission(Integer index) {
		int tmp = 1;
		tmp = tmp<<index;
		int num = this.aclState&tmp;
		return num>0;
	}
	
}
