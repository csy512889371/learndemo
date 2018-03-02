package com.ctoedu.demo.facade.org.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月8日 下午2:21:50
 * Version:1.0
 */
@Entity
@Table(name = "UMS_USER_ORG_RELATION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUserOrgRelation extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="ORG_ID", referencedColumnName="ID")
	private UmsOrg org;
//    @Column(name = "ORG_ID")
//    private String orgId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private UmsUser user;
	
//    @Column(name = "USER_ID")
//    private String userId;

//    public String getOrgId() {
//		return orgId;
//	}
//
//	public void setOrgId(String orgId) {
//		this.orgId = orgId;
//	}

//	public String getUserId() {
//        return userId;
//    }

    public UmsOrg getOrg() {
		return org;
	}

	public void setOrg(UmsOrg org) {
		this.org = org;
	}
//
//	public void setUserId(String userId) {
//        this.userId = userId;
//    }
	public UmsUser getUser() {
		return user;
	}

	public void setUser(UmsUser user) {
		this.user = user;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UmsUserOrgRelation that = (UmsUserOrgRelation) o;

        if (this.getId() != that.getId()) return false;
        if (org.getId() != null ? !org.getId().equals(that.org.getId()) : that.org.getId() != null) return false;
        if (user.getId() != null ? !user.getId().equals(that.user.getId()) : that.user.getId() != null) return false;

        return true;
    }

	@Override
    public int hashCode() {
        int result = (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (org.getId() != null ? org.getId().hashCode() : 0);
        result = 31 * result + (user.getId() != null ? user.getId().hashCode() : 0);
        return result;
    }
}
