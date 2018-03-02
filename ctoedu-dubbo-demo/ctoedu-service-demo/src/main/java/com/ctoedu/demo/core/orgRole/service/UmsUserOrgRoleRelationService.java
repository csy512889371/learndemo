package com.ctoedu.demo.core.orgRole.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.orgRole.repository.UmsUserOrgRoleRelationRepository;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsUserOrgRoleRelationService")
public class UmsUserOrgRoleRelationService extends BaseService<UmsUserOrgRoleRelation,String>{
	
	@Autowired
	private UmsUserOrgRoleRelationRepository umsUserOrgRoleRelationRepository;
	
	public void deleteByUserId(String userId){
		umsUserOrgRoleRelationRepository.clearUserRoleRelationByUserId(userId);
	}
}