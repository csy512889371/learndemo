package com.ctoedu.demo.api.controller.vo.org;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.api.util.tree.service.ITreeNode;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;

public class OrgForTreeSelectVO implements BaseVO, ITreeNode {
	
	private String id;
	
	private String name;
	
	private String parentId;
	
	private Integer orderNum; 

	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsOrg){
				UmsOrg org = (UmsOrg)poObj;
				this.id = org.getId();
				this.name = org.getName();
				this.orderNum = org.getOrderNum();
				UmsOrg parent = org.getParent();
				if(parent != null){
					this.parentId = parent.getId();
				}
			}else if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				UmsOrg org = uuor.getOrg();
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
					this.orderNum = org.getOrderNum();
					UmsOrg parent = org.getParent();
					if(parent != null){
						this.parentId = parent.getId();
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

	@Override
	public String getNodeId() {
		return this.id;
	}

	@Override
	public String getNodeName() {
		return this.name;
	}

	@Override
	public String getNodeParentId() {
		return this.parentId;
	}

	@Override
	public Integer getOrderNum() {
		return this.orderNum;
	}
}