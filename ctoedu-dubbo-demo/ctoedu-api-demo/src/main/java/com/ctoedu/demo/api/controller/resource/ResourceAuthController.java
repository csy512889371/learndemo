package com.ctoedu.demo.api.controller.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.vo.ListVO;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.controller.vo.resource.ControllerResourceForNavVO;
import com.ctoedu.demo.api.controller.vo.resource.MenuResourceForNavVO;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
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
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.resource.service.UmsControllerResFacade;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;
import com.ctoedu.demo.facade.role.entity.UmsRole;
import com.ctoedu.demo.facade.role.entity.UmsUserRoleRelation;
import com.ctoedu.demo.facade.role.service.UmsRoleFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/resource/auth")
public class ResourceAuthController {
	
	@Reference
	private UmsMenuResFacade umsMenuResFacade;
	
	@Reference
	private UmsAuthFacade umsAuthFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;

	@Reference
	private UmsOrgFacade umsOrgFacade;

	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;

	@Reference
	private UmsPositionFacade umsPositionFacade;

	@Reference
	private UmsRoleFacade umsRoleFacade;

	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;

	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * get all authorized menu by conditions
	 * @return
	 */
	@RequestMapping(value="/find", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findMenu(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		Set<UmsMenuResources> umsMenuResources = null;
		List<UmsMenuResources> umsMenuResourceList = null;
		ListVO<MenuResourceForNavVO> menulistVO = null;
		Set<UmsControllerResources> umsControllerResources = null;
		List<UmsControllerResources> umsControllerResourceList = null;
		ListVO<ControllerResourceForNavVO> controllerlistVO = null;
		ListVO<ControllerResourceForNavVO> isControlledlistVO = null;
		JSONObject outObj = new JSONObject();
		try {
			String appSn = (String) request.getAttribute("currentAppSn");
			Searchable cSearchable = Searchable.newSearchable();
			cSearchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
			cSearchable.addSearchParam("application.sn_eq", appSn);
			umsControllerResourceList = umsControllerResFacade.listUmsControllerRes(cSearchable);
			isControlledlistVO = new ListVO<>(umsControllerResourceList, ControllerResourceForNavVO.class);
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				umsMenuResources = new HashSet<>();
				menulistVO = new ListVO<>(umsMenuResources, MenuResourceForNavVO.class);
				umsControllerResources = new HashSet<>();
				controllerlistVO = new ListVO<>(umsControllerResources, ControllerResourceForNavVO.class);
			}else{
				String username = (String)currentUsername;
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					Searchable searchable = Searchable.newSearchable();
					searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
					searchable.addSearchParam("application.sn_eq", appSn);
					umsMenuResourceList = umsMenuResFacade.listUmsMenuResources(searchable);
					menulistVO = new ListVO<>(umsMenuResourceList, MenuResourceForNavVO.class);
					controllerlistVO = isControlledlistVO;
				}else{
					UmsUser user = umsUserFacade.findByUsername(username);
					umsMenuResources = umsMenuResFacade.getMenusByUserAndAppSn(user, appSn, AvailableEnum.TRUE.getValue());
					menulistVO = new ListVO<>(umsMenuResources, MenuResourceForNavVO.class);
					umsControllerResources = umsControllerResFacade.getControllerResByUserAndAppSn(user, appSn, null, AvailableEnum.TRUE.getValue());
					controllerlistVO = new ListVO<>(umsControllerResources, ControllerResourceForNavVO.class);
				}
			}
			outObj.put("menu", menulistVO);
			outObj.put("controller", controllerlistVO);
			outObj.put("isControlled", isControlledlistVO);
			result.setSuccess(true);
			result.setData(outObj);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findAuthedController", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findAuthedController(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				result.setData(new String[0]);
			}else{
		        String userId = obj.getString("dataId");
				List<UmsAcl> aclList = umsAuthFacade.findAcl(userId, Principal.PRINCIPAL_USER, Resource.RESOURCE_CONTROLLER);
				int size = aclList.size();
				String[] controllerIds = new String[size];
				for(int i = 0; i < size; i++){
					controllerIds[i] = aclList.get(i).getRid();
				}
				result.setData(controllerIds);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addControllerResource", method=RequestMethod.POST)
	public @ResponseBody ViewerResult addControllerResource(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			//get JSON format parameters
			String userId = obj.getString("dataId");
			List<String> list = (List<String>) obj.get("controllerIds");
			String[] controllerIds = new String[list.size()];
			list.toArray(controllerIds);
			umsAuthFacade.addPrincipalAcl(userId, Principal.PRINCIPAL_USER, controllerIds, Resource.RESOURCE_CONTROLLER, null);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/changeAuthedResource", method=RequestMethod.POST)
	public @ResponseBody ViewerResult changeAuthedResource(@RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		try {
			String dataId = obj.getString("dataId");
			String dataType = obj.getString("dataType");
			String resourceId = obj.getString("resourceId");
			String resourceType = obj.getString("resourceType");
			String dealType = obj.getString("dealType");
			if("add".equals(dealType)){
				umsAuthFacade.addPrincipalAcl(dataId, dataType, resourceId, resourceType);
			}else if("delete".equals(dealType)){
				umsAuthFacade.deletePrincipalAcl(dataId, dataType, resourceId, resourceType);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/findAuthedResource", method=RequestMethod.POST)
	public @ResponseBody ViewerResult findAuthedResource(HttpServletRequest request, @RequestBody JSONObject obj){
		ViewerResult result = new ViewerResult();
		List<UmsAcl> aclList = null;
		Set<String> parentAuthedKeys = null;
		Set<String> forbiddenKeys = new HashSet<>();
		try {
			JSONObject data = new JSONObject();
			Object currentUsername = request.getAttribute("currentUsername");
			if(currentUsername == null){
				data.put("checked", new String[0]);
				data.put("halfchecked", new String[0]);
				result.setData(data);
			}else{
				String available = "";
				String username = (String) currentUsername;
				if(Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(username) || roleService.validate(username, FaySysRoleConstant.SUPER_MANAGE_ROLE_SN)){
					available = "1";
				}else{
					available = "2";
				}
				String dataId = obj.getString("dataId");
				String form = obj.getString("form");
				if(form.equals("ORG")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_ORG);
					UmsOrg org = umsOrgFacade.getById(dataId);
					if(org.getParent() != null){
						parentAuthedKeys = getParentOrgResource(org.getParent().getId(), new HashSet<>());
					}
				}else if(form.equals("DEPARTMENT")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_DEPARTMENT);
					UmsDepartment department = umsDepartmentFacade.getById(dataId);
					parentAuthedKeys = getParentOrgResource(department.getOrgId(), new HashSet<>());
				}else if(form.equals("POSITION")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_POSITION);
					UmsPosition position = umsPositionFacade.getById(dataId);
					parentAuthedKeys = getPositionParentResource(position.getDepartmentId(), position.getOrgId(), new HashSet<>());
				}else if(form.equals("ROLE")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_ROLE);
				}else if(form.equals("ORGROLE")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_ORG_ROLE);
				}else if(form.equals("USER")){
					aclList = umsAuthFacade.findAclByP(dataId, Principal.PRINCIPAL_USER);
					parentAuthedKeys = getUserParentResource(dataId, new HashSet<>(), available);
				}else{
					aclList = new ArrayList<>();
				}
				if(parentAuthedKeys == null) parentAuthedKeys = new HashSet<>();
				int size = aclList.size();
				Set<String> checkedKeys = new HashSet<>();
				Set<String> resourceKeys = new HashSet<>();
				for(int i = 0; i < size; i++){
					UmsAcl acl = aclList.get(i);
					String id = acl.getRid();
					Integer state = acl.getAclState();
					if(state != null && state < 0){
						forbiddenKeys.add(id);
					}
					if(id != null){
						String type = acl.getRtype();
						if(type.equals(Resource.RESOURCE_CONTROLLER)){
							UmsControllerResources ucr = umsControllerResFacade.findById(id);
							if((ucr != null) && (ucr.getApplication() != null)){
								checkedKeys.add(available + "_APP_" + ucr.getApplication().getId());
							}
						} else if(type.equals(Resource.RESOURCE_MENU)){
							UmsMenuResources umr = umsMenuResFacade.findById(id);
							if((umr != null) && (umr.getApplication() != null)){
								checkedKeys.add(available + "_APP_" + umr.getApplication().getId());
							}
						}
						checkedKeys.add(available + "_" + type + "_" + id);
						resourceKeys.add(available + "_" + type + "_" + id);
					}
				}
				Set<String> halfcheckedKeys = getUnCheckedParentKeys(resourceKeys, available);
				Set<String> expandedKeys = new HashSet<>();
				expandedKeys.addAll(checkedKeys);
				expandedKeys.addAll(halfcheckedKeys);
				data.put("expanded", expandedKeys);
				data.put("checked", checkedKeys);
				data.put("halfchecked", halfcheckedKeys);
				data.put("parentAuthedKeys", parentAuthedKeys);
				data.put("forbiddenKeys", forbiddenKeys);
				result.setData(data);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private Set<String> getUnCheckedParentKeys(Set<String> keys, String available){
		Set<String> unCheckedParentKeys = new HashSet<>();
		for(String key : keys){
			String[] keyArr = key.split("_");
			String type = keyArr[1];
			String id = keyArr[2];
			if(type.equals("CONTROLLER")){
				unCheckedParentKeys.addAll(getControllerParentKeys(id, unCheckedParentKeys, unCheckedParentKeys, available));
			} else if(type.equals("MENU")){
				unCheckedParentKeys.addAll(getMenuParentKeys(id, unCheckedParentKeys, unCheckedParentKeys, available));
			}
		}
		return unCheckedParentKeys;
	}
	
	private Set<String> getControllerParentKeys(String id, Set<String> unCheckedParentKeys, Set<String> keys, String available){
		UmsControllerResources ucr = umsControllerResFacade.findById(id);
		if(ucr == null) return unCheckedParentKeys;
		UmsControllerResources parent = ucr.getParent();
		UmsMenuResources umr = ucr.getMenu();
		if(parent != null && !(parent.getId().equals(id))){
			String parentId = parent.getId();
			String parentKey = available + "_CONTROLLER_" + parentId;
			if(!keys.contains(parentKey)){
				unCheckedParentKeys.add(parentKey);
			}
			getControllerParentKeys(parentId, unCheckedParentKeys, keys, available);
		}else if(umr != null){
			String parentId = umr.getId();
			String parentKey = available + "_MENU_" + parentId;
			if(!keys.contains(parentKey)){
				unCheckedParentKeys.add(parentKey);
			}
			getControllerParentKeys(parentId, unCheckedParentKeys, keys, available);
		}
		return unCheckedParentKeys;
	}
	
	private Set<String> getMenuParentKeys(String id, Set<String> unCheckedParentKeys, Set<String> keys, String available){
		UmsMenuResources umr = umsMenuResFacade.findById(id);
		if(umr == null) return unCheckedParentKeys;
		UmsMenuResources parent = umr.getParent();
		if(parent != null && !(parent.getId().equals(id))){
			String parentId = parent.getId();
			String parentKey = available + "_MENU_" + parentId;
			if(!keys.contains(parentKey)){
				unCheckedParentKeys.add(parentKey);
			}
			getMenuParentKeys(parentId, unCheckedParentKeys, keys, available);
		}
		return unCheckedParentKeys;
	}
	
	private Set<String> getParentOrgResource(String id, Set<String> parentAuthedKeys){
		if(id != null){
			UmsOrg org = umsOrgFacade.getById(id);
			if(org != null && org.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ORG);
				for(UmsAcl acl : acls){
					parentAuthedKeys.add(acl.getRtype()+"_"+acl.getRid());
				}
				if(org.getParent() != null && org.getParent().getId() != null){
					getParentOrgResource(org.getParent().getId(), parentAuthedKeys);
				}
			}
		}
		return parentAuthedKeys;
	}
	
	private Set<String> getParentDepartmentResource(String id, Set<String> parentAuthedKeys){
		if(id != null){
			UmsDepartment department = umsDepartmentFacade.getById(id);
			if(department != null && department.getIsAvailable() == AvailableEnum.TRUE.getValue()){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_DEPARTMENT);
				for(UmsAcl acl : acls){
					parentAuthedKeys.add(acl.getRtype()+"_"+acl.getRid());
				}
				if(department.getOrgId() != null){
					getParentOrgResource(department.getOrgId(), parentAuthedKeys);
				}
			}
		}
		return parentAuthedKeys;
	}
	
	private Set<String> getParentPositionResource(String id, Set<String> parentAuthedKeys, String available){
		if(id != null){
			UmsPosition position = umsPositionFacade.getById(id);
			if(position != null && position.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_POSITION);
				for(UmsAcl acl : acls){
					parentAuthedKeys.add(available + "_" + acl.getRtype()+"_"+acl.getRid());
				}
				getPositionParentResource(position.getDepartmentId(), position.getOrgId(), parentAuthedKeys);
			}
		}
		return parentAuthedKeys;
	}
	
	private Set<String> getParentRoleResource(String id, Set<String> parentAuthedKeys, String available){
		if(id != null){
			UmsRole role = umsRoleFacade.getById(id);
			if(role != null && role.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ROLE);
				for(UmsAcl acl : acls){
					parentAuthedKeys.add(available + "_" + acl.getRtype()+"_"+acl.getRid());
				}
			}
		}
		return parentAuthedKeys;
	}

	private Set<String> getParentOrgRoleResource(String id, Set<String> parentAuthedKeys, String available){
		if(id != null){
			UmsOrgRole role = umsOrgRoleFacade.getById(id);
			if(role != null && role.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ORG_ROLE);
				for(UmsAcl acl : acls){
					parentAuthedKeys.add(available + "_" + acl.getRtype()+"_"+acl.getRid());
				}
			}
		}
		return parentAuthedKeys;
	}
	
	private Set<String> getPositionParentResource(String departmentId, String orgId, Set<String> parentAuthedKeys){
		getParentDepartmentResource(departmentId, parentAuthedKeys);
		getParentOrgResource(orgId, parentAuthedKeys);
		return parentAuthedKeys;
	}
	
	private Set<String> getUserParentResource(String id, Set<String> parentAuthedKeys, String available){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("user.id_eq", id);
		List<UmsUserOrgRelation> uuors = umsOrgFacade.listUmsUserOrgRelation(searchable);
		List<UmsUserDepartmentRelation> uugrs = umsDepartmentFacade.listUmsUserDepartmentRelation(searchable);
		List<UmsUserPositionRelation> uuprs = umsPositionFacade.listUmsUserPositionRelation(searchable);
		List<UmsUserRoleRelation> uurrs = umsRoleFacade.listUmsUserRoleRelation(searchable);
		List<UmsUserOrgRoleRelation> uuorrs = umsOrgRoleFacade.listUmsUserRoleRelation(searchable);
		for(UmsUserOrgRelation uuor : uuors){
			UmsOrg org = uuor.getOrg();
			if(org != null){
				getParentOrgResource(org.getId(), parentAuthedKeys);
			}
		}
		for(UmsUserDepartmentRelation uugr : uugrs){
			UmsDepartment department = uugr.getDepartment();
			if(department != null){
				getParentDepartmentResource(department.getId(), parentAuthedKeys);
			}
		}
		for(UmsUserPositionRelation uupr : uuprs){
			UmsPosition position = uupr.getPosition();
			if(position != null){
				getParentPositionResource(position.getId(), parentAuthedKeys, available);
			}
		}
		for(UmsUserRoleRelation uurr : uurrs){
			UmsRole role = uurr.getRole();
			if(role != null){
				getParentRoleResource(role.getId(), parentAuthedKeys, available);
			}
		}
		for(UmsUserOrgRoleRelation uuorr : uuorrs){
			UmsOrgRole role = uuorr.getRole();
			if(role != null){
				getParentOrgRoleResource(role.getId(), parentAuthedKeys, available);
			}
		}
		return parentAuthedKeys;
	}
}
