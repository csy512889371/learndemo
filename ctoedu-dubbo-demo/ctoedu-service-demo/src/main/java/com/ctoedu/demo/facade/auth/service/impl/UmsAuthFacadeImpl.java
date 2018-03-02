package com.ctoedu.demo.facade.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Sets;
import com.ctoedu.common.entity.enums.AuthStatus;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.entity.enums.BooleanEnum;
import com.ctoedu.common.model.search.SearchOperator;
import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.demo.core.auth.service.UmsAclService;
import com.ctoedu.demo.core.department.service.UmsDepartmentService;
import com.ctoedu.demo.core.jwt.service.UmsJwtService;
import com.ctoedu.demo.core.org.service.UmsOrgService;
import com.ctoedu.demo.core.org.service.UmsUserOrgDepartmentPosService;
import com.ctoedu.demo.core.position.service.UmsPositionService;
import com.ctoedu.demo.core.resource.service.UmsButtonService;
import com.ctoedu.demo.core.resource.service.UmsControllerResService;
import com.ctoedu.demo.core.resource.service.UmsMenuResService;
import com.ctoedu.demo.core.role.service.UmsRoleService;
import com.ctoedu.demo.core.user.service.UmsUserService;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.auth.domain.Authorization;
import com.ctoedu.demo.facade.auth.entity.UmsAcl;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
import com.ctoedu.demo.facade.jwt.domain.Payload;
import com.ctoedu.demo.facade.org.entity.UmsUserOrgDepartmentPos;
import com.ctoedu.demo.facade.resource.entity.UmsButton;
import com.ctoedu.demo.facade.resource.entity.UmsControllerOper;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service(retries=2)
public class UmsAuthFacadeImpl implements UmsAuthFacade{

	@Autowired
	private UmsRoleService umsRoleService;
	@Autowired
	private UmsOrgService umsOrgService;
	@Autowired
	private UmsPositionService umsPositionService;
	@Autowired
	private UmsDepartmentService umsDepartmentService;
	@Autowired
	private UmsAclService umsAclService;
	@Autowired
	private UmsMenuResService umsMenuResService;
	@Autowired
	private UmsButtonService umsButtonService;
	@Autowired
	private UmsUserOrgDepartmentPosService umsUserOrgDepartmentPosService;
	@Autowired
	private UmsControllerResService umsControllerResService;
	@Autowired
	private UmsJwtService umsJwtService;
	@Autowired
	private UmsUserService umsUserService;

	@Override
//	@Cacheable(value="uumsCache",key="permissions_#user.id")
	public Set<String> findStringPermissions(UmsUser user) {
		/* 1、获取用户Id
		 * 2、获取组织机构Id
		 * 3、获取职位Id
		 * 4、获取角色Id
		 * 5、获取组Id
		 */
		String userId = user.getId();
		Set<String> permissions = Sets.newHashSet();
        Set<String> orgIds = Sets.newHashSet();
        Set<String> posIds = Sets.newHashSet();
        List<UmsUserOrgDepartmentPos> userOrgPosList = umsUserOrgDepartmentPosService.findAllWithNoPageNoSort(Searchable.newSearchable().addSearchFilter("userId", SearchOperator.eq,userId));
        if(!userOrgPosList.isEmpty()){
        	for (UmsUserOrgDepartmentPos o : userOrgPosList) {
            	String orgId = o.getOrgId();
            	String posId = o.getPosId();
            	if(StringUtils.isNotEmpty(orgId)){
            		orgIds.add(orgId);
            	}
            	if(StringUtils.isNotEmpty(posId)){
            		posIds.add(posId);
            	}
            }
        }
        permissions.addAll(findStringPermissions(umsAclService.getAcl(userId, Principal.PRINCIPAL_USER)));
        if(!CollectionUtils.isEmpty(orgIds)){
        	//过滤组织机构 仅获取目前可用的组织机构数据
            orgIds = umsOrgService.filterForCanAvailable(orgIds);
            permissions.addAll(findStringPermissions(umsAclService.getAcl(StringUtils.join(orgIds, ","), Principal.PRINCIPAL_ORG)));
        }
        if(!CollectionUtils.isEmpty(posIds)){
        	//过滤工作职务 仅获取目前可用的工作职务数据
            posIds = umsPositionService.filterForCanAvailable(posIds);
            permissions.addAll(findStringPermissions(umsAclService.getAcl(StringUtils.join(posIds, ","), Principal.PRINCIPAL_POSITION)));
        }
        //获取用户的角色Id
        Set<String> roleIds = umsRoleService.findRoleIds(userId, BooleanEnum.TRUE.getValue());
        if(!CollectionUtils.isEmpty(roleIds)){
        	permissions.addAll(findStringPermissions(umsAclService.getAcl(StringUtils.join(roleIds, ","), Principal.PRINCIPAL_ROLE)));
        }
        //获取用户的组Id
        Set<String> departmentIds = umsDepartmentService.findDepartmentIds(userId, BooleanEnum.TRUE.getValue());
        if(!CollectionUtils.isEmpty(departmentIds)){
        	permissions.addAll(findStringPermissions(umsAclService.getAcl(StringUtils.join(departmentIds, ","), Principal.PRINCIPAL_DEPARTMENT)));
        }
		return permissions;
	}
	
	private Set<String> findStringPermissions(List<UmsAcl> acls){
		Set<String> permissions = Sets.newHashSet();
		Map<String,UmsMenuResources> menuResSnMap = findMenuResSn(acls);
		Map<String,UmsControllerResources> controllerResSnMap = findControllerResSn(acls);
		if(acls!=null){
			for(UmsAcl acl:acls){
				if(Resource.RESOURCE_MENU.equals(acl.getRtype())){
					List<UmsButton> buttons = umsButtonService.findButtonsByMenuId(acl.getRid(),AvailableEnum.TRUE.getValue());
					if(buttons!=null){
						for(UmsButton button:buttons){
							if(acl.checkPermission(button.getButtonOrder())){
								permissions.add(menuResSnMap.get(acl.getRid()).getMenuSn() + ":" + button.getButtonEvent());
							}
						}
					}
				}else if(Resource.RESOURCE_CONTROLLER.equals(acl.getRtype())){
					UmsControllerResources controllerResources = umsControllerResService.findOne(acl.getRid());
					Set<UmsControllerOper> umsControllerOpers = controllerResources.getUmsControllerOpers();
					if(umsControllerOpers!=null){
						for(UmsControllerOper umsControllerOper:umsControllerOpers){
							if(acl.checkPermission(umsControllerOper.getIndexPos())){
								permissions.add(controllerResSnMap.get(acl.getRid()).getControllerSn() + ":" + umsControllerOper.getControllerOperSn());
							}
						}
					}
				}
			}
		}
		return permissions;
	}
	
	private Map<String,UmsMenuResources> findMenuResSn(List<UmsAcl> acls){
		Set<String> menuResIds = Sets.newHashSet();
		Map<String,UmsMenuResources> menuResSnMap = new HashMap<String, UmsMenuResources>();
		List<UmsMenuResources> menuResList = new ArrayList<UmsMenuResources>();
		for(UmsAcl acl:acls){
			if(Resource.RESOURCE_MENU.equals(acl.getRtype())){
				menuResIds.add(acl.getRid());
			}
		}
		if(!menuResIds.isEmpty()&&menuResIds.size()>0){
			menuResList.addAll(umsMenuResService.findMenuByIds(menuResIds));
			for(UmsMenuResources res:menuResList){
				if(AvailableEnum.TRUE.getValue().equals(res.getIsAvailable())){
					menuResSnMap.put(res.getId(), res);
				}
			}
		}
		return menuResSnMap;
	}
	
	private Map<String,UmsControllerResources> findControllerResSn(List<UmsAcl> acls){
		Set<String> controllerResIds = Sets.newHashSet();
		Map<String,UmsControllerResources> controllerResSnMap = new HashMap<String, UmsControllerResources>();
		List<UmsControllerResources> controllerResList = new ArrayList<UmsControllerResources>();
		for(UmsAcl acl:acls){
			if(Resource.RESOURCE_CONTROLLER.equals(acl.getRtype())){
				controllerResIds.add(acl.getRid());
			}
		}
		if(!controllerResIds.isEmpty()&&controllerResIds.size()>0){
			controllerResList.addAll(umsControllerResService.findControllerResByIds(controllerResIds));
			for(UmsControllerResources res:controllerResList){
				if(AvailableEnum.TRUE.getValue().equals(res.getIsAvailable())){
					controllerResSnMap.put(res.getId(), res);
				}
			}
		}
		return controllerResSnMap;
	}
	
	public void addPrincipalAcl(String pid, String ptype, String[] rids, String rtype, Map<String,String[]> map){
		umsAclService.addPrincipalAcl(pid, ptype, rids, rtype, map);
    }

	@Override
	public List<UmsAcl> findAcl(String pid, String ptype, String rtype) {
		return umsAclService.getAcl(pid, ptype, rtype);
	}

	@Override
	public Authorization auth(String path, String appSn, String token) {
		Authorization authorization = new Authorization();
		if(appSn == null){
			authorization.setStatus(AuthStatus.A400.getValue());
		}else{
			UmsControllerResources ucr = isContolled(path, appSn);
			if(ucr!=null){
				if(token != null){
					Payload payload = umsJwtService.getPayload(token);
					if(payload == null){
						authorization.setStatus(AuthStatus.A4011.getValue());
					}else if(payload.getExp() > (new Date()).getTime()){
						String username = payload.getName();
						UmsUser user = umsUserService.findByUsername(username);
						if(user.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
							authorization.setUser(user);
							if(right(user, path, appSn)){
								authorization.setStatus(AuthStatus.A200.getValue());
							}else{
								authorization.setStatus(AuthStatus.A403.getValue());
							}
						}else{
							authorization.setStatus(AuthStatus.A4031.getValue());
						}
					}else{
						authorization.setStatus(AuthStatus.A4011.getValue());
					}
				}else{
					authorization.setStatus(AuthStatus.A4012.getValue());
				}
			}else{
				int status = 200;
				if(token != null){
					Payload payload = umsJwtService.getPayload(token);
					if(payload != null && payload.getExp() > (new Date()).getTime()){
						String username = payload.getName();
						UmsUser user = umsUserService.findByUsername(username);
						if(user.getIsAvailable().equals(AvailableEnum.TRUE.getValue())){
							authorization.setUser(user);
						}else{
							status = AuthStatus.A4031.getValue();
						}
					}
				}
				authorization.setStatus(status);
			}
		}
		return authorization;
	}
		
	private UmsControllerResources isContolled(String requestUrl, String appSn){
		List<UmsControllerResources> controllerResourcesList = null;
		try {
			Searchable searchable = Searchable.newSearchable();
			searchable.addSearchParam("controllerUrlMapping_eq", requestUrl);
			searchable.addSearchParam("application.sn_eq", appSn);
			searchable.addSearchParam("isAvailable_eq", AvailableEnum.TRUE.getValue());
			controllerResourcesList = umsControllerResService.findAllWithSort(searchable);
			if(controllerResourcesList != null && controllerResourcesList.size()>0){
				return controllerResourcesList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean right(UmsUser user, String requestUrl, String appSn){
		try {
			Set<UmsControllerResources> umsControllerRes =umsControllerResService.getControllerResByUrlAndUserAndAppSn(requestUrl, user, appSn, AvailableEnum.TRUE.getValue());
			if(umsControllerRes != null && umsControllerRes.size() > 0){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<UmsAcl> findAclByP(String pid, String ptype) {
		return umsAclService.getAcl(pid, ptype);
	}

	@Override
	public void addPrincipalAcl(String pid, String ptype, String rid, String rtype) {
		UmsAcl aclFromDB = umsAclService.getAcl(pid, ptype, rid, rtype);
		if(aclFromDB == null){
			UmsAcl acl = new UmsAcl();
			acl.setPid(pid);
			acl.setPtype(ptype);
			acl.setRid(rid);
			acl.setRtype(rtype);
			acl.setAclState(0);
			umsAclService.save(acl);
		}else{
			if(aclFromDB.getAclState()==-1){
				aclFromDB.setAclState(-2);
				umsAclService.update(aclFromDB);
			}
		}
	}
	
	@Override
	public void forbiddenPrincipalAcl(String pid, String ptype, String rid, String rtype) {
		UmsAcl aclFromDB = umsAclService.getAcl(pid, ptype, rid, rtype);
		if(aclFromDB == null) {
			UmsAcl acl = new UmsAcl();
			acl.setPid(pid);
			acl.setPtype(ptype);
			acl.setRid(rid);
			acl.setRtype(rtype);
			acl.setAclState(-1);
			umsAclService.save(acl);
		}else{
			aclFromDB.setAclState(-2);
			umsAclService.update(aclFromDB);
		}
	}
	
	@Override
	public void deleteForbiddenPrincipalAcl(String pid, String ptype, String rid, String rtype) {
		UmsAcl aclFromDB = umsAclService.getAcl(pid, ptype, rid, rtype);
		if(aclFromDB != null) {
			if(aclFromDB.getAclState() == -2){
				aclFromDB.setAclState(0);
				umsAclService.update(aclFromDB);
			}else if(aclFromDB.getAclState() == -1){
				umsAclService.clearPrincipalResourceAcl(pid, ptype, rtype);
			}
		}
	}

	@Override
	public void deletePrincipalAcl(String pid, String ptype, String rid, String rtype) {
		UmsAcl aclFromDB = umsAclService.getAcl(pid, ptype, rid, rtype);
		if(aclFromDB != null){
			if(aclFromDB.getAclState() != null && aclFromDB.getAclState() < 0){
				aclFromDB.setAclState(-1);
				umsAclService.update(aclFromDB);
			}else{
				umsAclService.delete(aclFromDB.getId());
			}
		}
	}
}
