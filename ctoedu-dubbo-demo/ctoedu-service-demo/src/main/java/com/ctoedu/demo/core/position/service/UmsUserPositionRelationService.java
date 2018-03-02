package com.ctoedu.demo.core.position.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.position.repository.UmsUserPositionRelationRepository;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsUserPositionRelationService")
public class UmsUserPositionRelationService extends BaseService<UmsUserPositionRelation,String>{
	
	@Autowired
	private UmsUserPositionRelationRepository umsUserPositionRelationRepository;
	
	public void deleteByUserId(String userId){
		umsUserPositionRelationRepository.clearUserPositionRelationByUserId(userId);
	}
}