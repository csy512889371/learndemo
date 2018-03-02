package com.ctoedu.demo.api.controller.vo.org;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.util.tree.service.IFayTreeNode;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.entity.enums.OrgManagerTypeEnum;

public class OrgVO implements BaseVO,IFayTreeNode {
	
	private String id;
	
	/**
	 * 组织机构的名称
	 */
	private String name;
	/**
	 * 组织机构所属类型的id
	 */
//	private String typeId;
	/**
	 * 组织机构所属类型的名称
	 */
	private String typeName;
	/**
	 * 组织机构的排序号
	 */
	private Integer orderNum;
	/**
	 * 组织机构的父亲组织
	 */
//	private UmsOrg parent;
	/**
	 * 管理类型
	 */
	private Short managerType;

	private String managerTypeS;
	/**
	 * 组织机构的地址
	 */
	private String address;
	/**
	 * 组织机构的电话
	 */
	private String phone;
	
	/**
	 * 是否可用
	 */
	private Short isAvailable;
	
	private String parentId;
	
	private String parentName;
	
	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsOrg){
				UmsOrg org = (UmsOrg)poObj;
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
					this.typeName = org.getTypeName();
					this.orderNum = org.getOrderNum();
					this.managerType = org.getManagerType();
					this.managerTypeS = OrgManagerTypeEnum.getInfo(org.getManagerType());
					this.address = org.getAddress();
					this.phone = org.getPhone();
					this.isAvailable = org.getIsAvailable();
					UmsOrg parent = org.getParent();
					if(parent != null){
						this.parentId = parent.getId();
						this.parentName = parent.getName();
					}
				}
			}else if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				UmsOrg org = uuor.getOrg();
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
					this.typeName = org.getTypeName();
					this.orderNum = org.getOrderNum();
					this.managerType = org.getManagerType();
					this.managerTypeS = OrgManagerTypeEnum.getInfo(org.getManagerType());
					this.address = org.getAddress();
					this.phone = org.getPhone();
					this.isAvailable = org.getIsAvailable();
					UmsOrg parent = org.getParent();
					if(parent != null){
						this.parentId = parent.getId();
						this.parentName = parent.getName();
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Short getManagerType() {
		return managerType;
	}

	public void setManagerType(Short managerType) {
		this.managerType = managerType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Short getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getManagerTypeS() {
		return managerTypeS;
	}

	public void setManagerTypeS(String managerTypeS) {
		this.managerTypeS = managerTypeS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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