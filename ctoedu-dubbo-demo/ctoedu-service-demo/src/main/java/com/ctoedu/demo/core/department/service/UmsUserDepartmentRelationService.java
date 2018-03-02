package com.ctoedu.demo.core.department.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.department.repository.UmsUserDepartmentRelationRepository;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;

/**
 * 
 * service接口
 * Date:2016-11-09 21:33
 * User:chenxiang
 * Version:1.0
 *
 */
@Service("umsUserDepartmentRelationService")
public class UmsUserDepartmentRelationService extends BaseService<UmsUserDepartmentRelation,String>{
	
	@Autowired
	private UmsUserDepartmentRelationRepository umsUserDepartmentRelationRepository;
	
	public void deleteByUserId(String userId){
		umsUserDepartmentRelationRepository.clearUserDepartmentRelationByUserId(userId);
	}
}