package com.ctoedu.demo.api.service.orgDepartmentPositionUser.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.unify.OrgTreeVO;
import com.ctoedu.demo.api.controller.vo.unify.UserVO;
import com.ctoedu.demo.api.enums.UnifyType;
import com.ctoedu.demo.api.service.orgDepartmentPositionUser.OrgDepartmentPositionUserService;
import com.ctoedu.demo.api.service.orgRole.OrgRoleService;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.api.service.user.UserService;
import com.ctoedu.demo.api.util.tree.FayTreeUtil;
import com.ctoedu.demo.facade.department.entity.UmsDepartment;
import com.ctoedu.demo.facade.department.entity.UmsUserDepartmentRelation;
import com.ctoedu.demo.facade.department.service.UmsDepartmentFacade;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgRelation;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.position.entity.UmsPosition;
import com.ctoedu.demo.facade.position.entity.UmsUserPositionRelation;
import com.ctoedu.demo.facade.position.service.UmsPositionFacade;

@Service
public class OrgDepartmentPositionUserServiceImpl implements OrgDepartmentPositionUserService {
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;
	
	@Reference
	private UmsPositionFacade umsPositionFacade;
	
	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrgRoleService orgRoleService;

	@Override
	public Object findOrgDepartmentPositionUserInTreeByLoginUser(String name, String currentUsername,
			List<String> currentUserRoleSn) {
		ListVO<OrgTreeVO> listVO = null;
		if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(currentUsername) || roleService.validate(currentUsername, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
			listVO = getOrgDepartmentPositionRoleUser(name);
		}else if(orgRoleService.validate(currentUsername, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
			listVO = getOrgDepartmentPositionRoleUserByOrgs(name, currentUsername);
		}else{
			listVO = getDefaultOrgDepartmentPositionRoleUser(name, currentUsername);
		}
		
		Object data = FayTreeUtil.getTreeInJsonObject(listVO.getVoList());
		return data;
	}
	
	private ListVO<OrgTreeVO> getOrgDepartmentPositionRoleUser(String name){
		ListVO<OrgTreeVO> listVO = getFromUumsOrg(name);
		listVO.getVoList().addAll(getFromUumsDepartment(name).getVoList());
		listVO.getVoList().addAll(getFromUumsPosition(name).getVoList());
		listVO.getVoList().addAll(getFromUumsOrgRole(name).getVoList());
		ListVO<UserVO> users = new ListVO<>(new ArrayList<>(), UserVO.class);
		for(OrgTreeVO vo : listVO.getVoList()){
			String type = vo.getType();
			if(type.equals(UnifyType.ORG.toString())){
				users.getVoList().addAll(getUserFromUumsUserOrgRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.DEPARTMENT.toString())){
				users.getVoList().addAll(getUserFromUumsUserDepartmentRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.POSITION.toString())){
				users.getVoList().addAll(getUserFromUumsUserPositionRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.ORGROLE.toString())){
				users.getVoList().addAll(getUserFromUumsUserOrgRoleRelation(null, vo.getId()).getVoList());
			}
		}
		listVO.getVoList().addAll(new ListVO<>(users.getVoList(), OrgTreeVO.class).getVoList());
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getOrgDepartmentPositionRoleUserByOrgs(String name, String currentUsername){
		ListVO<OrgTreeVO> listVO = null;
		
		List<String> orgIds = userService.getOrgIdsForManageByLoginUser(currentUsername);
		
		Searchable orgSearchable = Searchable.newSearchable();
		orgSearchable.addSearchParam("name_like", name);
		orgSearchable.addSearchParam("id_in", orgIds);
		List<UmsOrg> orgs = umsOrgFacade.listUmsOrg(orgSearchable);
		listVO = new ListVO<>(orgs, OrgTreeVO.class);
		
		
		Searchable departmentSearchable = Searchable.newSearchable();
		departmentSearchable.addSearchParam("name_like", name);
		departmentSearchable.addSearchParam("orgId_in", orgIds);
		List<UmsDepartment> departments = umsDepartmentFacade.list(departmentSearchable);
		List<String> departmentIds = new ArrayList<>();
		for(UmsDepartment department : departments){
			departmentIds.add(department.getId());
		}
		listVO.getVoList().addAll(new ListVO<>(departments, OrgTreeVO.class).getVoList());
		
		Searchable positionSearchable = Searchable.newSearchable();
		positionSearchable.addSearchParam("name_like", name);
		positionSearchable.addSearchParam("orgId_in", orgIds);
		List<UmsPosition> positions = umsPositionFacade.list(positionSearchable);
		List<String> positionIds = new ArrayList<>();
		for(UmsPosition position : positions){
			positionIds.add(position.getId());
		}
		listVO.getVoList().addAll(new ListVO<>(positions, OrgTreeVO.class).getVoList());
		
		Searchable roleSearchable = Searchable.newSearchable();
		roleSearchable.addSearchParam("name_like", name);
		roleSearchable.addSearchParam("orgId_in", orgIds);
		List<UmsOrgRole> roles = umsOrgRoleFacade.list(roleSearchable);
		List<String> roleIds = new ArrayList<>();
		for(UmsOrgRole role : roles){
			roleIds.add(role.getId());
		}
		listVO.getVoList().addAll(new ListVO<>(roles, OrgTreeVO.class).getVoList());
		
		Searchable orgUserSearchable = Searchable.newSearchable();
		orgUserSearchable.addSearchParam("user.nickname_like", name);
		orgUserSearchable.addSearchParam("org.id_in", orgIds);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(orgUserSearchable);
		ListVO<UserVO> users = new ListVO<>(uuors, UserVO.class);
		
		Searchable departmentUserSearchable = Searchable.newSearchable();
		departmentUserSearchable.addSearchParam("user.nickname_like", name);
		departmentUserSearchable.addSearchParam("department.id_in", departmentIds);
		List<UmsUserDepartmentRelation> uugrs = umsDepartmentFacade.listUmsUserDepartmentRelation(departmentUserSearchable);
		users.getVoList().addAll(new ListVO<>(uugrs, UserVO.class).getVoList());
		
		Searchable positionUserSearchable = Searchable.newSearchable();
		positionUserSearchable.addSearchParam("user.nickname_like", name);
		positionUserSearchable.addSearchParam("position.id_in", positionIds);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(positionUserSearchable);
		users.getVoList().addAll(new ListVO<>(uuprs, UserVO.class).getVoList());
		
		Searchable roleUserSearchable = Searchable.newSearchable();
		roleUserSearchable.addSearchParam("user.nickname_like", name);
		roleUserSearchable.addSearchParam("role.id_in", roleIds);
		List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(roleUserSearchable);
		users.getVoList().addAll(new ListVO<>(uuorrs, UserVO.class).getVoList());
		
		listVO.getVoList().addAll(new ListVO<>(users.getVoList(), OrgTreeVO.class).getVoList());
		
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getDefaultOrgDepartmentPositionRoleUser(String name, String currentUsername){
		ListVO<OrgTreeVO> listVO = getOrgFromUumsUserOrgRelation(name, currentUsername);
		listVO.getVoList().addAll(getDepartmentFromUumsUserDepartmentRelation(name, currentUsername).getVoList());
		listVO.getVoList().addAll(getPositionFromUumsUserPositionRelation(name, currentUsername).getVoList());
		listVO.getVoList().addAll(getOrgRoleFromUumsUserOrgRoleRelation(name, currentUsername).getVoList());
		ListVO<UserVO> users = new ListVO<>(new ArrayList<>(), UserVO.class);
		for(OrgTreeVO vo : listVO.getVoList()){
			String type = vo.getType();
			if(type.equals(UnifyType.ORG.toString())){
				users.getVoList().addAll(getUserFromUumsUserOrgRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.DEPARTMENT.toString())){
				users.getVoList().addAll(getUserFromUumsUserDepartmentRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.POSITION.toString())){
				users.getVoList().addAll(getUserFromUumsUserPositionRelation(null, vo.getId()).getVoList());
			}else if(type.equals(UnifyType.ORGROLE.toString())){
				users.getVoList().addAll(getUserFromUumsUserOrgRoleRelation(null, vo.getId()).getVoList());
			}
		}
		listVO.getVoList().addAll(new ListVO<>(users.getVoList(), OrgTreeVO.class).getVoList());
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getFromUumsOrg(String name){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "orderNum");
		searchable.addSearchParam("name_like", name);
		List<UmsOrg> listOrg = umsOrgFacade.listUmsOrg(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(listOrg, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getFromUumsDepartment(String name){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("name_like", name);
		List<UmsDepartment> listOrg = umsDepartmentFacade.list(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(listOrg, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getFromUumsPosition(String name){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("name_like", name);
		List<UmsPosition> listOrg = umsPositionFacade.list(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(listOrg, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getFromUumsOrgRole(String name){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("name_like", name);
		List<UmsOrgRole> listOrg = umsOrgRoleFacade.list(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(listOrg, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getOrgFromUumsUserOrgRelation(String name, String username){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "org.orderNum");
		searchable.addSearchParam("org.name_like", name);
		searchable.addSearchParam("user.username_eq", username);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(uuors, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getDepartmentFromUumsUserDepartmentRelation(String name, String username){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("department.name_like", name);
		searchable.addSearchParam("user.username_eq", username);
		List<UmsUserDepartmentRelation> uuors = umsDepartmentFacade.listUmsUserDepartmentRelation(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(uuors, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<OrgTreeVO> getPositionFromUumsUserPositionRelation(String name, String username){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("position.name_like", name);
		searchable.addSearchParam("user.username_eq", username);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(uuprs, OrgTreeVO.class);
		return listVO;
	}

	private ListVO<OrgTreeVO> getOrgRoleFromUumsUserOrgRoleRelation(String name, String username){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("role.name_like", name);
		searchable.addSearchParam("user.username_eq", username);
		List<UmsUserOrgRoleRelation> uuprs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
		ListVO<OrgTreeVO> listVO = new ListVO<>(uuprs, OrgTreeVO.class);
		return listVO;
	}
	
	private ListVO<UserVO> getUserFromUumsUserOrgRelation(String name, String orgId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", name);
		searchable.addSearchParam("org.id_eq", orgId);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(searchable);
		ListVO<UserVO> listVO = new ListVO<>(uuors, UserVO.class);
		return listVO;
	}
	
	private ListVO<UserVO> getUserFromUumsUserDepartmentRelation(String name, String departmentId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", name);
		searchable.addSearchParam("department.id_eq", departmentId);
		List<UmsUserDepartmentRelation> uuors = umsDepartmentFacade.listUmsUserDepartmentRelation(searchable);
		ListVO<UserVO> listVO = new ListVO<>(uuors, UserVO.class);
		return listVO;
	}
	
	private ListVO<UserVO> getUserFromUumsUserPositionRelation(String name, String positionId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", name);
		searchable.addSearchParam("position.id_eq", positionId);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(searchable);
		ListVO<UserVO> listVO = new ListVO<>(uuprs, UserVO.class);
		return listVO;
	}
	
	private ListVO<UserVO> getUserFromUumsUserOrgRoleRelation(String name, String roleId){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.nickname_like", name);
		searchable.addSearchParam("role.id_eq", roleId);
		List<UmsUserOrgRoleRelation> uuprs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
		ListVO<UserVO> listVO = new ListVO<>(uuprs, UserVO.class);
		return listVO;
	}
}
