package com.ctoedu.demo.api.controller.vo.unify;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.enums.UnifyType;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;

public class OrgTreeVO implements BaseVO,IFayTreeNode {
	
	private String id;
	
	/**
	 * 组织的名称
	 */
	private String name;
	/**
	 * 组织机构的排序号
	 */
	private Integer orderNum;
	
	private String parentId;
	
	private String type;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsOrg){
				UmsOrg org = (UmsOrg)poObj;
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
					this.orderNum = org.getOrderNum();
					this.type = UnifyType.ORG.toString();
					UmsOrg parent = org.getParent();
					if(parent != null){
						this.parentId = parent.getId();
					}
				}
			}else if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				UmsOrg org = uuor.getOrg();
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
					this.orderNum = org.getOrderNum();
					this.type = UnifyType.ORG.toString();
					UmsOrg parent = org.getParent();
					if(parent != null){
						this.parentId = parent.getId();
					}
				}
			}else if(poObj instanceof UmsOrgRole){
				UmsOrgRole orgRole = (UmsOrgRole)poObj;
				if(orgRole != null){
					this.id = orgRole.getId();
					this.name = orgRole.getName();
					this.type = UnifyType.ORGROLE.toString();
					this.parentId = orgRole.getOrgId();
				}
			}else if(poObj instanceof UmsUserOrgRoleRelation){
				UmsUserOrgRoleRelation uuorr = (UmsUserOrgRoleRelation)poObj;
				UmsOrgRole orgRole = uuorr.getRole();
				if(orgRole != null){
					this.id = orgRole.getId();
					this.name = orgRole.getName();
					this.type = UnifyType.ORGROLE.toString();
					this.parentId = orgRole.getOrgId();
				}
			}else if(poObj instanceof UmsDepartment){
				UmsDepartment department = (UmsDepartment)poObj;
				if(department != null){
					this.id = department.getId();
					this.name = department.getName();
					this.type = UnifyType.DEPARTMENT.toString();
					this.parentId = department.getOrgId();
				}
			}else if(poObj instanceof UmsUserDepartmentRelation){
				UmsUserDepartmentRelation uugr = (UmsUserDepartmentRelation)poObj;
				UmsDepartment department = uugr.getDepartment();
				if(department != null){
					this.id = department.getId();
					this.name = department.getName();
					this.type = UnifyType.DEPARTMENT.toString();
					this.parentId = department.getOrgId();
				}
			}else if(poObj instanceof UmsPosition){
				UmsPosition position = (UmsPosition)poObj;
				if(position != null){
					this.id = position.getId();
					this.name = position.getName();
					this.type = UnifyType.POSITION.toString();
					if(position.getDepartmentId() != null){
						this.parentId = position.getDepartmentId();
					}else{
						this.parentId = position.getOrgId();
					}
				}
			}else if(poObj instanceof UmsUserPositionRelation){
				UmsUserPositionRelation uupr = (UmsUserPositionRelation)poObj;
				UmsPosition position = uupr.getPosition();
				if(position != null){
					this.id = position.getId();
					this.name = position.getName();
					this.type = UnifyType.POSITION.toString();
					if(position.getDepartmentId() != null){
						this.parentId = position.getDepartmentId();
					}else{
						this.parentId = position.getOrgId();
					}
				}
			}else if(poObj instanceof UserVO){
				UserVO user = (UserVO)poObj;
				this.id = user.getId() + "_" + user.getParentType() + "_" + user.getParentId();
				this.name = user.getName();
				this.type = user.getType();
				this.parentId = user.getParentId();
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

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}
}