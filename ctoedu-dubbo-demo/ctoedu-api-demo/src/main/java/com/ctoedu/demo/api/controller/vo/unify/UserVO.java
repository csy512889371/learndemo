package com.ctoedu.demo.api.controller.vo.unify;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.enums.UnifyType;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;

public class UserVO implements BaseVO {
	
	private String id;
	
	/**
	 * 组织的名称
	 */
	private String name;
	
	private String parentId;
	
	private String type;
	
	private String parentType;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				UmsUser user = uuor.getUser();
				UmsOrg org = uuor.getOrg();
				if(user != null){
					this.id = user.getId();
					UmsPerson person = user.getPerson();
					if(person != null){
						this.name = person.getName();
					}
					this.type = UnifyType.USER.toString();
					if(org != null){
						this.parentId = org.getId();
						this.parentType = UnifyType.ORG.toString();
					}
				}
			}else if(poObj instanceof UmsUserDepartmentRelation){
				UmsUserDepartmentRelation uugr = (UmsUserDepartmentRelation)poObj;
				UmsUser user = uugr.getUser();
				UmsDepartment department = uugr.getDepartment();
				if(user != null){
					this.id = user.getId();
					UmsPerson person = user.getPerson();
					if(person != null){
						this.name = person.getName();
					}
					this.type = UnifyType.USER.toString();
					if(department != null){
						this.parentId = department.getId();
						this.parentType = UnifyType.DEPARTMENT.toString();
					}
				}
			}else if(poObj instanceof UmsUserPositionRelation){
				UmsUserPositionRelation uupr = (UmsUserPositionRelation)poObj;
				UmsUser user = uupr.getUser();
				UmsPosition position = uupr.getPosition();
				if(user != null){
					this.id = user.getId();
					UmsPerson person = user.getPerson();
					if(person != null){
						this.name = person.getName();
					}
					this.type = UnifyType.USER.toString();
					if(position != null){
						this.parentId = position.getId();
						this.parentType = UnifyType.POSITION.toString();
					}
				}
			}else if(poObj instanceof UmsUserOrgRoleRelation){
				UmsUserOrgRoleRelation uuorr = (UmsUserOrgRoleRelation)poObj;
				UmsUser user = uuorr.getUser();
				UmsOrgRole role = uuorr.getRole();
				if(user != null){
					this.id = user.getId();
					UmsPerson person = user.getPerson();
					if(person != null){
						this.name = person.getName();
					}
					this.type = UnifyType.USER.toString();
					if(role != null){
						this.parentId = role.getId();
						this.parentType = UnifyType.ORGROLE.toString();
					}
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
}