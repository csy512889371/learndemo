package com.ctoedu.demo.api.controller.open;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FayOrgRoleConstant;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
import com.ctoedu.demo.facade.org.entity.UmsOrg;
import com.ctoedu.demo.facade.org.service.UmsOrgFacade;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.service.UmsOrgRoleFacade;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsPerson;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsPersonFacade;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/open/init")
public class OpenInitController {
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Reference
	private UmsRoleFacade umsRoleFacade;
	
	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;

	@Reference
	private UmsUserFacade umsUserFacade;

	@Reference
	private UmsPersonFacade umsPersonFacade;

	@RequestMapping(value="/defaultRole", method=RequestMethod.GET)
	public @ResponseBody ViewerResult defaultRole(){
		ViewerResult result = new ViewerResult();
		try {
			List<UmsApp> apps = umsAppFacade.list(Searchable.newSearchable());
			for(UmsApp app : apps){
				String appId = app.getId();
				String appSn = app.getSn();
				
				Searchable searchable = Searchable.newSearchable();
				searchable.addSearchParam("role.appId_eq", appId);
				List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(searchable);
				for(UmsUserRoleRelation uurr : uurrs){
					List<String> userIds = new ArrayList<>();
					userIds.add(uurr.getUser().getId());
					umsRoleFacade.clearUserRoleRelation(uurr.getRole().getId(), userIds);
				}
				List<String> appIds = new ArrayList<>();
				appIds.add(appId);
				umsRoleFacade.deleteByApp(appIds);
				
				String[] appDefaultRolesPrefix = FaySysRoleConstant.DEFAULT_ROLE_SN_PREFIX;
				String[] appDefaultRolesName = FaySysRoleConstant.DEFAULT_ROLE_NAME;
				for(int i = 0; i < appDefaultRolesPrefix.length; i++){
					UmsRole role = new UmsRole();
					role.setAppId(appId);
					role.setName(appDefaultRolesName[i]);
					role.setSn(appDefaultRolesPrefix[i]+appSn);
					umsRoleFacade.create(role);
				}
				
			}
			result.setSuccess(true);
			result.setErrMessage("初始化默认的用户角色成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("初始化默认的用户角色失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/defaultOrgRole", method=RequestMethod.GET)
	public @ResponseBody ViewerResult defaultOrgRole(){
		ViewerResult result = new ViewerResult();
		try {
			List<UmsOrg> orgs = umsOrgFacade.listUmsOrg(Searchable.newSearchable());
			for(UmsOrg org : orgs){
				String orgId = org.getId();
				
				Searchable searchable = Searchable.newSearchable();
				searchable.addSearchParam("role.orgId_eq", orgId);
				List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
				for(UmsUserOrgRoleRelation uuorr : uuorrs){
					List<String> userIds = new ArrayList<>();
					userIds.add(uuorr.getUser().getId());
					umsRoleFacade.clearUserRoleRelation(uuorr.getRole().getId(), userIds);
				}
				
				umsOrgRoleFacade.deleteByOrg(new String[]{org.getId()});
				
				String[] appDefaultRolesPrefix = FayOrgRoleConstant.DEFAULT_ROLE_SN_PREFIX;
				String[] appDefaultRolesName = FayOrgRoleConstant.DEFAULT_ROLE_NAME;
				for(int i = 0; i < appDefaultRolesPrefix.length; i++){
					UmsOrgRole role = new UmsOrgRole();
					role.setOrgId(orgId);
					role.setName(appDefaultRolesName[i]);
					role.setSn(appDefaultRolesPrefix[i]+orgId);
					umsOrgRoleFacade.create(role);
				}
			}
			result.setSuccess(true);
			result.setErrMessage("初始化默认的用户角色成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("初始化默认的用户角色失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/defaultPersonFromUser", method=RequestMethod.GET)
	public @ResponseBody ViewerResult defaultPersonFromUser(){
		ViewerResult result = new ViewerResult();
		try {
			int number = 0;
			int size = Integer.MAX_VALUE;
			Pageable page = new PageRequest(number, size);
			Searchable searchable = Searchable.newSearchable();
			searchable.setPage(page);
			List<UmsUser> users = umsUserFacade.listPage(searchable).getContent();
			for(UmsUser user : users){
				UmsPerson person = user.getPerson();
				if(person == null){
					person = new UmsPerson();
					person.setName(user.getNickname());
					person.setSn(user.getUsername());
					person.setId(user.getId());
					person.setUser(user);
					person.getUser().setPerson(person);
					person = umsPersonFacade.updatePerson(person);
				}else{
					person.setName(user.getNickname());
					person.setSn(user.getUsername());
				}
				user.setPerson(person);
				umsUserFacade.update(user);
			}
			result.setSuccess(true);
			result.setErrMessage("初始化默认的用户对应的人员信息成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("初始化默认的用户对应的人员信息失败");
			e.printStackTrace();
		}
		return result;
	}
	
//	@RequestMapping(value="/defaultRoleForUser", method=RequestMethod.GET)
//	public @ResponseBody ViewerResult defaultRoleForUser(){
//		ViewerResult result = new ViewerResult();
//		try {
//			List<UmsApp> apps = umsAppFacade.list(Searchable.newSearchable());
//			List<UmsUser> users = 
//			for(UmsApp app : apps){
//				String appId = app.getId();
//				String appSn = app.getSn();
//				String roleSn = FayConstant.GENERAL_ROLE_SN_PREFIX+appSn;
//				UmsRole role = umsRoleFacade.getBySn(roleSn);
//				if(role == null){
//					role = new UmsRole();
//					role.setName("普通用户");
//					role.setSn(roleSn);
//					role.setAppId(appId);
//					umsRoleFacade.create(role);
//				}
//				
//				String muRoleSn = FayConstant.MANAGE_ROLE_SN_PREFIX+appSn;
//				UmsRole muRole = umsRoleFacade.getBySn(muRoleSn);
//				if(muRole == null){
//					muRole = new UmsRole();
//					muRole.setName("管理用户");
//					muRole.setSn(muRoleSn);
//					muRole.setAppId(appId);
//					umsRoleFacade.create(muRole);
//				}
//			}
//			result.setSuccess(true);
//			result.setErrMessage("初始化默认的用户角色成功");
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setErrMessage("初始化默认的用户角色失败");
//			e.printStackTrace();
//		}
//		return result;
//	}
}