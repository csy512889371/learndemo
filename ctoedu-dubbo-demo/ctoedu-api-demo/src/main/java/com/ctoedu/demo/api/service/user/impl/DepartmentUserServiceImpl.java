package com.ctoedu.demo.api.service.user.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.person.PersonVO;
import com.ctoedu.demo.api.controller.vo.user.DepartmentUserVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.DepartmentUserService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.department.service.UmsDepartmentFacade;

@Service
public class DepartmentUserServiceImpl implements DepartmentUserService {
	
	@Autowired
	private UserService userService;
	
	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private OrgRoleService orgRoleService;

	@Override
	public PageVO<DepartmentUserVO> getUser(String nickname, String username, String departmentId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<DepartmentUserVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getDepartmentUser(nickname, username, departmentId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getDepartmentUserByUnderOrgs(nickname, username, departmentId, number, size, currentUsername);
		}else{
			pageVO = getDefaultDepartmentUserByUnderOrgs(nickname, username, departmentId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	@Override
	public PageVO<PersonVO> getPerson(String name, String sn, String departmentId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<PersonVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromDepartmentUser(name, sn, departmentId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromDepartmentUserByUnderOrgs(name, sn, departmentId, number, size, currentUsername);
		}else{
			pageVO = getDefaultPersonFromDepartmentUserByUnderOrgs(name, sn, departmentId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	private PageVO<DepartmentUserVO> getDepartmentUser(String nickname, String username, String departmentId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("department.id_eq", departmentId);
		Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
		return new PageVO<>(pageDepartmentUser, DepartmentUserVO.class);
	}
	
	private PageVO<PersonVO> getPersonFromDepartmentUser(String name, String sn, String departmentId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("department.id_eq", departmentId);
		Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
		return new PageVO<>(pageDepartmentUser, PersonVO.class);
	}
	
	private PageVO<DepartmentUserVO> getDepartmentUserByUnderOrgs(String nickname, String username, String departmentId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);
		
		Searchable departmentSearchable = Searchable.newSearchable();
		departmentSearchable.addSearchParam("orgId_in", orgIds);
		departmentSearchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsDepartment> userDepartmentList = umsDepartmentFacade.list(departmentSearchable);
		for(UmsDepartment department : userDepartmentList){
			if(department.getId().equals(departmentId)){
				searchable.addSearchParam("department.id_eq", departmentId);
				Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
				return new PageVO<>(pageDepartmentUser, DepartmentUserVO.class);
			}
		}
		return new PageVO<>(new ArrayList<>(), DepartmentUserVO.class);
	}
	
	private PageVO<PersonVO> getPersonFromDepartmentUserByUnderOrgs(String name, String sn, String departmentId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);
		
		Searchable departmentSearchable = Searchable.newSearchable();
		departmentSearchable.addSearchParam("orgId_in", orgIds);
		departmentSearchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsDepartment> userDepartmentList = umsDepartmentFacade.list(departmentSearchable);
		for(UmsDepartment department : userDepartmentList){
			if(department.getId().equals(departmentId)){
				searchable.addSearchParam("department.id_eq", departmentId);
				Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
				return new PageVO<>(pageDepartmentUser, PersonVO.class);
			}
		}
		return new PageVO<>(new ArrayList<>(), PersonVO.class);
	}
	
	private PageVO<DepartmentUserVO> getDefaultDepartmentUserByUnderOrgs(String nickname, String username, String departmentId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("department.id_eq", departmentId);
		Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
		return new PageVO<>(pageDepartmentUser, DepartmentUserVO.class);
	}
	
	private PageVO<PersonVO> getDefaultPersonFromDepartmentUserByUnderOrgs(String name, String sn, String departmentId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("department.id_eq", departmentId);
		Page<UmsUserDepartmentRelation> pageDepartmentUser = umsDepartmentFacade.listUmsUserDepartmentRelationPage(searchable);
		return new PageVO<>(pageDepartmentUser, PersonVO.class);
	}
}
