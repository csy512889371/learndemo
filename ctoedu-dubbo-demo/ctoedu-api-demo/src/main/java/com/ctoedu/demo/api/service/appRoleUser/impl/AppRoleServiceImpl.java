package com.ctoedu.demo.api.service.appRoleUser.impl;//package com.ctoedu.demo.api.service.appRoleUser.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ctoedu.common.model.search.Searchable;
//import com.ctoedu.common.vo.ListVO;
//import com.ctoedu.demo.api.constant.FayConstant;
//import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
//import com.ctoedu.demo.api.constant.FaySysRoleConstant;
//import com.ctoedu.demo.api.controller.vo.unify.AppRoleTreeVO;
//import com.ctoedu.demo.api.controller.vo.unify.UserVO;
//import com.ctoedu.demo.api.service.appRoleUser.AppRoleService;
//import com.ctoedu.demo.api.service.role.RoleService;
//import com.ctoedu.demo.api.service.user.UserService;
//import com.ctoedu.demo.api.util.tree.FayTreeUtil;
//import com.ctoedu.demo.facade.app.entity.UmsApp;
//import com.ctoedu.demo.facade.app.service.UmsAppFacade;
//import com.ctoedu.demo.facade.role.entity.UmsRole;
//import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
//import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
//import com.ctoedu.demo.facade.user.entity.UmsUser;
//import com.ctoedu.demo.facade.user.service.UmsUserFacade;
//
//@Service
//public class AppRoleServiceImpl implements AppRoleService {
//	
//	@Reference
//	private UmsAppFacade umsAppFacade;
//
//	@Reference
//	private UmsRoleFacade umsRoleFacade;
//
//	@Reference
//	private UmsUserFacade umsUserFacade;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private RoleService roleService;
//
//	@Override
//	public Object findAppRoleInTreeByLoginUser(String name, String currentUsername,
//			List<String> currentUserRoleSn) {
//		ListVO<AppRoleTreeVO> listVO = null;
//		if(Arrays.asList(FaySysRoleConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
//			listVO = getFromUumsApp(name);
//			listVO.getVoList().addAll(getFromUumsRole(name).getVoList());
//		}else if(orgRoleService.validate(username, FayOrgRoleConstant.ORG_SUPER_MANAGE_ROLE_SN)){
//			listVO = getAppRoleByOrgs(name, currentUsername);
//		}else{
//			listVO = getFromUumsUserApp(name, currentUsername);
//			listVO.getVoList().addAll(getRoleFromUumsUserRole(name, currentUsername).getVoList());
//		}
//		Object data = FayTreeUtil.getTreeInJsonObject(listVO.getVoList());
//		return data;
//	}
//	
//	private ListVO<AppRoleTreeVO> getFromUumsApp(String name){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("name_like", name);
//		List<UmsApp> apps = umsAppFacade.list(searchable);
//		ListVO<AppRoleTreeVO> listVO = new ListVO<>(apps, AppRoleTreeVO.class);
//		return listVO;
//	}
//	
//	private ListVO<AppRoleTreeVO> getFromUumsRole(String name){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("name_like", name);
////		searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
//		List<UmsRole> roles = umsRoleFacade.list(searchable);
//		ListVO<UserVO> users = new ListVO<>(new ArrayList<>(), UserVO.class);
//		for(UmsRole role : roles){
//			users.getVoList().addAll(getUserFromUumsUserRole(name, role.getId()).getVoList());
//		}
//		ListVO<AppRoleTreeVO> listVO = new ListVO<>(roles, AppRoleTreeVO.class);
//		listVO.getVoList().addAll(new ListVO<>(users.getVoList(), AppRoleTreeVO.class).getVoList());
//		return listVO;
//	}
//	
//	private ListVO<AppRoleTreeVO> getAppRoleByOrgs(String name, String username){
//		List<String> userIds = userService.getUserIdsByLoginUser(username);
//		Searchable roleSearchable = Searchable.newSearchable();
//		roleSearchable.addSearchParam("role.name_like", name);
//		roleSearchable.addSearchParam("user.username_eq", username);
//		List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(roleSearchable);
//		ListVO<AppRoleTreeVO> roles = new ListVO<>(uurrs, AppRoleTreeVO.class);
//		
//		List<String> appIds = new ArrayList<>();
//		List<String> roleIds = new ArrayList<>();
//		for(UmsUserRoleRelation uurr : uurrs){
//			UmsRole role = uurr.getRole();
//			if(role != null) {
//				if(role.getAppId() != null) appIds.add(role.getAppId());
//				roleIds.add(role.getId());
//			}
//		}
//		
//		Searchable appSearchable = Searchable.newSearchable();
//		appSearchable.addSearchParam("name_like", name);
//		appSearchable.addSearchParam("id_in", appIds);
//		List<UmsApp> appList = umsAppFacade.list(appSearchable);
//		ListVO<AppRoleTreeVO> apps = new ListVO<>(appList, AppRoleTreeVO.class);
//		
//		Searchable userSearchable = Searchable.newSearchable();
//		userSearchable.addSearchParam("user.nickname_like", name);
//		userSearchable.addSearchParam("user.id_in", userIds);
//		userSearchable.addSearchParam("role.id_in", roleIds);
//		List<UmsUserRoleRelation> uuurrs = umsRoleFacade.listUmsUserRoleRelation(userSearchable);
//		ListVO<UserVO> userLists = new ListVO<>(uuurrs, UserVO.class);
//		ListVO<AppRoleTreeVO> users = new ListVO<>(userLists.getVoList(), AppRoleTreeVO.class);
//		
//		apps.getVoList().addAll(roles.getVoList());
//		apps.getVoList().addAll(users.getVoList());
//		
//		return apps;
//	}
//	
//	private ListVO<AppRoleTreeVO> getFromUumsUserApp(String name, String username){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("name_like", name);
//		UmsUser user = umsUserFacade.findByUsername(username);
//		List<UmsApp> apps = umsAppFacade.list(name, user.getId(), FayConstant.MANAGE_ROLE_SN_PREFIX);
//		ListVO<AppRoleTreeVO> listVO = new ListVO<>(apps, AppRoleTreeVO.class);
//		return listVO;
//	}
//	
//	private ListVO<AppRoleTreeVO> getRoleFromUumsUserRole(String name, String username){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("role.name_like", name);
//		searchable.addSearchParam("user.username_eq", username);
//		List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(searchable);
//		ListVO<UserVO> users = new ListVO<>(new ArrayList<>(), UserVO.class);
//		for(UmsUserRoleRelation uurr : uurrs){
//			UmsRole role = uurr.getRole();
//			if(role != null)
//			users.getVoList().addAll(getUserFromUumsUserRoleByUsername(name, username, role.getId()).getVoList());
//		}
//		ListVO<AppRoleTreeVO> listVO = new ListVO<>(uurrs, AppRoleTreeVO.class);
//		listVO.getVoList().addAll(new ListVO<>(users.getVoList(), AppRoleTreeVO.class).getVoList());
//		return listVO;
//	}
//	
//	private ListVO<UserVO> getUserFromUumsUserRole(String name, String roleId){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("user.nickname_like", name);
//		searchable.addSearchParam("role.id_eq", roleId);
////		searchable.addSearchParam("role.isAvailable_eq", AvailableEnum.TRUE.getValue());
////		searchable.addSearchParam("user.isAvailable_eq", AvailableEnum.TRUE.getValue());
//		List<UmsUserRoleRelation> uurr = umsRoleFacade.listUmsUserRoleRelation(searchable);
//		ListVO<UserVO> listVO = new ListVO<>(uurr, UserVO.class);
//		return listVO;
//	}
//	
//	private ListVO<UserVO> getUserFromUumsUserRoleByUsername(String name, String username, String roleId){
//		Searchable searchable = Searchable.newSearchable();
//		searchable.addSearchParam("user.nickname_like", name);
//		searchable.addSearchParam("user.username_eq", username);
//		searchable.addSearchParam("role.id_eq", roleId);
//		List<UmsUserRoleRelation> uurr = umsRoleFacade.listUmsUserRoleRelation(searchable);
//		ListVO<UserVO> listVO = new ListVO<>(uurr, UserVO.class);
//		return listVO;
//	}
//}
