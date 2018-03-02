package com.ctoedu.demo.core.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.department.repository.UmsUserDepartmentRelationRepository;
import com.ctoedu.demo.core.org.repository.UmsUserOrgRelationRepository;
import com.ctoedu.demo.core.orgRole.repository.UmsUserOrgRoleRelationRepository;
import com.ctoedu.demo.core.position.repository.UmsUserPositionRelationRepository;
import com.ctoedu.demo.core.resource.repository.UmsControllerResRepository;
import com.ctoedu.demo.core.role.repository.UmsUserRoleRelationRepository;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.Resource;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.exception.ControllerSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;
@Service
public class UmsControllerResService extends BaseService<UmsControllerResources, String>{
	
	@Autowired
	private UmsControllerResRepository umsControllerResRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	
	@Autowired
	private UmsUserOrgRelationRepository umsUserOrgRelationRepository;
	@Autowired
	private UmsUserDepartmentRelationRepository umsUserDepartmentRelationRepository;
	@Autowired
	private UmsUserPositionRelationRepository umsUserPositionRelationRepository;
	@Autowired
	private UmsUserRoleRelationRepository umsUserRoleRelationRepository;
	@Autowired
	private UmsUserOrgRoleRelationRepository umsUserOrgRoleRelationRepository;
	
	/**
	 * 保存控制器资源实体信息
	 * @param menu
	 */
	public UmsControllerResources saveControllerRes(UmsControllerResources umsControllerResources){
		if(findControllerResBySnAndApp(umsControllerResources.getControllerSn(),umsControllerResources.getApplication())!=null){
            throw new ControllerSnExistsException();
        }
        return save(umsControllerResources);
	}
	/**
	 * 根据控制器资源sn和应用系统获取菜单
	 * @param menuSn
	 * @param app
	 * @return
	 */
	public UmsControllerResources findControllerResBySnAndApp(String controllerResSn,UmsApp app){
		return umsControllerResRepository.findControllerResBySnAndApp(controllerResSn,app);
	}

	/**
	 * 删除控制器资源
	 * @param ids
	 */
	public void deleteControllerRes(String... ids){
		for(String id:ids){
			umsAclRepository.clearResourceAcl(id, Resource.RESOURCE_CONTROLLER);
			List<String> childIds = umsControllerResRepository.findControllerByParentId(id);
			if(childIds != null && childIds.size()>0){
				deleteControllerRes(childIds.toArray(new String[childIds.size()]));
			}
			delete(id);
		}
	}
	
	/**
	 * 根据主键集合获取控制器资源集合
	 * @param controllerIds
	 * @return
	 */
	public List<UmsControllerResources> findControllerResByIds(Set<String> controllerIds){
		return umsControllerResRepository.findAll(controllerIds);
	}
	
	/**
	 * 根据用户和appSn获取所有请求资源
	 * @param user
	 * @param appSn
	 * @param isAvaiable
	 * @return
	 */
	public Set<UmsControllerResources> getControllerResByUserAndAppSn(UmsUser user, String appSn, String menuId, Short isAvaiable){
		Set<UmsControllerResources> resSet = new TreeSet<UmsControllerResources>();
		String userId = user.getId();
		
		List<String> orgIds = umsUserOrgRelationRepository.findOrgIds(userId, AvailableEnum.TRUE.getValue());
		List<String> departmentIds = umsUserDepartmentRelationRepository.findDepartmentIds(userId,AvailableEnum.TRUE.getValue());
		List<String> posIds = umsUserPositionRelationRepository.findPositionIds(userId, AvailableEnum.TRUE.getValue());
		List<String> roleIds = umsUserRoleRelationRepository.findRoleIds(userId, AvailableEnum.TRUE.getValue());
		List<String> orgRoleIds = umsUserOrgRoleRelationRepository.findRoleIds(userId, AvailableEnum.TRUE.getValue());
		List<String> userIds = new ArrayList<>();
		userIds.add(userId);
		List<UmsControllerResources> res = new ArrayList<>();
		
		if(menuId == null){
			List<UmsControllerResources> userRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, userIds, Principal.PRINCIPAL_USER, appSn, isAvaiable);
			res.addAll(userRes);
			if(orgIds!=null&&orgIds.size()>0){
				List<UmsControllerResources> orgRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, orgIds, Principal.PRINCIPAL_ORG, appSn,isAvaiable);
				res.addAll(orgRes);
			}
			if(departmentIds!=null&&departmentIds.size()>0){
				List<UmsControllerResources> departmentRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, departmentIds, Principal.PRINCIPAL_DEPARTMENT, appSn,isAvaiable);
				res.addAll(departmentRes);
			}
			if(posIds!=null&&posIds.size()>0){
				List<UmsControllerResources> positionRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, posIds, Principal.PRINCIPAL_POSITION, appSn,isAvaiable);
				res.addAll(positionRes);
			}
			if(roleIds!=null&&roleIds.size()>0){
				List<UmsControllerResources> roleRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, roleIds, Principal.PRINCIPAL_ROLE, appSn,isAvaiable);
				res.addAll(roleRes);
			}
			if(orgRoleIds!=null&&orgRoleIds.size()>0){
				List<UmsControllerResources> orgRoleRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, orgRoleIds, Principal.PRINCIPAL_ORG_ROLE, appSn,isAvaiable);
				res.addAll(orgRoleRes);
			}
		}else{
			List<UmsControllerResources> userRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, userIds, Principal.PRINCIPAL_USER, appSn, menuId, isAvaiable);
			res.addAll(userRes);
			if(orgIds!=null&&orgIds.size()>0){
				List<UmsControllerResources> orgRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, orgIds, Principal.PRINCIPAL_ORG, appSn, menuId, isAvaiable);
				res.addAll(orgRes);
			}
			if(departmentIds!=null&&departmentIds.size()>0){
				List<UmsControllerResources> departmentRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, departmentIds, Principal.PRINCIPAL_DEPARTMENT, appSn, menuId, isAvaiable);
				res.addAll(departmentRes);
			}
			if(posIds!=null&&posIds.size()>0){
				List<UmsControllerResources> positionRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, posIds, Principal.PRINCIPAL_POSITION, appSn, menuId, isAvaiable);
				res.addAll(positionRes);
			}
			if(roleIds!=null&&roleIds.size()>0){
				List<UmsControllerResources> roleRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, roleIds, Principal.PRINCIPAL_ROLE, appSn, menuId, isAvaiable);
				res.addAll(roleRes);
			}
			if(orgRoleIds!=null&&orgRoleIds.size()>0){
				List<UmsControllerResources> orgRoleRes = umsControllerResRepository.getControllerResList(Resource.RESOURCE_CONTROLLER, orgRoleIds, Principal.PRINCIPAL_ROLE, appSn, menuId, isAvaiable);
				res.addAll(orgRoleRes);
			}
		}
		
		
		for(UmsControllerResources ucr : res){
			resSet.add(ucr);
		}
		return resSet;
	}
	
	/**
	 * 根据url、用户和appSn获取所有请求资源
	 * @param user
	 * @param appSn
	 * @param isAvaiable
	 * @return
	 */
	public Set<UmsControllerResources> getControllerResByUrlAndUserAndAppSn(String url, UmsUser user,String appSn,Short isAvaiable){
		Set<UmsControllerResources> resSet = new TreeSet<UmsControllerResources>();
		String userId = user.getId();
		
		List<String> orgIds = umsUserOrgRelationRepository.findOrgIds(userId, AvailableEnum.TRUE.getValue());
		List<String> departmentIds = umsUserDepartmentRelationRepository.findDepartmentIds(userId,AvailableEnum.TRUE.getValue());
		List<String> posIds = umsUserPositionRelationRepository.findPositionIds(userId, AvailableEnum.TRUE.getValue());
		List<String> roleIds = umsUserRoleRelationRepository.findRoleIds(userId, AvailableEnum.TRUE.getValue());
		List<String> userIds = new ArrayList<>();
		userIds.add(userId);
		List<UmsControllerResources> res = new ArrayList<>();
		List<UmsControllerResources> userRes = umsControllerResRepository.getControllerResList(url, Resource.RESOURCE_CONTROLLER, userIds, Principal.PRINCIPAL_USER, appSn,isAvaiable);
		res.addAll(userRes);
		if(orgIds!=null&&orgIds.size()>0){
			List<UmsControllerResources> orgRes = umsControllerResRepository.getControllerResList(url, Resource.RESOURCE_CONTROLLER, orgIds, Principal.PRINCIPAL_ORG, appSn,isAvaiable);
			res.addAll(orgRes);
		}
		if(departmentIds!=null&&departmentIds.size()>0){
			List<UmsControllerResources> departmentRes = umsControllerResRepository.getControllerResList(url, Resource.RESOURCE_CONTROLLER, departmentIds, Principal.PRINCIPAL_DEPARTMENT, appSn,isAvaiable);
			res.addAll(departmentRes);
		}
		if(posIds!=null&&posIds.size()>0){
			List<UmsControllerResources> positionRes = umsControllerResRepository.getControllerResList(url, Resource.RESOURCE_CONTROLLER, posIds, Principal.PRINCIPAL_POSITION, appSn,isAvaiable);
			res.addAll(positionRes);
		}
		if(roleIds!=null&&roleIds.size()>0){
			List<UmsControllerResources> roleRes = umsControllerResRepository.getControllerResList(url, Resource.RESOURCE_CONTROLLER, roleIds, Principal.PRINCIPAL_ROLE, appSn,isAvaiable);
			res.addAll(roleRes);
		}
		for(UmsControllerResources ucr : res){
			resSet.add(ucr);
		}
		return resSet;
	}
	
	public void deleteByAppIds(List<String> appIds){
		umsControllerResRepository.deleteByAppIds(appIds);
	}
}
