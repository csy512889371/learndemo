package com.ctoedu.demo.facade.department.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.Principal;

/**
 *
 * Date:2016年11月8日 下午2:21:50
 * Version:1.0
 */
@Entity
@Table(name = "UMS_DEPARTMENT")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsDepartment extends UUIDEntity<String> implements Principal{

	private static final long serialVersionUID = 1L;
	
    private String name;
    
    private String description;
    
    @Column(name="ORG_ID")
    private String orgId;
    
	/**
	 * 是否可用
	 */
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
