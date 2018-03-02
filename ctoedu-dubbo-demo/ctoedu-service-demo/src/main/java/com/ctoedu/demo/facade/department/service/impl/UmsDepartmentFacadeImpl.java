package com.ctoedu.demo.facade.department.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.dubbo.config.annotation.Service;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.department.service.UmsDepartmentService;
import com.ctoedu.demo.core.department.service.UmsUserDepartmentRelationService;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.department.service.UmsDepartmentFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service(retries=2)
public class UmsDepartmentFacadeImpl implements UmsDepartmentFacade {
	@Autowired
	private UmsDepartmentService umsDepartmentService;
	
	@Autowired
	private UmsUserDepartmentRelationService umsUserDepartmentRelationService;
	
	public UmsDepartment create(UmsDepartment umsDepartment){
		return umsDepartmentService.save(umsDepartment);
	}

	@Override
	public UmsDepartment update(UmsDepartment umsDepartment) {
		return umsDepartmentService.update(umsDepartment);
	}

	@Override
	public void delete(String... departmentIds) {
		umsDepartmentService.deleteDepartment(departmentIds);
	}
	
	@Override
	public UmsDepartment getById(String departmentId){
		return umsDepartmentService.findOne(departmentId);
	}

	@Override
	public Page<UmsDepartment> listPage(Searchable searchable) {
		return umsDepartmentService.findAll(searchable);
	}

	@Override
	public List<UmsDepartment> list(Searchable searchable) {
		return umsDepartmentService.findAllWithNoPageNoSort(searchable);
	}

	@Override
	public Set<String> findDepartmentIds(String userId, Short isAvailable) {
		return umsDepartmentService.findDepartmentIds(userId, isAvailable);
	}

	@Override
	public void buildUserDepartmentRelation(String departmentId, List<String> userIds) {
		umsDepartmentService.buildUserDepartmentRelation(departmentId, userIds);
	}
	
	@Override
	public void clearUserDepartmentRelation(String departmentId, List<String> userIds){
		umsDepartmentService.clearUserDepartmentRelation(departmentId, userIds);
	}

	public UmsDepartment updAvailable(String id, Short isAvailable) {
		UmsDepartment department = umsDepartmentService.findOne(id);
		department.setIsAvailable(isAvailable);
		department = umsDepartmentService.update(department);
		return department;
	}

	@Override
	public List<String> findUserIdByDepartmentId(String departmentId) {
		return umsDepartmentService.findUserIdByDepartmentId(departmentId);
	}

	@Override
	public Page<UmsUser> findUserByDepartmentId(String departmentId, String nickname, String username, Pageable page) {
		return umsDepartmentService.findUserByDepartmentId(departmentId, nickname, username, page);
	}

	@Override
	public Page<UmsUser> findNotUserByDepartmentId(String departmentId, String nickname, String username, Pageable page) {
		return umsDepartmentService.findNotUserByDepartmentId(departmentId, nickname, username, page);
	}
	
	@Override
	public Page<UmsUser> findUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page) {
		return umsDepartmentService.findUserByDepartmentIdAndNameAndSn(departmentId, name, sn, page);
	}

	@Override
	public Page<UmsUser> findNotUserByDepartmentIdAndNameAndSn(String departmentId, String name, String sn, Pageable page) {
		return umsDepartmentService.findNotUserByDepartmentIdAndNameAndSn(departmentId, name, sn, page);
	}

	@Override
	public void deleteUserDepartmentRelation(String departmentId, List<String> userIds) {
		umsDepartmentService.deleteUserDepartmentRelation(departmentId, userIds);
	}

	@Override
	public Page<UmsUserDepartmentRelation> listUmsUserDepartmentRelationPage(Searchable searchable) {
		return umsUserDepartmentRelationService.findAll(searchable);
	}

	@Override
	public List<UmsUserDepartmentRelation> listUmsUserDepartmentRelation(Searchable searchable) {
		return umsUserDepartmentRelationService.findAllWithSort(searchable);
	}
}
