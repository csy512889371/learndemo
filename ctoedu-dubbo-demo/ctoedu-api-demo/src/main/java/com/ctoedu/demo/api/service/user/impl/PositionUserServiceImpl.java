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
import com.ctoedu.demo.api.controller.vo.user.PositionUserVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.PositionUserService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;

@Service
public class PositionUserServiceImpl implements PositionUserService {
	
	@Autowired
	private UserService userService;
	
	@Reference
	private UmsPositionFacade umsPositionFacade;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private OrgRoleService orgRoleService;

	@Override
	public PageVO<PositionUserVO> getUser(String nickname, String username, String positionId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<PositionUserVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getPositionUser(nickname, username, positionId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getPositionUserByUnderOrgs(nickname, username, positionId, number, size, currentUsername);
		}else{
			pageVO = getDefaultPositionUser(nickname, username, positionId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	@Override
	public PageVO<PersonVO> getPerson(String name, String sn, String positionId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<PersonVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromPositionUser(name, sn, positionId, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonFromPositionUserByUnderOrgs(name, sn, positionId, number, size, currentUsername);
		}else{
			pageVO = getDefaultPersonFromPositionUser(name, sn, positionId, number, size, currentUsername);
		}
		return pageVO;
	}
	
	private PageVO<PositionUserVO> getPositionUser(String nickname, String username, String positionId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("position.id_eq", positionId);
		Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
		return new PageVO<>(pagePositionUser, PositionUserVO.class);
	}
	
	private PageVO<PersonVO> getPersonFromPositionUser(String name, String sn, String positionId, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("position.id_eq", positionId);
		Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
		return new PageVO<>(pagePositionUser, PersonVO.class);
	}
	
	private PageVO<PositionUserVO> getPositionUserByUnderOrgs(String nickname, String username, String positionId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);

		Searchable positionSearchable = Searchable.newSearchable();
		positionSearchable.addSearchParam("orgId_in", orgIds);
		positionSearchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsPosition> userPositionList = umsPositionFacade.list(positionSearchable);
		for(UmsPosition position : userPositionList){
			if(position.getId().equals(positionId)){
				searchable.addSearchParam("position.id_eq", positionId);
				Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
				return new PageVO<>(pagePositionUser, PositionUserVO.class);
			}
		}
		return new PageVO<>(new ArrayList<>(), PositionUserVO.class);
	}
	
	private PageVO<PersonVO> getPersonFromPositionUserByUnderOrgs(String name, String sn, String positionId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);

		Searchable positionSearchable = Searchable.newSearchable();
		positionSearchable.addSearchParam("orgId_in", orgIds);
		positionSearchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
		List<UmsPosition> userPositionList = umsPositionFacade.list(positionSearchable);
		for(UmsPosition position : userPositionList){
			if(position.getId().equals(positionId)){
				searchable.addSearchParam("position.id_eq", positionId);
				Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
				return new PageVO<>(pagePositionUser, PersonVO.class);
			}
		}
		return new PageVO<>(new ArrayList<>(), PersonVO.class);
	}
	
	private PageVO<PositionUserVO> getDefaultPositionUser(String nickname, String username, String positionId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("position.id_eq", positionId);
		Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
		return new PageVO<>(pagePositionUser, PositionUserVO.class);
	}
	
	private PageVO<PersonVO> getDefaultPersonFromPositionUser(String name, String sn, String positionId, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("user.person.name_like", name);
		searchable.addSearchParam("user.person.sn_like", sn);
		searchable.addSearchParam("user.username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "user.createDate");
		searchable.addSort(sort);
		searchable.addSearchParam("position.id_eq", positionId);
		Page<UmsUserPositionRelation> pagePositionUser = umsPositionFacade.listUmsUserPositionRelationPage(searchable);
		return new PageVO<>(pagePositionUser, PersonVO.class);
	}
}
