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
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.PageVO;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.person.PersonVO;
import com.ctoedu.demo.api.controller.vo.user.OrgUserVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.OrgUserService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;

@Service
public class OrgUserServiceImpl implements OrgUserService {
	
	@Autowired
	private UserService userService;
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrgRoleService orgRoleService;

	@Override
	public PageVO<OrgUserVO> getUser(String nickname, String username, String orgId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<OrgUserVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getOrgUser(nickname, username, orgId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getOrgUserByUnderOrgs(nickname, username, orgId, number, size, currentUsername);
		}else{
			pageVO = getDefaultOrgUserByUnderOrgs(nickname, username, orgId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	@Override
	public PageVO<PersonVO> getPerson(String name, String sn, String orgId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<PersonVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromOrgUser(name, sn, orgId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromOrgUserByUnderOrgs(name, sn, orgId, number, size, currentUsername);
		}else{
			pageVO = getDefaultPersonFromOrgUserByUnderOrgs(name, sn, orgId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	private PageVO<OrgUserVO> getOrgUser(String nickname, String username, String orgId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("org.id_eq", orgId);
		Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
		return new PageVO<>(pageOrgUser, OrgUserVO.class);
	}
	
	private PageVO<PersonVO> getPersonFromOrgUser(String name, String sn, String orgId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("org.id_eq", orgId);
		Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
		return new PageVO<>(pageOrgUser, PersonVO.class);
	}
	
	private PageVO<OrgUserVO> getOrgUserByUnderOrgs(String nickname, String username, String orgId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);
		
		if(orgId == null){
			searchable.addSearchParam("org.id_in", orgIds);
			Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
			return new PageVO<>(pageOrgUser, OrgUserVO.class);
		}else if(orgIds.contains(orgId)){
			searchable.addSearchParam("org.id_eq", orgId);
			Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
			return new PageVO<>(pageOrgUser, OrgUserVO.class);
		}else{
			return new PageVO<>(new ArrayList<>(), OrgUserVO.class);
		}
	}
	
	private PageVO<PersonVO> getPersonFromOrgUserByUnderOrgs(String name, String sn, String orgId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);
		
		if(orgId == null){
			searchable.addSearchParam("org.id_in", orgIds);
			Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
			return new PageVO<>(pageOrgUser, PersonVO.class);
		}else if(orgIds.contains(orgId)){
			searchable.addSearchParam("org.id_eq", orgId);
			Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
			return new PageVO<>(pageOrgUser, PersonVO.class);
		}else{
			return new PageVO<>(new ArrayList<>(), PersonVO.class);
		}
	}
	
	private PageVO<OrgUserVO> getDefaultOrgUserByUnderOrgs(String nickname, String username, String orgId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("org.id_eq", orgId);
		Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
		return new PageVO<>(pageOrgUser, OrgUserVO.class);
	}
	
	private PageVO<PersonVO> getDefaultPersonFromOrgUserByUnderOrgs(String name, String sn, String orgId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", name);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("org.id_eq", orgId);
		Page<UmsUserOrgRelation> pageOrgUser = umsOrgFacade.listUmsUserOrgRelationPage(searchable);
		return new PageVO<>(pageOrgUser, PersonVO.class);
	}
}
