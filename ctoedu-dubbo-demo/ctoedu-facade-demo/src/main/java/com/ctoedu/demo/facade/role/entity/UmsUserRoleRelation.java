package com.ctoedu.demo.facade.role.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月8日 下午3:07:39
 * Version:1.0
 */
@Entity
@Table(name = "UMS_USER_ROLE_RELATION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUserRoleRelation extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
//	@Column(name="USER_ID")
//	private String userId;
//	@Column(name="ROLE_ID")
//	private String roleId;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID", referencedColumnName="ID")
	private UmsRole role;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private UmsUser user;

	public UmsRole getRole() {
		return role;
	}

	public void setRole(UmsRole role) {
		this.role = role;
	}

	public UmsUser getUser() {
		return user;
	}

	public void setUser(UmsUser user) {
		this.user = user;
	}
}
