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
import com.ctoedu.demo.api.controller.vo.user.UserVO;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.department.service.UmsDepartmentFacade;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Service
public class UserServiceImpl implements UserService {
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;
	
	@Reference
	private UmsPositionFacade umsPositionFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private OrgRoleService orgRoleService;
	
	@Override
	public List<String> getOrgIdsForManageByLoginUser(String username){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("role.sn", FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN);
		searchable.addSearchParam("role.isAvailable", AvailableEnum.TRUE.getValue());
		searchable.addSearchParam("user.username_eq", username);
		searchable.addSearchParam("user.isAvailable", AvailableEnum.TRUE.getValue());
		List<UmsUserOrgRoleRelation> list = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
		List<String> orgIds = new ArrayList<>();
		for(UmsUserOrgRoleRelation uuorr : list){
			UmsOrgRole role = uuorr.getRole();
			if(role != null) orgIds.add(role.getOrgId());
		}
		return orgIds;
	}
	
	@Override
	public List<String> getUserIdsForManageByLoginUser(String username){
		
		List<String> orgIds = getOrgIdsForManageByLoginUser(username);
		
		List<String> userIds = new ArrayList<>();
		
		Searchable orgSearchable = Searchable.newSearchable();
		orgSearchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		orgSearchable.addSearchParam("org.id_in", orgIds);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(orgSearchable);
		for(UmsUserOrgRelation uuor : uuors){
			UmsUser user = uuor.getUser();
			if(user != null) userIds.add(user.getId());
		}
		
		Searchable departmentSearchable = Searchable.newSearchable();
		departmentSearchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		departmentSearchable.addSearchParam("department.orgId_in", orgIds);
		List<UmsUserDepartmentRelation> uugrs = umsDepartmentFacade.listUmsUserDepartmentRelation(departmentSearchable);
		for(UmsUserDepartmentRelation uugr : uugrs){
			UmsUser user = uugr.getUser();
			if(user != null) userIds.add(user.getId());
		}
		
		Searchable positionSearchable = Searchable.newSearchable();
		positionSearchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		positionSearchable.addSearchParam("position.orgId_in", orgIds);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(positionSearchable);
		for(UmsUserPositionRelation uupr : uuprs){
			UmsUser user = uupr.getUser();
			if(user != null) userIds.add(user.getId());
		}
		
		Searchable orgRoleSearchable = Searchable.newSearchable();
		orgRoleSearchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
		orgRoleSearchable.addSearchParam("role.orgId_in", orgIds);
		List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(orgRoleSearchable);
		for(UmsUserOrgRoleRelation uuorr : uuorrs){
			UmsUser user = uuorr.getUser();
			if(user != null) userIds.add(user.getId());
		}
		
		return userIds;
	}

	@Override
	public PageVO<UserVO> getUserByLoginUser(String nickname, String username, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<UserVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN) || roleService.validate(currentUsername, FaySysRoleConstant.MANAGE_ROLE_SN)){
			pageVO = getUser(nickname, username, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getUserByUnderOrgs(nickname, username, number, size, currentUsername);
		}else{
			pageVO = getDefultUser(nickname, username, number, size, currentUsername);
		}
		return pageVO;
	}
	
	@Override
	public PageVO<PersonVO> getPersonByLoginUser(String name, String sn, int number, int size, String currentUsername, List<String> currentUserRoleSn){
		PageVO<PersonVO> pageVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			pageVO = getPerson(name, sn, number, size);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			pageVO = getPersonByUnderOrgs(name, sn, number, size, currentUsername);
		}else{
			pageVO = getDefultPerson(name, sn, number, size, currentUsername);
		}
		return pageVO;
	}
	
	private PageVO<UserVO> getUser(String nickname, String username, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("nickname_like", nickname);
		searchable.addSearchParam("username_like", username);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, UserVO.class);
	}
	
	private PageVO<PersonVO> getPerson(String name, String sn, int number, int size){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("person.name_like", name);
		searchable.addSearchParam("person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, PersonVO.class);
	}
	
	private PageVO<UserVO> getUserByUnderOrgs(String nickname, String username, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("nickname_like", nickname);
		searchable.addSearchParam("username_like", username);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		
		List<String> userIds = getUserIdsForManageByLoginUser(currentUsername);
		searchable.addSearchParam("id_in", userIds);
		
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, UserVO.class);
	}
	
	private PageVO<PersonVO> getPersonByUnderOrgs(String name, String sn, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("person.name_like", name);
		searchable.addSearchParam("person.sn_like", sn);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		
		List<String> userIds = getUserIdsForManageByLoginUser(currentUsername);
		searchable.addSearchParam("id_in", userIds);
		
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, PersonVO.class);
	}
	
	private PageVO<UserVO> getDefultUser(String nickname, String username, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("nickname_like", nickname);
		searchable.addSearchParam("username_like", username);
		searchable.addSearchParam("username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, UserVO.class);
	}
	
	private PageVO<PersonVO> getDefultPerson(String name, String sn, int number, int size, String currentUsername){
		Pageable page = new PageRequest(number, size);
		Searchable searchable = Searchable.newSearchable();
		searchable.setPage(page);
		searchable.addSearchParam("person.name_like", name);
		searchable.addSearchParam("person.sn_like", sn);
		searchable.addSearchParam("username_eq", currentUsername);
		Sort sort = new Sort(Direction.DESC, "createDate");
		searchable.addSort(sort);
		Page<UmsUser> pageUser = umsUserFacade.listPage(searchable);
		return new PageVO<>(pageUser, PersonVO.class);
	}
	
	@Override
	public List<String> getUserIdsByOrg(String nickname, String username, String orgId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("org.id_eq", orgId);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(searchable);
		List<String> userIds = new ArrayList<>();
		for(UmsUserOrgRelation uugr : uuors){
			if(uugr.getUser() != null)
			userIds.add(uugr.getUser().getId());
		}
		return userIds;
	}
	
	@Override
	public List<String> getUserIdsByDepartment(String nickname, String username, String departmentId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("department.id_eq", departmentId);
		List<UmsUserDepartmentRelation> uugrs = umsDepartmentFacade.listUmsUserDepartmentRelation(searchable);
		List<String> userIds = new ArrayList<>();
		for(UmsUserDepartmentRelation uugr : uugrs){
			if(uugr.getUser() != null)
			userIds.add(uugr.getUser().getId());
		}
		return userIds;
	}
	
	@Override
	public List<String> getUserIdsByPosition(String nickname, String username, String positionId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", nickname);
		searchable.addSearchParam("user.username_like", username);
		searchable.addSearchParam("position.id_eq", positionId);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(searchable);
		List<String> userIds = new ArrayList<>();
		for(UmsUserPositionRelation uupr : uuprs){
			if(uupr.getUser() != null)
			userIds.add(uupr.getUser().getId());
		}
		return userIds;
	}
}
