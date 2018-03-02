package com.ctoedu.demo.api.service.auth.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.api.controller.common.service.AuthedResourceService;
import com.ctoedu.demo.api.service.auth.AuthedService;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.app.service.UmsAppFacade;
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
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Service
public class AuthedServiceImpl implements AuthedService {
	
	@Reference
	private UmsOrgFacade umsOrgFacade;
	
	@Reference
	private UmsAppFacade umsAppFacade;
	
	@Reference
	private UmsMenuResFacade umsMenuResFacade;
	
	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsAuthFacade umsAuthFacade;
	
	@Reference
	private UmsDepartmentFacade umsDepartmentFacade;

	@Reference
	private UmsPositionFacade umsPositionFacade;

	@Reference
	private UmsRoleFacade umsRoleFacade;

	@Reference
	private UmsOrgRoleFacade umsOrgRoleFacade;

	@Override
	public Map<String, Set<String>> find(String dataId, String pType) {
		Set<UmsAcl> acls = null;
		Map<String, Set<String>> resourcesMap = new HashMap<>();
		List<UmsAcl> aclList = umsAuthFacade.findAclByP(dataId, pType);
		switch (pType) {
			case Principal.PRINCIPAL_ORG:
				acls = getOrgParentResource(dataId, new HashSet<>());
				break;
			case Principal.PRINCIPAL_DEPARTMENT:
				acls = getDepartmentParentResource(dataId, new HashSet<>());
				break;
			case Principal.PRINCIPAL_POSITION:
				acls = getPositionParentResource(dataId, new HashSet<>());
				break;
			case Principal.PRINCIPAL_USER:
				acls = getUserParentResource(dataId, new HashSet<>());
				break;
			default:
				acls = new HashSet<>();
				break;
		}
		acls.addAll(aclList);
		for(UmsAcl acl : acls){
			Integer state = acl.getAclState();
			if(state == null || state == 0 || state > 0) {
				String id = acl.getRid();
				String type = acl.getRtype();
				String name = null;
				UmsApp app = null;
				if(type.equals(Resource.RESOURCE_CONTROLLER)){
					UmsControllerResources ucr = umsControllerResFacade.findById(id);
					if(ucr != null && AvailableEnum.TRUE.getValue().equals(ucr.getIsAvailable())){
						app = ucr.getApplication();
						name = AuthedResourceService.getControllerResourceLinkedNames(ucr, "CONTROLLER_" + ucr.getControllerName());
					}
				} else if(type.equals(Resource.RESOURCE_MENU)){
					UmsMenuResources umr = umsMenuResFacade.findById(id);
					if(umr != null && AvailableEnum.TRUE.getValue().equals(umr.getIsAvailable())){
						app = umr.getApplication();
						name = AuthedResourceService.getMenuResourceLinkedNames(umr, "MENU_" + umr.getMenuName());
					}
				}
				if(name != null){
					if(app != null){
						String appName = app.getName();
						Set<String> set = resourcesMap.get(appName);
						if(set == null) set = new HashSet<>();
						set.add(name);
						resourcesMap.put(app.getName(), set);
					}
				}
			}
		}
		return resourcesMap;
	}
	
	private Set<UmsAcl> getUserParentResource(String id, Set<UmsAcl> parentAcls){
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
				getParentOrgResource(org.getId(), parentAcls);
			}
		}
		for(UmsUserDepartmentRelation uugr : uugrs){
			UmsDepartment department = uugr.getDepartment();
			if(department != null){
				getParentDepartmentResource(department.getId(), parentAcls);
			}
		}
		for(UmsUserPositionRelation uupr : uuprs){
			UmsPosition position = uupr.getPosition();
			if(position != null){
				getParentPositionResource(position.getId(), parentAcls);
			}
		}
		for(UmsUserRoleRelation uurr : uurrs){
			UmsRole role = uurr.getRole();
			if(role != null){
				getParentRoleResource(role.getId(), parentAcls);
			}
		}
		for(UmsUserOrgRoleRelation uuorr : uuorrs){
			UmsOrgRole role = uuorr.getRole();
			if(role != null){
				getParentOrgRoleResource(role.getId(), parentAcls);
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getParentOrgResource(String id, Set<UmsAcl> parentAcls){
		if(id != null){
			UmsOrg org = umsOrgFacade.getById(id);
			if(org != null && org.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ORG);
				parentAcls.addAll(acls);
				if(org.getParent() != null && org.getParent().getId() != null){
					getParentOrgResource(org.getParent().getId(), parentAcls);
				}
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getParentDepartmentResource(String id, Set<UmsAcl> parentAcls){
		if(id != null){
			UmsDepartment department = umsDepartmentFacade.getById(id);
			if(department != null && department.getIsAvailable() == AvailableEnum.TRUE.getValue()){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_DEPARTMENT);
				parentAcls.addAll(acls);
				if(department.getOrgId() != null){
					getParentOrgResource(department.getOrgId(), parentAcls);
				}
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getParentPositionResource(String id, Set<UmsAcl> parentAcls){
		if(id != null){
			UmsPosition position = umsPositionFacade.getById(id);
			if(position != null && position.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_POSITION);
				parentAcls.addAll(acls);
				getPositionParentResource(position.getDepartmentId(), position.getOrgId(), parentAcls);
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getParentRoleResource(String id, Set<UmsAcl> parentAcls){
		if(id != null){
			UmsRole role = umsRoleFacade.getById(id);
			if(role != null && role.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ROLE);
				parentAcls.addAll(acls);
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getParentOrgRoleResource(String id, Set<UmsAcl> parentAcls){
		if(id != null){
			UmsOrgRole role = umsOrgRoleFacade.getById(id);
			if(role != null && role.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
				List<UmsAcl> acls = umsAuthFacade.findAclByP(id, Principal.PRINCIPAL_ORG_ROLE);
				parentAcls.addAll(acls);
			}
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getPositionParentResource(String departmentId, String orgId, Set<UmsAcl> parentAcls){
		getParentDepartmentResource(departmentId, parentAcls);
		getParentOrgResource(orgId, parentAcls);
		return parentAcls;
	}
	
	private Set<UmsAcl> getPositionParentResource(String id, Set<UmsAcl> parentAcls){
		UmsPosition position = umsPositionFacade.getById(id);
		if(position.getDepartmentId() != null){
			getParentDepartmentResource(position.getDepartmentId(), parentAcls);
		}else if(position.getOrgId() != null){
			getParentOrgResource(position.getOrgId(), parentAcls);
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getOrgParentResource(String id, Set<UmsAcl> parentAcls){
		UmsOrg org = umsOrgFacade.getById(id);
		if(org != null && org.getParent() != null){
			getParentOrgResource(org.getParent().getId(), parentAcls);
		}
		return parentAcls;
	}
	
	private Set<UmsAcl> getDepartmentParentResource(String id, Set<UmsAcl> parentAcls){
		UmsDepartment department = umsDepartmentFacade.getById(id);
		if(department != null){
			getParentOrgResource(department.getOrgId(), parentAcls);
		}
		return parentAcls;
	}
}
