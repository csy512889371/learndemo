package com.ctoedu.demo.api.controller.vo.org;

import com.ctoedu.common.vo.BaseVO;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;

public class OrgForSelectVO implements BaseVO {
	
	private String id;
	
	private String name;

	@Override
	public void convertPOToVO(Object poObj) {
		
		if(poObj != null){
			if(poObj instanceof UmsOrg){
				UmsOrg org = (UmsOrg)poObj;
				this.id = org.getId();
				this.name = org.getName();
			}else if(poObj instanceof UmsUserOrgRelation){
				UmsUserOrgRelation uuor = (UmsUserOrgRelation)poObj;
				UmsOrg org = uuor.getOrg();
				if(org != null){
					this.id = org.getId();
					this.name = org.getName();
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
}