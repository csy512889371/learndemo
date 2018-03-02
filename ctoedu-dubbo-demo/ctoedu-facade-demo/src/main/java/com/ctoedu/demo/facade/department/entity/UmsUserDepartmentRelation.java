package com.ctoedu.demo.facade.department.entity;

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
@Table(name = "UMS_USER_DEPARTMENT_RELATION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUserDepartmentRelation extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID", referencedColumnName="ID")
	private UmsDepartment department;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private UmsUser user;

	public UmsDepartment getDepartment() {
		return department;
	}

	public void setDepartment(UmsDepartment department) {
		this.department = department;
	}

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

        UmsUserDepartmentRelation that = (UmsUserDepartmentRelation) o;

        if (this.getId() != that.getId()) return false;
        if (department.getId() != null ? !department.getId().equals(that.department.getId()) : that.department.getId() != null) return false;
        if (user.getId() != null ? !user.getId().equals(that.user.getId()) : that.user.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (department.getId() != null ? department.getId().hashCode() : 0);
        result = 31 * result + (user.getId() != null ? user.getId().hashCode() : 0);
        return result;
    }
}
