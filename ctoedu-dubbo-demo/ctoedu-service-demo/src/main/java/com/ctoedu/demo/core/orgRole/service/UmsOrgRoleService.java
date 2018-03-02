package com.ctoedu.demo.core.orgRole.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.ctoedu.common.service.BaseService;
import com.ctoedu.demo.core.auth.repository.UmsAclRepository;
import com.ctoedu.demo.core.orgRole.repository.UmsOrgRoleRepository;
import com.ctoedu.demo.core.orgRole.repository.UmsUserOrgRoleRelationRepository;
import com.ctoedu.demo.facade.Principal;
import com.ctoedu.demo.facade.orgRole.entity.UmsOrgRole;
import com.ctoedu.demo.facade.orgRole.entity.UmsUserOrgRoleRelation;
import com.ctoedu.demo.facade.orgRole.exception.OrgRoleSnExistsException;
import com.ctoedu.demo.facade.user.entity.UmsUser;

/**
 *
 * Date:2016年11月23日 下午3:37:58
 * Version:1.0
 */
@Service("umsOrgRoleService")
public class UmsOrgRoleService extends BaseService<UmsOrgRole, String>{
	@Autowired
	private UmsUserOrgRoleRelationRepository umsUserOrgRoleRelationRepository;
	@Autowired
	private UmsOrgRoleRepository umsOrgRoleRepository;
	@Autowired
	private UmsAclRepository umsAclRepository;
	/**
	 * 保存角色
	 * @param role
	 */
	public UmsOrgRole saveRole(UmsOrgRole role){
		if(findByRoleSn(role.getSn())!=null){
            throw new OrgRoleSnExistsException();
        }
        return save(role);
	}
	
	/**
	 * 根据角色标识符查找角色
	 * @param roleSn
	 * @return
	 */
	public UmsOrgRole findByRoleSn(String roleSn){
		return umsOrgRoleRepository.findByRoleSn(roleSn);
	}
	/**
	 * 根据userId获取所有可用角色Sn
	 * @param userId
	 * @param isAvailable
	 * @return
	 */
	public Set<String> findRoleIds(String userId, Short isAvailable){
		Set<String> roleIds = Sets.newHashSet();
		roleIds.addAll(Sets.newHashSet(umsUserOrgRoleRelationRepository.findRoleIds(userId,isAvailable)));
        return roleIds;
	}
	/**
	 * 根据userId获取所有可用角色
	 * @param userId
	 * @param isAvailable
	 * @return
	 */
	public Set<UmsOrgRole> findRoles(String userId, Short isAvailable){
		Set<UmsOrgRole> roles = Sets.newHashSet();
		roles.addAll(Sets.newHashSet(umsUserOrgRoleRelationRepository.findRoles(userId,isAvailable)));
        return roles;
	}
	/**
	 * 添加用户角色关联
	 * @param roleId
	 * @param userIds
	 */
	public void buildUserRoleRelation(String roleId, String[] userIds){
		if (ArrayUtils.isEmpty(userIds)) {
	            return;
        }else{
            for (String userId : userIds) {
                if (StringUtils.isEmpty(userId)) {
                    continue;
                }
                UmsUserOrgRoleRelation r = umsUserOrgRoleRelationRepository.findByRoleIdAndUserId(roleId, userId);
                if (r == null) {
                    r = new UmsUserOrgRoleRelation();
                    UmsOrgRole role = new UmsOrgRole();
                    role.setId(roleId);
                    UmsUser user = new UmsUser();
                    user.setId(userId);
                    r.setRole(role);
                    r.setUser(user);
                    umsUserOrgRoleRelationRepository.save(r);
                }
            }
        }
	}
	
	/**
     * 删除角色
     * @param roleIds
     */
    public void deleteRole(String... roleIds){
    	for(String roleId:roleIds){
    		umsAclRepository.clearPrincipalAcl(roleId, Principal.PRINCIPAL_ROLE);
    		umsUserOrgRoleRelationRepository.clearUserRoleRelation(roleId);
    		delete(roleId);
    	}
    }
    
    public List<UmsOrgRole> findByOrgId(String orgId){
    	return umsOrgRoleRepository.findByOrgId(orgId);
    }
    
    /**
     * 删除用户与角色关系
     * @param roleId
     * @param userIds
     */
    public void clearUserRoleRelation(String roleId, List<String> userIds){
    	if (userIds == null || userIds.size() == 0) {
            return;
        }else{
        	umsUserOrgRoleRelationRepository.clearUserRoleRelation(roleId, userIds);
        }
    }
    
    /**
     * 根据角色id获取该角色下的所有用户
     * @param roleId
     * @param nickname
     * @param username
     * @param page
     * @return
     */
    public Page<UmsUser> findUserByRoleId(String roleId, String nickname, String username, Pageable page){
    	return umsUserOrgRoleRelationRepository.findUserByRoleId(roleId, nickname, username, page);
    }
    
    /**
     * 根据角色id获取该角色下以外的所有用户
     * @param roleId
     * @param nickname
     * @param username
     * @param page
     * @return
     */
    public Page<UmsUser> findNotUserByRoleId(String roleId, String nickname, String username, Pageable page){
    	return umsUserOrgRoleRelationRepository.findNotUserByRoleId(roleId, nickname, username, page);
    }
    
    public Page<UmsUser> findUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page){
    	return umsUserOrgRoleRelationRepository.findUserByRoleIdAndNameAndSn(roleId, name, sn, page);
    }
    
    public Page<UmsUser> findNotUserByRoleIdAndNameAndSn(String roleId, String name, String sn, Pageable page){
    	return umsUserOrgRoleRelationRepository.findNotUserByRoleIdAndNameAndSn(roleId, name, sn, page);
    }
    
    public void deleteByOrgIdIn(String[] orgIds){
    	umsOrgRoleRepository.deleteByOrgIdIn(orgIds);
    }
}