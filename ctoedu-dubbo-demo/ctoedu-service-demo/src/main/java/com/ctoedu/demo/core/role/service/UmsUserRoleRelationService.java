package com.ctoedu.demo.core.role.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.role.repository.UmsUserRoleRelationRepository;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsUserRoleRelationService")
public class UmsUserRoleRelationService extends BaseService<UmsUserRoleRelation,String>{
	
	@Autowired
	private UmsUserRoleRelationRepository umsUserRoleRelationRepository;
	
	public void deleteByAppIds(List<String> appIds){
		umsUserRoleRelationRepository.deleteByAppIds(appIds);
	}

	public void deleteByRoleIds(List<String> roleIds){
		umsUserRoleRelationRepository.deleteByRoleIds(roleIds);
	}
	
	public void deleteByUserId(String userId){
		umsUserRoleRelationRepository.clearUserRoleRelationByUserId(userId);
	}
}