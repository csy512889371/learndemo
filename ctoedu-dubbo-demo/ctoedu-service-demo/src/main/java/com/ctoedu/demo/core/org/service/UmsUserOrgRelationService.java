package com.ctoedu.demo.core.org.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.org.repository.UmsUserOrgRelationRepository;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsUserOrgRelationService")
public class UmsUserOrgRelationService extends BaseService<UmsUserOrgRelation,String>{
	
	@Autowired
	private UmsUserOrgRelationRepository umsUserOrgRelationRepository;
	
	public void deleteByUserId(String userId){
		umsUserOrgRelationRepository.clearUserOrgRelationByUserId(userId);
	}
}