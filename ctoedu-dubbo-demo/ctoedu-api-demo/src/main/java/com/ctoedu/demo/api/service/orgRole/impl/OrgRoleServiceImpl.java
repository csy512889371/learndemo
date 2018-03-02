package com.ctoedu.demo.api.service.orgRole.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;

@Service
public class OrgRoleServiceImpl implements OrgRoleService {

	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;
	
	@Override
	public boolean validate(String username, String sn) {
		if(username == null || sn == null){
			return false;
		}
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.username_eq", username);
		searchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		searchable.addSearchParam("role.isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
		for(int i = 0; i < uuorrs.size(); i++){
			UmsUserOrgRoleRelation uuorr = uuorrs.get(i);
			UmsOrgRole role = uuorr.getRole();
			if(role != null && sn.startsWith(FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN_PREFIX)) return true;
		}
		return false;
	}

}
