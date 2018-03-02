package com.ctoedu.demo.facade.position.entity;

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
@Table(name = "UMS_USER_POSITION_RELATION")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUserPositionRelation extends UUIDEntity<String>{

	private static final long serialVersionUID = 1L;
    
    @ManyToOne
	@JoinColumn(name="POSITION_ID", referencedColumnName="ID")
	private UmsPosition position;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private UmsUser user;

    public UmsPosition getPosition() {
		return position;
	}

	public void setPosition(UmsPosition position) {
		this.position = position;
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

        UmsUserPositionRelation that = (UmsUserPositionRelation) o;

        if (this.getId() != that.getId()) return false;
        if (position.getId() != null ? !position.getId().equals(that.position.getId()) : that.position.getId() != null) return false;
        if (user.getId() != null ? !user.getId().equals(that.user.getId()) : that.user.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (position.getId() != null ? position.getId().hashCode() : 0);
        result = 31 * result + (user.getId() != null ? user.getId().hashCode() : 0);
        return result;
    }
}
