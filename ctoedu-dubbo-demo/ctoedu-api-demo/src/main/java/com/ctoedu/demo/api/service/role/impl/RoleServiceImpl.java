package com.ctoedu.demo.api.service.role.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;

@Service
public class RoleServiceImpl implements RoleService {

	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Override
	public boolean validate(String username, String sn) {
		if(username == null || sn == null){
			return false;
		}
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.username_eq", username);
		searchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		searchable.addSearchParam("role.isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(searchable);
		for(int i = 0; i < uurrs.size(); i++){
			UmsUserRoleRelation uurr = uurrs.get(i);
			UmsRole role = uurr.getRole();
			if(role != null && sn.equals(role.getSn())) return true;
		}
		return false;
	}
}
