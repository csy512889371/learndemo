package com.ctoedu.demo.api.service.user.impl;//package com.ctoedu.demo.api.service.user.impl;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ctoedu.common.model.search.Searchable;
//import com.ctoedu.common.vo.PageVO;
//import com.ctoedu.demo.api.constant.FayConstant;
//import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
//import com.ctoedu.demo.api.constant.FaySysRoleConstant;
//import com.ctoedu.demo.api.controller.vo.user.RoleUserVO;
//import com.ctoedu.demo.api.service.role.RoleService;
//import com.ctoedu.demo.api.service.user.OrgRoleUserService;
//import com.ctoedu.demo.api.service.user.UserService;
//import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
//import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
//import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
//
//@Service
//public class OrgRoleUserServiceImpl implements OrgRoleUserService {
//	
//	@Autowired
//	private UserService userService;
//	
//	@Reference
//	private UmsOrgFacade umsOrgFacade;
//	
//	@Reference
//	private UmsRoleFacade umsRoleFacade;
//	
//	@Autowired
//	private RoleService roleService;
//
//	@Override
//	public PageVO<RoleUserVO> getUser(String nickname, String username, String orgId, String roleId, int number, int size, String currentUsername, List<String> currentUserRoleSn){
//		PageVO<RoleUserVO> pageVO = null;
//		if(Arrays.asList(FaySysRoleConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN) || orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
//			pageVO = getRoleUserWithUserIds(roleId, userService.getUserIdsByOrg(nickname, username, orgId), number, size);
//		}else{
//			pageVO = getDefaultRoleUserByUnderOrgs(nickname, username, roleId, number, size, currentUsername);
//		}
//		return pageVO;
//	}
//	
//	private PageVO<RoleUserVO> getRoleUserWithUserIds(String roleId, List<String> userIds, int number, int size){
//		Pageable page = new PageRequest(number, size);
//		Searchable searchable = Searchable.newSearchable();
//		searchable.setPage(page);
//		searchable.addSearchParam("user.id_in", userIds);
//		Sort sort = new Sort(Direction.DESC, "user.createDate");
//		searchable.addSort(sort);
//		searchable.addSearchParam("role.id_eq", roleId);
//		Page<UmsUserRoleRelation> pageRoleUser = umsRoleFacade.listUmsUserRoleRelationPage(searchable);
//		return new PageVO<>(pageRoleUser, RoleUserVO.class);
//	}
//	
//	private PageVO<RoleUserVO> getDefaultRoleUserByUnderOrgs(String nickname, String username, String roleId, int number, int size, String currentUsername){
//		Pageable page = new PageRequest(number, size);
//		Searchable searchable = Searchable.newSearchable();
//		searchable.setPage(page);
//		searchable.addSearchParam("user.nickname_like", nickname);
//		searchable.addSearchParam("user.username_like", username);
//		searchable.addSearchParam("user.username_eq", currentUsername);
//		Sort sort = new Sort(Direction.DESC, "user.createDate");
//		searchable.addSort(sort);
//		searchable.addSearchParam("role.id_eq", roleId);
//		Page<UmsUserRoleRelation> pageRoleUser = umsRoleFacade.listUmsUserRoleRelationPage(searchable);
//		return new PageVO<>(pageRoleUser, RoleUserVO.class);
//	}
//}
